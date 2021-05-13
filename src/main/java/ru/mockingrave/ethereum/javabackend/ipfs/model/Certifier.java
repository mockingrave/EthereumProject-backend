package ru.mockingrave.ethereum.javabackend.ipfs.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mockingrave.ethereum.javabackend.dto.substruct.AuthorityDataPack;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Certifier implements Serializable {

    AuthorityDataPack sourceAccreditor;

    String companyName;

    String departmentName;

    String address;
}
