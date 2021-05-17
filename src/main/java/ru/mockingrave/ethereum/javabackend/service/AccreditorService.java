package ru.mockingrave.ethereum.javabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;
import ru.mockingrave.ethereum.javabackend.dto.EthAuthorityDto;
import ru.mockingrave.ethereum.javabackend.dto.IpfsAuthorityDto;
import ru.mockingrave.ethereum.javabackend.dto.substruct.AuthenticationData;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Primary
public class AccreditorService extends GethService {

    @Value("${account.viewing.wallet}")
    protected String ACC_WALL;
    @Value("${account.viewing.password}")
    protected String ACC_PASS;

    @Autowired
    GethContractService gethContractService;
    @Autowired
    IpfsService ipfsService;

    public EthAuthorityDto createAccreditor(IpfsAuthorityDto newDto, String walletName, String password) {

        var sourceHash = newDto.getSourceAccreditorIpfsHash();

        //sourceEthAddress == (sourceEthAddress by IPFS) ?
        if (isRealHash(sourceHash, new AuthenticationData(walletName, password)))
            return null;
        //load contract
        var accreditorContract = gethContractService.accreditorContractLoad(walletName, password);

        var sourceAccreditorDto = getIpfsAccreditor(sourceHash);

        newDto.setSourceAccreditorEthAddress(sourceAccreditorDto.getEthAddress());
        newDto.setSourceAccreditorName(sourceAccreditorDto.getCompanyName());
        newDto.setActivationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

        //save in IPFS
        var newIpfsHash = ipfsService.serializableToIpfs(newDto);

        //save in Ethereum
        try {
            var credentials = WalletUtils.loadCredentials(password, KEY_PATH + walletName);
            accreditorContract.createAccreditor(sourceHash, newDto.getEthAddress(), newIpfsHash, credentials);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getEthAccreditor(newIpfsHash);
    }

    public EthAuthorityDto updateAccreditor(String oldHash, IpfsAuthorityDto newData, String walletName, String password) {

        var accreditorContract = gethContractService.accreditorContractLoad(walletName, password);

        var oldData = getIpfsAccreditor(oldHash);

        newData.setSourceAccreditorEthAddress(oldData.getSourceAccreditorEthAddress());
        if (newData.getSourceAccreditorName().isEmpty())
            newData.setSourceAccreditorName(oldData.getSourceAccreditorName());
        if (newData.getSourceAccreditorIpfsHash().isEmpty())
            newData.setSourceAccreditorIpfsHash(oldData.getSourceAccreditorIpfsHash());

        newData.setEthAddress(oldData.getEthAddress());
        if (newData.getCompanyName().isEmpty()) newData.setCompanyName(oldData.getCompanyName());
        if (newData.getDepartmentName().isEmpty()) newData.setDepartmentName(oldData.getDepartmentName());
        if (newData.getAddress().isEmpty()) newData.setAddress(oldData.getAddress());
        newData.setActivationDate(oldData.getActivationDate());

        var newIpfsHash = ipfsService.serializableToIpfs(newData);

        try {
            var credentials = WalletUtils.loadCredentials(password, KEY_PATH + walletName);
            accreditorContract.updateAccreditor(newData.getSourceAccreditorIpfsHash(), oldHash, newIpfsHash, credentials);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getEthAccreditor(newIpfsHash);
    }

    public boolean deleteAccreditor(String deleteIpfsHash, String sourceIpfsHash, String walletName, String password) {

        var accreditorContract = gethContractService.accreditorContractLoad(walletName, password);

        try {
            var credentials = WalletUtils.loadCredentials(password, KEY_PATH + walletName);
            accreditorContract.deleteAccreditor(sourceIpfsHash, deleteIpfsHash, credentials);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return !checkEthAccreditor(deleteIpfsHash);
    }

    public IpfsAuthorityDto getIpfsAccreditor(String ipfsHash) {

        return (IpfsAuthorityDto) ipfsService.serializableFromIpfs(ipfsHash);

    }

    public EthAuthorityDto getEthAccreditor(String ipfsHash) {

        var accreditorContract = gethContractService.accreditorContractLoad(ACC_WALL, ACC_PASS);
        var response = new EthAuthorityDto();
        try {
            response.setIpfsHash(ipfsHash);
            response.setEthAddress(accreditorContract.getAccreditorAddress(ipfsHash).send());
            response.setSourceAccreditorIpfsHash(accreditorContract.getAccreditorSource(ipfsHash).send());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean checkEthAccreditor(String ipfsHash) {

        var accreditorContract = gethContractService.accreditorContractLoad(ACC_WALL, ACC_PASS);
        try {
            return accreditorContract.checkAccreditor(ipfsHash).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isRealHash(String ipfsHash, AuthenticationData a) {
        try {
            var password=a.getPassword();
            if (password == null) password ="";
            var credentials = WalletUtils.loadCredentials(password, KEY_PATH + a.getWalletName());
            var sourceAccreditorData = getIpfsAccreditor(ipfsHash);
            return credentials.getAddress() == sourceAccreditorData.getEthAddress();

        } catch (IOException | CipherException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isRealHash(String ipfsHash, String ethAddress) {
        var sourceAccreditorData = getIpfsAccreditor(ipfsHash);
        return ethAddress == sourceAccreditorData.getEthAddress();
    }


}
