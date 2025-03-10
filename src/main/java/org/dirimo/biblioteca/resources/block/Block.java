package org.dirimo.biblioteca.resources.block;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.dirimo.biblioteca.common.BaseEntity;
import java.security.MessageDigest;


@Entity
@Table(name = "Blocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Block extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "HASH")
    private String hash;

    @Column(name = "PREVIOUS_HASH")
    private String previousHash;

    @Column(name = "DATA")
    private String data;

    @JsonIgnore
    @Column(name = "TIMESTAMP")
    private long timestamp;

    public Block(String data, String previousHash) {
    this.data = data;
    this.previousHash = previousHash == null ? "0" : previousHash;
    this.timestamp = System.currentTimeMillis();
    this.hash = calcHash();}

    public String calcHash() {
        String input = previousHash + timestamp + data;
        return applySHA256(input);
    }

    private String applySHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
