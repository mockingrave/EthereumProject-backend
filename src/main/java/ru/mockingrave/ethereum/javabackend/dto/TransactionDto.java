package ru.mockingrave.ethereum.javabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDto {

    String name;
    String password;

    String addressTo;

    String value;

    String gasLimit;
    String gasPrice;
}
