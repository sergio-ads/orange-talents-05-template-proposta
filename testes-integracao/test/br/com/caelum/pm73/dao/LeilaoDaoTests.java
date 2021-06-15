package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.builder.LeilaoBuilder;
import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.Usuario;

public class LeilaoDaoTests {
	private Session session;
	private UsuarioDao usuarioDao;
	private LeilaoDao leilaoDao;
	
	@Before
	public void setUp() {
		session = new CriadorDeSessao().getSession();	
		usuarioDao = new UsuarioDao(session);
		leilaoDao = new LeilaoDao(session);
		session.beginTransaction();
	}
	
	@After
	public void fimTeste() {
		session.getTransaction().rollback();
		session.close();		
	}
	
	@Test
	public void deveContarLeiloesNaoEncerrados() {
		Usuario mauricio = new Usuario("Maurício", "mauricio@mauricio.com.br");
		Leilao ativo = new Leilao("Geladeira", 1500.0, mauricio, false);
		Leilao encerrado = new Leilao("xBox", 700.0, mauricio, false);
		encerrado.encerra();
		
		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(ativo);
		leilaoDao.salvar(encerrado);
		
		long total = leilaoDao.total();
		
		assertEquals(1L, total);
	}
	
	@Test
	public void deveRetornarZeroLeiloes() {
		Usuario mauricio = new Usuario("Maurício", "mauricio@mauricio.com.br");
		Leilao encerrado1 = new Leilao("Geladeira", 1500.0, mauricio, false);
		Leilao encerrado2 = new Leilao("xBox", 700.0, mauricio, false);
		encerrado1.encerra();
		encerrado2.encerra();
		
		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(encerrado1);
		leilaoDao.salvar(encerrado2);
		
		long total = leilaoDao.total();
		
		assertEquals(0, total);
	}

	@Test
	public void deveRetornarLeilaoNaoUsado() {
		Usuario mauricio = new Usuario("Maurício", "mauricio@mauricio.com.br");
		Leilao novo = new Leilao("Geladeira", 1500.0, mauricio, false);
		Leilao usado = new Leilao("xBox", 700.0, mauricio, true);
		novo.encerra();
		
		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(novo);
		leilaoDao.salvar(usado);
		
		List<Leilao> novos = leilaoDao.novos();

		assertEquals(1, novos.size());
		assertEquals(novo.getNome(), novos.get(0).getNome());
	}

	@Test
	public void deveRetornarLeiloesAntigos() {
		Usuario mauricio = new Usuario("Maurício", "mauricio@mauricio.com.br");
		Leilao leilao1 = new Leilao("Geladeira", 1500.0, mauricio, false);
		Leilao leilao2 = new Leilao("xBox", 700.0, mauricio, true);
        Calendar dataRecente = Calendar.getInstance();
        Calendar dataAntiga = Calendar.getInstance();
        dataAntiga.add(Calendar.DAY_OF_MONTH, -7);
		leilao1.setDataAbertura(dataRecente);
		leilao2.setDataAbertura(dataAntiga);
		
		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(leilao1);
		leilaoDao.salvar(leilao2);
		
		List<Leilao> antigos = leilaoDao.antigos();

		assertEquals(1, antigos.size());
		assertEquals(leilao2.getNome(), antigos.get(0).getNome());
	}
	
    @Test
    public void deveRetornarLeiloesNaoEncerradosnoPeriodo() {
    	
        // criando as datas
        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();
        Calendar dataDoLeilao1 = Calendar.getInstance();
        dataDoLeilao1.add(Calendar.DAY_OF_MONTH, -2);
        Calendar dataDoLeilao2 = Calendar.getInstance();
        dataDoLeilao2.add(Calendar.DAY_OF_MONTH, -20);

        Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        // criando os leiloes, cada um com uma data
        Leilao leilao1 = new Leilao("XBox", 700.0, mauricio, false);
        leilao1.setDataAbertura(dataDoLeilao1);
        Leilao leilao2 = new Leilao("Geladeira", 1700.0, mauricio, false);
        leilao2.setDataAbertura(dataDoLeilao2);

        // persistindo os objetos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        // invocando o metodo para testar
        List<Leilao> leiloes = leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        // garantindo que a query funcionou
        assertEquals(1, leiloes.size());
        assertEquals("XBox", leiloes.get(0).getNome());
    }
	
