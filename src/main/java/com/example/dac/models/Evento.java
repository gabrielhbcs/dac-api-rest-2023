package com.example.dac.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String nome;
    private String descricao;
    private String sigla;
    private String caminho;
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EdicaoEvento> edicoesEventos;
    
    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getSigla() {
        return sigla;
    }
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    public String getCaminho() {
        return caminho;
    }
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<EdicaoEvento> getEdicoesEventos() {
        return edicoesEventos;
    }
    public void setEdicoesEventos(List<EdicaoEvento> edicoesEventos) {
        this.edicoesEventos = edicoesEventos;
    }
}

