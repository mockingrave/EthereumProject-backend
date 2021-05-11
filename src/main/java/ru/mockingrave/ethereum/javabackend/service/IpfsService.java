package ru.mockingrave.ethereum.javabackend.service;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import org.springframework.stereotype.Service;
import ru.mockingrave.ethereum.javabackend.dto.InfoDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class IpfsService {

    protected int IPFS_PORT = 5001;

    IPFS ipfs = new IPFS("localhost", IPFS_PORT);

    public InfoDto byteArrayToIpfs() {
        try {
            NamedStreamable.ByteArrayWrapper bytearray =
                    new NamedStreamable.ByteArrayWrapper(
                            ("Hello, Bytes! " +
                                    LocalDateTime.now().format(
                                            DateTimeFormatter.ofPattern("dd.MM - HH:mm:ss"))
                            ).getBytes());
            MerkleNode response = ipfs.add(bytearray).get(0);

            return new InfoDto(Map.of("hash", response.hash.toBase58()));

        } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
        }
    }

    public InfoDto byteArrayFromIpfs(String hash) {

        try {
            Multihash multihash = Multihash.fromBase58(hash);
            byte[] content = ipfs.cat(multihash);
            return new InfoDto(Map.of("hash", hash, "content", new String(content)));

        } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
        }
    }

    public InfoDto objectToIpfs(Serializable object) {
        try {

            var bytesOut = new ByteArrayOutputStream();
            var objectsOut = new ObjectOutputStream(bytesOut);

            objectsOut.writeObject(object);
            objectsOut.flush();
            objectsOut.close();

            NamedStreamable.InputStreamWrapper inputStreamWrapper =
                    new NamedStreamable.InputStreamWrapper(new ByteArrayInputStream(bytesOut.toByteArray()));

            MerkleNode response = ipfs.add(inputStreamWrapper).get(0);

            return new InfoDto(Map.of("hash", response.hash.toBase58()));

        } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
        }
    }

    public InfoDto objectFromIpfs(String hash) {

        UserDto user = (UserDto) serializableFromIpfs(hash);
        return new InfoDto(Map.of("name", user.getName(), "password", user.getPassword()));
    }


    public Map<Multihash, Object> hostedContent(IPFS.PinType filter){

        try {
            return ipfs.pin.ls(filter);
        } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
        }
    }

    private Serializable serializableFromIpfs(String hash) {

        try {
            var multihash = Multihash.fromBase58(hash);
            var inputStream = ipfs.catStream(multihash);

            var objectsIn = new ObjectInputStream(inputStream);
            var response  = (Serializable) objectsIn.readObject();
            objectsIn.close();

            return response;

        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException("Error whilst communicating with the IPFS node", ex);
        }
    }

}
