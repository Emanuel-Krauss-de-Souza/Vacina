package controller;

import java.util.List;

import exception.CarroException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.entity.carros.Carro;
import model.entity.seletores.CarroSeletor;
import service.carros.CarroService;

@Path("/carros")
public class CarroController {
	
	
private CarroService service = new CarroService();

	
	@POST
	@Path("/filtro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Carro> consultarComFiltros(CarroSeletor seletor) throws CarroException{
		 return service.consultarComFiltros(seletor);
	}
	

}