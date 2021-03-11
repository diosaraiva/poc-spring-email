package com.diogo.springemail.web;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.http.MediaType;
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
	@ResponseBody
	@ApiOperation(value = "Envia E-mail por TLS.")
	@RequestMapping(value= "/emailTls", 
	method = RequestMethod.GET,
	produces= {MediaType.APPLICATION_JSON_VALUE})
	public void enviarEmailSmtpTls(Email email)
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

		try 
		{
			String recipients = String.join(",", email.getDestinatarios());
			
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(email.getRemetente()));

			message.setRecipients(
					Message.RecipientType.TO,
					InternetAddress.parse(recipients)
					);

			message.setSubject("Testing Gmail TLS");

			message.setText(email.getMensagem());

			Transport.send(message);

			System.out.println("Done");
		} 
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}
	}

	@ResponseBody
	@ApiOperation(value = "Envia E-mail por SSL.")
	@RequestMapping(value= "/emailSsl", 
	method = RequestMethod.GET,
	produces= {MediaType.APPLICATION_JSON_VALUE})
	public void enviarEmailSmtpSsl()
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

		} catch (MessagingException e) 
		{
			e.printStackTrace();
		}
	}
}
