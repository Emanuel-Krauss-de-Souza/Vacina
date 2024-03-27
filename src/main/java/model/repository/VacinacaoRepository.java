package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Vacinacao;

public class VacinacaoRepository {

	public Vacinacao salvar(Vacinacao novaVacinacao) {
		
		String sql = " INSERT INTO vacinacao(id_pessoa_aplicada, id_vacina, data_aplicacao, avaliacao)"
				+ "(?,?,?,?)";
		
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, sql);
		
		try {
			
			pstmt.setInt(1, novaVacinacao.getId());
			pstmt.setInt(2, novaVacinacao.getVacina().getId());
			pstmt.setDate(3, Date.valueOf(novaVacinacao.getDataAplicacao()));
			pstmt.setInt(4, novaVacinacao.getAvaliacao());
			
		} catch (SQLException erro) {
			
			System.out.println("Erro ao salvar nova vacinação!");
			System.out.println("Erro: " + erro.getMessage());
			
		} finally {
			
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
			
		}
		
		return novaVacinacao;
	}
	
	public boolean excluir(int id) {
		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		boolean excluiu = false;
		
		String query = "DELETE * FROM VACINACAO WHERE ID = " + id;
		
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			
			System.out.println("Erro ao excluir vacinação!");
			System.out.println("Erro: " + erro.getMessage());
			
		} finally {
			
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
			
		}
		return excluiu;
	}
	
	public boolean atualizarVanicacao(Vacinacao vacinacaoEditada) {
		boolean atualizou = false;
		
		String query = "UPDATE vacinacao"
				     + "SET id_pessoa_aplicada=?, id_vacina=?, data_aplicacao=?, avaliacao=? "
					 + "WHERE id=?";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		
		try {
			
			pstmt.setInt(1, vacinacaoEditada.getIdPessoa());
			pstmt.setInt(2, vacinacaoEditada.getVacina().getId());
			pstmt.setDate(3, Date.valueOf(vacinacaoEditada.getDataAplicacao()));
			pstmt.setInt(4, vacinacaoEditada.getAvaliacao());
			
			atualizou = pstmt.executeUpdate() > 0;
			
		} catch (SQLException erro) {
			
			System.out.println("Erro ao atualizar vacinacao!");
			System.out.println("Erro: " + erro.getMessage());
			
		} finally {
			
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
			
		}
		return atualizou;
	}
	
	public Vacinacao consultarPorId(int id) {
		
		Connection conn = Banco.getConnection();
		Statement pstmt = Banco.getStatement(conn);
		
		Vacinacao vacinacao = null;
		ResultSet resultado = null;
		String query = " SELECT * FROM vacinacao WHERE id = " + id;
		
		try {
			
			resultado = pstmt.executeQuery(query);
			
			if(resultado.next()) {
			vacinacao = new Vacinacao();
			vacinacao.setId(Integer.parseInt(resultado.getString("id")));
			vacinacao.setIdPessoa(Integer.parseInt(resultado.getString("id")));
			vacinacao.setDataAplicacao(resultado.getDate("data_aplicacao").toLocalDate());
			vacinacao.setAvaliacao(resultado.getInt("avaliacao"));
			}
		} catch (Exception erro) {
			System.out.println("Erro ao consultar vacinação com o id: " + id + "!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return vacinacao;
	}
	
	public ArrayList<Vacinacao> consultarTodos() {
		ArrayList<Vacinacao> vacinacoes = new ArrayList<>();
		
		Connection conn = Banco.getConnection();
		Statement pstmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		
		String query = " SELECT * FROM vacina";
		
		try {
			
			resultado = pstmt.executeQuery(query);
			
			while (resultado.next()) {
				
			Vacinacao vacinacao = new Vacinacao();
			
			vacinacao.setId(Integer.parseInt(resultado.getString("id")));
			vacinacao.setIdPessoa(Integer.parseInt(resultado.getString("id")));
			vacinacao.setDataAplicacao(resultado.getDate("data_aplicacao").toLocalDate());
			vacinacao.setAvaliacao(resultado.getInt("avaliacao"));
			}
		} catch (Exception erro) {
			
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return vacinacoes;
	}
}
