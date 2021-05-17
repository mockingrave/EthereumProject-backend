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
    private final AccreditorService aService;

    @GetMapping("/{ipfsHash}")
    public ResponseEntity<IpfsAuthorityDto> getAccreditor(@PathVariable String ipfsHash) {
        return ResponseEntity.ok()
                .body(aService.getIpfsAccreditor(ipfsHash));
    }

    @GetMapping("/eth/{ipfsHash}")
    public ResponseEntity<EthAuthorityDto> getEthAccreditor(@PathVariable String ipfsHash) {

        return ResponseEntity.ok()
                .body(aService.getEthAccreditor(ipfsHash));
    }

    @GetMapping("/check/{ipfsHash}")
    public ResponseEntity<String> checkAccreditor(@PathVariable String ipfsHash) {
        String response = "The Accreditor does not exist.";
        if (aService.checkEthAccreditor(ipfsHash))
            response = "The Accreditor is valid.";
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    public ResponseEntity<EthAuthorityDto> createAccreditor
            (@RequestBody IpfsAuthorityDto dto, String walletName, String password) {
        return ResponseEntity.ok()
                .body(aService.createAccreditor(dto, walletName, password));
    }

    @PutMapping("/{oldIpfsHash}")
    public ResponseEntity<EthAuthorityDto> updateAccreditor
            (@PathVariable String oldIpfsHash, @RequestBody IpfsAuthorityDto dto, String walletName, String password) {
        return ResponseEntity.ok()
                .body(aService.updateAccreditor(oldIpfsHash, dto, walletName, password));
    }

    @DeleteMapping("/{deleteIpfsHash}")
    public ResponseEntity<String> deleteAccreditor
            (@PathVariable String deleteIpfsHash, @RequestBody String sourceIpfsHash, String walletName, String password) {
        String response = "Failed";
        if (aService.deleteAccreditor(deleteIpfsHash, sourceIpfsHash, walletName, password))
            response = "Success";
        return ResponseEntity.ok()
                .body(response);
    }
}
