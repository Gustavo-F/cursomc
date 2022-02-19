package com.gustavo.cursomc.resources;

import com.gustavo.cursomc.domain.Cliente;
import com.gustavo.cursomc.services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
    
    @Autowired
    private ClienteService clienteService;

    @GetMapping(value="/{id}")
    public ResponseEntity<?> find(@PathVariable("id") Integer id) {
        Cliente cliente = clienteService.buscar(id);
		return ResponseEntity.ok(cliente);
    }
    
}
