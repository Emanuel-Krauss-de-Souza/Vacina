package model.entity.carros;

import java.time.LocalDate;

public class Montadora {
	
	private Integer id;
	private String nomeMontadora;
	private String paisFundacao;
	private LocalDate dataFundacao;
	private String nomePresidente;
	
	public Montadora() {
		super();
		
	}

	public Montadora(Integer id, String nomeMontadora, String paisFundacao, LocalDate dataFundacao,
			String nomePresidente) {
		super();
		this.id = id;
		this.nomeMontadora = nomeMontadora;
		this.paisFundacao = paisFundacao;
		this.dataFundacao = dataFundacao;
		this.nomePresidente = nomePresidente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeMontadora() {
		return nomeMontadora;
	}

	public void setNomeMontadora(String nomeMontadora) {
		this.nomeMontadora = nomeMontadora;
	}

	public String getPaisFundacao() {
		return paisFundacao;
	}

	public void setPaisFundacao(String paisFundacao) {
		this.paisFundacao = paisFundacao;
	}

	public LocalDate getDataFundacao() {
		return dataFundacao;
	}

	public void setDataFundacao(LocalDate dataFundacao) {
		this.dataFundacao = dataFundacao;
	}

	public String getNomePresidente() {
		return nomePresidente;
	}

	public void setNomePresidente(String nomePresidente) {
		this.nomePresidente = nomePresidente;
	}
	
	
	
}
