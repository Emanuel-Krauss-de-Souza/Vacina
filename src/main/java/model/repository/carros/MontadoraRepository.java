package model.repository.carros;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.entity.carros.Montadora;
import model.repository.Banco;

public class MontadoraRepository {

    public Montadora salvar(Montadora novaMontadora) {
    	
        String query = "INSERT INTO carros.montadora (nomeMontadora, paisFundacao, dataFundacao, nomePresidente) VALUES (?,?,?,?)";
        Connection conn = Banco.getConnection();
        PreparedStatement psmt = Banco.getPreparedStatementWithPk(conn, query);
        
        try {
        	
            psmt.setString(1, novaMontadora.getNomeMontadora());
            psmt.setString(2, novaMontadora.getPaisFundacao());
            psmt.setDate(3, Date.valueOf(novaMontadora.getDataFundacao()));
            psmt.setString(4, novaMontadora.getNomePresidente());
            
            psmt.execute();
            ResultSet resultado = psmt.getGeneratedKeys();
            
            if (resultado.next()) {
                novaMontadora.setId(resultado.getInt(1));
            }
        
        } catch (SQLException erro) {
        	
            System.out.println("Erro ao salvar nova montadora!");
            System.out.println("Erro: " + erro.getMessage());
            
        } finally {
        	
            Banco.closePreparedStatement(psmt);
            Banco.closeConnection(conn);
            
        }
        return novaMontadora;
    }
    
    public Montadora consultarPorId(int id) {
		Connection conexao = Banco.getConnection();
		Statement stmt = Banco.getStatement(conexao);
		
		Montadora montadora = null;
		ResultSet resultado = null;
		String query = " SELECT * FROM montadora WHERE id = " + id;
		
		try{
			resultado = stmt.executeQuery(query);
			if(resultado.next()){
				montadora = new Montadora();
				montadora.setId(resultado.getInt("ID"));
				montadora.setNomePresidente("NOMEMONTADORA");
				montadora.setPaisFundacao(resultado.getString("PAISFUNDACAO"));
				montadora.setDataFundacao(resultado.getDate("DATAFUNDACAO").toLocalDate());
				montadora.setNomePresidente(resultado.getString("NOMEPRESIDENTE"));
			}
		} catch (SQLException erro){
			System.out.println("Erro ao consultar montadora com o id: " + id);
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conexao);
		}
		return montadora;
	}
}