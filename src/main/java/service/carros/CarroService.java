package service.carros;
 
import java.util.List;

import exception.CarroException;
import model.entity.carros.Carro;
import model.entity.seletores.CarroSeletor;
import model.repository.carros.CarroRepository;
 
public class CarroService {
	
	CarroRepository carroRepository = new CarroRepository();
	
	public List<Carro> consultarComFiltros(CarroSeletor seletor) throws CarroException {
		
		  if (!seletor.temFiltro()) {
			  
	            throw new CarroException("Um filtro precisa ser preenchido!.");
	        }
		  
		  verificarSeAnosForamPreenchidos(seletor);
		  verificarSeValoresForamPreenchidos(seletor);
		  verificarFiltros(seletor);
		  
		return carroRepository.consultarComSeletor(seletor);
	}
	
	
	public void verificarSeAnosForamPreenchidos(CarroSeletor seletor) throws CarroException {
		
	    boolean anoInicialPreenchido = seletor.getAnoInicial() != null && seletor.getAnoInicial() != 0;
	    boolean anoFinalPreenchido = seletor.getAnoFinal() != null && seletor.getAnoFinal() != 0;
 
	    if (anoInicialPreenchido && !anoFinalPreenchido) {
	    	
	        throw new CarroException("Erro: Falta preencher o ano final!");
	        
	    } else if (!anoInicialPreenchido && anoFinalPreenchido) {
	    	
	        throw new CarroException("Erro: Falta preencher o ano inicial!");
	        
	    }
	}
	
	
	public void verificarSeValoresForamPreenchidos(CarroSeletor seletor) throws CarroException {
		
	    boolean valorInicialPreenchido = seletor.getValorInicial() != null && seletor.getValorInicial() != 0;
	    boolean valorFinalPreenchido = seletor.getValorFinal() != null && seletor.getValorFinal() != 0;
 
	    if (valorInicialPreenchido && !valorFinalPreenchido) {
	    	
	        throw new CarroException("AVISO: O valor inicial está preenchido, mas o valor final está vazio.");
	        
	    } else if (!valorInicialPreenchido && valorFinalPreenchido) {
	    	
	        throw new CarroException("AVISO: O valor final está preenchido, mas o valor inicial está vazio.");
	        
	    }
	}

	public boolean verificarFiltros(CarroSeletor seletor) {
	    return (seletor.getNomeMontadora() != null && !seletor.getNomeMontadora().trim().isEmpty()) ||
	           (seletor.getModelo() != null && !seletor.getModelo().trim().isEmpty()) ||
	           seletor.getAnoInicial() != null && seletor.getAnoInicial() != 0 ||
	           seletor.getAnoFinal() != null && seletor.getAnoFinal() != 0 ||
	           seletor.getValorInicial() != null && seletor.getValorInicial() != 0 ||
	           seletor.getValorFinal() != null && seletor.getValorInicial() != 0;
	}

}	
