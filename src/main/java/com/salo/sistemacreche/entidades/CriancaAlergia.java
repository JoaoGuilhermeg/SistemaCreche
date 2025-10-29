package com.salo.sistemacreche.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "CRIANCA_ALERGIA")
public class CriancaAlergia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CRIANCA_ALERGIA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CRIANCA", nullable = false)
    private Crianca crianca;

    @ManyToOne
    @JoinColumn(name = "ID_ALERGIA", nullable = false)
    private Alergia alergia;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Crianca getCrianca() {
        return crianca;
    }

    public void setCrianca(Crianca crianca) {
        this.crianca = crianca;
    }

    public Alergia getAlergia() {
        return alergia;
    }

    public void setAlergia(Alergia alergia) {
        this.alergia = alergia;
    }
}
