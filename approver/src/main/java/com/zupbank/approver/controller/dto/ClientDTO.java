package com.zupbank.approver.controller.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {
	
public ClientDTO() {}

	private Long id;
	private String name;


	private String sobrenome;
	private String email;
	private String cnh;
	private LocalDate dataNascimento;
	
	private String cep;
	private String rua;
	private String bairro;
	private String complemento;
	private String cidade;
	private String estado;
	
	//Non-Owner da relação
//	@OneToOne(mappedBy = "client")
//	private Proposal proposal;	
}
