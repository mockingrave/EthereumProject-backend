package ru.mockingrave.ethereum.javabackend.service;

import org.springframework.stereotype.Service;

@Service
public class CertifierService {

    GethContractService gethContractService;
    GethService gethService;
    IpfsService ipfsService;
//
//    public void create(IpfsAuthorityDto dto, String walletName, String password) {
//
//        var certifierContract = gethContractService.certifierContractLoad(walletName, password);
//
//
//
//        var ipfsHash = ipfsService.serializableToIpfs(dto);
//
//    }
}
