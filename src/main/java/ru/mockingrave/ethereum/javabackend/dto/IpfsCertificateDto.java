package ru.mockingrave.ethereum.javabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mockingrave.ethereum.javabackend.dto.substruct.Qualification;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IpfsCertificateDto implements Serializable {

    List<Qualification> qualifications;

    String accreditorName;
    String certifierName;

    String certifierIpfsHash;

    String ownerName;
    String receivingDate;

    String note;
}
