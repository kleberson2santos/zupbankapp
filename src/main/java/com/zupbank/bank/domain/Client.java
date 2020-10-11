package com.zupbank.bank.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

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
