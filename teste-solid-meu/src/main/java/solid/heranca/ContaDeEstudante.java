package solid.heranca;

public class ContaDeEstudante {

    private ManipuladorDeConta m;
    private int milhas;

    public ContaDeEstudante() {
    	this.m = new ManipuladorDeConta();
	}
    
    public void deposita(double valor) {
    	m.deposita(valor);
        this.milhas += (int)valor;
    }

    public int getMilhas() {
        return milhas;
    }

}