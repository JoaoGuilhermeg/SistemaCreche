package com.salo.sistemacreche.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "ALERGIA")
public class Alergia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERGIA")
    private Long id;

    @Column(name = "NOME_ALERGIA", length = 100)
    private String nomeAlergia;

    // Getters e Setters

    public String getNomeAlergia() {
        return nomeAlergia;
    }

    public void setNomeAlergia(String nomeAlergia) {
        this.nomeAlergia = nomeAlergia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
