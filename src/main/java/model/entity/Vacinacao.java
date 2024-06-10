package model.entity;

import java.time.LocalDate;

public class Vacinacao {

	private int id;
	private int idPessoa;
	private Vacina vacina;
	private LocalDate dataAplicacao;
	private int avaliacao;
	
	
	public Vacinacao() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vacinacao(int id, int idPessoa, Vacina idVacina, LocalDate dataAplicacao, int avaliacao) {
		super();
		this.id = id;
		this.idPessoa = idPessoa;
		this.vacina = idVacina;
		this.dataAplicacao = dataAplicacao;
		this.avaliacao = avaliacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina idVacina) {
		this.vacina = idVacina;
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
