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
import model.entity.seletores.VacinaSeletor;
import model.repository.Banco;
import model.repository.PaisRepository;
import model.repository.PessoaRepository;

public class VacinaRepository {


	public Vacina salvar(Vacina novaVacina) {
		String sql = " INSERT INTO vacina(id_pesquisador, id_pais_origem, nome, estagio_pesquisa, data_inicio, media) "
				   + " VALUES(?, ?, ?, ?, ?, ?) ";
		Connection conexao = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conexao, sql);
		
		try {
			stmt.setInt(1, novaVacina.getPesquisadorResponsavel().getId());
			stmt.setInt(2, novaVacina.getPais().getId());
			stmt.setString(3, novaVacina.getNome());
			stmt.setInt(4, novaVacina.getEstagioPesquisa());
			stmt.setDate(5, Date.valueOf(novaVacina.getDataInicio()));
			stmt.setDouble(6, novaVacina.getMedia());
			
			stmt.execute();
			ResultSet resultado = stmt.getGeneratedKeys();
			if(resultado.next()) {
				novaVacina.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao salvar nova vacina");
			System.out.println("Erro: " + e.getMessage());
		}
		
		return novaVacina;
	}

	public boolean excluir(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM vacina WHERE id = " + id;
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

	public boolean alterar(Vacina vacinaEditada) {
		boolean alterou = false;
		String query = " UPDATE vacina "
				     + " SET id_pesquisador=?, id_pais_origem=?, nome=?, estagio_pesquisa=?, data_inicio=?, media=? "
				     + " WHERE id=? ";
		Connection conn = Banco.getConnection();
		PreparedStatement stmt = Banco.getPreparedStatementWithPk(conn, query);
		try {
			stmt.setInt(1, vacinaEditada.getPesquisadorResponsavel().getId());
			stmt.setInt(2, vacinaEditada.getPais().getId());
			stmt.setString(3, vacinaEditada.getNome());
			stmt.setInt(4, vacinaEditada.getEstagioPesquisa());
			stmt.setDate(5, Date.valueOf(vacinaEditada.getDataInicio()));
			stmt.setDouble(6, vacinaEditada.getMedia());
			
			
			stmt.setInt(7, vacinaEditada.getId());
			alterou = stmt.executeUpdate() > 0;
		} catch (SQLException erro) {
			System.out.println("Erro ao atualizar vacina");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	public Vacina consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		Vacina vacina = null;
		ResultSet resultado = null;
		String query = " SELECT * FROM vacina WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			PaisRepository paisRepository = new PaisRepository();
			
			if(resultado.next()){
				vacina = new Vacina();
				vacina.setId(Integer.parseInt(resultado.getString("ID")));
				vacina.setNome(resultado.getString("NOME"));
				vacina.setEstagioPesquisa(resultado.getInt("ESTAGIO_PESQUISA"));
				vacina.setDataInicio(resultado.getDate("DATA_INICIO").toLocalDate()); 
				vacina.setMedia(resultado.getDouble("MEDIA"));
				
				Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
				vacina.setPesquisadorResponsavel(pesquisador);
				
				Pais pais = paisRepository.consultarPorId(resultado.getInt("ID_PAIS_ORIGEM"));
				vacina.setPais(pais);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar vacina com o id: " + id);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacina;
	}

	public ArrayList<Vacina> consultarTodos() {
		ArrayList<Vacina> vacinas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM vacina";
		
		try{
			resultado = stmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			PaisRepository paisRepository = new PaisRepository();
			
			while(resultado.next()){
				Vacina vacina = new Vacina();
				vacina.setId(Integer.parseInt(resultado.getString("ID")));
				vacina.setNome(resultado.getString("NOME"));
				vacina.setEstagioPesquisa(resultado.getInt("ESTAGIO_PESQUISA"));
				vacina.setDataInicio(resultado.getDate("DATA_INICIO").toLocalDate()); 
				vacina.setMedia(resultado.getDouble("MEDIA"));
				
				Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
				vacina.setPesquisadorResponsavel(pesquisador);
				
				Pais pais = paisRepository.consultarPorId(resultado.getInt("ID_PAIS_ORIGEM"));
				vacina.setPais(pais);
				
				vacinas.add(vacina);
				
				
				
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as vacinas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacinas;
	}

	public ArrayList<Vacina> consultarComFiltro(VacinaSeletor seletor){
		ArrayList<Vacina> vacinas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " select v.* from vacina v "
				 	+ " inner join pais p on v.id_pais_origem = p.id "
				 	+ " inner join pessoa pe on v.id_pesquisador = pe.id  ";
		
		if (seletor.temFiltro()) {
			query += preencherFiltros(seletor, query);			
		}
		
		if (seletor.temPaginacao()) {
			query += " LIMIT " + seletor.getLimite()
					+" OFFSET " + seletor.getOffset();		
		}
		
		
		
		try{
			resultado = stmt.executeQuery(query);
			PessoaRepository pessoaRepository = new PessoaRepository();
			while(resultado.next()){
				Vacina vacina = new Vacina();
				vacina.setId(Integer.parseInt(resultado.getString("ID")));
				vacina.setNome(resultado.getString("NOME"));

				PaisRepository paisRepository = new PaisRepository();
				vacina.setPais(paisRepository.consultarPorId(resultado.getInt("ID_PAIS_ORIGEM")));

				vacina.setEstagioPesquisa(resultado.getInt("ESTAGIO_PESQUISA"));
				vacina.setDataInicio(resultado.getDate("DATA_INICIO").toLocalDate()); 
				Pessoa pesquisador = pessoaRepository.consultarPorId(resultado.getInt("ID_PESQUISADOR"));
				vacina.setPesquisadorResponsavel(pesquisador);
				vacinas.add(vacina);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar todas as vacinas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return vacinas;
		
	}
	
	private String preencherFiltros(VacinaSeletor seletor, String query) {
		query += " WHERE ";
		boolean primeiro = true;
		
		if (seletor.getNomeVacina() != null) {
			if (!primeiro) {
				query += " AND ";
			}
			query += "upper(v.nome) LIKE UPPER('%" + seletor.getNomeVacina() + "%')";
			primeiro = false;
		}
		

		if(seletor.getNomePais() != null) {
			if(!primeiro) {
				query += " AND ";
			}
			query += " upper(p.nome) LIKE UPPER('%" + seletor.getNomePais() + "%')";
			primeiro = false;
		}
		
		if (seletor.getNomePesquisador() != null) {
			if(!primeiro) {
				query += " AND ";
			}
			query += "upper(pe.nome) LIKE UPPER('%" + seletor.getNomePesquisador() + "%')";
			primeiro = false;
		}
		
		if(seletor.getDataInicioSelecao() != null & seletor.getDataFinalSelecao() != null) {
			if(!primeiro) {
				query += " AND ";
			}
			query += " data_inicio between '" + seletor.getDataInicioSelecao() + "' and '" + seletor.getDataFinalSelecao() + "';";
			primeiro = false;
		}
		
		return query;
	}
	
	public int contarTotalRegistros(VacinaSeletor seletor) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		int totalRegistros = 0;
		ResultSet resultado = null;
		String query = " select (v.ID) from vacina v "
			 	+ " inner join pais p on v.id_pais_origem = p.id "
			 	+ " inner join pessoa pe on v.id_pesquisador = pe.id  ";
		
		query = preencherFiltros(seletor, query);
		
		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()){
				totalRegistros = resultado.getInt(1);
			}
		} catch (SQLException erro){
			System.out.println("Erro ao contar as vacinas filtradas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return totalRegistros;
	
	}
	
	public int contarPaginas(VacinaSeletor seletor) {
		int totalPaginas = 0;
		int totalRegistros = this.contarTotalRegistros(seletor);
		
		totalPaginas = totalRegistros / seletor.getLimite();
		int resto = totalRegistros % seletor.getLimite();
		
		if(resto > 0) {
			totalPaginas++;
		}
		return totalPaginas;
	}
	
	
}
