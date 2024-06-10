package model.entity.seletores;

import java.time.LocalDate;

public class PessoaSeletor extends BaseSeletor {
	
	private String nomePessoa;
	private String nomePais;
	private String sexoPessoa;
	private LocalDate dataInicioSelecao;
	private LocalDate dataFinalSelecao;
	
	public PessoaSeletor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PessoaSeletor(String nomePessoa, String nomePais, String sexoPessoa, LocalDate dataInicioSelecao,
			LocalDate dataFinalSelecao) {
		super();
		this.nomePessoa = nomePessoa;
		this.nomePais = nomePais;
		this.sexoPessoa = sexoPessoa;
		this.dataInicioSelecao = dataInicioSelecao;
		this.dataFinalSelecao = dataFinalSelecao;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getNomePais() {
		return nomePais;
	}

	public void setNomePais(String nomePais) {
		this.nomePais = nomePais;
	}

	public String getSexoPessoa() {
		return sexoPessoa;
	}

	public void setSexoPessoa(String sexoPessoa) {
		this.sexoPessoa = sexoPessoa;
	}

	public LocalDate getDataInicioSelecao() {
		return dataInicioSelecao;
	}

	public void setDataInicioSelecao(LocalDate dataInicioSelecao) {
		this.dataInicioSelecao = dataInicioSelecao;
	}

	public LocalDate getDataFinalSelecao() {
		return dataFinalSelecao;
	}

	public void setDataFinalSelecao(LocalDate dataFinalSelecao) {
		this.dataFinalSelecao = dataFinalSelecao;
	}

	
	
}
