package com.salo.sistemacreche.controller.extracadastro;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MembroFamiliarController {

    @FXML private TextField fieldNome;
    @FXML private TextField fieldIdade;
    @FXML private TextField fieldParentesco;
    @FXML private TextField fieldEscolaridade;
    @FXML private TextField fieldEmprego;
    @FXML private TextField fieldRenda;

    private Stage dialogStage;
    private boolean salvo = false;

    @FXML
    public void initialize() {
        // Configurações iniciais se necessário
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSalvo() {
        return salvo;
    }

    @FXML
    private void salvarMembro() {
        if (validarCampos()) {
            // Lógica para salvar membro familiar
            System.out.println("Salvando membro familiar: " + fieldNome.getText());
            this.salvo = true;
            fecharDialog();
        }
    }

    @FXML
    private void fecharDialog() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    private boolean validarCampos() {
        if (fieldNome.getText().trim().isEmpty()) {
            mostrarErro("Nome é obrigatório!");
            fieldNome.requestFocus();
            return false;
        }

        if (fieldIdade.getText().trim().isEmpty()) {
            mostrarErro("Idade é obrigatória!");
            fieldIdade.requestFocus();
            return false;
        }

        // Validar se idade é numérica
        try {
            Integer.parseInt(fieldIdade.getText());
        } catch (NumberFormatException e) {
            mostrarErro("Idade deve ser um número válido!");
            fieldIdade.requestFocus();
            return false;
        }

        if (fieldParentesco.getText().trim().isEmpty()) {
            mostrarErro("Parentesco é obrigatório!");
            fieldParentesco.requestFocus();
            return false;
        }

        return true;
    }

    private void mostrarErro(String mensagem) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR
        );
        alert.setTitle("Erro de Validação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Getters para os dados
    public String getNome() { return fieldNome.getText(); }
    public int getIdade() {
        try {
            return Integer.parseInt(fieldIdade.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    public String getParentesco() { return fieldParentesco.getText(); }
    public String getEscolaridade() { return fieldEscolaridade.getText(); }
    public String getEmprego() { return fieldEmprego.getText(); }
    public String getRenda() { return fieldRenda.getText(); }

    // Método para obter todos os dados como objeto (opcional)
    public DadosMembroFamiliar getDadosMembro() {
        return new DadosMembroFamiliar(
                getNome(),
                getIdade(),
                getParentesco(),
                getEscolaridade(),
                getEmprego(),
                getRenda()
        );
    }

    // Classe interna para transportar dados
    public static class DadosMembroFamiliar {
        private String nome;
        private int idade;
        private String parentesco;
        private String escolaridade;
        private String emprego;
        private String renda;

        public DadosMembroFamiliar(String nome, int idade, String parentesco,
                                   String escolaridade, String emprego, String renda) {
            this.nome = nome;
            this.idade = idade;
            this.parentesco = parentesco;
            this.escolaridade = escolaridade;
            this.emprego = emprego;
            this.renda = renda;
        }

        // Getters
        public String getNome() { return nome; }
        public int getIdade() { return idade; }
        public String getParentesco() { return parentesco; }
        public String getEscolaridade() { return escolaridade; }
        public String getEmprego() { return emprego; }
        public String getRenda() { return renda; }
    }
}