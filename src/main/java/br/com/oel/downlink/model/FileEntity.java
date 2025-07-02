package br.com.oel.downlink.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {

    @Id
    private String id; // id

    @Column(nullable = false)
    private String fileName; //nome do arquivo

    @Column(nullable = false)
    private String contentType; // tipo de midia


    @Column(nullable = false)
    private long size;

    // A anotação LOB permite armazenar uma quantidade alta de dados binarios (byte)
    @Lob
    @Column(nullable = false)
    private byte[] data;

}
