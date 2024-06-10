package service;

import java.util.List;

import exception.VacinacaoException;
import model.entity.Vacina;
import model.entity.seletores.VacinaSeletor;
import model.repository.VacinaRepository;
import model.repository.VacinacaoRepository;

public class VacinaService {
	
	private VacinacaoRepository vacinacaoRepository = new VacinacaoRepository();
	private VacinaRepository repository = new VacinaRepository();
	
	public Vacina salvar(Vacina novaVacina){
		
		return repository.salvar(novaVacina);
	}

	public boolean atualizar(Vacina vacinaEditada) {
		
		return repository.alterar(vacinaEditada);
	}

	public boolean excluir(int id) throws VacinacaoException{
		vacinaFoiUtilizada(id);
		
		return repository.excluir(id);
	}

	public Vacina consultarPorId(int id) {
		return repository.consultarPorId(id);
	}

	public List<Vacina> consultarTodas() {
		return repository.consultarTodos();
	}
	
	
	private void vacinaFoiUtilizada(int id) throws VacinacaoException {
		if (!vacinacaoRepository.consultarPorIdVacina(id).isEmpty()) {
			throw new VacinacaoException("Vacina já utilizada não pode ser excluída");
		}
	}
	
	public List<Vacina> consultarComFiltros(VacinaSeletor seletor) {
		return repository.consultarComFiltro(seletor);
	}
	
	public int contarTotalRegistros(VacinaSeletor seletor) {
		return repository.contarTotalRegistros(seletor);
	}
	
	public int contarPaginas(VacinaSeletor seletor) {
		return repository.contarPaginas(seletor);
	}
	
	
	
	
	
	
	
}
