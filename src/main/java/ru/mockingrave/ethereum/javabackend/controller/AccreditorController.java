package ru.mockingrave.ethereum.javabackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mockingrave.ethereum.javabackend.dto.EthAuthorityDto;
import ru.mockingrave.ethereum.javabackend.dto.IpfsAuthorityDto;
import ru.mockingrave.ethereum.javabackend.service.AccreditorService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accreditor")
public class AccreditorController {

    @Autowired
    private final AccreditorService accreditorService;

    @GetMapping("/{ipfsHash}")
    public ResponseEntity<IpfsAuthorityDto> getAccreditor(@PathVariable String ipfsHash) {
        return ResponseEntity.ok()
                .body(accreditorService.getIpfsAccreditor(ipfsHash));
    }

    @GetMapping("/eth/{ipfsHash}")
    public ResponseEntity<EthAuthorityDto> getEthAccreditor(
            @PathVariable String ipfsHash) {

        return ResponseEntity.ok()
                .body(accreditorService.getEthAccreditor(ipfsHash));
    }

    @PostMapping("/check/{ipfsHash}")
    public ResponseEntity<String> checkAccreditor
            (@PathVariable String ipfsHash) {
        String response = "The Accreditor does not exist.";
        if (accreditorService.checkEthAccreditor(ipfsHash))
            response = "The Accreditor is valid.";
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    public ResponseEntity<EthAuthorityDto> createAccreditor
            (@RequestBody IpfsAuthorityDto dto, String walletName, String password) {
        return ResponseEntity.ok()
                .body(accreditorService.createAccreditor(dto, walletName, password));
    }

    @PutMapping("/{ipfsHash}")
    public ResponseEntity<EthAuthorityDto> updateAccreditor
            (@PathVariable String ipfsHash, @RequestBody IpfsAuthorityDto dto, String walletName, String password) {
        return ResponseEntity.ok()
                .body(accreditorService.updateAccreditor(ipfsHash, dto, walletName, password));
    }

    @DeleteMapping("/{ipfsHash}")
    public ResponseEntity<String> deleteAccreditor
            (@PathVariable String ipfsHash, @RequestBody String sourceIpfsHash, String walletName, String password) {
        String response = "Failed";
        if (accreditorService.deleteAccreditor(ipfsHash, sourceIpfsHash, walletName, password))
            response = "Success";
        return ResponseEntity.ok()
                .body(response);
    }
}
