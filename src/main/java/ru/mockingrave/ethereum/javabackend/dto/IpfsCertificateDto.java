package ru.mockingrave.ethereum.javabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mockingrave.ethereum.javabackend.dto.substruct.AuthorityDataPack;
import ru.mockingrave.ethereum.javabackend.dto.substruct.Qualification;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IpfsCertificateDto {

    List<Qualification> qualifications;

    AuthorityDataPack accreditor;
    AuthorityDataPack certifier;

    String ownerName;
    String receivingDate;

    String note;
}
