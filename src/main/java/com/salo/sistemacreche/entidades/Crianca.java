package com.salo.sistemacreche.entidades;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "CRIANCA")
public class Crianca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CRIANCA")
    private Long id;

    @Column(name = "FOTO", length = 255)
    private String foto;

    @Column(name = "NOME", length = 255)
    private String nome;

    @Column(name = "nome_mae", length = 100)
    private String nomeMae;

    @Column(name = "nome_pai", length = 100)
    private String nomePai;

    @Column(name = "DATA_NASCIMENTO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEXO", nullable = false)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "COR_RACA")
    private CorRaca corRaca;

    @Column(name = "POSSUI_IRMAO_CRECHE", nullable = false)
    private Boolean possuiIrmaoCreche;

    @Column(name = "POSSUI_IRMAO_GEMEO", nullable = false)
    private Boolean possuiIrmaoGemeo;

    @Column(name = "CARTSUS", unique = true, nullable = false, length = 15)
    private String cartSus;

    @Column(name = "UNIDADE_SAUDE", length = 100)
    private String unidadeSaude;

    @Column(name = "MUNICIPIO_NASCIMENTO", length = 255)
    private String municipioNascimento;

    @Column(name = "CARTORIO_REGISTRO", length = 255)
    private String cartorioRegistro;

    @Column(name = "CERTIDAO_NASCIMENTO_NUM", length = 255)
    private String certidaoNascimentoNum;

    @Column(name = "DATA_EMISSAO_CERTIDAO")
    @Temporal(TemporalType.DATE)
    private Date dataEmissaoCertidao;

    @Column(name = "ORG_EMISSOR_CERTIDAO", length = 10)
    private String orgEmissorCertidao;

    @Column(name = "RESTRICAO_ALIMENTAR", nullable = false)
    private Boolean restricaoAlimentar;

    @Column(name = "DESCRICAO_RESTRICOES_ALIMENTARES", length = 255)
    private String descricaoRestricoesAlimentares;

    @Column(name = "ALERGIA", nullable = false)
    private Boolean alergia;

    @Column(name = "PROBLEMA_SAUDE", columnDefinition = "TEXT")
    private String problemaSaude;

    @Column(name = "RESTRI_ALIMENTAR", columnDefinition = "TEXT")
    private String restriAlimentar;

    @Enumerated(EnumType.STRING)
    @Column(name = "MOB_RED")
    private MobRed mobRed;

    @Column(name = "DEF_MULTI", nullable = false)
    private Boolean defMulti;

    @Column(name = "EDUC_ESPECIAL", nullable = false)
    private Boolean educEspecial;

    @Column(name = "RESPONSAVEL_BENEFICIARIO_AUXILIO_GOV", nullable = false)
    private Boolean responsavelBeneficiarioAuxilioGov;

    @ManyToOne
    @JoinColumn(name = "ID_RESPONSAVEL")
    private Responsavel responsavel;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO_AUXILIO")
    private TipoAuxilio tipoAuxilio;

    @ManyToOne
    @JoinColumn(name = "ID_CLASSIFICACAO_ESPECIAL")
    private ClassificacaoEspecial classificacaoEspecial;

    @Column(name = "STATUS_CLASSIFICACAO_ESPECIAL", nullable = false)
    private Boolean statusClassificacaoEspecial = false;

    @OneToMany(mappedBy = "crianca", cascade = CascadeType.ALL)
    private List<CriancaAlergia> alergias;

    // Enums
    public enum Sexo {
        MASCULINO, FEMININO, OUTRO
    }

    public enum CorRaca {
        BRANCA, PRETA, PARDA, AMARELA, INDIGENA
    }

    public enum MobRed {
        TEMPORARIA, PERMANENTE, NENHUMA
    }

    // Getters e Setters


    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public CorRaca getCorRaca() {
        return corRaca;
    }

    public void setCorRaca(CorRaca corRaca) {
        this.corRaca = corRaca;
    }

    public Boolean getPossuiIrmaoCreche() {
        return possuiIrmaoCreche;
    }

    public void setPossuiIrmaoCreche(Boolean possuiIrmaoCreche) {
        this.possuiIrmaoCreche = possuiIrmaoCreche;
    }

    public Boolean getPossuiIrmaoGemeo() {
        return possuiIrmaoGemeo;
    }

    public void setPossuiIrmaoGemeo(Boolean possuiIrmaoGemeo) {
        this.possuiIrmaoGemeo = possuiIrmaoGemeo;
    }

    public String getCartSus() {
        return cartSus;
    }

    public void setCartSus(String cartSus) {
        this.cartSus = cartSus;
    }

    public String getUnidadeSaude() {
        return unidadeSaude;
    }

    public void setUnidadeSaude(String unidadeSaude) {
        this.unidadeSaude = unidadeSaude;
    }

    public String getMunicipioNascimento() {
        return municipioNascimento;
    }

    public void setMunicipioNascimento(String municipioNascimento) {
        this.municipioNascimento = municipioNascimento;
    }

    public String getCartorioRegistro() {
        return cartorioRegistro;
    }

    public void setCartorioRegistro(String cartorioRegistro) {
        this.cartorioRegistro = cartorioRegistro;
    }

    public String getCertidaoNascimentoNum() {
        return certidaoNascimentoNum;
    }

    public void setCertidaoNascimentoNum(String certidaoNascimentoNum) {
        this.certidaoNascimentoNum = certidaoNascimentoNum;
    }

    public Date getDataEmissaoCertidao() {
        return dataEmissaoCertidao;
    }

    public void setDataEmissaoCertidao(Date dataEmissaoCertidao) {
        this.dataEmissaoCertidao = dataEmissaoCertidao;
    }

    public String getOrgEmissorCertidao() {
        return orgEmissorCertidao;
    }

    public void setOrgEmissorCertidao(String orgEmissorCertidao) {
        this.orgEmissorCertidao = orgEmissorCertidao;
    }

    public Boolean getRestricaoAlimentar() {
        return restricaoAlimentar;
    }

    public void setRestricaoAlimentar(Boolean restricaoAlimentar) {
        this.restricaoAlimentar = restricaoAlimentar;
    }

    public String getDescricaoRestricoesAlimentares() {
        return descricaoRestricoesAlimentares;
    }

    public void setDescricaoRestricoesAlimentares(String descricaoRestricoesAlimentares) {
        this.descricaoRestricoesAlimentares = descricaoRestricoesAlimentares;
    }

    public Boolean getAlergia() {
        return alergia;
    }

    public void setAlergia(Boolean alergia) {
        this.alergia = alergia;
    }

    public String getProblemaSaude() {
        return problemaSaude;
    }

    public void setProblemaSaude(String problemaSaude) {
        this.problemaSaude = problemaSaude;
    }

    public String getRestriAlimentar() {
        return restriAlimentar;
    }

    public void setRestriAlimentar(String restriAlimentar) {
        this.restriAlimentar = restriAlimentar;
    }

    public MobRed getMobRed() {
        return mobRed;
    }

    public void setMobRed(MobRed mobRed) {
        this.mobRed = mobRed;
    }

    public Boolean getDefMulti() {
        return defMulti;
    }

    public void setDefMulti(Boolean defMulti) {
        this.defMulti = defMulti;
    }

    public Boolean getEducEspecial() {
        return educEspecial;
    }

    public void setEducEspecial(Boolean educEspecial) {
        this.educEspecial = educEspecial;
    }

    public Boolean getResponsavelBeneficiarioAuxilioGov() {
        return responsavelBeneficiarioAuxilioGov;
    }

    public void setResponsavelBeneficiarioAuxilioGov(Boolean responsavelBeneficiarioAuxilioGov) {
        this.responsavelBeneficiarioAuxilioGov = responsavelBeneficiarioAuxilioGov;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public TipoAuxilio getTipoAuxilio() {
        return tipoAuxilio;
    }

    public void setTipoAuxilio(TipoAuxilio tipoAuxilio) {
        this.tipoAuxilio = tipoAuxilio;
    }

    public ClassificacaoEspecial getClassificacaoEspecial() {
        return classificacaoEspecial;
    }

    public void setClassificacaoEspecial(ClassificacaoEspecial classificacaoEspecial) {
        this.classificacaoEspecial = classificacaoEspecial;
    }

    public Boolean getStatusClassificacaoEspecial() {
        return statusClassificacaoEspecial;
    }

    public void setStatusClassificacaoEspecial(Boolean statusClassificacaoEspecial) {
        this.statusClassificacaoEspecial = statusClassificacaoEspecial;
    }

    public List<CriancaAlergia> getAlergias() {
        return alergias;
    }

    public void setAlergias(List<CriancaAlergia> alergias) {
        this.alergias = alergias;
    }
}