package ru.mockingrave.ethereum.javabackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mockingrave.ethereum.javabackend.dto.auxiliary.AuthorityDataPack;

@Data
@NoArgsConstructor
public class IpfsAuthorityDto {

    AuthorityDataPack sourceAccreditor;

    String companyName;
    String departmentName;
    String authorityAddress;
}
