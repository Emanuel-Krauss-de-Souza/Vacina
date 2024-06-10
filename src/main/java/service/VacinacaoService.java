package service;

import java.time.LocalDate;
import java.util.ArrayList;

import exception.VacinacaoException;
import model.entity.Pessoa;
import model.entity.Vacina;
import model.entity.Vacinacao;
import model.repository.PessoaRepository;
import model.repository.VacinaRepository;
import model.repository.VacinacaoRepository;

public class VacinacaoService {
	private static final int NOTA_MAXIMA=5;
	private VacinacaoRepository vacinacaoRepository = new VacinacaoRepository();
	private VacinaRepository vacinaRepository = new VacinaRepository();

	public Vacinacao salvar(Vacinacao novaVacinacao) throws VacinacaoException{
		
		validarDadosVacinacao(novaVacinacao);
		recalcularMediaVacina(novaVacinacao);
		ValidarRecebimentoVacina(novaVacinacao);
		
		
		return  vacinacaoRepository.salvar(novaVacinacao);
		
	}
	
	public boolean alterar(Vacinacao vacinacao) throws VacinacaoException {
		validarDadosVacinacao(vacinacao);
		recalcularMediaVacina(vacinacao);
		ValidarRecebimentoVacina(vacinacao);
		
		return vacinacaoRepository.alterar(vacinacao);
	}
	
	public boolean excluir(int id) {
		return vacinacaoRepository.excluir(id);
	}
	
	public Vacinacao consultarPorId(int id) {
		return vacinacaoRepository.consultarPorId(id);
	}

	public ArrayList<Vacinacao> consultarTodos(){
		return vacinacaoRepository.consultarTodos();
	}
	
	private void recalcularMediaVacina(Vacinacao vacinacao) {
		ArrayList<Vacinacao> dosesAplicadas = vacinacaoRepository.consultarPorIdVacina(vacinacao.getVacina().getId());
		
		double novaMediaVacina = 0;
		double somatorioNotasDaVacina = 0;
		
		for(Vacinacao dose: dosesAplicadas) {
			somatorioNotasDaVacina += dose.getAvaliacao();
		}
		
		novaMediaVacina = (somatorioNotasDaVacina + vacinacao.getAvaliacao()) / (dosesAplicadas.size() + 1);
		
		VacinaRepository vacinaRepository = new VacinaRepository();
		Vacina vacinaAplicada = vacinaRepository.consultarPorId(vacinacao.getVacina().getId());
		vacinaAplicada.setMedia(novaMediaVacina);
		
		vacinaRepository.alterar(vacinaAplicada);
		
		vacinacao.setVacina(vacinaAplicada);
	}
	
	
	public void ValidarRecebimentoVacina(Vacinacao vacinacao) throws VacinacaoException {
		Vacina vac = vacinacao.getVacina();
		Pessoa paciente = new PessoaRepository().consultarPorId(vacinacao.getIdPessoa());
		
		boolean podeReceberDose = false;
		if(vac.getEstagioPesquisa() == Vacina.ESTAGIO_INICIAL && paciente.getTipoPessoa() == Pessoa.PESQUISADOR) {
			podeReceberDose = true;
		}
		
		if(vac.getEstagioPesquisa() == Vacina.ESTAGIO_TESTES && 
				(paciente.getTipoPessoa() == Pessoa.PESQUISADOR || paciente.getTipoPessoa() == Pessoa.VOLUNTARIO)) {
			podeReceberDose = true;
		}
		
		if(vac.getEstagioPesquisa() == Vacina.ESTAGIO_APLICACAO_MASSA) {
			podeReceberDose = true;
		}
		
		if(!podeReceberDose) {
			throw new VacinacaoException("Usuário sem permissão para receber a vacina");
		}
	}
	private void validarDadosVacinacao(Vacinacao novaVacinacao) throws VacinacaoException {
		if(novaVacinacao.getIdPessoa() == 0 
				|| novaVacinacao.getVacina() == null
				|| novaVacinacao.getVacina().getId() == 0) {
			throw new VacinacaoException("Informe a o id da pessoa e a vacina da aplicação");
		}
		
		novaVacinacao.setDataAplicacao(LocalDate.now());
		
		if(novaVacinacao.getAvaliacao() == 0) {
			novaVacinacao.setAvaliacao(NOTA_MAXIMA);
		}
	}
	

	
}
