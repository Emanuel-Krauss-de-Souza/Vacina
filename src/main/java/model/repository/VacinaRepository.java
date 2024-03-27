package model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Pais;
import model.entity.Pessoa;
import model.entity.Vacina;

public class VacinaRepository implements BaseRepository<Vacina> {

	@Override
	public Vacina salvar(Vacina novaVacina) {
		String sql = " INSERT INTO vacina(id_pesquisador, nome, pais_origem, data_inicio_pesquisa, estagio_pesquisa) "
				   + " VALUES(?, ?, ?, ?, ?) ";
		
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, sql);
		
		try {
			pstmt.setInt(1, novaVacina.getPesquisadorResponsavel().getId());
			pstmt.setString(2, novaVacina.getNome());
			pstmt.setObject(3, novaVacina.getPaisOrigem());
			pstmt.setInt(4, novaVacina.getEstagio());
			pstmt.setDate(5, Date.valueOf(novaVacina.getDataInicioPesquisa()));
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			if(resultado.next()) {
				novaVacina.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nova vacina!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		
		return novaVacina;
	}

	@Override
	public boolean excluir(int id) {
		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE * FROM vacina WHERE id = " + id;
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao excluir vacina");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Vacina vacinaEditada) {
		boolean alterou = false;
		String query = " UPDATE vacina "
				     + " SET id_pesquisador=?, nome=?, pais_origem=?, estagio_pesquisa=?, data_inicio_pesquisa=? "
				     + " WHERE id=? ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			pstmt.setInt(1, vacinaEditada.getPesquisadorResponsavel().getId());
			pstmt.setString(2, vacinaEditada.getNome());
			pstmt.setObject(3, vacinaEditada.getPaisOrigem());
			pstmt.setInt(4, vacinaEditada.getEstagio());
			pstmt.setDate(5, Date.valueOf(vacinaEditada.getDataInicioPesquisa()));
			
			
			pstmt.setInt(6, vacinaEditada.getId());
			alterou = pstmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar vacina");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public Vacina consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement pstmt = Banco.getStatement(conn);
		
		Vacina vacina = null;
		ResultSet resultado = null;
		String query = " SELECT * FROM vacina WHERE id = " + id;
		
		try{
			resultado = pstmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			PaisRepository paisRepository = new PaisRepository();
			
			if(resultado.next()){
				vacina = new Vacina();
				vacina.setId(Integer.parseInt(resultado.getString("ID")));
				vacina.setNome(resultado.getString("NOME"));
				Pais paisOrigem = paisRepository.consultarPorId(resultado.getInt("ID_ORIGEM"));
				vacina.setPaisOrigem(paisOrigem);
				vacina.setEstagio(resultado.getInt("ESTAGIO_PESQUISA"));
				vacina.setDataInicioPesquisa(resultado.getDate("DATA_INICIO_PESQUISA").toLocalDate()); 
				Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
				vacina.setPesquisadorResponsavel(pesquisador);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar vacina com o id: " + id);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return vacina;
	}

	@Override
	public ArrayList<Vacina> consultarTodos() {
		ArrayList<Vacina> vacinas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement pstmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM vacina";
		
		try{
			resultado = pstmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			PaisRepository paisRepository = new PaisRepository();
			while(resultado.next()){
				Vacina vacina = new Vacina();
				vacina.setId(Integer.parseInt(resultado.getString("ID")));
				vacina.setNome(resultado.getString("NOME"));
				Pais paisOrigem = paisRepository.consultarPorId(resultado.getInt("ID_ORIGEM"));
				vacina.setPaisOrigem(paisOrigem);
				vacina.setEstagio(resultado.getInt("ESTAGIO_PESQUISA"));
				vacina.setDataInicioPesquisa(resultado.getDate("DATA_INICIO_PESQUISA").toLocalDate()); 
				Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
				vacina.setPesquisadorResponsavel(pesquisador);
				vacinas.add(vacina);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as vacinas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return vacinas;
	}
}
