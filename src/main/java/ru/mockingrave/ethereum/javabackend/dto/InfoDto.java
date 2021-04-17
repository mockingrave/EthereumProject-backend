package ru.mockingrave.ethereum.javabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class InfoDto {

    Map<String, String> information;
}
