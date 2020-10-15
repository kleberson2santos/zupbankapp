package com.zupbank.approver.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDTO {

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

	public ClientDTO() {
	}

	//Non-Owner da relação
//	@OneToOne(mappedBy = "client")
//	private Proposal proposal;	
}
