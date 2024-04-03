package com.poly.service;

public interface EmailService {
	public void sendConfirmationEmail(String toEmail, String username, String password);
	public void sendConfirmationEmailforgotPassword(String username, String email, String password);
}