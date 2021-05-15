package ru.mockingrave.ethereum.javabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CertificateDto {

    IpfsCertificateDto mainInfo;

    String ipfsHash;
}
