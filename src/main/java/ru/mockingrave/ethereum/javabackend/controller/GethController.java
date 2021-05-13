package ru.mockingrave.ethereum.javabackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mockingrave.ethereum.javabackend.dto.DeployDto;
import ru.mockingrave.ethereum.javabackend.dto.EthAccountDto;
import ru.mockingrave.ethereum.javabackend.dto.InfoDto;
import ru.mockingrave.ethereum.javabackend.dto.TransactionDto;
import ru.mockingrave.ethereum.javabackend.service.GethContractService;
import ru.mockingrave.ethereum.javabackend.service.GethService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/geth")
public class GethController {

    private final GethService gethService;
    private final GethContractService deployService;


    @GetMapping("/connectionTest")
    public ResponseEntity<InfoDto> checkConnection() {
        return ResponseEntity.ok()
                .body(gethService.connectionTest());
    }

    @PostMapping("/account")
    public ResponseEntity<EthAccountDto> createAccount(@RequestBody EthAccountDto account) {
        return ResponseEntity.ok()
                .body(gethService.createNewAccount(account.getPassword()));
    }

    @PostMapping("/account/check")
    public ResponseEntity<EthAccountDto> checkAccount(@RequestBody EthAccountDto account) {
        return ResponseEntity.ok()
                .body(gethService.checkAccount(account.getWallet(), account.getPassword()));
    }

    @PostMapping("/account/transfer")
    public ResponseEntity<InfoDto> transferMoney(@RequestBody TransactionDto dto) {
        return ResponseEntity.ok()
                .body(gethService.transferMoney(
                        dto.getWalletFrom(),
                        dto.getPassword(),
                        dto.getAddressTo(),
                        dto.getValue(),
                        dto.getGasLimit(),
                        dto.getGasPrice()));
    }

    @PostMapping("/contract/deploy")
    public ResponseEntity<String> deploySystemOfContracts
            (@RequestBody DeployDto dto) {
        String response = "Failed. Something is wrong.";
        if (deployService.createOwnerAndDeploySystem(dto))
            response = "Successfully. Contract addresses added to application.properties";
        return ResponseEntity.ok()
                .body(response);
    }

}
