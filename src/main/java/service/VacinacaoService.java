package service;

import java.util.List;

import model.entity.Vacina;
import model.entity.Vacinacao;
import model.repository.VacinacaoRepository;

public class VacinacaoService {
	
	private VacinacaoRepository repository = new VacinacaoRepository();
	
	public Vacinacao salvar(Vacinacao novaVacinacao) {
		return repository.salvar(novaVacinacao);
	}
	
	public boolean excluir(int id) {
		return repository.excluir(id);
	}
	
	public boolean atualizar(Vacinacao vacinaEditada) {
		return repository.atualizarVanicacao(vacinaEditada);
	}
	public Vacinacao consultarPorId(int id) {
		return repository.consultarPorId(id);
	}
	
	public List<Vacinacao> consultarTodos() {
		return repository.consultarTodos();
	}
}
