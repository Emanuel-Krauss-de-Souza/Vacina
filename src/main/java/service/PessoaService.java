package service;

import java.util.ArrayList;

import exception.PessoaException;
import model.entity.Pessoa;
import model.repository.PessoaRepository;

public class PessoaService {

	private PessoaRepository pessoaRepository = new PessoaRepository();


	public Pessoa salvar(Pessoa novaPessoa) throws PessoaException {
		if (novaPessoa.getNome() == null || novaPessoa.getNome().isEmpty() 
				|| novaPessoa.getDataNascimento() == null
				|| novaPessoa.getSexo() == null || novaPessoa.getSexo().isEmpty() 
				|| novaPessoa.getCpf() == null || novaPessoa.getCpf().isEmpty() 
				|| novaPessoa.getTipoPessoa() == null || novaPessoa.getTipoPessoa().isEmpty()) {
			throw new PessoaException("UM DOS CAMPOS NÃO ESTÁ PREENCHIDO");
		}
		
		if (pessoaRepository.verificarCPF(novaPessoa)) {
			throw new PessoaException("CPF JÁ CADASTRADO NO BANCO DE DADOS");
		}

		pessoaRepository.salvar(novaPessoa);
		return novaPessoa;
	}

	public boolean excluir (int id) {
		return this.pessoaRepository.excluir(id);
	}

	public ArrayList <Pessoa> listarTodos() {
		return this.pessoaRepository.consultarTodos();
	}

}
