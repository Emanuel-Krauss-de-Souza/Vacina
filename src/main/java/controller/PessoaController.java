package controller;

import java.util.List;

import exception.VacinacaoException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Pessoa;
import model.entity.Vacina;
import model.entity.seletores.PessoaSeletor;
import model.entity.seletores.VacinaSeletor;
import service.PessoaService;

@Path("/pessoa")
public class PessoaController {
	
	private PessoaService service = new PessoaService();

	
	@POST
	@Path("/filtro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pessoa> consultarComFiltros(PessoaSeletor seletor){
		 return service.consultarComFiltros(seletor);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Pessoa salvar(Pessoa novaPessoa) throws VacinacaoException {
		return service.salvar(novaPessoa);
	}
	
	@DELETE 
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id) throws VacinacaoException {
		return service.excluir(id);
	}
	
	@GET
	@Path("/todas")
	public List<Pessoa> consultarTodas(){
		return service.consultarTodas();
	}

	@PUT
	@Path("/atualizar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean alterar(Pessoa pessoaAlterada) throws VacinacaoException{
		return service.alterar(pessoaAlterada);
	}
	
	@GET
	@Path("/{id}")
	public Pessoa consultarPorId(@PathParam("id") int id){
		 return service.consultarPorId(id);
	}
	
	@GET
	@Path("/pesquisadores")
	public List<Pessoa> consultarPesquisadores(){
		return service.consultarPesquisadores();
	}
}

