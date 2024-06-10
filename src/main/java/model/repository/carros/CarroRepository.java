package model.repository.carros;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.entity.carros.Carro;
import model.entity.carros.Montadora;
import model.entity.seletores.CarroSeletor;
import model.repository.Banco;

public class CarroRepository {

	public ArrayList<Carro> consultarComSeletor(CarroSeletor seletor) {
		
		ArrayList<Carro> carros = new ArrayList<>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		
		String query = "SELECT carro.*, montadora.* FROM carros.carro "
					  + "INNER JOIN carros.montadora ON carro.id_Montadora = montadora.id";
		
		boolean primeiro = true;
		 
		if (seletor.getNomeMontadora() != null) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "upper(montadora.nomeMontadora) LIKE UPPER('%" + seletor.getNomeMontadora() + "%')";
			primeiro = false;
		}
 
		if (seletor.getModelo() != null) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "upper(carro.modelo) LIKE UPPER('%" + seletor.getModelo() + "%')";
			primeiro = false;
		}
 
		if (seletor.getAnoInicial() != null && seletor.getAnoFinal() != null) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "carro.ano BETWEEN '" + seletor.getAnoInicial() + "' AND '" + seletor.getAnoFinal() + "'";
			primeiro = false;
		}
		
		if (seletor.getValorInicial() != null && seletor.getValorFinal() != null) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += "carro.valor BETWEEN '" + seletor.getValorInicial() + "' AND '" + seletor.getValorFinal() + "'";
			primeiro = false;
		}
 
		try {
			resultado = stmt.executeQuery(query);
			MontadoraRepository montadoraRepository = new MontadoraRepository();
 
			while (resultado.next()) {
				Carro carro = new Carro();
				carro.setId(resultado.getInt("id"));
				carro.setModelo(resultado.getString("modelo"));
				carro.setPlaca(resultado.getString("placa"));
				carro.setAno(resultado.getInt("ano"));
				carro.setValor(resultado.getDouble("valor"));
 
				int montadoraId = resultado.getInt("montadora_id");
				Montadora montadora = montadoraRepository.consultarPorId(montadoraId);
				carro.setMontadora(montadora);
 
				carros.add(carro);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar carros com filtro");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
 
		return carros;
	}
	
}