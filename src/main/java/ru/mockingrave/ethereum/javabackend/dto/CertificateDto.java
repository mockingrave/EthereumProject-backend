package ru.mockingrave.ethereum.javabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mockingrave.ethereum.javabackend.elasticsearch.model.Certificate;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CertificateDto {

    IpfsCertificateDto mainInfo;

    String ipfsHash;

    public CertificateDto(Certificate model) {
        this.mainInfo = new IpfsCertificateDto(
                model.getQualifications(),
                null,
                null,
                null,
                model.getOwnerName(),
                model.getContacts(),
                model.getReceivingDate(),
                model.getNote());
        this.ipfsHash = model.getIpfsHash();
    }
}
