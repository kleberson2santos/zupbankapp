package com.zupbank.bank.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Client() {
    }
//	private String sobrenome;
//	private String email;
//	private String cnh;
//	private LocalDate dataNascimento;
//	
//	private String cep;
//	private String rua;
//	private String bairro;
//	private String complemento;
//	private String cidade;
//	private String estado;

}
