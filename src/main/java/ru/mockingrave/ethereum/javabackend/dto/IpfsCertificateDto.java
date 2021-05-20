package ru.mockingrave.ethereum.javabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mockingrave.ethereum.javabackend.elasticsearch.model.substruct.Qualification;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IpfsCertificateDto implements Serializable {

    List<Qualification> qualifications;

    String accreditorName;
    String certifierName;

    String certifierIpfsHash;

    String ownerName;

    String contacts;

    String receivingDate;

    String note;
}
