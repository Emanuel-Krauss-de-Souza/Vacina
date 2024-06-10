package service.carros;

import java.time.LocalDate;
import java.util.ArrayList;

import exception.MontadoraException;
import model.entity.carros.Montadora;
import model.repository.carros.MontadoraRepository;

public class MontadoraService {

	public ArrayList<Montadora> consultarTodas() {
		return this.gerarMockMontadoras();
	}
	
	public ArrayList<Montadora> gerarMockMontadoras() {
		
		ArrayList<Montadora> montadoras = new ArrayList<>();
		
		montadoras.add(new Montadora(1, "Toyota", "Japão", LocalDate.of(2002, 12, 30), "Emanuel"));
		montadoras.add(new Montadora(2, "Ford", "Estados Unidos", LocalDate.of(2005, 8, 10), "João"));
		montadoras.add(new Montadora(3, "Volkswagen", "Alemanha", LocalDate.of(2001, 2, 21), "Miguel"));
		montadoras.add(new Montadora(4, "GM", "Estados Unidos", LocalDate.of(2007, 4, 16), "Ana"));
		montadoras.add(new Montadora(5, "Honda", "Japão", LocalDate.of(2002, 3, 12), "Carlos"));
		montadoras.add(new Montadora(6, "Nissan", "Japão", LocalDate.of(2003, 5, 03), "Pedro"));

		return montadoras;
	}
	
	MontadoraRepository repository = new MontadoraRepository();
	
    public Montadora salvar(Montadora novaMontadora) throws MontadoraException {
    	
        validarCamposObrigatorios(novaMontadora);

        return repository.salvar(novaMontadora);
    }

    private void validarCamposObrigatorios(Montadora m) throws MontadoraException {
        String mensagemValidacao = "";
        if (m.getNomeMontadora() == null || m.getNomeMontadora().isEmpty()) {
            mensagemValidacao += " - Informe o nome da montadora \n";
        }
        if (m.getPaisFundacao() == null || m.getPaisFundacao().isEmpty()) {
            mensagemValidacao += " - Informe o nome do país de fundação \n";
        }
        if (m.getNomePresidente() == null || m.getNomePresidente().isEmpty()) {
            mensagemValidacao += " - Informe o nome do presidente \n";
        }
        if (m.getDataFundacao() == null) {
            mensagemValidacao += " - Informe a data de fundação \n";
        }

        if (!mensagemValidacao.isEmpty()) {
            throw new MontadoraException("Preencha o(s) seguinte(s) campo(s) \n " + mensagemValidacao);
        }
    }
}
