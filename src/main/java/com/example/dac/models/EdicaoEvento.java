package com.example.dac.models;

public class EdicaoEvento {
    private int ano;
    private int numero;
    private String dataInicial;
    private String dataFinal;
    private String cidade;

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
}

