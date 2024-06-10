package model.entity;

import java.time.LocalDate;

public class Vacina {

	public static final int ESTAGIO_INICIAL = 1;
	public static final int ESTAGIO_TESTES = 2;
	public static final int ESTAGIO_APLICACAO_MASSA = 3;
	
	
	private int id;
	private String nome;
	private Pessoa pesquisadorResponsavel;
	private int estagioPesquisa;
	private LocalDate dataInicio;
	private Pais pais;
	private double media;
	
	public Vacina() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vacina(int id, String nome, Pessoa pesquisadorResponsavel, int estagioPesquisa,
			LocalDate dataInicio, Pais pais, double media) {
		super();
		this.id = id;
		this.nome = nome;
		this.pesquisadorResponsavel = pesquisadorResponsavel;
		this.estagioPesquisa = estagioPesquisa;
		this.dataInicio = dataInicio;
		this.pais = pais;
		this.media= media;
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


	public Pessoa getPesquisadorResponsavel() {
		return pesquisadorResponsavel;
	}

	public void setPesquisadorResponsavel(Pessoa pesquisadorResponsavel) {
		this.pesquisadorResponsavel = pesquisadorResponsavel;
	}

	public int getEstagioPesquisa() {
		return estagioPesquisa;
	}

	public void setEstagioPesquisa(int estagioPesquisa) {
		this.estagioPesquisa = estagioPesquisa;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

}
