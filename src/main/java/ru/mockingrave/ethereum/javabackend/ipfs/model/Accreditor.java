package ru.mockingrave.ethereum.javabackend.ipfs.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class Accreditor implements Serializable {

    String sourceAccreditorName;
    String sourceAccreditorIpfsHash;
    String sourceAccreditorEthAddress;

    String ethAddress;
    String companyName;
    String departmentName;
    String address;
    String activationDate;
}
