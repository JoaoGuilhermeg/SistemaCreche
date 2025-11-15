package com.salo.sistemacreche.controller.extracadastro;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PessoaAutorizadaController {

    @FXML private TextField fieldNome;
    @FXML private TextField fieldParentesco;
    @FXML private TextField fieldRg;
    @FXML private TextField fieldTelefone;

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
    private void salvarPessoaAutorizada() {
        if (validarCampos()) {
            // Lógica para salvar pessoa autorizada
            System.out.println("Salvando pessoa autorizada: " + fieldNome.getText());
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

        if (fieldParentesco.getText().trim().isEmpty()) {
            mostrarErro("Parentesco é obrigatório!");
            fieldParentesco.requestFocus();
            return false;
        }

        if (fieldRg.getText().trim().isEmpty()) {
            mostrarErro("RG é obrigatório!");
            fieldRg.requestFocus();
            return false;
        }

        if (fieldTelefone.getText().trim().isEmpty()) {
            mostrarErro("Telefone é obrigatório!");
            fieldTelefone.requestFocus();
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
    public String getParentesco() { return fieldParentesco.getText(); }
    public String getRg() { return fieldRg.getText(); }
    public String getTelefone() { return fieldTelefone.getText(); }

    // Método para obter todos os dados como objeto
    public DadosPessoaAutorizada getDadosPessoa() {
        return new DadosPessoaAutorizada(
                getNome(),
                getParentesco(),
                getRg(),
                getTelefone()
        );
    }

    // Classe interna para transportar dados
    public static class DadosPessoaAutorizada {
        private String nome;
        private String parentesco;
        private String rg;
        private String telefone;

        public DadosPessoaAutorizada(String nome, String parentesco, String rg, String telefone) {
            this.nome = nome;
            this.parentesco = parentesco;
            this.rg = rg;
            this.telefone = telefone;
        }

        // Getters
        public String getNome() { return nome; }
        public String getParentesco() { return parentesco; }
        public String getRg() { return rg; }
        public String getTelefone() { return telefone; }
    }
}