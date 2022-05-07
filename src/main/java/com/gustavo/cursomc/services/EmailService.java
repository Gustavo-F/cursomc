package com.gustavo.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.gustavo.cursomc.domain.Cliente;
import com.gustavo.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}