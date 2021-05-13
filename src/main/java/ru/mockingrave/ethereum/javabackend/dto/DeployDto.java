package ru.mockingrave.ethereum.javabackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeployDto{

    String companyName;

    String departmentName;

    String address;

    String walletFrom;
    String password;

    String gasLimit;
    String gasPrice;
}
