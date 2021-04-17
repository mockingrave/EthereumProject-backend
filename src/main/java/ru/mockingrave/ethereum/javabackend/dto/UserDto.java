package ru.mockingrave.ethereum.javabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    String name;
    String password;
}
