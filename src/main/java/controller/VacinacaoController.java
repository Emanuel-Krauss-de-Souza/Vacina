package controller;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.Vacinacao;
import service.VacinacaoService;

@Path("/vacinacao")

public class VacinacaoController {
	
	private VacinacaoService vacinacaoService = new VacinacaoService();
	
	@POST
	@Path("/salvar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Vacinacao salvar(Vacinacao novaVacinacao) {
		return vacinacaoService.salvar(novaVacinacao);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean atualizar(Vacinacao vacinaEditada){
		 return vacinacaoService.atualizar(vacinaEditada);
	}
	
	@DELETE
	@Path("/{id}")
	public boolean excluir(@PathParam("id") int id) {
		return vacinacaoService.excluir(id);
	}
	
	@GET
	@Path("/{id}")
	public Vacinacao consultaVacinacao(@PathParam("id") int id) {
		return vacinacaoService.consultarPorId(id);
	}
	
	@GET
	@Path("/todas")
	public List<Vacinacao> consultarTodos(){
		return vacinacaoService.consultarTodos();
	}
}
