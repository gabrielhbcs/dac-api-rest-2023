package com.example.dac.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EdicaoEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private int ano;
    private int numero;
    private String dataInicial;
    private String dataFinal;
    private String cidade;
    @ManyToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "id")
    @JsonIgnore
    private Evento evento;

    public Long getId() {
        return id;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getDataInicial() {
        return dataInicial;
    }
    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }
    public String getDataFinal() {
        return dataFinal;
    }
    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Evento getEvento() {
        return evento;
    }
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}

