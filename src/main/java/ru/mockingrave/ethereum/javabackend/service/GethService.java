package ru.mockingrave.ethereum.javabackend.service;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;
import ru.mockingrave.ethereum.javabackend.dto.InfoDto;
import ru.mockingrave.ethereum.javabackend.model.DocumentRegistry;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Optional;

@Service
public class GethService {

    @Value("${keystore.path}")
    protected String KEY_PATH;

    @Value("${ipfs.port}")
    protected int IPFS_PORT;

    @Autowired
    protected Web3j web3j;

    IPFS ipfs = new IPFS("localhost", IPFS_PORT);


    public InfoDto connectionTest() {
        var result = new HashMap<String, String>();
        try {
            // web3_clientVersion returns the current client version.
            var clientVersion = web3j.web3ClientVersion().send();
            result.put("Client version", clientVersion.getWeb3ClientVersion());

            //eth_blockNumber returns the number of most recent block.
            var blockNumber = web3j.ethBlockNumber().send();
            result.put("Block number", blockNumber.getBlockNumber().toString());

            //eth_gasPrice, returns the current price per gas in wei.
            var gasPrice = web3j.ethGasPrice().send();
            result.put("Gas price", gasPrice.getGasPrice() + " eth");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new InfoDto(result);
    }

    public InfoDto createNewAccount(String password) {
        var result = new HashMap<String, String>();
        try {
            String walletName = WalletUtils.generateFullNewWalletFile(password, new File(KEY_PATH));
            result.put("Wallet name", walletName);

            Credentials credentials = WalletUtils.loadCredentials(password, KEY_PATH + walletName);
            result.put("Account address", credentials.getAddress());

            //TODO ОБЯЗАТЕЛЬНО УДАЛИТЬ. (необходимо было для тестирования)
            String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
            result.put("(debug info) Private key", privateKey);

        } catch (NoSuchAlgorithmException | IOException | CipherException | InvalidAlgorithmParameterException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return new InfoDto(result);
    }

    public InfoDto checkAccount(String walletName, String password) {
        var result = new HashMap<String, String>();
        try {
            String source = KEY_PATH + walletName;
            Credentials credentials = WalletUtils.loadCredentials(password, source);

            String balance = Convert.fromWei
                    (web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                            .send().getBalance().toString(), Convert.Unit.ETHER)
                    .toString();

            result.put("Balance", balance);
            result.put("Wallet name", walletName);
            result.put("Account address", credentials.getAddress());

        } catch (IOException | CipherException e) {
            e.printStackTrace();
            result.put("Error", "Invalid name or password.");
        }
        return new InfoDto(result);
    }

    public InfoDto transferMoney(String walletName, String password, String addressTo, String value, String gasLimit, String gasPrice) {
        var result = new HashMap<String, String>();

        try {
            var source = KEY_PATH + walletName;
            var credentials = WalletUtils.loadCredentials(password, source);

            result.put("Balance 'From' (before)", getBalance(credentials.getAddress()));
            result.put("Balance 'To' (before)", getBalance(addressTo));

            BigInteger bIntValue = Convert.toWei(value, Convert.Unit.ETHER).toBigInteger();
            BigInteger bIntGasLimit = BigInteger.valueOf(Long.parseLong(gasLimit));
            BigInteger bIntGasPrice = Convert.toWei(gasPrice, Convert.Unit.ETHER).toBigInteger();

            //TODO: попробовать через менеджер транзакций
            var rawTransactionManager = new RawTransactionManager(web3j, credentials, Long.parseLong(web3j.netVersion().send().getNetVersion()));

            var ethSendTransaction = rawTransactionManager.sendTransaction(bIntGasPrice, bIntGasLimit, addressTo, "", bIntValue);

            var transactionHash = ethSendTransaction.getTransactionHash();
            result.put("transactionHash", transactionHash);


            //Wait for transaction to be mined
            Optional<TransactionReceipt> transactionReceipt = null;
            do {
                System.out.println("checking if transaction " + transactionHash + " is mined...");
                EthGetTransactionReceipt ethGetTransactionReceiptResp = web3j.ethGetTransactionReceipt(transactionHash).send();
                transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
                try {
                    Thread.sleep(500); // Wait 1 sec
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (!transactionReceipt.isPresent());

            result.put("Balance 'From' (after)", getBalance(credentials.getAddress()));
            result.put("Balance 'To' (after)", getBalance(addressTo));

            result.put("address FROM", credentials.getAddress());
            result.put("address TO", addressTo);

        } catch (IOException | CipherException e) {
            e.printStackTrace();
            result.put("Error", "Invalid name or password.");
        }
        return new InfoDto(result);
    }


    public InfoDto contractDeploy(String walletName, String password, String gasLimit, String gasPrice) {
        var result = new HashMap<String, String>();
        DocumentRegistry registryContract = null;
        BigInteger bIntGasLimit = BigInteger.valueOf(Long.parseLong(gasLimit));
        BigInteger bIntGasPrice = Convert.toWei(gasPrice, Convert.Unit.ETHER).toBigInteger();

        try {
            Credentials credentials = WalletUtils.loadCredentials(password, KEY_PATH + walletName);

            var transactionManager = new RawTransactionManager(
                    web3j, credentials, Long.parseLong(web3j.netVersion().send().getNetVersion()));


                registryContract = DocumentRegistry.deploy(web3j, transactionManager, new StaticGasProvider(bIntGasPrice,bIntGasLimit)).send();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (registryContract != null) {
            result.put("Contract address", registryContract.getContractAddress());
        }
        return new InfoDto(result);
    }

    private String getBalance(String address) {
        try {
            return Convert.fromWei
                    (web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST)
                            .send().getBalance().toString(), Convert.Unit.ETHER)
                    .toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
