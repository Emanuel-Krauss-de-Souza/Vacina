package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Vacinacao;
import model.entity.Vacina;

public class VacinacaoRepository {

	

	public Vacinacao salvar(Vacinacao novaVacinacao) {
		
		String query = "INSERT INTO vacinacao (id_pessoa, id_vacina, data_aplicacao,"
					 + " avaliacao) VALUES (?,?,?,?)";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, query);
		
		try {

			stmt.setInt(1, novaVacinacao.getIdPessoa());
			stmt.setInt(2, novaVacinacao.getVacina().getId());
			stmt.setDate(3, Date.valueOf(novaVacinacao.getDataAplicacao()));
			stmt.setInt(4, novaVacinacao.getAvaliacao());
			stmt.execute();
			ResultSet resultado = stmt.getGeneratedKeys();
			if (resultado.next()) {
				novaVacinacao.setId(resultado.getInt(1));
			}
			
		} catch (SQLException erro) {
			System.out.println("Erro ao salvar nova aplicação");
			System.out.println("Erro: " + erro.getMessage());
		}
	
		return novaVacinacao;
		
	}
	
	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String sql = "DELETE  FROM vacinacao WHERE id = " + id;
		try {
			
			if (stmt.executeUpdate(sql) == 1) {
				excluiu = true;
			}
			
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir aplicação");
			System.out.println("Erro: " + erro.getMessage());
		}
		finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
		
	}
	
	public boolean alterar(Vacinacao vacinacaoEditada) {
		// TODO pode alterar tudo na aplicação? Ou apenas a nota?
		
		boolean alterou = false;
		String sql = "UPDATE controle_vacinas.vacinacao "
				+ "SET id_pessoa=?, id_vacina=?, data_aplicacao=?, "
				+ "avaliacao=? WHERE id=?";
		
		Connection conn = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conn, sql);
		try {
			stmt.setInt(1, vacinacaoEditada.getIdPessoa());
			stmt.setInt(2, vacinacaoEditada.getVacina().getId());
			stmt.setDate(3, Date.valueOf(vacinacaoEditada.getDataAplicacao()));
			stmt.setInt(4, vacinacaoEditada.getAvaliacao());
			
			stmt.setInt(5, vacinacaoEditada.getId());
			alterou = stmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar aplicação de vacina");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeConnection(conn);
			Banco.closeStatement(stmt);
		}
		return alterou;
	}
	
	public Vacinacao consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		Vacinacao vacinacao = null;
		ResultSet resultado = null;
		String sql = "SELECT * FROM controle_vacinas.vacinacao WHERE id = " + id;
		
		try {
			resultado = stmt.executeQuery(sql);
			VacinaRepository vacinaRepository = new VacinaRepository();
			if (resultado.next()) {
				vacinacao = new Vacinacao();
				vacinacao.setId(resultado.getInt("ID"));
				vacinacao.setIdPessoa(resultado.getInt("ID_PESSOA"));
				vacinacao.setDataAplicacao(resultado.getDate("DATA_APLICACAO").toLocalDate());
				vacinacao.setAvaliacao(resultado.getInt("AVALIACAO"));
				vacinacao.setVacina(vacinaRepository.consultarPorId(resultado.getInt("ID_VACINA")));
				
			}
			
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar a aplicação de vacina com o id: " + id);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		

		return vacinacao;
		
	}
	
	public ArrayList<Vacinacao> consultarTodos(){
		ArrayList<Vacinacao> aplicacoes = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM vacinacao";
		
		try{
			resultado = stmt.executeQuery(query);
			VacinaRepository vacinaRepository = new VacinaRepository();
			while(resultado.next()){
				Vacinacao vacinacao = new Vacinacao();
				vacinacao.setId(resultado.getInt("ID"));
				vacinacao.setIdPessoa(resultado.getInt("ID_PESSOA"));
				vacinacao.setDataAplicacao(resultado.getDate("DATA_APLICACAO").toLocalDate());
				vacinacao.setAvaliacao(resultado.getInt("AVALIACAO"));
				vacinacao.setVacina(vacinaRepository.consultarPorId(resultado.getInt("ID_VACINA")));
				
				aplicacoes.add(vacinacao);
			}
		} catch (SQLException erro){
			System.out.println("Erro consultar todas as aplicações de vacinas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return aplicacoes;
		
		
	}


	private Vacinacao converterParaObjeto(ResultSet resultado) throws SQLException {
		Vacinacao aplicacaoVacina = new Vacinacao();
		aplicacaoVacina.setId(resultado.getInt("ID"));
		aplicacaoVacina.setIdPessoa(resultado.getInt("ID_PESSOA"));
		aplicacaoVacina.setAvaliacao(resultado.getInt("AVALIACAO"));
		aplicacaoVacina.setDataAplicacao(resultado.getDate("DATA_APLICACAO").toLocalDate());
		
		VacinaRepository vacinaRepository = new VacinaRepository();
		Vacina vacinaAplicada = 
				vacinaRepository.consultarPorId(resultado.getInt("ID_VACINA"));
		
		aplicacaoVacina.setVacina(vacinaAplicada);
		return aplicacaoVacina;
	}
	
	public ArrayList<Vacinacao> consultarPorIdPessoa(int idPessoa){
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ArrayList<Vacinacao> aplicacoes = new ArrayList<Vacinacao>();
		ResultSet resultado = null;
		String query = " SELECT * FROM vacinacao WHERE id_pessoa = " + idPessoa;
		try{
			resultado = stmt.executeQuery(query);

			while(resultado.next()){
				Vacinacao aplicacaoVacina = this.converterParaObjeto(resultado);
				aplicacoes.add(aplicacaoVacina);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as vacinações realizadas na pessoa com id: " + idPessoa);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return aplicacoes;
	}


	public ArrayList<Vacinacao> consultarPorIdVacina(int idVacina){
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ArrayList<Vacinacao> aplicacoes = new ArrayList<Vacinacao>();
		ResultSet resultado = null;
		String query = " SELECT * FROM vacinacao WHERE id_vacina = " + idVacina;
		try{
			resultado = stmt.executeQuery(query);

			while(resultado.next()){
				Vacinacao aplicacaoVacina = this.converterParaObjeto(resultado);
				aplicacoes.add(aplicacaoVacina);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as vacinações realizadas na vacina com id" + idVacina);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return aplicacoes;
	}
}
