package solid.acoplamento;

public interface AposGerarNota {

	public void enviaEmail(NotaFiscal nf);
	public void persiste(NotaFiscal nf);

}