package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.Pais;
import model.entity.Pessoa;
import model.entity.seletores.PessoaSeletor;

public class PessoaRepository {


	public Pessoa salvar(Pessoa novaPessoa) {
		String query = "INSERT INTO pessoa (id_pais, nome, data_nascimento, sexo, "
				+ "cpf, tipo_pessoa) VALUES (?,?,?,?,?,?)";
		Connection conn = Banco.getConnection();
		PreparedStatement psmt = Banco.getPreparedStatementWithPk(conn, query);
		


		try {
			
			psmt.setInt(1, novaPessoa.getPais().getId());
			psmt.setString(2, novaPessoa.getNome());
			psmt.setDate(3, Date.valueOf(novaPessoa.getDataNascimento()));
			psmt.setString(4, novaPessoa.getSexo() + "");
			psmt.setString(5, novaPessoa.getCpf());
			psmt.setInt(6, novaPessoa.getTipoPessoa());

			
			psmt.execute();
			ResultSet resultado = psmt.getGeneratedKeys();
			
			if (resultado.next()) {
				novaPessoa.setId(resultado.getInt(1));
				
			}
			
		} catch (SQLException erro) {
			System.out.println("Erro ao salvar a nova Pessoa");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(psmt);
			Banco.closeConnection(conn);
		}
		
		return novaPessoa;
		
	}

	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM pessoa WHERE id = " + id;
		try {
			if (stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir pessoa");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}
	
	public boolean alterar(Pessoa pessoaEditada) {
		boolean alterou = false;
		String query = " UPDATE controle_vacinas.pessoa "
				     + " SET id_pais=?, nome=?, cpf=?, sexo=?, data_nascimento=?, tipo_pessoa=? "
				     + " WHERE id=? ";
		Connection conn = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			stmt.setInt(1, pessoaEditada.getPais().getId());
			stmt.setString(2, pessoaEditada.getNome());
			stmt.setString(3, pessoaEditada.getCpf());
			stmt.setString(4, pessoaEditada.getSexo() + "");
			stmt.setDate(5, Date.valueOf(pessoaEditada.getDataNascimento()));
			stmt.setInt(6, pessoaEditada.getTipoPessoa());
			
			stmt.setInt(7, pessoaEditada.getId());
			alterou = stmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar pessoa");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}
	
	public Pessoa consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		Pessoa pessoa = null;
		ResultSet resultado = null;
		String query = " SELECT * FROM pessoa WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			
			PaisRepository paisRepository = new PaisRepository();
			
			if(resultado.next()){
				pessoa = new Pessoa();
				pessoa.setId(resultado.getInt("ID"));
				pessoa.setNome(resultado.getString("NOME"));
				pessoa.setCpf(resultado.getString("CPF"));
				pessoa.setSexo(resultado.getString("SEXO").charAt(0));
				pessoa.setDataNascimento(resultado.getDate("DATA_NASCIMENTO").toLocalDate()); 
				pessoa.setTipoPessoa(resultado.getInt("TIPO_PESSOA"));
				
				Pais pais = paisRepository.consultarPorId(resultado.getInt("ID_PAIS"));
				pessoa.setPais(pais);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar pessoa com o id: " + id);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pessoa;
	}

	public ArrayList<Pessoa> consultarTodos() {
		ArrayList<Pessoa> pessoas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM pessoa";
		
		try{
			resultado = stmt.executeQuery(query);
			
			PaisRepository paisRepository = new PaisRepository();
			
			
			while(resultado.next()){
				Pessoa pessoa = new Pessoa();
				pessoa.setId(resultado.getInt("ID"));
				pessoa.setNome(resultado.getString("NOME"));
				pessoa.setCpf(resultado.getString("CPF"));
				pessoa.setSexo(resultado.getString("SEXO").charAt(0));
				pessoa.setDataNascimento(resultado.getDate("DATA_NASCIMENTO").toLocalDate()); 
				pessoa.setTipoPessoa(resultado.getInt("TIPO_PESSOA"));
				
				Pais pais = paisRepository.consultarPorId(resultado.getInt("ID_PAIS"));
				pessoa.setPais(pais);
				
				pessoas.add(pessoa);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as pessoas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pessoas;
	}

	public boolean cpfJaCadastrado(String cpf) {
		boolean cpfJaUtilizado = false;	
		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		String query = " SELECT count(id) FROM pessoa WHERE cpf = " + cpf;
		
		try {
			 ResultSet resultado = stmt.executeQuery(query);
		        if (resultado.next()) { // Avance para a primeira linha
		            cpfJaUtilizado = (resultado.getInt(1) > 0);
		        }
		} catch (SQLException e) {
			System.out.println("Erro ao consultar CPF. Causa: " + e.getMessage());
		}
		
		return cpfJaUtilizado;
	}

	public List<Pessoa> consultarPesquisadores() {
		ArrayList<Pessoa> pessoas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM pessoa WHERE tipo_pessoa = " + Pessoa.PESQUISADOR;
		
		try{
			PaisRepository paisRepository = new PaisRepository();
			resultado = stmt.executeQuery(query);
			while(resultado.next()){
				Pessoa pessoa = new Pessoa();
				pessoa.setId(resultado.getInt("ID"));
				pessoa.setNome(resultado.getString("NOME"));
				pessoa.setCpf(resultado.getString("CPF"));
				pessoa.setSexo(resultado.getString("SEXO").charAt(0));
				pessoa.setDataNascimento(resultado.getDate("DATA_NASCIMENTO").toLocalDate()); 
				pessoa.setTipoPessoa(resultado.getInt("TIPO_PESSOA"));
				
				Pais pais = paisRepository.consultarPorId(resultado.getInt("ID_PAIS"));
				pessoa.setPais(pais);
				pessoas.add(pessoa);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todos os pesquisadores");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pessoas;
	}
	
	
	public ArrayList<Pessoa> consultarComFiltro(PessoaSeletor seletor){
		ArrayList<Pessoa> pessoas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " select * from pessoa"
				 	+ " inner join pais on pessoa.id_pais = pais.id ";
		boolean primeiro = true;
		if (seletor.getNomePessoa() != null) {
			if (primeiro) {
				query += " WHERE ";
			}else {
				query += " AND ";
			}
			query += "upper(pessoa.nome) LIKE UPPER('%" + seletor.getNomePessoa() + "%')";
			primeiro = false;
		}
		

		if(seletor.getNomePais() != null) {
			if(primeiro) {
				query += " WHERE ";
			}else {
				query += " AND ";
			}
			query += " upper(pais.nome) LIKE UPPER('%" + seletor.getNomePais() + "%')";
			primeiro = false;
		}
		
		if (seletor.getSexoPessoa() != null) {
			if(primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "upper(pessoa.sexo) LIKE UPPER('%" + seletor.getSexoPessoa() + "%')";
			primeiro = false;
		}
		
		if(seletor.getDataInicioSelecao() != null & seletor.getDataFinalSelecao() != null) {
			if(primeiro) {
				query += " WHERE ";
			}else {
				query += " AND ";
			}
			query += " data_nascimento between '" + seletor.getDataInicioSelecao() + "' and '" + seletor.getDataFinalSelecao() + "';";
			primeiro = false;
		}

		
		
		try{
			resultado = stmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			while(resultado.next()){
				Pessoa pessoa = new Pessoa();
				pessoa.setId(Integer.parseInt(resultado.getString("ID")));
				pessoa.setNome(resultado.getString("NOME"));

				PaisRepository paisRepository = new PaisRepository();
				pessoa.setPais(paisRepository.consultarPorId(resultado.getInt("ID_PAIS")));
				pessoa.setDataNascimento(resultado.getDate("DATA_NASCIMENTO").toLocalDate()); 
				pessoa.setCpf(resultado.getString("CPF"));
				pessoa.setSexo(resultado.getString("SEXO").charAt(0));
				pessoa.setTipoPessoa(resultado.getInt("TIPO_PESSOA"));
				pessoas.add(pessoa);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as vacinas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pessoas;
		
	}
	
	
}
