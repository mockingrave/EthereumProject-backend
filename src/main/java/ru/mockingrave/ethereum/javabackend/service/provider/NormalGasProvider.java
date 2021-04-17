package ru.mockingrave.ethereum.javabackend.service.provider;

import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

public class NormalGasProvider implements ContractGasProvider {
    private BigInteger gasPrice;
    private BigInteger gasLimit;

    public void setGasPrice(BigInteger gasPrice) {
        this.gasPrice = gasPrice;
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
    }

    public BigInteger getGasPrice(String contractFunc) {
        return this.gasPrice;
    }

    public BigInteger getGasPrice() {
        return this.gasPrice;
    }

    public BigInteger getGasLimit(String contractFunc) {
        return this.gasLimit;
    }

    public BigInteger getGasLimit() {
        return this.gasLimit;
    }
}
