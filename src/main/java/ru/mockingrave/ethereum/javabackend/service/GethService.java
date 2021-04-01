package ru.mockingrave.ethereum.javabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import ru.mockingrave.ethereum.javabackend.dto.TestDto;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Collections;

@Service
public class GethService {

    @Value("${keystore.path}")
    protected String KEY_PATH;

    @Autowired
    protected Web3j web3j;

    public TestDto connectionTest() {
        try {
            // web3_clientVersion returns the current client version.
            var clientVersion = web3j.web3ClientVersion().send();

            //eth_blockNumber returns the number of most recent block.
            var blockNumber = web3j.ethBlockNumber().send();

            //eth_gasPrice, returns the current price per gas in wei.
            var gasPrice = web3j.ethGasPrice().send();

            return new TestDto(
                    Arrays.asList(
                            ("Client version: " + clientVersion.getWeb3ClientVersion()),
                            ("Block number: " + blockNumber.getBlockNumber()),
                            ("Gas price: " + gasPrice.getGasPrice() + " eth")
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TestDto(Collections.singletonList("Something wrong"));
    }

    public String createNewAccount(String password) {
        try {
            return WalletUtils.generateFullNewWalletFile(password, new File(KEY_PATH));
        } catch (NoSuchAlgorithmException | IOException | CipherException | InvalidAlgorithmParameterException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
}
