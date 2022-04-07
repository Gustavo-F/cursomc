package com.gustavo.cursomc.services;

import javax.mail.internet.MimeMessage;

import com.gustavo.cursomc.domain.Cliente;
import com.gustavo.cursomc.domain.Pedido;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    public void sendOrderConfirmationEmail(Pedido pedido);

    public void sendEmail(SimpleMailMessage msg);

    public void sendOrderConfirmationHtmlEmail(Pedido pedido);

    public void sendHtmlEmail(MimeMessage message);

    public void sendNewPasswordEmail(Cliente cliente, String password);
}
