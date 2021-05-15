package ru.mockingrave.ethereum.javabackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.mockingrave.ethereum.javabackend.dto.substruct.AuthenticationData;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EthAccountDto {

    String balance;

    String address;

    AuthenticationData authenticationData;

    public EthAccountDto(){
        authenticationData = new AuthenticationData();
    }

    public void setWallet(String walletName) {
        this.authenticationData.setWalletName(walletName);
    }

    public void setPassword(String password) {
        this.authenticationData.setPassword(password);
    }
}
