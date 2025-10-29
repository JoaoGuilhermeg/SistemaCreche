package com.salo.sistemacreche.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DeclaracoesController {

    @FXML private RadioButton radioDeclaracaoMatricula;
    @FXML private RadioButton radioDeclaracaoFrequencia;
    @FXML private RadioButton radioDeclaracaoTransferencia;
    @FXML private RadioButton radioDeclaracaoQuitacao;
    @FXML private RadioButton radioAtestadoEscolar;

    @FXML private ComboBox<String> comboAlunos;

    @FXML private Button btnGerarDeclaracao;
    @FXML private Button btnImprimirDeclaracao;
    @FXML private Button btnSalvarPdf;

    @FXML private TextArea textAreaDeclaracao;

    private ToggleGroup toggleGroupTipoDeclaracao;

    @FXML
    public void initialize() {
        configurarRadioButtons();
        configurarComboBox();
        configurarBotoes();
    }

    private void configurarRadioButtons() {
        toggleGroupTipoDeclaracao = new ToggleGroup();

        radioDeclaracaoMatricula.setToggleGroup(toggleGroupTipoDeclaracao);
        radioDeclaracaoFrequencia.setToggleGroup(toggleGroupTipoDeclaracao);
        radioDeclaracaoTransferencia.setToggleGroup(toggleGroupTipoDeclaracao);
        radioDeclaracaoQuitacao.setToggleGroup(toggleGroupTipoDeclaracao);
        radioAtestadoEscolar.setToggleGroup(toggleGroupTipoDeclaracao);

        // Selecionar o primeiro por padrão
        radioDeclaracaoMatricula.setSelected(true);
    }

    private void configurarComboBox() {
        // Simular dados de alunos
        comboAlunos.getItems().addAll(
                "João da Silva",
                "Maria Oliveira",
                "Pedro Santos",
                "Ana Costa",
                "Lucas Fernandes"
        );
    }

    private void configurarBotoes() {
        // As ações já estão configuradas no FXML via onAction
    }

    @FXML
    public void gerarDeclaracao() {
        System.out.println("Gerando declaração...");

        String tipoDeclaracao = obterTipoDeclaracaoSelecionado();
        String alunoSelecionado = comboAlunos.getValue();

        if (alunoSelecionado == null || alunoSelecionado.isEmpty()) {
            mostrarAlerta("Erro", "Selecione um aluno!", Alert.AlertType.ERROR);
            return;
        }

        System.out.println("Tipo: " + tipoDeclaracao + ", Aluno: " + alunoSelecionado);

        // Simular geração de declaração
        String declaracao = simularDeclaracao(tipoDeclaracao, alunoSelecionado);
        textAreaDeclaracao.setText(declaracao);
    }

    @FXML
    public void imprimirDeclaracao() {
        System.out.println("Imprimindo declaração...");
        // TODO: Implementar impressão
        mostrarAlerta("Imprimir", "Funcionalidade em desenvolvimento", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void salvarPdf() {
        System.out.println("Salvando como PDF...");
        // TODO: Implementar salvamento em PDF
        mostrarAlerta("Salvar PDF", "Funcionalidade em desenvolvimento", Alert.AlertType.INFORMATION);
    }

    private String obterTipoDeclaracaoSelecionado() {
        RadioButton selecionado = (RadioButton) toggleGroupTipoDeclaracao.getSelectedToggle();
        return selecionado != null ? selecionado.getText() : "Nenhum";
    }

    private String simularDeclaracao(String tipo, String aluno) {
        return "DECLARAÇÃO DE " + tipo.toUpperCase() + "\n\n" +
                "A Creche Estrela do Oriente, situada na Rua das Flores, 123, Centro, " +
                "declara para os devidos fins que o(a) aluno(a):\n\n" +
                "NOME: " + aluno + "\n" +
                "DATA DE NASCIMENTO: 15/03/2020\n" +
                "NOME DA MÃE: Maria da Silva\n" +
                "NOME DO PAI: José da Silva\n\n" +
                "Encontra-se regularmente matriculado(a) nesta instituição " +
                "no ano letivo de 2024, na turma do Maternal II.\n\n" +
                "Esta declaração é fornecida a pedido do interessado para fins de comprovação escolar.\n\n" +
                "Local e Data: Cidade, 28 de Março de 2024.\n\n\n" +
                "_______________________________________\n" +
                "Coordenação Pedagógica\n" +
                "Creche Estrela do Oriente";
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}