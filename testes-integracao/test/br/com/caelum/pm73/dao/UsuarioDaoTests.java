package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Usuario;

public class UsuarioDaoTests {
	private Session session;
	private UsuarioDao usuarioDao;
	
	@Before
	public void setUp() {
		session = new CriadorDeSessao().getSession();	
		usuarioDao = new UsuarioDao(session);
		session.beginTransaction();
	}
	
	@After
	public void fimTeste() {
		session.getTransaction().rollback();
		session.close();		
	}
	
	@Test
	public void deveEncontrarPeloNomeEEmail() {		
		Usuario novoUsuario = new Usuario("Jao da silva", "joao@dasilva.com.br");
		usuarioDao.salvar(novoUsuario);
		
		Usuario usuario = usuarioDao.porNomeEEmail("Jao da silva", "joao@dasilva.com.br");

		assertEquals("Jao da silva", usuario.getNome());
		assertEquals("joao@dasilva.com.br", usuario.getEmail());
	}
	
	@Test
	public void deveRetornarNull() {
		Usuario usuario = usuarioDao.porNomeEEmail("José da silva", "jose@dasilva.com.br");		
		assertNull(usuario);
	}
	
    @Test
    public void deveDeletarUmUsuario() {
        Usuario usuario = 
                new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        usuarioDao.salvar(usuario);
        usuarioDao.deletar(usuario);

        session.flush();

        Usuario usuarioNoBanco = 
                usuarioDao.porNomeEEmail("Mauricio Aniche", "mauricio@aniche.com.br");

        assertNull(usuarioNoBanco);

    }
    
    @Test
    public void deveAlterarUmUsuario() {
        Usuario usuario = 
                new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

        usuarioDao.salvar(usuario);

        usuario.setNome("João da Silva");
        usuario.setEmail("joao@silva.com.br");

        usuarioDao.atualizar(usuario);

        session.flush();

        Usuario novoUsuario = 
                usuarioDao.porNomeEEmail("João da Silva", "joao@silva.com.br");
        assertNotNull(novoUsuario);
        System.out.println(novoUsuario);

        Usuario usuarioInexistente = 
                usuarioDao.porNomeEEmail("Mauricio Aniche", "mauricio@aniche.com.br");
        assertNull(usuarioInexistente);

    }
    
}
