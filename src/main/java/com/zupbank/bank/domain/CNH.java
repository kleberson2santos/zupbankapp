package com.zupbank.bank.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cnh")
public class CNH {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "cnh_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Client client;

    //    @OneToMany(mappedBy = "cnh", cascade = CascadeType.ALL)
    @ElementCollection
    private Set<CnhFile> files;

    public String getClientName() {
        if (getClient() != null) {
            return getClient().getNome();
        }
        return null;
    }

    @Getter
    @Setter
    @Embeddable
    public static class CnhFile {
        private String contentType;
        private String nomeArquivo;
        private String descricao;
        private Long tamanho;

//        @ManyToOne
//        @JoinColumn(nullable = false)
//        private CNH cnh;

        public CnhFile() {
        }
    }
}
