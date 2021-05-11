package com.email.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	// simply send text mail to specific single person
	public boolean sendMail(String message, String subject, String to) {

		boolean flag = false;
		String from = "hbahire2014@gmail.com";
		// variable for gmail host
		String host = "smtp.gmail.com";

		// get the system propertices
		Properties properties = System.getProperties();
		System.out.println(properties);

		// setting imp info to properties object
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587"); // 465
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// step : 1 to get the session object...
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "HitsNiki@98");
			}
		});
		session.setDebug(true);

		// step : 2 compose the message [text, multimedia]
		MimeMessage mimeMessage = new MimeMessage(session);
		try {

			// from mail
			mimeMessage.setFrom(from);

			// adding Recipient
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // you can also use internate
																							// address array for sending
																							// mai to multiple users
			// adding subject to message
			mimeMessage.setSubject(subject);

			// adding text .. you can also use multipart like this
			mimeMessage.setText(message);

			// step : 3 send the message usding transpose class
			Transport.send(mimeMessage);

			System.out.println("Mail sended ");

			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

}
