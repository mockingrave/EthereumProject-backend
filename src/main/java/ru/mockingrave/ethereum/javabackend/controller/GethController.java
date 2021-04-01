package ru.mockingrave.ethereum.javabackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mockingrave.ethereum.javabackend.dto.TestDto;
import ru.mockingrave.ethereum.javabackend.service.GethService;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class GethController {

    private final GethService gethService;

    @GetMapping("/test")
    public ResponseEntity<TestDto> checkConnection(@RequestParam("info") String info) {
        return ResponseEntity.ok()
                .body(gethService.connectionTest());
    }

    @PostMapping("/account")
    public ResponseEntity<String> createAccount(@RequestBody String password) {
        return ResponseEntity.ok()
                .body(gethService.createNewAccount(password));
    }
}
