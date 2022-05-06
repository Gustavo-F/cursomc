package com.gustavo.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import com.gustavo.cursomc.domain.Cidade;
import com.gustavo.cursomc.domain.Estado;
import com.gustavo.cursomc.dto.CidadeDTO;
import com.gustavo.cursomc.dto.EstadoDTO;
import com.gustavo.cursomc.services.CidadeService;
import com.gustavo.cursomc.services.EstadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {
    
    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> estados = estadoService.findAll();
        List<EstadoDTO> estadosDto = estados.stream().map(estado -> new EstadoDTO(estado)).collect(Collectors.toList());

        return ResponseEntity.ok().body(estadosDto);
    }

    @GetMapping(value = "/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> findByEstado(@PathVariable Integer estadoId) {
        List<Cidade> cidades = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> cidadesDTO = cidades.stream().map(cidade -> new CidadeDTO(cidade)).collect(Collectors.toList());

        return ResponseEntity.ok().body(cidadesDTO);
    }
}
