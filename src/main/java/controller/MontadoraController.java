package controller;

import exception.MontadoraException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.carros.Montadora;
import service.carros.MontadoraService;

@Path("/montadora")
public class MontadoraController {
	
	private MontadoraService service = new MontadoraService();
	
	@Path("/salvar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Montadora salvar(Montadora novaMontadora) throws MontadoraException {
		 return service.salvar(novaMontadora);
	}
}
