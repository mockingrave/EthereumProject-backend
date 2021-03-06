package ru.mockingrave.ethereum.javabackend.controller;

import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mockingrave.ethereum.javabackend.service.IpfsService;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ipfs")
public class IpfsController {

    private final IpfsService ipfsService;

    @GetMapping("/hostedContent")
    public ResponseEntity<Map<Multihash, Object>> hostedContent(@RequestParam IPFS.PinType type) {
        return ResponseEntity.ok()
                .body(ipfsService.hostedContent(type));
    }

}
