package com.salo.sistemacreche.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "PESSOA_AUTORIZADA")
public class PessoaAutorizada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PESSOA_AUT")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PESSOA")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "ID_CRIANCA")
    private Crianca crianca;

    @Enumerated(EnumType.STRING)
    @Column(name = "PARENTESCO")
    private Parentesco parentesco;

    @Column(name = "TELEFONE", length = 50)
    private String telefone;

    // Enum
    public enum Parentesco {
        MAE, PAI, IRMAO, IRMA, AVO, TIO, TIA,
        RESPONSAVEL_LEGAL, PRIMO, PRIMA, NENHUM
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Crianca getCrianca() {
        return crianca;
    }

    public void setCrianca(Crianca crianca) {
        this.crianca = crianca;
    }

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
