package com.diogo.springemail.web;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.diogo.springemail.domain.Email;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "PoC E-mail")
public class EmailController 
{
	/*
	REFERENCIAS: 
	https://mkyong.com/spring-boot/spring-boot-how-to-send-email-via-smtp/
	https://stackoverflow.com/questions/46663/how-can-i-send-an-email-by-java-application-using-gmail-yahoo-or-hotmail
	*/

	@ResponseBody
	@ApiOperation(value = "Envia E-mail por TLS.")
	@RequestMapping(value= "/emailTls", 
	method = RequestMethod.GET,
	produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> enviarEmailSmtpTls(Email email)
	{
		final String username = email.getRemetente();
		final String password = email.getSenhaRemetente();

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); //TLS

		Session session = Session.getInstance(prop,	new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(username, password);
			}
		});
		session.setDebug(true);
		
		try 
		{
			String recipients = String.join(",", email.getDestinatarios());

			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(username));

			message.setRecipients(
					Message.RecipientType.TO,
					InternetAddress.parse(recipients)
					);

			message.setSubject(email.getAssunto());

			message.setText(email.getMensagem());

			Transport.send(message);
		}
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
		
		return ResponseEntity.ok("");
	}

	@Deprecated
	@ResponseBody
	@ApiOperation(value = "Envia E-mail por TLS.")
	@RequestMapping(value= "/emailTls2", 
	method = RequestMethod.GET,
	produces= {MediaType.APPLICATION_JSON_VALUE})
	public void enviarEmailSmtpTls2(Email email)
	{
		String host = "smtp.gmail.com";
		
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", email.getRemetente());
		props.put("mail.smtp.password", email.getSenhaRemetente());
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(email.getRemetente()));
			InternetAddress[] toAddress = new InternetAddress[email.getDestinatarios().length];

			// To get the array of addresses
			for( int i = 0; i < email.getDestinatarios().length; i++ ) {
				toAddress[i] = new InternetAddress(email.getDestinatarios()[i]);
			}

			for( int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(email.getAssunto());
			
			message.setText(email.getMensagem());
			
			Transport transport = session.getTransport("smtp");
			transport.connect(host, email.getRemetente(), email.getSenhaRemetente());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (AddressException ae) 
		{
			ae.printStackTrace();
		}
		catch (MessagingException me) 
		{
			me.printStackTrace();
		}
	}

	@ResponseBody
	@ApiOperation(value = "Envia E-mail por SSL.")
	@RequestMapping(value= "/emailSsl", 
	method = RequestMethod.GET,
	produces= {MediaType.APPLICATION_JSON_VALUE})
	public void enviarEmailSmtpSsl(Email email)
	{
		final String username = "username@gmail.com";
		final String password = "password";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop,
				new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(username, password);
			}
		});

		try 
		{
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress("from@gmail.com"));

			message.setRecipients(
					Message.RecipientType.TO,
					InternetAddress.parse("to_username_a@gmail.com, to_username_b@yahoo.com")
					);

			message.setSubject("Testing Gmail SSL");

			message.setText("Dear Mail Crawler,"
					+ "\n\n Please do not spam my email!");

			Transport.send(message);

			System.out.println("Done");
		}
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
	}
}
