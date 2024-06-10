package model.entity;

import java.time.LocalDate;

public class Pessoa {
	public static final int PESQUISADOR = 1;
	public static final int VOLUNTARIO = 2;
	public static final int PUBLICO_GERAL = 3;
	
	private int id;
	private String nome;
	private LocalDate dataNascimento;
	private char sexo;
	private String cpf;
	private int tipoPessoa;
	private Pais pais;
//	private List<Vacinacao> vacinacoes;     
		
	public Pessoa() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pessoa(int id, String nome, LocalDate dataNascimento, char sexo,
			String cpf, int tipoPessoa, Pais pais) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.cpf = cpf;
		this.tipoPessoa = tipoPessoa;
		this.pais = pais;
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public int getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(int tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	
	
}
