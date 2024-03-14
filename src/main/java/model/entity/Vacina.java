package model.entity;

import java.time.LocalDate;

public class Vacina {
	
	private int id;
	private String nome;
	private String paisOrigem;
	private int estagio;
	private LocalDate dataInicio;
	private String nomePesquisadorResponsavel;
	
	public Vacina(int id, String nome, String paisOrigem, int estagio, LocalDate dataInicio,
			String nomePesquisadorResponsavel) {
		super();
		this.id = id;
		this.nome = nome;
		this.paisOrigem = paisOrigem;
		this.estagio = estagio;
		this.dataInicio = dataInicio;
		this.nomePesquisadorResponsavel = nomePesquisadorResponsavel;
	}
	
	public Vacina() {
		super();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	public int getEstagio() {
		return estagio;
	}

	public void setEstagio(int estagio) {
		this.estagio = estagio;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getNomePesquisadorResponsavel() {
		return nomePesquisadorResponsavel;
	}

	public void setNomePesquisadorResponsavel(String nomePesquisadorResponsavel) {
		this.nomePesquisadorResponsavel = nomePesquisadorResponsavel;
	}

}
