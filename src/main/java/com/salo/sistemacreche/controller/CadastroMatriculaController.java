package com.salo.sistemacreche.controller;

import com.salo.sistemacreche.components.EmptyCard;
import com.salo.sistemacreche.components.ResponsavelCard;
import com.salo.sistemacreche.controller.extracadastro.FiliacaoResponsavelController;
import com.salo.sistemacreche.controller.extracadastro.MembroFamiliarController;
import com.salo.sistemacreche.controller.extracadastro.PessoaAutorizadaController;
import com.salo.sistemacreche.dao.DBConnection;
import com.salo.sistemacreche.entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CadastroMatriculaController {

    // Seção 1: Identificação da Criança
    @FXML private TextField fieldNomeCrianca;
    @FXML private TextField fieldRgCrianca;
    @FXML private DatePicker datePickerNascimento;
    @FXML private ComboBox<String> comboSexo;
    @FXML private ComboBox<String> comboCorRaca;
    @FXML private TextField fieldSus;
    @FXML private TextField fieldUnidadeSaude;
    @FXML private ComboBox<String> comboRestricaoAlimentar;
    @FXML private ComboBox<String> comboMobilidadeReduzida;
    @FXML private ComboBox<String> comboEducacaoEspecial;

    // Combos que virão do banco
    @FXML private ComboBox<String> comboClassificacaoEspecial;
    @FXML private ComboBox<String> comboAlergias;
    @FXML private ComboBox<String> comboTipoAuxilio;

    // Seção 2: Filiação/Responsáveis
    @FXML private TextField fieldPesquisaMae;
    @FXML private TextField fieldPesquisaPai;
    @FXML private TextField fieldPesquisaResponsavel;

    // Seção 3: Endereço
    @FXML private TextField fieldEndereco;
    @FXML private TextField fieldPontoReferencia;
    @FXML private TextField fieldBairro;
    @FXML private TextField fieldMunicipio;
    @FXML private TextField fieldNumero;
    @FXML private TextField fieldCEP;
    @FXML private ComboBox<String> comboUF;
    @FXML private TextField fieldTelefoneResidencial;
    @FXML private TextField fieldTelefoneContato;

    // Seção 4: Documentos
    @FXML private TextField fieldCertidaoNascimento;
    @FXML private TextField fieldNumeroMatricula;
    @FXML private TextField fieldMunicipioNascimento;
    @FXML private TextField fieldCartorioRegistro;
    @FXML private TextField fieldMunicipioRegistro;
    @FXML private TextField fieldCpfCrianca;
    @FXML private DatePicker datePickerEmissaoRG;
    @FXML private TextField fieldOrgaoEmissor;

    // Seção 5: Situação Habitacional
    @FXML private ComboBox<String> comboTipoMoradia;
    @FXML private TextField fieldValorAluguel;
    @FXML private TextField fieldNumeroComodos;
    @FXML private ComboBox<String> comboTipoPiso;
    @FXML private ComboBox<String> comboMaterialParede;
    @FXML private ComboBox<String> comboTipoCobertura;
    @FXML private CheckBox checkFossa;
    @FXML private CheckBox checkCifon;
    @FXML private CheckBox checkEnergiaEletrica;
    @FXML private CheckBox checkAguaEncanada;

    // Seção 6: Bens
    @FXML private CheckBox checkTV;
    @FXML private CheckBox checkDVD;
    @FXML private CheckBox checkComputador;
    @FXML private CheckBox checkInternet;
    @FXML private CheckBox checkGeladeira;
    @FXML private CheckBox checkFogao;
    @FXML private CheckBox checkMaquinaLavar;
    @FXML private CheckBox checkMicroondas;
    @FXML private CheckBox checkCarro;
    @FXML private CheckBox checkMoto;

    // Seção 7: Composição Familiar
    @FXML private TableView<MembroFamilia> tableComposicaoFamiliar;
    private ObservableList<MembroFamilia> membrosFamiliares = FXCollections.observableArrayList();

    // Seção 8: Série
    @FXML private ComboBox<String> comboSerie;
    @FXML private TextField fieldAnoLetivo;

    // Seção 9: Pessoas Autorizadas
    @FXML private TableView<PessoaAutorizada> tablePessoasAutorizadas;
    private ObservableList<PessoaAutorizada> pessoaAutorizadas = FXCollections.observableArrayList();

    // Seção 11: Irmão Gêmeo
    @FXML private CheckBox checkIrmaoGemeo;

    // Botões
    @FXML private Button btnSalvar;
    @FXML private Button btnCancelar;

    @FXML private VBox cardsContainerMaes;
    @FXML private VBox cardsContainerPais;
    @FXML private VBox cardsContainerResponsaveis;

    @FXML
    public void initialize() {
        configurarComboBoxFixos();
        carregarDadosDoBanco();
        pesquisarMae();
        pesquisarPai();
        pesquisarResponsavel();
        configurarPesquisaPorEnter();
        configurarTableViews();
    }

    private void configurarComboBoxFixos() {
        comboSexo.setItems(FXCollections.observableArrayList(
                "Masculino", "Feminino", "Outro"
        ));

        comboCorRaca.setItems(FXCollections.observableArrayList(
                "Branca", "Preta", "Parda", "Amarela", "Indígena"
        ));

        comboRestricaoAlimentar.setItems(FXCollections.observableArrayList(
                "Não", "Sim"
        ));

        comboMobilidadeReduzida.setItems(FXCollections.observableArrayList(
                "Não", "Sim, temporária", "Sim, permanente"
        ));

        comboEducacaoEspecial.setItems(FXCollections.observableArrayList(
                "Não", "Sim"
        ));

        comboUF.setItems(FXCollections.observableArrayList(
                "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
                "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
                "RS", "RO", "RR", "SC", "SP", "SE", "TO"
        ));

        comboTipoMoradia.setItems(FXCollections.observableArrayList(
                "Casa própria", "Casa cedida", "Casa alugada"
        ));

        comboTipoPiso.setItems(FXCollections.observableArrayList(
                "Cimento", "Lajota", "Chão batido", "Outro"
        ));

        comboMaterialParede.setItems(FXCollections.observableArrayList(
                "Tijolo", "Taipa", "Madeira", "Outro"
        ));

        comboTipoCobertura.setItems(FXCollections.observableArrayList(
                "Telha", "Zinco", "Palha", "Outro"
        ));

        comboSerie.setItems(FXCollections.observableArrayList(
                "Berçário I", "Berçário II", "Maternal I", "Maternal II", "Pré I", "Pré II"
        ));
    }

    private void carregarDadosDoBanco() {
        carregarClassificacoesEspeciais();
        carregarAlergias();
        carregarTiposAuxilio();
    }

    private void carregarClassificacoesEspeciais() {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();

            // Query com ordenação personalizada para "Nenhum" ficar primeiro
            TypedQuery<ClassificacaoEspecial> query = em.createQuery(
                    "SELECT c FROM ClassificacaoEspecial c WHERE c.statusClassificacaoEspecial = true " +
                            "ORDER BY CASE WHEN c.classificacaoEspecial = 'Nenhum' THEN 0 ELSE 1 END, c.classificacaoEspecial",
                    ClassificacaoEspecial.class
            );

            List<ClassificacaoEspecial> classificacoes = query.getResultList();

            List<String> nomesClassificacoes = classificacoes.stream()
                    .map(ClassificacaoEspecial::getClassificacaoEspecial)
                    .toList();

            comboClassificacaoEspecial.setItems(FXCollections.observableArrayList(nomesClassificacoes));

            System.out.println("✅ " + classificacoes.size() + " classificação(ões) especial(is) carregada(s)");

        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar classificações especiais: " + e.getMessage());
            e.printStackTrace();
            comboClassificacaoEspecial.setItems(FXCollections.observableArrayList("Nenhum"));
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private void carregarAlergias() {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();

            TypedQuery<Alergia> query = em.createQuery(
                    "SELECT a FROM Alergia a " +
                            "ORDER BY CASE WHEN a.nomeAlergia = 'Nenhum' THEN 0 ELSE 1 END, a.nomeAlergia",
                    Alergia.class
            );

            List<Alergia> alergias = query.getResultList();

            List<String> nomesAlergias = alergias.stream()
                    .map(Alergia::getNomeAlergia)
                    .toList();

            comboAlergias.setItems(FXCollections.observableArrayList(nomesAlergias));

            System.out.println("✅ " + alergias.size() + " alergia(s) carregada(s)");

        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar alergias: " + e.getMessage());
            e.printStackTrace();
            comboAlergias.setItems(FXCollections.observableArrayList("Nenhum"));
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private void carregarTiposAuxilio() {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();

            TypedQuery<TipoAuxilio> query = em.createQuery(
                    "SELECT t FROM TipoAuxilio t " +
                            "ORDER BY CASE WHEN t.nomeAuxilio = 'Nenhum' THEN 0 ELSE 1 END, t.nomeAuxilio",
                    TipoAuxilio.class
            );

            List<TipoAuxilio> tiposAuxilio = query.getResultList();

            List<String> nomesAuxilios = tiposAuxilio.stream()
                    .map(TipoAuxilio::getNomeAuxilio)
                    .toList();

            comboTipoAuxilio.setItems(FXCollections.observableArrayList(nomesAuxilios));

            System.out.println("✅ " + tiposAuxilio.size() + " tipo(s) de auxílio carregado(s)");

        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar tipos de auxílio: " + e.getMessage());
            e.printStackTrace();
            comboTipoAuxilio.setItems(FXCollections.observableArrayList("Nenhum"));
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // === PESQUISAR MÃES ===
    @FXML
    private void pesquisarMae() {
        String termoPesquisa = fieldPesquisaMae.getText().trim();

        if (termoPesquisa.isEmpty()) {
            carregarMaes();
        } else {
            // Pesquisa mães pelo nome
            pesquisarResponsaveisPorTipoENome(2L, termoPesquisa, cardsContainerMaes, "Nenhuma mãe encontrada");
        }
    }

    // === PESQUISAR PAIS ===
    @FXML
    private void pesquisarPai() {
        String termoPesquisa = fieldPesquisaPai.getText().trim();

        if (termoPesquisa.isEmpty()) {
            // Se campo vazio, carrega todos os pais
            carregarPais();
        } else {
            // Pesquisa pais pelo nome
            pesquisarResponsaveisPorTipoENome(1L, termoPesquisa, cardsContainerPais, "Nenhum pai encontrado");
        }
    }

    // === PESQUISAR RESPONSÁVEIS ===
    @FXML
    private void pesquisarResponsavel() {
        String termoPesquisa = fieldPesquisaResponsavel.getText().trim();

        if (termoPesquisa.isEmpty()) {
            carregarResponsaveis();
        } else {
            pesquisarResponsaveisPorTipoENome(3L, termoPesquisa, cardsContainerResponsaveis, "Nenhum responsável encontrado");
        }
    }

    // === MÉTODO GENÉRICO PARA PESQUISAR POR TIPO E NOME ===
    private void pesquisarResponsaveisPorTipoENome(Long tipoId, String termoPesquisa, VBox container, String mensagemVazio) {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();

            List<Responsavel> resultados = em.createQuery(
                            "SELECT r FROM Responsavel r " +
                                    "JOIN FETCH r.pessoa p " +
                                    "JOIN r.tipoResponsavel tr " +
                                    "WHERE tr.id = :tipoId " +
                                    "AND (UPPER(p.nome) LIKE UPPER(:termo) " +
                                    "     OR UPPER(p.cpf) LIKE UPPER(:termo)) " +
                                    "ORDER BY p.nome",
                            Responsavel.class
                    )
                    .setParameter("tipoId", tipoId)
                    .setParameter("termo", "%" + termoPesquisa + "%")
                    .setMaxResults(10)
                    .getResultList();

            atualizarCardsContainer(container, resultados, mensagemVazio);

            System.out.println("🔍 " + resultados.size() + " resultado(s) encontrado(s) para: " + termoPesquisa);

        } catch (Exception e) {
            System.err.println("❌ Erro na pesquisa: " + e.getMessage());
            e.printStackTrace();
            limparContainerComMensagem(container, "Erro na pesquisa");
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // === CARREGAR MÃES ===
    private void carregarMaes() {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();

            List<Responsavel> maes = em.createQuery(
                    "SELECT r FROM Responsavel r " +
                            "JOIN FETCH r.pessoa p " +
                            "JOIN r.tipoResponsavel tr " +
                            "WHERE tr.id = 2 " + // Mãe
                            "ORDER BY r.id DESC",
                    Responsavel.class
            ).setMaxResults(5).getResultList();

            atualizarCardsContainer(cardsContainerMaes, maes, "Nenhuma mãe cadastrada");
            System.out.println("✅ " + maes.size() + " mãe(s) carregada(s)");

        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar mães: " + e.getMessage());
            limparContainerComMensagem(cardsContainerMaes, "Erro ao carregar mães");
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // === CARREGAR PAIS ===
    private void carregarPais() {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();

            List<Responsavel> pais = em.createQuery(
                    "SELECT r FROM Responsavel r " +
                            "JOIN FETCH r.pessoa p " +
                            "JOIN r.tipoResponsavel tr " +
                            "WHERE tr.id = 1 " + // Pai
                            "ORDER BY r.id DESC",
                    Responsavel.class
            ).setMaxResults(5).getResultList();

            atualizarCardsContainer(cardsContainerPais, pais, "Nenhum pai cadastrado");
            System.out.println("✅ " + pais.size() + " pai(s) carregado(s)");

        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar pais: " + e.getMessage());
            limparContainerComMensagem(cardsContainerPais, "Erro ao carregar pais");
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // === CARREGAR RESPONSÁVEIS ===
    private void carregarResponsaveis() {
        EntityManager em = null;
        try {
            em = DBConnection.getEntityManager();

            List<Responsavel> responsaveis = em.createQuery(
                    "SELECT r FROM Responsavel r " +
                            "JOIN FETCH r.pessoa p " +
                            "JOIN r.tipoResponsavel tr " +
                            "WHERE tr.id = 3 " + // Responsável
                            "ORDER BY r.id DESC",
                    Responsavel.class
            ).setMaxResults(5).getResultList();

            atualizarCardsContainer(cardsContainerResponsaveis, responsaveis, "Nenhum responsável cadastrado");
            System.out.println("✅ " + responsaveis.size() + " responsável(eis) carregado(s)");

        } catch (Exception e) {
            System.err.println("❌ Erro ao carregar responsáveis: " + e.getMessage());
            limparContainerComMensagem(cardsContainerResponsaveis, "Erro ao carregar responsáveis");
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    // === MÉTODO GENÉRICO PARA ATUALIZAR CONTAINER ===
    private void atualizarCardsContainer(VBox container, List<Responsavel> responsaveis, String mensagemVazio) {
        container.getChildren().clear();

        if (responsaveis == null || responsaveis.isEmpty()) {
            EmptyCard vazio = new EmptyCard(mensagemVazio);
            container.getChildren().add(vazio);
        } else {
            for (Responsavel responsavel : responsaveis) {
                try {
                    ResponsavelCard card = new ResponsavelCard(responsavel);
                    //card.setOnEditAction(() -> editarResponsavel(responsavel));
                    container.getChildren().add(card);
                } catch (Exception e) {
                    System.err.println("❌ Erro ao criar card: " + e.getMessage());
                    Label erroCard = new Label("Erro ao carregar");
                    erroCard.setStyle("-fx-background-color: #ffebee; -fx-border-color: #f44336; " +
                            "-fx-padding: 10; -fx-border-radius: 5;");
                    container.getChildren().add(erroCard);
                }
            }
        }
    }

    // === CONFIGURAR PESQUISA POR ENTER ===
    private void configurarPesquisaPorEnter() {
        // Mãe
        fieldPesquisaMae.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                pesquisarMae();
            }
        });

        // Pai
        fieldPesquisaPai.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                pesquisarPai();
            }
        });

        // Responsável
        fieldPesquisaResponsavel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                pesquisarResponsavel();
            }
        });
    }

    // === MÉTODO PARA LIMPAR CONTAINER COM MENSAGEM ===
    private void limparContainerComMensagem(VBox container, String mensagem) {
        container.getChildren().clear();
        EmptyCard erro = new EmptyCard(mensagem);
        container.getChildren().add(erro);
    }

    // Métodos para abrir os modais
    @FXML
    private void abrirCadastroResponsavel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/salo/sistemacreche/extracadastro/filiaçãoResponsavel.fxml"));
            Parent root = loader.load();

            FiliacaoResponsavelController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Responsável");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(btnSalvar.getScene().getWindow());

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            // Se salvou, você pode adicionar à lista ou fazer algo com os dados
            if (controller.isSalvo()) {
                // Recarregar a lista de responsáveis após salvar
                carregarMaes();
                carregarPais();
                carregarResponsaveis();
                System.out.println("✅ Responsável salvo com sucesso!");
            }

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensagem("Erro", "Erro ao abrir cadastro de responsável");
        }
    }

    @FXML
    private void abrirCadastroMembroFamiliar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/salo/sistemacreche/extracadastro/membrofamiliar.fxml"));
            Parent root = loader.load();

            MembroFamiliarController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Membro Familiar");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(btnSalvar.getScene().getWindow());

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            if (controller.isSalvo()) {
                MembroFamiliarController.DadosMembroFamiliar dados = controller.getDadosMembro();
                adicionarMembroFamiliar(dados);
                System.out.println("✅ Membro familiar adicionado: " + dados.getNome());
            }

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensagem("Erro", "Erro ao abrir cadastro de membro familiar");
        }
    }

    @FXML
    private void abrirCadastroPessoaAutorizada() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/salo/sistemacreche/extracadastro/pessoaautorizada.fxml"));
            Parent root = loader.load();

            PessoaAutorizadaController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Pessoa Autorizada");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(btnSalvar.getScene().getWindow());

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            // Se salvou, você pode adicionar à lista ou fazer algo com os dados
            if (controller.isSalvo()) {
                System.out.println("✅ Pessoa autorizada salva com sucesso!");
                // TODO: Adicionar à tabela de pessoas autorizadas
            }

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensagem("Erro", "Erro ao abrir cadastro de pessoa autorizada");
        }
    }

    private void configurarTableViews() {
        configurarTableViewComposicaoFamiliar();
        configurarTableViewPessoasAutorizadas();
    }

    private void configurarTableViewComposicaoFamiliar() {
        // Limpa colunas existentes
        tableComposicaoFamiliar.getColumns().clear();

        // Cria as colunas
        TableColumn<MembroFamilia, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(150);

        TableColumn<MembroFamilia, String> colIdade = new TableColumn<>("Idade");
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colIdade.setPrefWidth(80);

        TableColumn<MembroFamilia, String> colParentesco = new TableColumn<>("Parentesco");
        colParentesco.setCellValueFactory(new PropertyValueFactory<>("parentesco"));
        colParentesco.setPrefWidth(100);

        TableColumn<MembroFamilia, String> colEscolaridade = new TableColumn<>("Escolaridade");
        colEscolaridade.setCellValueFactory(new PropertyValueFactory<>("escolaridade"));
        colEscolaridade.setPrefWidth(120);

        TableColumn<MembroFamilia, String> colEmprego = new TableColumn<>("Emprego");
        colEmprego.setCellValueFactory(new PropertyValueFactory<>("emprego"));
        colEmprego.setPrefWidth(120);

        TableColumn<MembroFamilia, String> colRenda = new TableColumn<>("Renda");
        colRenda.setCellValueFactory(new PropertyValueFactory<>("renda"));
        colRenda.setPrefWidth(100);

        // Adiciona as colunas à tabela
        tableComposicaoFamiliar.getColumns().addAll(colNome, colIdade, colParentesco, colEscolaridade, colEmprego, colRenda);

        // Conecta a ObservableList com a TableView
        tableComposicaoFamiliar.setItems(membrosFamiliares);
    }

    private void configurarTableViewPessoasAutorizadas() {
        // Limpa colunas existentes
        tablePessoasAutorizadas.getColumns().clear();

        // Cria as colunas para pessoas autorizadas
        TableColumn<PessoaAutorizada, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNome.setPrefWidth(150);

        TableColumn<PessoaAutorizada, String> colParentesco = new TableColumn<>("Parentesco");
        colParentesco.setCellValueFactory(new PropertyValueFactory<>("parentesco"));
        colParentesco.setPrefWidth(100);

        TableColumn<PessoaAutorizada, String> colRg = new TableColumn<>("RG");
        colRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
        colRg.setPrefWidth(120);

        TableColumn<PessoaAutorizada, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colTelefone.setPrefWidth(120);

        // Adiciona as colunas à tabela
        tablePessoasAutorizadas.getColumns().addAll(colNome, colParentesco, colRg, colTelefone);

        // Conecta a ObservableList com a TableView
        tablePessoasAutorizadas.setItems(pessoaAutorizadas);
    }

    @FXML
    public void salvarMatricula() {
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            if (!validarCamposObrigatorios()) {
                return;
            }

            em = DBConnection.getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();

            // 1. Criar e salvar a Criança
            Crianca crianca = criarCrianca(em);
            em.persist(crianca);

            // 2. Criar e salvar Endereço
            Endereco endereco = criarEndereco();
            em.persist(endereco);

            // 3. Criar e salvar Matrícula
            Matricula matricula = criarMatricula(crianca);
            em.persist(matricula);

            // 4. Salvar Membros da Família
            salvarMembrosFamilia(em, crianca);

            // 5. Salvar Pessoas Autorizadas
            salvarPessoasAutorizadas(em, crianca);

            // 6. Salvar Composição Familiar
            salvarComposicaoFamiliar(em, crianca);

            transaction.commit();

            mostrarMensagem("Sucesso", "Matrícula cadastrada com sucesso!");
            limparFormulario();

            System.out.println("✅ Matrícula salva com ID: " + matricula.getId());

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("❌ Erro ao salvar matrícula: " + e.getMessage());
            e.printStackTrace();
            mostrarMensagem("Erro", "Erro ao salvar matrícula: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private Crianca criarCrianca(EntityManager em) {
        Crianca crianca = new Crianca();

        // Dados básicos
        crianca.setNome(fieldNomeCrianca.getText().trim());
        crianca.setDataNascimento(java.sql.Date.valueOf(datePickerNascimento.getValue()));

        // Sexo
        String sexoValue = comboSexo.getValue();
        if (sexoValue != null) {
            crianca.setSexo(Crianca.Sexo.valueOf(sexoValue.toUpperCase()));
        }

        // Cor/Raça
        String corRacaValue = comboCorRaca.getValue();
        if (corRacaValue != null) {
            crianca.setCorRaca(Crianca.CorRaca.valueOf(corRacaValue.toUpperCase()));
        }

        // Saúde
        crianca.setCartSus(fieldSus.getText().trim());
        crianca.setUnidadeSaude(fieldUnidadeSaude.getText().trim());

        // Restrição alimentar
        String restricaoValue = comboRestricaoAlimentar.getValue();
        crianca.setRestricaoAlimentar("Sim".equals(restricaoValue));
        if ("Sim".equals(restricaoValue)) {
            crianca.setDescricaoRestricoesAlimentares("Restrição alimentar informada");
        }

        // Mobilidade reduzida
        String mobilidadeValue = comboMobilidadeReduzida.getValue();
        if (mobilidadeValue != null) {
            if (mobilidadeValue.contains("temporária")) {
                crianca.setMobRed(Crianca.MobRed.TEMPORARIA);
            } else if (mobilidadeValue.contains("permanente")) {
                crianca.setMobRed(Crianca.MobRed.PERMANENTE);
            } else {
                crianca.setMobRed(Crianca.MobRed.NENHUMA);
            }
        }

        // Educação especial
        String educacaoEspecialValue = comboEducacaoEspecial.getValue();
        crianca.setEducEspecial("Sim".equals(educacaoEspecialValue));

        // Classificação especial
        String classificacaoValue = comboClassificacaoEspecial.getValue();
        if (classificacaoValue != null && !classificacaoValue.equals("Nenhum")) {
            ClassificacaoEspecial classificacao = em.createQuery(
                    "SELECT c FROM ClassificacaoEspecial c WHERE c.classificacaoEspecial = :nome",
                    ClassificacaoEspecial.class
            ).setParameter("nome", classificacaoValue).getSingleResult();
            crianca.setClassificacaoEspecial(classificacao);
            crianca.setStatusClassificacaoEspecial(true);
        }

        // Alergias
        String alergiaValue = comboAlergias.getValue();
        crianca.setAlergia(alergiaValue != null && !alergiaValue.equals("Nenhum"));

        // Irmão gêmeo
        crianca.setPossuiIrmaoGemeo(checkIrmaoGemeo.isSelected());

        // Documentos
        crianca.setCertidaoNascimentoNum(fieldCertidaoNascimento.getText().trim());
        crianca.setMunicipioNascimento(fieldMunicipioNascimento.getText().trim());
        crianca.setCartorioRegistro(fieldCartorioRegistro.getText().trim());

        // Auxílio governo
        String auxilioValue = comboTipoAuxilio.getValue();
        crianca.setResponsavelBeneficiarioAuxilioGov(auxilioValue != null && !auxilioValue.equals("Nenhum"));
        if (auxilioValue != null && !auxilioValue.equals("Nenhum")) {
            TipoAuxilio tipoAuxilio = em.createQuery(
                    "SELECT t FROM TipoAuxilio t WHERE t.nomeAuxilio = :nome",
                    TipoAuxilio.class
            ).setParameter("nome", auxilioValue).getSingleResult();
            crianca.setTipoAuxilio(tipoAuxilio);
        }

        return crianca;
    }

    private Endereco criarEndereco() {
        Endereco endereco = new Endereco();

        endereco.setLogradouro(fieldEndereco.getText().trim());
        endereco.setNumero(fieldNumero.getText().trim());
        endereco.setBairro(fieldBairro.getText().trim());
        endereco.setMunicipio(fieldMunicipio.getText().trim());
        endereco.setCep(fieldCEP.getText().trim());
        endereco.setUf(comboUF.getValue());
        endereco.setPontoReferencia(fieldPontoReferencia.getText().trim());
        endereco.setTelefoneResidencial(fieldTelefoneResidencial.getText().trim());

        return endereco;
    }

    private Matricula criarMatricula(Crianca crianca) {
        Matricula matricula = new Matricula();

        matricula.setCrianca(crianca);
        matricula.setDataMatricula(new java.sql.Date(System.currentTimeMillis()));
        matricula.setSerie(comboSerie.getValue());

        // Ano letivo
        try {
            matricula.setAnoLetivo(Integer.parseInt(fieldAnoLetivo.getText().trim()));
        } catch (NumberFormatException e) {
            matricula.setAnoLetivo(java.time.LocalDate.now().getYear());
        }

        matricula.setOrientacaoRecebida(false);
        matricula.setSituacaoMatricula(Matricula.SituacaoMatricula.ATIVA);

        // Data de vencimento (1 ano a partir de hoje)
        java.time.LocalDate hoje = java.time.LocalDate.now();
        java.time.LocalDate vencimento = hoje.plusYears(1);
        matricula.setDataVencimento(java.sql.Date.valueOf(vencimento));

        return matricula;
    }

    private void salvarMembrosFamilia(EntityManager em, Crianca crianca) {
        for (MembroFamilia membro : membrosFamiliares) {
            // Cria uma nova instância para o banco
            MembroFamilia novoMembro = new MembroFamilia();
            novoMembro.setCrianca(crianca);
            novoMembro.setParentesco(membro.getParentesco());
            novoMembro.setSituacaoEscolar(membro.getSituacaoEscolar());
            novoMembro.setSituacaoEmprego(membro.getSituacaoEmprego());
            novoMembro.setRenda(membro.getRenda());

            em.persist(novoMembro);
        }

        System.out.println("✅ " + membrosFamiliares.size() + " membro(s) familiar(es) salvo(s)");
    }

    private void salvarPessoasAutorizadas(EntityManager em, Crianca crianca) {
        for (PessoaAutorizada pessoa : pessoaAutorizadas) {
            // Cria uma nova instância para o banco
            PessoaAutorizada novaPessoa = new PessoaAutorizada();
            novaPessoa.setCrianca(crianca);
            novaPessoa.setPessoa(pessoa.getPessoa());
            novaPessoa.setParentesco(pessoa.getParentesco());
            novaPessoa.setTelefone(pessoa.getTelefone());

            em.persist(novaPessoa);
        }

        System.out.println("✅ " + pessoaAutorizadas.size() + " pessoa(s) autorizada(s) salva(s)");
    }

    private void salvarComposicaoFamiliar(EntityManager em, Crianca crianca) {
        ComposicaoFamiliar composicao = new ComposicaoFamiliar();
        composicao.setCrianca(crianca);

        // Calcula renda familiar total
        java.math.BigDecimal rendaTotal = java.math.BigDecimal.ZERO;
        for (MembroFamilia membro : membrosFamiliares) {
            if (membro.getRenda() != null) {
                rendaTotal = rendaTotal.add(membro.getRenda());
            }
        }
        composicao.setRendaFamiliarTotal(rendaTotal);

        // Calcula renda per capita
        int totalMembros = membrosFamiliares.size() + 1; // +1 para a criança
        if (totalMembros > 0) {
            java.math.BigDecimal rendaPerCapita = rendaTotal.divide(
                    new java.math.BigDecimal(totalMembros), 2, java.math.RoundingMode.HALF_UP
            );
            composicao.setRendaPerCapita(rendaPerCapita);
        } else {
            composicao.setRendaPerCapita(java.math.BigDecimal.ZERO);
        }

        composicao.setTotalMembros(totalMembros);

        em.persist(composicao);
        System.out.println("✅ Composição familiar salva");
    }

    // Método para adicionar membro familiar à tabela
    public void adicionarMembroFamiliar(MembroFamiliarController.DadosMembroFamiliar dados) {
        try {
            MembroFamilia membro = new MembroFamilia();

            // Converte string para enum Parentesco
            MembroFamilia.Parentesco parentesco = converterStringParaParentesco(dados.getParentesco());
            membro.setParentesco(parentesco);

            // Converte string para enum SituacaoEscolar
            MembroFamilia.SituacaoEscolar escolaridade = converterParaSituacaoEscolar(dados.getEscolaridade());
            membro.setSituacaoEscolar(escolaridade);

            // Converte string para enum SituacaoEmprego
            MembroFamilia.SituacaoEmprego emprego = converterParaSituacaoEmprego(dados.getEmprego());
            membro.setSituacaoEmprego(emprego);

            // Converte renda para BigDecimal - CORREÇÃO AQUI
            if (!dados.getRenda().isEmpty()) {
                try {
                    // Substitui vírgula por ponto para conversão numérica
                    String rendaFormatada = dados.getRenda().replace(",", ".");
                    membro.setRenda(new java.math.BigDecimal(rendaFormatada));
                } catch (NumberFormatException e) {
                    System.err.println("❌ Erro ao converter renda: " + dados.getRenda());
                    membro.setRenda(java.math.BigDecimal.ZERO);
                }
            } else {
                membro.setRenda(java.math.BigDecimal.ZERO);
            }

            membrosFamiliares.add(membro);
            tableComposicaoFamiliar.refresh();

            System.out.println("✅ Membro familiar adicionado: " + dados.getNome());
            System.out.println("💰 Renda convertida: " + membro.getRenda());

        } catch (Exception e) {
            System.err.println("❌ Erro ao adicionar membro familiar: " + e.getMessage());
            e.printStackTrace();
            mostrarMensagem("Erro", "Erro ao adicionar membro familiar: " + e.getMessage());
        }
    }

    // Método para adicionar pessoa autorizada à tabela
    public void adicionarPessoaAutorizada(PessoaAutorizadaController.DadosPessoaAutorizada dados) {
        try {
            PessoaAutorizada pessoa = new PessoaAutorizada();

            // Cria uma nova Pessoa
            Pessoa novaPessoa = new Pessoa();
            novaPessoa.setNome(dados.getNome());
            novaPessoa.setRg(dados.getRg());
            novaPessoa.setTelefone(dados.getTelefone());
            // Você precisará definir outros campos obrigatórios da Pessoa

            pessoa.setPessoa(novaPessoa);

            // Converte string para enum Parentesco
            PessoaAutorizada.Parentesco parentesco = PessoaAutorizada.Parentesco.valueOf(
                    dados.getParentesco().toUpperCase().replace("Ã", "A")
            );
            pessoa.setParentesco(parentesco);
            pessoa.setTelefone(dados.getTelefone());

            pessoaAutorizadas.add(pessoa);
            tablePessoasAutorizadas.refresh();

        } catch (Exception e) {
            System.err.println("❌ Erro ao adicionar pessoa autorizada: " + e.getMessage());
            mostrarMensagem("Erro", "Erro ao adicionar pessoa autorizada");
        }
    }

    // Métodos de conversão (implemente conforme suas necessidades)
    private MembroFamilia.SituacaoEscolar converterParaSituacaoEscolar(String escolaridade) {
        // Implemente a lógica de conversão baseada nos valores do seu enum
        return MembroFamilia.SituacaoEscolar.NAO_INFORMADO;
    }

    private MembroFamilia.SituacaoEmprego converterParaSituacaoEmprego(String emprego) {
        // Implemente a lógica de conversão baseada nos valores do seu enum
        return MembroFamilia.SituacaoEmprego.OUTRO;
    }

    private MembroFamilia.Parentesco converterStringParaParentesco(String parentesco) {
        if (parentesco == null) return MembroFamilia.Parentesco.OUTRO;

        String parentescoUpper = parentesco.toUpperCase()
                .replace("Ã", "A")
                .replace("Õ", "O")
                .replace("Â", "A")
                .replace("Ô", "O");

        try {
            return MembroFamilia.Parentesco.valueOf(parentescoUpper);
        } catch (IllegalArgumentException e) {
            // Mapeamento para valores comuns
            switch (parentescoUpper) {
                case "MAE": case "MÃE": return MembroFamilia.Parentesco.MAE;
                case "PAI": return MembroFamilia.Parentesco.PAI;
                case "IRMAO": case "IRMÃO": return MembroFamilia.Parentesco.IRMAO;
                case "IRMA": case "IRMÃ": return MembroFamilia.Parentesco.IRMA; // ← CORREÇÃO AQUI
                case "AVO": case "AVÔ": case "AVÓ": return MembroFamilia.Parentesco.AVO;
                case "TIO": return MembroFamilia.Parentesco.TIO;
                case "TIA": return MembroFamilia.Parentesco.TIA;
                default: return MembroFamilia.Parentesco.OUTRO;
            }
        }
    }


    @FXML
    public void cancelarCadastro() {
        limparFormulario();
        // TODO: Fechar a tela ou voltar para lista
    }

    private boolean validarCamposObrigatorios() {
        // Validar campos mais importantes
        if (fieldNomeCrianca.getText().trim().isEmpty()) {
            mostrarMensagem("Erro", "Nome da criança é obrigatório!");
            fieldNomeCrianca.requestFocus();
            return false;
        }

        if (comboSexo.getValue() == null) {
            mostrarMensagem("Erro", "Sexo é obrigatório!");
            comboSexo.requestFocus();
            return false;
        }

        if (datePickerNascimento.getValue() == null) {
            mostrarMensagem("Erro", "Data de nascimento é obrigatória!");
            datePickerNascimento.requestFocus();
            return false;
        }

        if (comboSerie.getValue() == null) {
            mostrarMensagem("Erro", "Série é obrigatória!");
            comboSerie.requestFocus();
            return false;
        }

        return true;
    }

    private void limparFormulario() {
        // Limpar campos básicos
        fieldNomeCrianca.clear();
        fieldRgCrianca.clear();
        datePickerNascimento.setValue(null);
        comboSexo.setValue(null);
        comboCorRaca.setValue(null);
        fieldSus.clear();
        fieldUnidadeSaude.clear();

        // Limpar combos do banco
        comboClassificacaoEspecial.setValue(null);
        comboAlergias.setValue(null);
        comboTipoAuxilio.setValue(null);

        // Limpar endereço
        fieldEndereco.clear();
        fieldBairro.clear();
        fieldMunicipio.clear();
        fieldNumero.clear();
        fieldCEP.clear();
        comboUF.setValue(null);

        // Limpar série
        comboSerie.setValue(null);
        fieldAnoLetivo.clear();
    }

    private void mostrarMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void importarAnexo() {
        System.out.println("Importando anexo...");
    }
}