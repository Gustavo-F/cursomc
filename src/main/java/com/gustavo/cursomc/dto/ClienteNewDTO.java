package com.gustavo.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.gustavo.cursomc.services.validation.ClienteInsert;

import org.hibernate.validator.constraints.Length;

@ClienteInsert
public class ClienteNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Este campo é obrigatório.")
    @Length(min = 5, max = 120, message = "Seu nome deve possuir entre {min} e {max} caracteres.")
    private String nome;

    @NotEmpty(message = "Este campo é obrigatório.")
    @Email(message = "Email inválido.")
    private String email;

    @NotEmpty
    private String senha;

    @NotEmpty(message = "Este campo é obrigatório.")
    private String CpfOuCnpj;

    private Integer tipo;

    @NotEmpty(message = "Este campo é obrigatório..")
    private String logradouro;

    @NotEmpty(message = "Este campo é obrigatório..")
    private String numero;

    private String complemento;
    private String bairro;

    @NotEmpty(message = "Este campo é obrigatório..")
    private String cep;

    private Integer cidadeId;

    @NotEmpty(message = "Este campo é obrigatório..")
    private String telefone1;

    private String telefone2;
    private String telefone3;

    public ClienteNewDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpfOuCnpj() {
        return CpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        CpfOuCnpj = cpfOuCnpj;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Integer cidadeId) {
        this.cidadeId = cidadeId;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }
}
