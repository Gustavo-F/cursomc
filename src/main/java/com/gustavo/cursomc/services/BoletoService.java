package com.gustavo.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import com.gustavo.cursomc.domain.PagamentoBoleto;

import org.springframework.stereotype.Service;

@Service
public class BoletoService {
    public void preencherPagamentoBoleto(PagamentoBoleto pagto, Date instantePedido) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(instantePedido);
        cal.add(Calendar.DAY_OF_MONTH, 7);

        pagto.setDataPagamento(cal.getTime());
    }
}
