package com.salo.sistemacreche.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "CLASSIFICACAO_ESPECIAL")
public class ClassificacaoEspecial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLASSIFICACAO_ESPECIAL")
    private Long id;

    @Column(name = "CLASSIFICACAO_ESPECIAL", length = 255)
    private String classificacaoEspecial;

    @Column(name = "CODIGO", length = 255)
    private String codigo;

    @Column(name = "DESCRICAO", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "STATUS_CLASSIFICACAO_ESPECIAL", nullable = false)
    private Boolean statusClassificacaoEspecial;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassificacaoEspecial() {
        return classificacaoEspecial;
    }

    public void setClassificacaoEspecial(String classificacaoEspecial) {
        this.classificacaoEspecial = classificacaoEspecial;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getStatusClassificacaoEspecial() {
        return statusClassificacaoEspecial;
    }

    public void setStatusClassificacaoEspecial(Boolean statusClassificacaoEspecial) {
        this.statusClassificacaoEspecial = statusClassificacaoEspecial;
    }
}
