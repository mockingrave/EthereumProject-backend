package ru.mockingrave.ethereum.javabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IpfsAuthorityDto implements Serializable {

    @ApiModelProperty(name = "(Источник полномочий) Имя компании")
    String sourceAccreditorName;

    @ApiModelProperty(name = "(Источник полномочий) Hash для доступа к данным в IPFS")
    String sourceAccreditorIpfsHash;

    @ApiModelProperty(name = "(Источник полномочий) Адрес в сети Ethereum")
    String sourceAccreditorEthAddress;

    @ApiModelProperty(name = "Адрес в сети Ethereum")
    String ethAddress;

    @ApiModelProperty(name = "Имя компании")
    String companyName;

    @ApiModelProperty(name = "Имя подразделения, отвечающего аккаунт")
    String departmentName;

    @ApiModelProperty(name = "Физический адрес")
    String address;

    @ApiModelProperty(name = "Дата начала деятельности", readOnly = true, example = "controlled by the system")
    String activationDate;
}
