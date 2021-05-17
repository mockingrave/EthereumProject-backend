package ru.mockingrave.ethereum.javabackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.WalletUtils;
import ru.mockingrave.ethereum.javabackend.dto.EthAuthorityDto;
import ru.mockingrave.ethereum.javabackend.dto.IpfsAuthorityDto;
import ru.mockingrave.ethereum.javabackend.dto.substruct.AuthenticationData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AccreditorCertifierService extends AccreditorService {

    @Value("${account.viewing.wallet}")
    protected String ACC_WALL;
    @Value("${account.viewing.password}")
    protected String ACC_PASS;

    public EthAuthorityDto createCertifier(IpfsAuthorityDto newDto, String walletName, String password) {

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
            accreditorContract.createCertifier(sourceHash, newDto.getEthAddress(), newIpfsHash, credentials);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getEthCertifier(newIpfsHash);
    }

    public EthAuthorityDto updateCertifier(String oldHash, IpfsAuthorityDto newData, String walletName, String password) {

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
            accreditorContract.updateCertifier(newData.getSourceAccreditorIpfsHash(), oldHash, newIpfsHash, credentials);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getEthCertifier(newIpfsHash);
    }

    public boolean deleteCertifier(String deleteIpfsHash, String sourceIpfsHash, String walletName, String password) {

        var accreditorContract = gethContractService.accreditorContractLoad(walletName, password);

        try {
            var credentials = WalletUtils.loadCredentials(password, KEY_PATH + walletName);
            accreditorContract.deleteCertifier(sourceIpfsHash, deleteIpfsHash, credentials);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return !checkEthCertifier(deleteIpfsHash);
    }

    public IpfsAuthorityDto getIpfsCertifier(String ipfsHash) {

        return (IpfsAuthorityDto) ipfsService.serializableFromIpfs(ipfsHash);
    }

    public EthAuthorityDto getEthCertifier(String ipfsHash) {

        var accreditorContract = gethContractService.accreditorContractLoad(ACC_WALL, ACC_PASS);
        var response = new EthAuthorityDto();
        try {
            response.setIpfsHash(ipfsHash);
            response.setEthAddress(accreditorContract.getCertifierAddress(ipfsHash).send());
            response.setSourceAccreditorIpfsHash(accreditorContract.getCertifierSource(ipfsHash).send());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean checkEthCertifier(String ipfsHash) {

        var accreditorContract = gethContractService.accreditorContractLoad(ACC_WALL, ACC_PASS);
        try {
            return accreditorContract.checkCertifier(ipfsHash).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
