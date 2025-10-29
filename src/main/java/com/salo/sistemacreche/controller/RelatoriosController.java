package com.salo.sistemacreche.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RelatoriosController {

    @FXML private RadioButton radioMatriculasAtivas;
    @FXML private RadioButton radioMatriculasVencidas;
    @FXML private RadioButton radioAlunosIdade;
    @FXML private RadioButton radioAlunosTurma;
    @FXML private RadioButton radioFrequencia;
    @FXML private RadioButton radioFinanceiro;

    @FXML private DatePicker datePickerInicio;
    @FXML private DatePicker datePickerFim;

    @FXML private Button btnGerarRelatorio;
    @FXML private Button btnExportarPdf;
    @FXML private Button btnImprimir;

    @FXML private TextArea textAreaRelatorio;

    private ToggleGroup toggleGroupTipoRelatorio;

    @FXML
    public void initialize() {
        configurarRadioButtons();
        configurarBotoes();
    }

    private void configurarRadioButtons() {
        toggleGroupTipoRelatorio = new ToggleGroup();

        radioMatriculasAtivas.setToggleGroup(toggleGroupTipoRelatorio);
        radioMatriculasVencidas.setToggleGroup(toggleGroupTipoRelatorio);
        radioAlunosIdade.setToggleGroup(toggleGroupTipoRelatorio);
        radioAlunosTurma.setToggleGroup(toggleGroupTipoRelatorio);
        radioFrequencia.setToggleGroup(toggleGroupTipoRelatorio);
        radioFinanceiro.setToggleGroup(toggleGroupTipoRelatorio);

        // Selecionar o primeiro por padrão
        radioMatriculasAtivas.setSelected(true);
    }

    private void configurarBotoes() {
        // As ações já estão configuradas no FXML via onAction
    }

    @FXML
    public void gerarRelatorio() {
        System.out.println("Gerando relatório...");

        String tipoRelatorio = obterTipoRelatorioSelecionado();
        System.out.println("Tipo de relatório: " + tipoRelatorio);

        // Simular geração de relatório
        String relatorio = simularRelatorio(tipoRelatorio);
        textAreaRelatorio.setText(relatorio);
    }

    @FXML
    public void exportarPdf() {
        System.out.println("Exportando para PDF...");
        // TODO: Implementar exportação para PDF
        mostrarAlerta("Exportar PDF", "Funcionalidade em desenvolvimento", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void imprimirRelatorio() {
        System.out.println("Imprimindo relatório...");
        // TODO: Implementar impressão
        mostrarAlerta("Imprimir", "Funcionalidade em desenvolvimento", Alert.AlertType.INFORMATION);
    }

    private String obterTipoRelatorioSelecionado() {
        RadioButton selecionado = (RadioButton) toggleGroupTipoRelatorio.getSelectedToggle();
        return selecionado != null ? selecionado.getText() : "Nenhum";
    }

    private String simularRelatorio(String tipo) {
        return "Relatório de " + tipo + " - Creche Estrela do Oriente\n" +
                "Período: " + datePickerInicio.getValue() + " a " + datePickerFim.getValue() + "\n\n" +
                "Total de alunos: 145\n" +
                "Matrículas ativas: 142\n" +
                "Matrículas pendentes: 3\n\n" +
                "Este é um relatório simulado.\n" +
                "Em produção, aqui viriam os dados reais do banco de dados.";
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}