package model.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.entity.Pessoa;


public class PessoaRepository implements BaseRepository<Pessoa>{
	
	
	public boolean verificarCPF (Pessoa novaPessoa) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT cpf FROM pessoa WHERE cpf = '" + novaPessoa.getCpf() + "'";
		
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro VERIFICAR CADASTRO");
			System.out.println("Erro " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}
	
	@Override
	public Pessoa salvar(Pessoa novaPessoa) {
		String query = "INSERT INTO PESSOA (nome, dataNascimento, sexo, cpf, tipoPessoa) VALUES (?,?,?,?,?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
	
		try{
			 pstmt.setString(1, novaPessoa.getNome());
		     pstmt.setDate(2, Date.valueOf(novaPessoa.getDataNascimento()));
		     pstmt.setString(3, novaPessoa.getSexo());
		     pstmt.setString(4, novaPessoa.getCpf());
		     pstmt.setInt(5, novaPessoa.getTipoPessoa());
		        
		     pstmt.execute();
		     ResultSet resultado = pstmt.getGeneratedKeys();
		     
		     if(resultado.next()) {
		    	novaPessoa.setId(resultado.getInt(1));
		     }
		} catch (SQLException erro) {
			System.out.println("Erro ao salvar nova pessoa!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return novaPessoa;
	}


	@Override
	public boolean excluir(int id) {
		
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean excluiu = false;
		String query = "DELETE FROM pessoa WHERE id = " + id;
		
		try {
			if(stmt.executeUpdate(query) == 1) {
				excluiu = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erri ao excluir pessoa!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return excluiu;
	}

	@Override
	public boolean alterar(Pessoa novaPessoa) {
		return false;
	}

	@Override
	public Pessoa consultarPorId(int id) {
		return null;
}

	@Override
	public ArrayList<Pessoa> consultarTodos() {
		ArrayList<Pessoa> pessoas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM pessoa";
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Pessoa pessoa = new Pessoa();
				pessoa.setNome(resultado.getString("nome"));
				pessoa.setDataNascimento(resultado.getDate("dataNascimento").toLocalDate());
				pessoa.setCpf(resultado.getString("cpf"));
				pessoa.setSexo(resultado.getString("sexo"));
				pessoa.setTipoPessoa(resultado.getInt("tipo"));
				pessoas.add(pessoa);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar consultar todas as pessoas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return pessoas;
	}
}
