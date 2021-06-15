package solid.acoplamento;

public class AposGerarNotaImpl implements AposGerarNota {

	@Override
	public void enviaEmail(NotaFiscal nf) {
        System.out.println("envia email da nf " + nf.getId());		
	}

	@Override
	public void persiste(NotaFiscal nf) {
        System.out.println("salva nf no banco");		
	}

}
