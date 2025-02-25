package org.dirimo.biblioteca.resources.prototype;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dirimo.biblioteca.common.BaseEntity;
import org.dirimo.biblioteca.resources.prototype.enumerated.PrototypeType;

import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "Prototypes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prototype extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Lob
    @Column(name = "BODY", columnDefinition = "MEDIUMBLOB")
    private byte[] body; //

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private PrototypeType type;

    // Getter personalizzato per ottenere il testo dal BLOB
    public String getBodyAsString() {
        return (body != null) ? new String(body, StandardCharsets.UTF_8) : null;
    }

    // Setter personalizzato per salvare una stringa come BLOB
    public void setBodyFromString(String body) {
        this.body = (body != null) ? body.getBytes(StandardCharsets.UTF_8) : null;
    }
}
