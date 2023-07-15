package com.example.dac.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String login;
    private String email;
    private String nome;
    private String afiliacao;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="usuario_id", referencedColumnName="id")
    @JsonIgnore
    private List<Atividade> atividades;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getAfiliacao() {
        return afiliacao;
    }
    public void setAfiliacao(String afiliacao) {
        this.afiliacao = afiliacao;
    }
    public List<Atividade> getAtividades() {
        return atividades;
    }
    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }    
}
