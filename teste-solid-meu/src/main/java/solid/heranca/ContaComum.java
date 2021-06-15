package solid.heranca;

public class ContaComum {

    private ManipuladorDeConta m;

    public ContaComum() {
    	this.m = new ManipuladorDeConta();    	
    }

    public void deposita(double valor) {
    	m.deposita(valor);
    }

    public void saca(double valor) {
    	m.saca(valor);
    }

    public void rende() {
    	m.rende(0.01);
    }

    public double getSaldo() {
        return m.getSaldo();
    }
}