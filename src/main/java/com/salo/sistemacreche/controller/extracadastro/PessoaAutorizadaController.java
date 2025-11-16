package com.salo.sistemacreche.controller.extracadastro;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        aplicarMascaraTelefone();
        aplicarMascaraRG();
        aplicarMascaraNome();
        aplicarMascaraParentesco();
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
            try {
                // Pega todos os dados FORMATADOS para o banco
                String nomeFormatado = getNomeFormatadoParaBanco();
                String parentescoFormatado = getParentescoFormatadoParaBanco();
                String rgFormatado = getRgFormatadoParaBanco();
                String telefoneFormatado = getTelefoneFormatadoParaBanco();

                // Exibe no console
                System.out.println("=== DADOS PESSOA AUTORIZADA PARA O BANCO ===");
                System.out.println("Nome: " + nomeFormatado);
                System.out.println("Parentesco: " + parentescoFormatado);
                System.out.println("RG: " + rgFormatado);
                System.out.println("Telefone: " + telefoneFormatado);

                // TODO: Salvar no banco de dados
                // seuRepository.salvarPessoaAutorizada(
                //     nomeFormatado, parentescoFormatado, rgFormatado, telefoneFormatado
                // );

                this.salvo = true;
                fecharDialog();

            } catch (Exception e) {
                mostrarErro("Erro ao salvar: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void cancelarPessoaAutorizada() {
        this.salvo = false;
        fecharDialog();
    }

    @FXML
    private void fecharDialog() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    private boolean validarCampos() {
        // === NOME ===
        if (fieldNome.getText().trim().isEmpty()) {
            mostrarErro("Nome é obrigatório!");
            fieldNome.requestFocus();
            return false;
        }
        if (!validarNome(fieldNome.getText())) {
            mostrarErro("Informe nome e sobrenome completos!\nEx: João Silva Oliveira");
            fieldNome.requestFocus();
            return false;
        }

        // === PARENTESCO ===
        if (fieldParentesco.getText().trim().isEmpty()) {
            mostrarErro("Parentesco é obrigatório!");
            fieldParentesco.requestFocus();
            return false;
        }
        if (!validarParentesco(fieldParentesco.getText())) {
            mostrarErro("Parentesco deve conter apenas letras!\nEx: Avó, Tio, Madrinha");
            fieldParentesco.requestFocus();
            return false;
        }

        // === RG ===
        if (fieldRg.getText().trim().isEmpty()) {
            mostrarErro("RG é obrigatório!");
            fieldRg.requestFocus();
            return false;
        }
        if (!validarRG(fieldRg.getText())) {
            mostrarErro("RG inválido!\nDeve ter entre 8 e 12 dígitos.");
            fieldRg.requestFocus();
            return false;
        }

        // === TELEFONE ===
        if (fieldTelefone.getText().trim().isEmpty()) {
            mostrarErro("Telefone é obrigatório!");
            fieldTelefone.requestFocus();
            return false;
        }
        if (!validarTelefone(fieldTelefone.getText())) {
            mostrarErro("Telefone inválido!\nFormato: (11) 99999-9999");
            fieldTelefone.requestFocus();
            return false;
        }

        return true;
    }

    // === VALIDAÇÃO DE RG ===
    private boolean validarRG(String rg) {
        if (rg == null || rg.trim().isEmpty()) return false;

        // Remove caracteres especiais, mantém números e X
        String rgLimpo = rg.replaceAll("[^0-9Xx]", "").toUpperCase();

        // RG geralmente tem entre 8 e 12 caracteres
        return rgLimpo.length() >= 8 && rgLimpo.length() <= 12;
    }

    // === FORMATAÇÃO DE RG ===
    private String formatarRG(String rg) {
        String rgLimpo = rg.replaceAll("[^0-9Xx]", "").toUpperCase();
        // Você pode adicionar formatação específica se quiser
        return rgLimpo;
    }

    public String getRgFormatadoParaBanco() {
        return formatarRG(fieldRg.getText());
    }

    // === VALIDAÇÃO DE NOME ===
    private boolean validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        // Remove espaços extras e verifica se tem pelo menos 2 palavras
        String nomeLimpo = nome.trim().replaceAll("\\s+", " ");
        String[] partes = nomeLimpo.split(" ");

        // Deve ter pelo menos nome e sobrenome
        if (partes.length < 2) {
            return false;
        }

        // Cada parte deve ter pelo menos 2 caracteres
        for (String parte : partes) {
            if (parte.length() < 2) {
                return false;
            }
        }

        // Verifica se tem apenas letras, espaços e acentos
        return nomeLimpo.matches("[a-zA-ZÀ-ÿ\\s]+");
    }

    // === FORMATAÇÃO DE NOME ===
    private String formatarNome(String nome) {
        if (nome == null) return "";

        // Remove espaços extras e formata cada palavra
        String nomeLimpo = nome.trim().replaceAll("\\s+", " ");
        String[] palavras = nomeLimpo.split(" ");
        StringBuilder nomeFormatado = new StringBuilder();

        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                // Capitaliza: maria → Maria
                String capitalizada = palavra.substring(0, 1).toUpperCase() +
                        palavra.substring(1).toLowerCase();
                nomeFormatado.append(capitalizada).append(" ");
            }
        }

        return nomeFormatado.toString().trim();
    }

    public String getNomeFormatadoParaBanco() {
        return formatarNome(fieldNome.getText());
    }

    // === VALIDAÇÃO DE PARENTESCO ===
    private boolean validarParentesco(String parentesco) {
        if (parentesco == null || parentesco.trim().isEmpty()) {
            return false;
        }

        // Remove espaços extras
        String parentescoLimpo = parentesco.trim().replaceAll("\\s+", " ");

        // Verifica se tem apenas letras e espaços
        return parentescoLimpo.matches("[a-zA-ZÀ-ÿ\\s]+");
    }

    // === FORMATAÇÃO DE PARENTESCO ===
    private String formatarParentesco(String parentesco) {
        if (parentesco == null) return "";

        // Remove espaços extras e formata
        String parentescoLimpo = parentesco.trim().replaceAll("\\s+", " ");

        // Capitaliza a primeira letra
        if (!parentescoLimpo.isEmpty()) {
            return parentescoLimpo.substring(0, 1).toUpperCase() +
                    parentescoLimpo.substring(1).toLowerCase();
        }

        return parentescoLimpo;
    }

    public String getParentescoFormatadoParaBanco() {
        return formatarParentesco(fieldParentesco.getText());
    }

    // === MÉTODO DE FORMATAÇÃO DE TELEFONE ===
    private String formatarTelefone(String telefone) {
        // Remove TUDO que não é número (parênteses, traços, espaços, etc)
        String numeros = telefone.replaceAll("[^0-9]", "");

        // Verifica quantos dígitos tem e formata adequadamente
        if (numeros.length() == 10) {
            // Formato para telefone fixo: (11) 9999-9999
            return "(" + numeros.substring(0, 2) + ") " +
                    numeros.substring(2, 6) + "-" +
                    numeros.substring(6);
        } else if (numeros.length() == 11) {
            // Formato para celular: (11) 99999-9999
            return "(" + numeros.substring(0, 2) + ") " +
                    numeros.substring(2, 7) + "-" +
                    numeros.substring(7);
        }

        // Se não tiver 10 ou 11 dígitos, retorna os números limpos
        return numeros;
    }

    // === MÉTODO DE VALIDAÇÃO DE TELEFONE ===
    private boolean validarTelefone(String telefone) {
        // Remove tudo que não é número e conta os dígitos
        String numeros = telefone.replaceAll("[^0-9]", "");

        // Telefone válido deve ter 10 ou 11 dígitos (com DDD)
        return numeros.length() == 10 || numeros.length() == 11;
    }

    // === MÉTODOS PARA PEGAR DADOS FORMATADOS PARA O BANCO ===
    public String getTelefoneFormatadoParaBanco() {
        return formatarTelefone(fieldTelefone.getText());
    }

    // === MÉTODO PARA APLICAR MÁSCARA EM TEMPO REAL ===
    private void aplicarMascaraTelefone() {
        // Ouve as mudanças no campo telefone
        fieldTelefone.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.equals(oldValue)) {
                String formatado = formatarTelefone(newValue);
                if (!formatado.equals(newValue)) {
                    fieldTelefone.setText(formatado);
                    // Coloca o cursor no final do texto
                    fieldTelefone.positionCaret(formatado.length());
                }
            }
        });
    }

    private void aplicarMascaraRG() {
        fieldRg.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.equals(oldValue)) {
                String formatado = formatarRG(newValue);
                if (!formatado.equals(newValue)) {
                    fieldRg.setText(formatado);
                    fieldRg.positionCaret(formatado.length());
                }
            }
        });
    }

    private void aplicarMascaraNome() {
        fieldNome.textProperty().addListener((observable, oldValue, newValue) -> {
            // Apenas permite letras e espaços
            if (!newValue.matches("[a-zA-ZÀ-ÿ\\s]*")) {
                fieldNome.setText(oldValue);
            }
        });
    }

    private void aplicarMascaraParentesco() {
        fieldParentesco.textProperty().addListener((observable, oldValue, newValue) -> {
            // Apenas permite letras e espaços
            if (!newValue.matches("[a-zA-ZÀ-ÿ\\s]*")) {
                fieldParentesco.setText(oldValue);
            }
        });
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
                getNomeFormatadoParaBanco(),
                getParentescoFormatadoParaBanco(),
                getRgFormatadoParaBanco(),
                getTelefoneFormatadoParaBanco()
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

        @Override
        public String toString() {
            return "DadosPessoaAutorizada{" +
                    "nome='" + nome + '\'' +
                    ", parentesco='" + parentesco + '\'' +
                    ", rg='" + rg + '\'' +
                    ", telefone='" + telefone + '\'' +
                    '}';
        }
    }
}