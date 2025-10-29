package com.salo.sistemacreche.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CadastroMatriculaController {

    // Seção 1: Identificação da Criança
    @FXML private TextField fieldNomeCrianca;
    @FXML private TextField fieldRgCrianca;
    @FXML private DatePicker datePickerNascimento;
    @FXML private ComboBox<String> comboSexo;
    @FXML private TextField fieldSus;
    @FXML private TextField fieldUnidadeSaude;

    // Seção 2: Filiação/Responsáveis - Mãe
    @FXML private TextField fieldNomeMae;
    @FXML private TextField fieldCpfMae;
    @FXML private TextField fieldRgMae;

    // Seção 2: Filiação/Responsáveis - Pai
    @FXML private TextField fieldNomePai;
    @FXML private TextField fieldCpfPai;
    @FXML private TextField fieldRgPai;

    // Botões
    @FXML private Button btnCancelar;
    @FXML private Button btnSalvar;

    @FXML
    public void initialize() {
        configurarComboBox();
        configurarBotoes();
    }

    private void configurarComboBox() {
        // Configurar opções do ComboBox de sexo
        comboSexo.getItems().addAll("Masculino", "Feminino", "Outro");
    }

    private void configurarBotoes() {
        // As ações já estão configuradas no FXML via onAction
    }

    @FXML
    public void salvarMatricula() {
        if (validarFormulario()) {
            System.out.println("Salvando matrícula...");

            // Capturar dados do formulário
            String nomeCrianca = fieldNomeCrianca.getText();
            String rgCrianca = fieldRgCrianca.getText();
            String sexo = comboSexo.getValue();
            String sus = fieldSus.getText();

            String nomeMae = fieldNomeMae.getText();
            String cpfMae = fieldCpfMae.getText();

            String nomePai = fieldNomePai.getText();
            String cpfPai = fieldCpfPai.getText();

            // TODO: Implementar lógica de salvamento no banco
            System.out.println("Dados capturados:");
            System.out.println("Criança: " + nomeCrianca);
            System.out.println("Mãe: " + nomeMae);
            System.out.println("Pai: " + nomePai);

            // Limpar formulário após salvar
            limparFormulario();

            // Mostrar mensagem de sucesso
            mostrarAlerta("Sucesso", "Matrícula cadastrada com sucesso!", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void cancelarCadastro() {
        System.out.println("Cancelando cadastro...");
        limparFormulario();
        // TODO: Navegar de volta para a lista de matrículas
    }

    private boolean validarFormulario() {
        // Validar campos obrigatórios
        if (fieldNomeCrianca.getText().isEmpty()) {
            mostrarAlerta("Erro", "Nome da criança é obrigatório!", Alert.AlertType.ERROR);
            fieldNomeCrianca.requestFocus();
            return false;
        }

        if (fieldNomeMae.getText().isEmpty()) {
            mostrarAlerta("Erro", "Nome da mãe é obrigatório!", Alert.AlertType.ERROR);
            fieldNomeMae.requestFocus();
            return false;
        }

        // Adicionar mais validações conforme necessário

        return true;
    }

    private void limparFormulario() {
        // Limpar todos os campos
        fieldNomeCrianca.clear();
        fieldRgCrianca.clear();
        datePickerNascimento.setValue(null);
        comboSexo.setValue(null);
        fieldSus.clear();
        fieldUnidadeSaude.clear();

        fieldNomeMae.clear();
        fieldCpfMae.clear();
        fieldRgMae.clear();

        fieldNomePai.clear();
        fieldCpfPai.clear();
        fieldRgPai.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}