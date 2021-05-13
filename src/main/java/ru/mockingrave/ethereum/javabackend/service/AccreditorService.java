package ru.mockingrave.ethereum.javabackend.service;

import org.springframework.stereotype.Service;
import ru.mockingrave.ethereum.javabackend.dto.IpfsAuthorityDto;

@Service
public class AccreditorService {

    GethContractService gethContractService;
    IpfsService ipfsService;

    public IpfsAuthorityDto createAccreditor(IpfsAuthorityDto dto, String walletName, String password) {

        var accreditorContract = gethContractService.accreditorContractLoad(walletName, password);

        var ipfsHash = ipfsService.serializableToIpfs(dto);

        accreditorContract.createAccreditor( dto.getSourceAccreditorIpfsHash(), dto.getEthAddress(), ipfsHash);

        return getEthAccreditor(ipfsHash, walletName, password);
    }

    public IpfsAuthorityDto updateAccreditor(String oldHash, IpfsAuthorityDto newData, String walletName, String password) {

        var accreditorContract = gethContractService.accreditorContractLoad(walletName, password);

        var oldData = getIpfsAccreditor(oldHash);

        newData.setSourceAccreditorEthAddress(oldData.getSourceAccreditorEthAddress());
        if (newData.getSourceAccreditorName().isEmpty()) newData.setSourceAccreditorName(oldData.getSourceAccreditorName());
        if (newData.getSourceAccreditorIpfsHash().isEmpty()) newData.setSourceAccreditorIpfsHash(oldData.getSourceAccreditorIpfsHash());

        newData.setEthAddress(oldData.getEthAddress());
        if (newData.getCompanyName().isEmpty()) newData.setCompanyName(oldData.getCompanyName());
        if (newData.getDepartmentName().isEmpty()) newData.setDepartmentName(oldData.getDepartmentName());
        if (newData.getAddress().isEmpty()) newData.setAddress(oldData.getAddress());
        newData.setActivationDate(oldData.getActivationDate());

        var newIpfsHash = ipfsService.serializableToIpfs(newData);

        accreditorContract.updateAccreditor( newData.getSourceAccreditorIpfsHash(), oldHash, newIpfsHash);

        return getEthAccreditor(newIpfsHash, walletName, password);
    }

    public boolean deleteAccreditor(String deleteIpfsHash, String sourceIpfsHash, String walletName, String password) {

        var accreditorContract = gethContractService.accreditorContractLoad(walletName, password);

        accreditorContract.deleteAccreditor(sourceIpfsHash, deleteIpfsHash);

        return checkEthAccreditor(deleteIpfsHash, walletName, password);
    }

    public IpfsAuthorityDto getIpfsAccreditor(String ipfsHash) {

        return (IpfsAuthorityDto) ipfsService.serializableFromIpfs(ipfsHash);

    }

    public IpfsAuthorityDto getEthAccreditor(String ipfsHash, String walletName, String password) {

        var accreditorContract = gethContractService.accreditorContractLoad(walletName, password);
        var response = new IpfsAuthorityDto();
        try {
            response.setEthAddress(accreditorContract.getAccreditorAddress(ipfsHash).send());
            response.setSourceAccreditorIpfsHash(accreditorContract.getAccreditorSource(ipfsHash).send());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean checkEthAccreditor(String ipfsHash, String walletName, String password) {

        var accreditorContract = gethContractService.accreditorContractLoad(walletName, password);
        try {
            return accreditorContract.checkAccreditor(ipfsHash).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
