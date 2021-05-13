package ru.mockingrave.ethereum.javabackend.ipfs.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mockingrave.ethereum.javabackend.dto.substruct.AuthorityDataPack;
import ru.mockingrave.ethereum.javabackend.dto.substruct.Qualification;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class Certificate implements Serializable {

    List<Qualification> qualifications;

    AuthorityDataPack accreditor;
    AuthorityDataPack certifier;

    String ownerName;
    String receivingDate;

    String note;
}
