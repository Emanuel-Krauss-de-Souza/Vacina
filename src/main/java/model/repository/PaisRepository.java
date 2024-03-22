package model.repository;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.Pais;

public class PaisRepository {

	
	public void salvar(Pais novoPais) {
		String query = "INSERT INTO vacina.pais\r\n"
				+ "(id, nome, sigla)\r\n"
				+ "VALUES(0, '', '')";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		
		try {
			pstmt.setString(1, novoPais.getNome());
			pstmt.setString(2, novoPais.getSigla());
			
			pstmt.execute();
			ResultSet resultado = pstmt.getGeneratedKeys();
			
			if(resultado.next()) {
				novoPais.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao CADASTRAR PAIS!");
			System.out.println("Erro " + e.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
	}
	
	public ArrayList<Pais> consultarTodos(){
	ArrayList<Pais> paises = new ArrayList<>();
	Connection conn = Banco.getConnection();
	Statement stmt = Banco.getStatement(conn);
	
	ResultSet resultado = null;
	String query = " SELECT * FROM pais";
	
	try {
		resultado = stmt.executeQuery(query);
	while(resultado.next()) {
		Pais pessoa = new Pais();
		Pais.setNome(resultado.getString("nome"));
		
	}
	}
	return paises;
	}
}
