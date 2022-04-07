package com.gustavo.cursomc.services;

import java.util.Date;
import java.util.Optional;

import com.gustavo.cursomc.domain.Cliente;
import com.gustavo.cursomc.domain.ItemPedido;
import com.gustavo.cursomc.domain.PagamentoBoleto;
import com.gustavo.cursomc.domain.Pedido;
import com.gustavo.cursomc.domain.enums.EstadoPagamento;
import com.gustavo.cursomc.repositories.ItemPedidoRepository;
import com.gustavo.cursomc.repositories.PagamentoRepository;
import com.gustavo.cursomc.repositories.PedidoRepository;
import com.gustavo.cursomc.security.UserSS;
import com.gustavo.cursomc.services.exceptions.AuthorizationException;
import com.gustavo.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public Pedido find(Integer id) {
        Optional<Pedido> pedidoObj = repo.findById(id);

        return pedidoObj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);

        if (pedido.getPagamento() instanceof PagamentoBoleto) {
            PagamentoBoleto pagto = (PagamentoBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoBoleto(pagto, pedido.getInstante());
        }

        pedido = repo.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());

        for (ItemPedido ip : pedido.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(pedido);
        }

        itemPedidoRepository.saveAll(pedido.getItens());
        emailService.sendOrderConfirmationHtmlEmail(pedido);

        return pedido;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSS user = UserService.authenticated();
        if (user == null)
            throw new AuthorizationException("Acesso negado");

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteService.find(user.getId());

        return repo.findByCliente(cliente, pageRequest);
    }
}
