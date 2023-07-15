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
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String nome;
    private TipoAtividade tipo;
    private String descricao;
    private String data;
    private String horarioInicial;
    private String horarioFinal;
    @ManyToOne
    @JoinColumn(name = "espaco_id", nullable = false)
    @JsonIgnore
    private Espaco espaco;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public TipoAtividade getTipo() {
        return tipo;
    }
    public void setTipo(TipoAtividade tipo) {
        this.tipo = tipo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getHorarioInicial() {
        return horarioInicial;
    }
    public void setHorarioInicial(String horarioInicial) {
        this.horarioInicial = horarioInicial;
    }
    public String getHorarioFinal() {
        return horarioFinal;
    }
    public void setHorarioFinal(String horarioFinal) {
        this.horarioFinal = horarioFinal;
    }
    public Espaco getEspaco() {
        return espaco;
    }
    public void setEspaco(Espaco espaco) {
        this.espaco = espaco;
    }
    
}
