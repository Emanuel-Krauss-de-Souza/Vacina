package model.entity.seletores;
 
public class CarroSeletor extends BaseSeletor{
	
	private String nomeMontadora;
	private String modelo;
	private Integer anoInicial;
	private Integer anoFinal;
	private Double valorInicial;
	private Double valorFinal;
	
	public boolean temFiltro() {
        return  (this.nomeMontadora != null && this.nomeMontadora.trim().length() > 0)
             || (this.modelo != null && this.modelo.trim().length() > 0)
             || (this.anoInicial != null)
             || (this.anoFinal != null)
             || (this.valorInicial != null)
             || (this.valorFinal != null);
    }

	public String getNomeMontadora() {
		return nomeMontadora;
	}

	public void setNomeMontadora(String nomeMontadora) {
		this.nomeMontadora = nomeMontadora;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getAnoInicial() {
		return anoInicial;
	}

	public void setAnoInicial(Integer anoInicial) {
		this.anoInicial = anoInicial;
	}

	public Integer getAnoFinal() {
		return anoFinal;
	}

	public void setAnoFinal(Integer anoFinal) {
		this.anoFinal = anoFinal;
	}

	public Double getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(Double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public Double getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Double valorFinal) {
		this.valorFinal = valorFinal;
	}

	
	
	

}