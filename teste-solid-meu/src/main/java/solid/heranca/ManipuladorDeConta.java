package solid.heranca;

public class ManipuladorDeConta {

    protected double saldo;
    
    public ManipuladorDeConta() {
    	this.saldo = 0;
    }

    public void deposita(double valor) {
        this.saldo += valor;
    }

    public void saca(double valor) {
        if (valor <= this.saldo) {
            this.saldo -= valor;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void rende(Double valor) {
        this.saldo += this.saldo * valor;
    }

    public double getSaldo() {
        return saldo;
    }
}
