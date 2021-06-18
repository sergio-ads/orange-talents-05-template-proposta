package br.com.zupacademy.proposta.compartilhado;

import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class MinhaCriptografiaSimetrica {

	public static String encript(String texto) {
		TextEncryptor encryptor = Encryptors.text(System.getenv("MYCRIPTOKEY"), System.getenv("MYCRIPTOSALT"));
		return encryptor.encrypt(texto);
	}

	public static String decript(String texto) {
		TextEncryptor decryptor = Encryptors.text(System.getenv("MYCRIPTOKEY"), System.getenv("MYCRIPTOSALT"));
    	return decryptor.decrypt(texto);
	}

	public static byte[] encript(byte[] texto) {
		BytesEncryptor encryptor = Encryptors.stronger(System.getenv("MYCRIPTOKEY"), System.getenv("MYCRIPTOSALT"));
		return encryptor.encrypt(texto);
	}

	public static byte[] decript(byte[] texto) {
		BytesEncryptor decryptor = Encryptors.stronger(System.getenv("MYCRIPTOKEY"), System.getenv("MYCRIPTOSALT"));
    	return decryptor.decrypt(texto);
	}
	
}
