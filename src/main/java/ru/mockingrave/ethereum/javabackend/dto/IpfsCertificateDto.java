package ru.mockingrave.ethereum.javabackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mockingrave.ethereum.javabackend.dto.auxiliary.AuthorityDataPack;
import ru.mockingrave.ethereum.javabackend.dto.auxiliary.Qualification;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class IpfsCertificateDto implements Serializable {

    List<Qualification> qualifications;

    AuthorityDataPack accreditor;
    AuthorityDataPack certifier;

    String ownerName;
    String receivingDate;

    String note;
}
