package ru.mockingrave.ethereum.javabackend.dto.auxiliary;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorityDataPack {

    String name;
    String ipfsHash;
    String ethAddress;
}
