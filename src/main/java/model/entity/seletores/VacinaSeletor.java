package model.entity.seletores;

import java.time.LocalDate;

public class VacinaSeletor extends BaseSeletor{
	
	private String nomePais; 
	private String nomePesquisador;
	private String nomeVacina;
	private LocalDate dataInicioSelecao;
	private LocalDate dataFinalSelecao;
	
	public boolean temFiltro() {
		return (this.nomeVacina != null && this.nomeVacina.trim().length() > 0)
				|| (this.nomePais != null && this.nomePais.trim().length() > 0)
				|| (this.nomePesquisador != null && this.nomePesquisador.trim().length() > 0)
				|| (this.dataInicioSelecao != null)
				|| (this.dataFinalSelecao != null);
	}
	
	public VacinaSeletor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VacinaSeletor(String nomePais, String nomePesquisador, String nomeVacina, LocalDate dataInicioSelecao, LocalDate dataFinalSelecao) {
		super();
		this.nomePais = nomePais;
		this.nomePesquisador = nomePesquisador;
		this.nomeVacina = nomeVacina;
		this.dataInicioSelecao = dataInicioSelecao;
		this.dataFinalSelecao = dataFinalSelecao;
	}

	public String getNomePais() {
		return nomePais;
	}

	public void setNomePais(String nomePais) {
		this.nomePais = nomePais;
	}

	public String getNomePesquisador() {
		return nomePesquisador;
	}

	public void setNomePesquisador(String nomePesquisador) {
		this.nomePesquisador = nomePesquisador;
	}

	public String getNomeVacina() {
		return nomeVacina;
	}

	public void setNomeVacina(String nomeVacina) {
		this.nomeVacina = nomeVacina;
	}

	public LocalDate getDataInicioSelecao () {
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