    @Test
    public void naoDeveTrazerLeiloesEncerradosNoPeriodo() {

        // criando as datas
        Calendar comecoDoIntervalo = Calendar.getInstance();
        comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
        Calendar fimDoIntervalo = Calendar.getInstance();
        Calendar dataDoLeilao1 = Calendar.getInstance();
        dataDoLeilao1.add(Calendar.DAY_OF_MONTH, -2);

        Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        // criando os leiloes, cada um com uma data
        Leilao leilao1 = new Leilao("XBox", 700.0, mauricio, false);
        leilao1.setDataAbertura(dataDoLeilao1);
        leilao1.encerra();

        // persistindo os objetos no banco
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);

        // invocando o metodo para testar
        List<Leilao> leiloes = leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);

        // garantindo que a query funcionou
        assertEquals(0, leiloes.size());
    }
    
    @Test
    public void deveRetornarLeiloesDisputados() {
        Usuario mauricio = new Usuario("Mauricio", "mauricio@aniche.com.br");
        Usuario marcelo = new Usuario("Marcelo", "marcelo@aniche.com.br");

        Leilao leilao1 = new LeilaoBuilder()
                .comDono(marcelo)
                .comValor(3000.0)
                .comLance(Calendar.getInstance(), mauricio, 3000.0)
                .comLance(Calendar.getInstance(), marcelo, 3100.0)
                .constroi();

        Leilao leilao2 = new LeilaoBuilder()
                .comDono(mauricio)
                .comValor(3200.0)
                .comLance(Calendar.getInstance(), mauricio, 3000.0)
                .comLance(Calendar.getInstance(), marcelo, 3100.0)
                .comLance(Calendar.getInstance(), mauricio, 3200.0)
                .comLance(Calendar.getInstance(), marcelo, 3300.0)
                .comLance(Calendar.getInstance(), mauricio, 3400.0)
                .comLance(Calendar.getInstance(), marcelo, 3500.0)
                .constroi();

        usuarioDao.salvar(marcelo);
        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        List<Leilao> leiloes = leilaoDao.disputadosEntre(2500, 3500);

        assertEquals(1, leiloes.size());
        assertEquals(3200.0, leiloes.get(0).getValorInicial(), 0.00001);
    }
    
    @Test
    public void listaSomenteOsLeiloesDoUsuario() throws Exception {
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Usuario comprador2 = new Usuario("Guilherme", "g@g.com");
        Leilao leilao = new LeilaoBuilder()
            .comDono(dono)
            .comValor(50.0)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .comLance(Calendar.getInstance(), comprador2, 200.0)
            .constroi();
        Leilao leilao2 = new LeilaoBuilder()
            .comDono(dono)
            .comValor(250.0)
            .comLance(Calendar.getInstance(), comprador2, 100.0)
            .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        usuarioDao.salvar(comprador2);
        leilaoDao.salvar(leilao);
        leilaoDao.salvar(leilao2);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(comprador);
        assertEquals(1, leiloes.size());
        assertEquals(leilao, leiloes.get(0));
    }
    
    @Test
    public void listaDeLeiloesDeUmUsuarioNaoTemRepeticao() throws Exception {
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Leilao leilao = new LeilaoBuilder()
            .comDono(dono)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .comLance(Calendar.getInstance(), comprador, 200.0)
            .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        leilaoDao.salvar(leilao);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(comprador);
        assertEquals(1, leiloes.size());
        assertEquals(leilao, leiloes.get(0));
    }
    
    @Test
    public void devolveAMediaDoValorInicialDosLeiloesQueOUsuarioParticipou(){
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Leilao leilao = new LeilaoBuilder()
            .comDono(dono)
            .comValor(50.0)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .comLance(Calendar.getInstance(), comprador, 200.0)
            .comLance(Calendar.getInstance(), dono, 500.0)
            .comLance(Calendar.getInstance(), dono, 600.0)
            .constroi();
        Leilao leilao2 = new LeilaoBuilder()
            .comDono(dono)
            .comValor(250.0)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .comLance(Calendar.getInstance(), dono, 500.0)
            .comLance(Calendar.getInstance(), dono, 600.0)
            .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        leilaoDao.salvar(leilao);
        leilaoDao.salvar(leilao2);

        assertEquals(150.0, leilaoDao.getValorInicialMedioDoUsuario(comprador), 0.001);
    }
    
    @Test
    public void deveDeletarUmLeilao() {
        Usuario mauricio = new Usuario("Mauricio", "m@a.com");
        Leilao leilao = new LeilaoBuilder()
            .comDono(mauricio)
            .comLance(Calendar.getInstance(), mauricio, 10000.0)
            .constroi();

        usuarioDao.salvar(mauricio);
        leilaoDao.salvar(leilao);

        session.flush();

        leilaoDao.deleta(leilao);

        assertNull(leilaoDao.porId(leilao.getId()));

    }
    
}
