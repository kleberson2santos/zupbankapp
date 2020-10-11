package com.zupbank.bank.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_cnh_client"))
    private Client client;

    @ElementCollection
    @CollectionTable(joinColumns =
    @JoinColumn(name = "cnh_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_cnh_files_cnh")))
    private Set<CnhFile> files;

    @Data
    @Embeddable
    public static class CnhFile {
        private String contentType;
        private String nomeArquivo;
        private String descricao;
        private Long tamanho;

        public CnhFile() {
        }
    }
}
