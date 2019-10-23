package com.enviando.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	
	private String userName = "georgealanrufo@gmail.com";
	private String senha = "K@75Stw1#f58%!UL";
	
	@org.junit.Test
	public void testeEmail() {
		/*Olhe as configurações smtp do seu email
		 * O email usado neste exemplo foi o do Gmail, outros emails vão 
		 * precisar de outra configuração.*/
		
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");/*Autorização*/
			properties.put("mail.smtp.starttls", "true");/*Autenticação*/
			properties.put("mail.smtp.host", "smtp.gmail.com");/*Servidor GMail Google*/
			properties.put("mail.smtp.port", "465");/*Porta do Servidor*/
			properties.put("mail.smtp.socketFactory.port", "465");/*Especifica a porta a ser conectada pelo socket*/
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");/*Classe de conexão SMTP*/
			
			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, senha);
				}
			});
			
			Address[] toUser = InternetAddress.parse("george.a@primeclip.com.br, georgealanrufo@gmail.com, seedrawstudio@outlook.com");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Chegou e-mail enviado com Java");
			message.setText("Olá programador, você acaba de receber um email enviado por um sistema Java.");
			
			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
}
