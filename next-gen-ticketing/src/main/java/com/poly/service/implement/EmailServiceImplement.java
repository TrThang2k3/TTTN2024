package com.poly.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.poly.service.EmailService;

@Service
public class EmailServiceImplement {
	@Autowired
	JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	
	public void sendConfirmationEmail(String toEmail, String username, String password) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromEmail);
		mailMessage.setTo(toEmail);
		mailMessage.setSubject("Success!");
		  String emailContent = String.format("Thank you for registering. Your account has been successfully created with.%n%nUsername: %s%nPassword: %s", username, password);
		    mailMessage.setText(emailContent);

		javaMailSender.send(mailMessage);
		
	}
	
	
	public void sendConfirmationEmailforgotPassword(String toEmail, String username, String password) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromEmail);
		mailMessage.setTo(toEmail);
		mailMessage.setSubject("Password recovery successful!");
		  String emailContent = String.format("Recover password with account name and password:%n%nUsername: %s%nPassword: %s", username, password);
		    mailMessage.setText(emailContent);

		javaMailSender.send(mailMessage);
		
	}

}
