package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import model.entity.Pessoa;
import repository.Banco;
import repository.BaseRepository;

public class PessoaRepository implements BaseRepository<Pessoa>{
	
	@Override
	public Pessoa salvar(Pessoa novaPessoa) {
		String query = "INSERT INTO PESSOA (nome. dataNascimento, sexo, cpf) VALUES (?,?,?,?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		preencherParametrosParaInsertOuUpdate(pstmt, novaPessoa);
		
		
		return novaPessoa;
	}

	private void preencherParametrosParaInsertOuUpdate(PreparedStatement pstmt, Pessoa novaPessoa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean excluir(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean alterar(Pessoa entidade) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pessoa consultarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Pessoa> consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}
}
