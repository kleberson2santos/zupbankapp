package com.zupbank.bank.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cnh")
public class CNH {

    @EqualsAndHashCode.Include
    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY) //TODO: Lazy proxy aqui
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_cnh_client"))
    private Client client;

    @ElementCollection
    @CollectionTable(joinColumns =
    @JoinColumn(name = "cnh_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_cnh_files_cnh")))
    private Set<CnhFile> files;

    public CNH(HashSet<CnhFile> cnhFiles, Client client) {
        this.files = cnhFiles;
        this.client = client;
    }

    public CNH() {
    }

    @Data
    @Embeddable
    public static class CnhFile {
        private String contentType;
        @Column(name = "file_name")
        private String fileName;
        private String description;
        private Long size;

        public CnhFile() {
        }
    }
}
