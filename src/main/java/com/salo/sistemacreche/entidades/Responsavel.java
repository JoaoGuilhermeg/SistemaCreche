package com.salo.sistemacreche.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "RESPONSAVEL")
public class Responsavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESPONSAVEL")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_RESPONSAVEL")
    private TipoResponsavel tipoResponsavel;

    @ManyToOne
    @JoinColumn(name = "ID_PESSOA", nullable = false)
    private Pessoa pessoa;

    @Column(name = "TELEFONE", nullable = false, length = 20)
    private String telefone;

    @Column(name = "LOCAL_TRABALHO", length = 100)
    private String localTrabalho;

    @Column(name = "AUXILIO_GOV", nullable = false)
    private Boolean auxilioGov;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_AUXILIO")
    private TipoAuxilio tipoAuxilio;

    @Column(name = "NUMERO_NIS", length = 255)
    private String numeroNis;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoResponsavel getTipoResponsavel() {
        return tipoResponsavel;
    }

    public void setTipoResponsavel(TipoResponsavel tipoResponsavel) {
        this.tipoResponsavel = tipoResponsavel;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
    }

    public Boolean getAuxilioGov() {
        return auxilioGov;
    }

    public void setAuxilioGov(Boolean auxilioGov) {
        this.auxilioGov = auxilioGov;
    }

    public TipoAuxilio getTipoAuxilio() {
        return tipoAuxilio;
    }

    public void setTipoAuxilio(TipoAuxilio tipoAuxilio) {
        this.tipoAuxilio = tipoAuxilio;
    }

    public String getNumeroNis() {
        return numeroNis;
    }

    public void setNumeroNis(String numeroNis) {
        this.numeroNis = numeroNis;
    }
}
