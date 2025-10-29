package com.salo.sistemacreche.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "COMPOSICAO_FAMILIAR")
public class ComposicaoFamiliar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMPOSICAO_FAMILIAR")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_CRIANCA", nullable = false)
    private Crianca crianca;

    @Column(name = "RENDA_FAMILIAR_TOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal rendaFamiliarTotal;

    @Column(name = "RENDA_PER_CAPITA", nullable = false, precision = 10, scale = 2)
    private BigDecimal rendaPerCapita;

    @Column(name = "TOTAL_MEMBROS")
    private Integer totalMembros;

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

    public BigDecimal getRendaFamiliarTotal() {
        return rendaFamiliarTotal;
    }

    public void setRendaFamiliarTotal(BigDecimal rendaFamiliarTotal) {
        this.rendaFamiliarTotal = rendaFamiliarTotal;
    }

    public BigDecimal getRendaPerCapita() {
        return rendaPerCapita;
    }

    public void setRendaPerCapita(BigDecimal rendaPerCapita) {
        this.rendaPerCapita = rendaPerCapita;
    }

    public Integer getTotalMembros() {
        return totalMembros;
    }

    public void setTotalMembros(Integer totalMembros) {
        this.totalMembros = totalMembros;
    }
}
