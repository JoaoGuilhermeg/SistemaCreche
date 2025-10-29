package com.salo.sistemacreche.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "TIPO_RESPONSAVEL")
public class TipoResponsavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_RESPONSAVEL")
    private Long id;

    @Column(name = "TIPO_RESPONSAVEL", length = 255)
    private String tipoResponsavel;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoResponsavel() {
        return tipoResponsavel;
    }

    public void setTipoResponsavel(String tipoResponsavel) {
        this.tipoResponsavel = tipoResponsavel;
    }
}
