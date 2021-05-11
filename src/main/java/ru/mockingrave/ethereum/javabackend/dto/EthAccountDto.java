package ru.mockingrave.ethereum.javabackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EthAccountDto implements Serializable {

    String balance;

    String wallet;

    String address;

    String password;
}
