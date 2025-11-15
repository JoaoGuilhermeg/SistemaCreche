package com.salo.sistemacreche.controller.extracadastro;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FiliacaoResponsavelController {

    @FXML private TextField fieldNome;
    @FXML private TextField fieldCpf;
    @FXML private TextField fieldRg;
    @FXML private TextField fieldCelular;
    @FXML private TextField fieldOutroContato;
    @FXML private TextField fieldTrabalho;

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
    private void salvarResponsavel() {
        if (validarCampos()) {
            // Lógica para salvar responsável
            System.out.println("Salvando responsável: " + fieldNome.getText());
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

        if (fieldCpf.getText().trim().isEmpty()) {
            mostrarErro("CPF é obrigatório!");
            fieldCpf.requestFocus();
            return false;
        }

        if (fieldRg.getText().trim().isEmpty()) {
            mostrarErro("RG é obrigatório!");
            fieldRg.requestFocus();
            return false;
        }

        if (fieldCelular.getText().trim().isEmpty()) {
            mostrarErro("Celular é obrigatório!");
            fieldCelular.requestFocus();
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
    public String getCpf() { return fieldCpf.getText(); }
    public String getRg() { return fieldRg.getText(); }
    public String getCelular() { return fieldCelular.getText(); }
    public String getOutroContato() { return fieldOutroContato.getText(); }
    public String getTrabalho() { return fieldTrabalho.getText(); }

    // Método para obter todos os dados como objeto
    public DadosResponsavel getDadosResponsavel() {
        return new DadosResponsavel(
                getNome(),
                getCpf(),
                getRg(),
                getCelular(),
                getOutroContato(),
                getTrabalho()
        );
    }

    // Classe interna para transportar dados
    public static class DadosResponsavel {
        private String nome;
        private String cpf;
        private String rg;
        private String celular;
        private String outroContato;
        private String trabalho;

        public DadosResponsavel(String nome, String cpf, String rg, String celular,
                                String outroContato, String trabalho) {
            this.nome = nome;
            this.cpf = cpf;
            this.rg = rg;
            this.celular = celular;
            this.outroContato = outroContato;
            this.trabalho = trabalho;
        }

        // Getters
        public String getNome() { return nome; }
        public String getCpf() { return cpf; }
        public String getRg() { return rg; }
        public String getCelular() { return celular; }
        public String getOutroContato() { return outroContato; }
        public String getTrabalho() { return trabalho; }
    }
}