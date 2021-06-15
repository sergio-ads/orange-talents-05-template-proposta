package solid.acoplamento;

public class GeradorDeNotaFiscal {

	private final AposGerarNota aposGerarNota;

    public GeradorDeNotaFiscal(AposGerarNota aposGerarNota) {
        this.aposGerarNota = aposGerarNota;
    }

    public NotaFiscal gera(Fatura fatura) {

        double valor = fatura.getValorMensal();

        NotaFiscal nf = new NotaFiscal(valor, impostoSimplesSobreO(valor));

        aposGerarNota.enviaEmail(nf);
        aposGerarNota.persiste(nf);        

        return nf;
    }

    private double impostoSimplesSobreO(double valor) {
        return valor * 0.06;
    }
}