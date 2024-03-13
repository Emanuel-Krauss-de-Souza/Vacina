package model.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.entity.Pessoa;
import repository.Banco;
import repository.BaseRepository;

public class PessoaRepository implements BaseRepository<Pessoa>{
	
	@Override
	public Pessoa salvar(Pessoa novaPessoa) {
		String query = "INSERT INTO PESSOA (nome, dataNascimento, sexo, cpf) VALUES (?,?,?,?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		preencherParametrosParaInsertOuUpdate(pstmt, novaPessoa);
		try{
			 pstmt.setString(1, novaPessoa.getNome());
		     pstmt.setDate(2, Date.valueOf(novaPessoa.getDataNascimento()));
		     pstmt.setString(3, String.valueOf(novaPessoa.getSexo()));
		     pstmt.setString(4, novaPessoa.getCpf());
		        
		     pstmt.execute();
		     ResultSet resultado = pstmt.getGeneratedKeys();
		     
		     if(resultado.next()) {
		    	 novaPessoa.setCpf(resultado.getString(novaPessoa.getCpf()));
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
	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, Pessoa novaPessoa) {
		
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
		boolean alterou = false;
		String query = "UPDATE pessoa SET nome = ?, dataNascimento = ?, sexo = ?, cpf = ?";
		
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			 pstmt.setString(1, novaPessoa.getNome());
		     pstmt.setDate(2, Date.valueOf(novaPessoa.getDataNascimento()));
		     pstmt.setString(3, String.valueOf(novaPessoa.getSexo()));
		     pstmt.setString(4, novaPessoa.getCpf());
		     
		     alterou = pstmt.executeUpdate(query) == 1;
		} catch (SQLException erro){
			System.out.println("Erro ao atualizar pessoa!");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return alterou;
	}

	@Override
	public Pessoa consultarPorId(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		Pessoa pessoa = new Pessoa();
		String query = "SELECT * FROM pessoa WHERE id = " + id;
		
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				pessoa.setNome(resultado.getString("NOME"));
				pessoa.setCpf(resultado.getString("CPF"));
				pessoa.setDataNascimento(resultado.getDate("DATA_NASCIMENTO").toLocalDate());
				pessoa.setSexo(resultado.getString("SEXO"));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar consultar jogador com id (" + id + ")");
		}
		return pessoa;
}

	@Override
	public ArrayList<Pessoa> consultarTodos() {
		ArrayList<Pessoa> pessoa = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		
		ResultSet resultado = null;
		String query = " SELECT * FROM jogador";

		return null;
	}
}
