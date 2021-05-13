package ru.mockingrave.ethereum.javabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IpfsAuthorityDto implements Serializable {

    String sourceAccreditorName;
    String sourceAccreditorIpfsHash;
    String sourceAccreditorEthAddress;

    String ethAddress;
    String companyName;
    String departmentName;
    String address;
    String activationDate;
}
