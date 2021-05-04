package ru.mockingrave.ethereum.javabackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mockingrave.ethereum.javabackend.dto.InfoDto;
import ru.mockingrave.ethereum.javabackend.dto.TransactionDto;
import ru.mockingrave.ethereum.javabackend.dto.UserDto;
import ru.mockingrave.ethereum.javabackend.service.GethService;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class GethController {

    private final GethService gethService;

    @GetMapping("/test")
    public ResponseEntity<InfoDto> checkConnection(@RequestParam("info") String info) {
        return ResponseEntity.ok()
                .body(gethService.connectionTest());
    }

    @PostMapping("/account")
    public ResponseEntity<InfoDto> createAccount(@RequestBody UserDto user) {
        return ResponseEntity.ok()
                .body(gethService.createNewAccount(user.getPassword()));
    }

    @PostMapping("/account/check")
    public ResponseEntity<InfoDto> checkAccount(@RequestBody UserDto user) {
        return ResponseEntity.ok()
                .body(gethService.checkAccount(user.getName(), user.getPassword()));
    }

    @PostMapping("/account/transfer")
    public ResponseEntity<InfoDto> transferMoney(@RequestBody TransactionDto dto) {
        return ResponseEntity.ok()
                .body(gethService.transferMoney(
                        dto.getName(),
                        dto.getPassword(),
                        dto.getAddressTo(),
                        dto.getValue(),
                        dto.getGasLimit(),
                        dto.getGasPrice()));
    }

    @PostMapping("/contract")
    public ResponseEntity<InfoDto> createContract(@RequestBody TransactionDto dto) {
        return ResponseEntity.ok()
                .body(gethService.contractDeploy(dto.getName(), dto.getPassword(), dto.getGasLimit(), dto.getGasPrice()));
    }

    @GetMapping("/testIpfs")
    public ResponseEntity<InfoDto> checkIpfs() {
        return ResponseEntity.ok()
                .body(gethService.testIfs());
    }}
