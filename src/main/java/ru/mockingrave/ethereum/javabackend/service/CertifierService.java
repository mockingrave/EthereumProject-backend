package ru.mockingrave.ethereum.javabackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CertifierService extends GethService {

    @Value("${account.viewing.wallet}")
    protected String ACC_WALL;
    @Value("${account.viewing.password}")
    protected String ACC_PASS;

    @Autowired
    GethContractService gethContractService;
    @Autowired
    IpfsService ipfsService;


}
