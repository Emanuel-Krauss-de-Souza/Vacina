package service;

import java.util.List;

import exception.VacinacaoException;
import model.entity.Pais;
import model.repository.PaisRepository;

public class PaisService {

	
	private PaisRepository repository = new PaisRepository();
	
	
	public Pais salvar(Pais novoPais) throws VacinacaoException{
		validarCamposObrigatorios(novoPais);
		
		return repository.salvar(novoPais);	
		
	}
	
	public Pais consultarPorId(int id) {
		return repository.consultarPorId(id);
	}
	
	
	private void validarCamposObrigatorios(Pais paisValidado) throws VacinacaoException {
		String mensagemValidacao = "";
		
		if(paisValidado.getNome() == null || paisValidado.getNome().isEmpty()) {
			mensagemValidacao = "Nome obrigatório!";
		}
		if(paisValidado.getSigla() == null || paisValidado.getSigla().isEmpty()) {
			mensagemValidacao = "Sigla obrigatória!";
		}
		if (!mensagemValidacao.isEmpty()) {
			throw new VacinacaoException("Preencha os seguintes campos: " + mensagemValidacao);
			
		}
		
		
	}

	public List<Pais> consultarTodas(){
		return repository.consultarTodos();
	}
}
