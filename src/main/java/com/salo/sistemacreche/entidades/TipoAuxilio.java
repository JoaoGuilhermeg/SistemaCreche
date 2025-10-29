package com.salo.sistemacreche.entidades;


import jakarta.persistence.*;

@Entity
@Table(name = "TIPO_AUXILIO")
public class TipoAuxilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO_AUXILIO")
    private Long id;

    @Column(name = "NOME_AUXILIO", unique = true, length = 255)
    private String nomeAuxilio;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAuxilio() {
        return nomeAuxilio;
    }

    public void setNomeAuxilio(String nomeAuxilio) {
        this.nomeAuxilio = nomeAuxilio;
    }
}
