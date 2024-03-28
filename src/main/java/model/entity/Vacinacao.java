package model.entity;

import java.time.LocalDate;

public class Vacinacao {
	
	private int id;
	private Pessoa pessoaAplicada;
	private Vacina vacina;
	private LocalDate dataAplicacao;
	private int avaliacao;
	
	public Vacinacao() {
		super();
	}

	public Vacinacao(int id, Pessoa pessoaAplicada, Vacina vacina, LocalDate dataAplicacao, int avaliacao) {
		super();
		this.id = id;
		this.pessoaAplicada = pessoaAplicada;
		this.vacina = vacina;
		this.dataAplicacao = dataAplicacao;
		this.avaliacao = avaliacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pessoa getPessoaAplicada() {
		return pessoaAplicada;
	}

	public void setPessoaAplicada(Pessoa pessoaAplicada) {
		this.pessoaAplicada = pessoaAplicada;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public LocalDate getDataAplicacao() {
		return dataAplicacao;
	}

	public void setDataAplicacao(LocalDate dataAplicacao) {
		this.dataAplicacao = dataAplicacao;
	}

	public int getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(int avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	
	
}
