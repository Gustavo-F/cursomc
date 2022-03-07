package com.gustavo.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.gustavo.cursomc.domain.Pedido;

public interface EmailService {
    public void sendOrderConfirmationEmail(Pedido pedido);

    public void sendEmail(SimpleMailMessage msg);
}
