package solid.coesao;
public enum Cargo {
    DESENVOLVEDOR(new CalculoVintePorCento()),
    DBA(new CalculoVintePorCento()),
    TESTER(new CalculoVintePorCento());
    
    private RegraDeCalculo regra;
    
    Cargo(RegraDeCalculo regra) {
    	this.regra = regra;
    }
    
    public RegraDeCalculo getRegra() {
    	return regra;
    }
    
}