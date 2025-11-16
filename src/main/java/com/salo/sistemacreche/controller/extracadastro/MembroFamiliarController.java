package com.salo.sistemacreche.controller.extracadastro;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        aplicarMascaraNome();
        aplicarMascaraIdade();
        aplicarMascaraParentesco();
        aplicarMascaraEscolaridade();
        aplicarMascaraEmprego();
        aplicarMascaraRenda();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSalvo() {
        return salvo;
    }

    @FXML
    private void btnSalvarMembro() {
        if (validarCampos()) {
            try {
                // Pega todos os dados FORMATADOS para o banco
                String nomeFormatado = getNomeFormatadoParaBanco();
                String idadeFormatada = getIdadeFormatadaParaBanco();
                String parentescoFormatado = getParentescoFormatadoParaBanco();
                String escolaridadeFormatada = getEscolaridadeFormatadaParaBanco();
                String empregoFormatado = getEmpregoFormatadoParaBanco();
                String rendaFormatada = getRendaFormatadaParaBanco();

                // Exibe no console
                System.out.println("=== DADOS MEMBRO FAMILIAR PARA O BANCO ===");
                System.out.println("Nome: " + nomeFormatado);
                System.out.println("Idade: " + idadeFormatada);
                System.out.println("Parentesco: " + parentescoFormatado);
                System.out.println("Escolaridade: " + escolaridadeFormatada);
                System.out.println("Emprego: " + empregoFormatado);
                System.out.println("Renda: " + rendaFormatada);

                // TODO: Salvar no banco de dados
                // seuRepository.salvarMembroFamiliar(
                //     nomeFormatado, idadeFormatada, parentescoFormatado,
                //     escolaridadeFormatada, empregoFormatado, rendaFormatada
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
    private void btnCancelarMembro() {
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
            mostrarErro("Informe nome e sobrenome completos!\nEx: Pedro Silva Santos");
            fieldNome.requestFocus();
            return false;
        }

        // === IDADE ===
        if (fieldIdade.getText().trim().isEmpty()) {
            mostrarErro("Idade é obrigatória!");
            fieldIdade.requestFocus();
            return false;
        }
        if (!validarIdade(fieldIdade.getText())) {
            mostrarErro("Idade inválida!\nDeve ser entre 0 e 120 anos.");
            fieldIdade.requestFocus();
            return false;
        }

        // === PARENTESCO ===
        if (fieldParentesco.getText().trim().isEmpty()) {
            mostrarErro("Parentesco é obrigatório!");
            fieldParentesco.requestFocus();
            return false;
        }
        if (!validarParentesco(fieldParentesco.getText())) {
            mostrarErro("Parentesco deve conter apenas letras!\nEx: Pai, Mãe, Irmão, Avó");
            fieldParentesco.requestFocus();
            return false;
        }

        // === ESCOLARIDADE ===
        if (fieldEscolaridade.getText().trim().isEmpty()) {
            mostrarErro("Escolaridade é obrigatória!");
            fieldEscolaridade.requestFocus();
            return false;
        }

        // === EMPREGO === (não obrigatório)
        if (!fieldEmprego.getText().trim().isEmpty() &&
                !validarEmprego(fieldEmprego.getText())) {
            mostrarErro("Emprego deve conter apenas letras e espaços!");
            fieldEmprego.requestFocus();
            return false;
        }

        // === RENDA === (não obrigatória)
        if (!fieldRenda.getText().trim().isEmpty() &&
                !validarRenda(fieldRenda.getText())) {
            mostrarErro("Renda inválida!\nUse apenas números e vírgula.\nEx: 2500,00");
            fieldRenda.requestFocus();
            return false;
        }

        return true;
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

    // === VALIDAÇÃO DE IDADE ===
    private boolean validarIdade(String idade) {
        if (idade == null || idade.trim().isEmpty()) {
            return false;
        }

        try {
            // Remove caracteres não numéricos
            String idadeLimpa = idade.replaceAll("[^0-9]", "");
            if (idadeLimpa.isEmpty()) return false;

            int idadeNum = Integer.parseInt(idadeLimpa);
            return idadeNum >= 0 && idadeNum <= 120;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // === FORMATAÇÃO DE IDADE ===
    private String formatarIdade(String idade) {
        if (idade == null) return "";

        // Remove tudo que não é número
        String idadeLimpa = idade.replaceAll("[^0-9]", "");

        if (idadeLimpa.isEmpty()) return "";

        // Retorna apenas os números
        return idadeLimpa;
    }

    public String getIdadeFormatadaParaBanco() {
        return formatarIdade(fieldIdade.getText());
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

    // === VALIDAÇÃO DE ESCOLARIDADE ===
    private boolean validarEscolaridade(String escolaridade) {
        if (escolaridade == null || escolaridade.trim().isEmpty()) {
            return false;
        }

        // Remove espaços extras
        String escolaridadeLimpa = escolaridade.trim().replaceAll("\\s+", " ");

        // Permite letras, números, espaços e alguns caracteres comuns em escolaridade
        return escolaridadeLimpa.matches("[a-zA-ZÀ-ÿ0-9\\s\\-ºª]+");
    }

    // === FORMATAÇÃO DE ESCOLARIDADE ===
    private String formatarEscolaridade(String escolaridade) {
        if (escolaridade == null) return "";

        // Remove espaços extras
        String escolaridadeLimpa = escolaridade.trim().replaceAll("\\s+", " ");

        // Capitaliza a primeira letra de cada palavra
        String[] palavras = escolaridadeLimpa.split(" ");
        StringBuilder formatada = new StringBuilder();

        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                String capitalizada = palavra.substring(0, 1).toUpperCase() +
                        palavra.substring(1).toLowerCase();
                formatada.append(capitalizada).append(" ");
            }
        }

        return formatada.toString().trim();
    }

    public String getEscolaridadeFormatadaParaBanco() {
        return formatarEscolaridade(fieldEscolaridade.getText());
    }

    // === VALIDAÇÃO DE EMPREGO ===
    private boolean validarEmprego(String emprego) {
        if (emprego == null) return true; // Não é obrigatório

        // Remove espaços extras
        String empregoLimpo = emprego.trim().replaceAll("\\s+", " ");

        // Permite letras, espaços e alguns caracteres comuns
        return empregoLimpo.isEmpty() || empregoLimpo.matches("[a-zA-ZÀ-ÿ\\s\\-&]+");
    }

    // === FORMATAÇÃO DE EMPREGO ===
    private String formatarEmprego(String emprego) {
        if (emprego == null || emprego.trim().isEmpty()) return "";

        // Remove espaços extras
        String empregoLimpo = emprego.trim().replaceAll("\\s+", " ");

        // Capitaliza a primeira letra de cada palavra
        String[] palavras = empregoLimpo.split(" ");
        StringBuilder formatado = new StringBuilder();

        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                String capitalizada = palavra.substring(0, 1).toUpperCase() +
                        palavra.substring(1).toLowerCase();
                formatado.append(capitalizada).append(" ");
            }
        }

        return formatado.toString().trim();
    }

    public String getEmpregoFormatadoParaBanco() {
        return formatarEmprego(fieldEmprego.getText());
    }

    // === VALIDAÇÃO DE RENDA ===
    private boolean validarRenda(String renda) {
        if (renda == null || renda.trim().isEmpty()) return true; // Não é obrigatório

        // Remove R$, espaços e mantém apenas números e vírgula
        String rendaLimpa = renda.replace("R$", "").trim();

        try {
            if (rendaLimpa.isEmpty()) return true;

            // Verifica se tem apenas números e uma vírgula
            if (!rendaLimpa.matches("[0-9]*[,]?[0-9]{0,2}")) {
                return false;
            }

            // Converte para double para validar o valor
            String valorStr = rendaLimpa.replace(",", ".");
            double valor = Double.parseDouble(valorStr);
            return valor >= 0 && valor <= 1000000; // Até 1 milhão
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // === FORMATAÇÃO DE RENDA ===
    private String formatarRenda(String renda) {
        if (renda == null || renda.trim().isEmpty()) return "R$ ";

        // Remove tudo que não é número ou vírgula, mas mantém o "R$ " no início
        String apenasNumeros = renda.replace("R$", "").replaceAll("[^0-9,]", "");

        if (apenasNumeros.isEmpty()) return "R$ ";

        // Garante que há apenas uma vírgula
        String[] partes = apenasNumeros.split(",");
        if (partes.length > 2) {
            apenasNumeros = partes[0] + "," + partes[1];
        }

        // Limita a 2 casas decimais após a vírgula
        if (apenasNumeros.contains(",")) {
            String[] partesDecimal = apenasNumeros.split(",");
            if (partesDecimal.length > 1 && partesDecimal[1].length() > 2) {
                apenasNumeros = partesDecimal[0] + "," + partesDecimal[1].substring(0, 2);
            }
        }

        return "R$ " + apenasNumeros;
    }

    public String getRendaFormatadaParaBanco() {
        String texto = fieldRenda.getText();
        if (texto == null || texto.trim().isEmpty() || texto.equals("R$ ")) {
            return "";
        }

        // Remove "R$ " e formata para banco (substitui vírgula por ponto)
        String valor = texto.substring(3).replace(",", ".");
        try {
            // Converte para double e formata com 2 casas decimais
            double valorDouble = Double.parseDouble(valor);
            return String.format("%.2f", valorDouble);
        } catch (NumberFormatException e) {
            return valor;
        }
    }

    // === MÉTODOS PARA APLICAR MÁSCARA EM TEMPO REAL ===
    private void aplicarMascaraNome() {
        fieldNome.textProperty().addListener((observable, oldValue, newValue) -> {
            // Apenas permite letras e espaços
            if (!newValue.matches("[a-zA-ZÀ-ÿ\\s]*")) {
                fieldNome.setText(oldValue);
            }
        });
    }

    private void aplicarMascaraIdade() {
        fieldIdade.textProperty().addListener((observable, oldValue, newValue) -> {
            // Apenas permite números (até 3 dígitos)
            if (!newValue.matches("\\d{0,3}")) {
                fieldIdade.setText(oldValue);
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

    private void aplicarMascaraEscolaridade() {
        fieldEscolaridade.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permite letras, números, espaços, hífen e caracteres de ordinal
            if (!newValue.matches("[a-zA-ZÀ-ÿ0-9\\s\\-ºª]*")) {
                fieldEscolaridade.setText(oldValue);
            }
        });
    }

    private void aplicarMascaraEmprego() {
        fieldEmprego.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permite letras, espaços, hífen e &
            if (!newValue.matches("[a-zA-ZÀ-ÿ\\s\\-&]*")) {
                fieldEmprego.setText(oldValue);
            }
        });
    }

    private void aplicarMascaraRenda() {
        // Adiciona "R$ " inicial se estiver vazio
        if (fieldRenda.getText().isEmpty()) {
            fieldRenda.setText("R$ ");
            fieldRenda.positionCaret(fieldRenda.getText().length());
        }

        fieldRenda.textProperty().addListener((observable, oldValue, newValue) -> {
            // Se estiver tentando apagar o "R$ ", não permite
            if (newValue != null && newValue.length() < 3) {
                fieldRenda.setText("R$ ");
                fieldRenda.positionCaret(3);
                return;
            }

            // Se não começar com "R$ ", restaura
            if (newValue != null && !newValue.startsWith("R$ ")) {
                // Tenta extrair números da nova entrada
                String numeros = newValue.replaceAll("[^0-9,]", "");
                String formatado = "R$ " + numeros;
                fieldRenda.setText(formatado);
                fieldRenda.positionCaret(formatado.length());
                return;
            }

            // Aplica formatação mantendo o "R$ " fixo
            if (newValue != null && !newValue.equals(oldValue) && newValue.length() > 3) {
                String apenasValor = newValue.substring(3); // Remove "R$ "
                String valorFormatado = formatarValorRenda(apenasValor);
                String resultado = "R$ " + valorFormatado;

                if (!resultado.equals(newValue)) {
                    fieldRenda.setText(resultado);

                    // Posiciona o cursor no final
                    int caretPos = resultado.length();
                    fieldRenda.positionCaret(caretPos);
                }
            }
        });

        // Manipula eventos de teclado para melhor controle
        fieldRenda.setOnKeyPressed(event -> {
            // Se cursor estiver antes do "R$ ", move para depois
            if (fieldRenda.getCaretPosition() < 3) {
                fieldRenda.positionCaret(3);
            }
        });

        // Manipula clique no campo para garantir cursor após "R$ "
        fieldRenda.setOnMouseClicked(event -> {
            if (fieldRenda.getCaretPosition() < 3) {
                fieldRenda.positionCaret(3);
            }
        });
    }

    // === MÉTODO AUXILIAR PARA FORMATAR APENAS O VALOR ===
    private String formatarValorRenda(String valor) {
        if (valor == null || valor.trim().isEmpty()) return "";

        // Remove caracteres inválidos, mantém números e vírgula
        String limpo = valor.replaceAll("[^0-9,]", "");

        // Garante apenas uma vírgula
        if (limpo.contains(",")) {
            String[] partes = limpo.split(",");
            if (partes.length > 1) {
                limpo = partes[0] + "," + (partes[1].length() > 2 ? partes[1].substring(0, 2) : partes[1]);
            }
        }

        return limpo;
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
    public String getIdade() { return fieldIdade.getText(); }
    public String getParentesco() { return fieldParentesco.getText(); }
    public String getEscolaridade() { return fieldEscolaridade.getText(); }
    public String getEmprego() { return fieldEmprego.getText(); }
    public String getRenda() { return fieldRenda.getText(); }

    // Método para obter todos os dados como objeto
    public DadosMembroFamiliar getDadosMembro() {
        return new DadosMembroFamiliar(
                getNomeFormatadoParaBanco(),
                getIdadeFormatadaParaBanco(),
                getParentescoFormatadoParaBanco(),
                getEscolaridadeFormatadaParaBanco(),
                getEmpregoFormatadoParaBanco(),
                getRendaFormatadaParaBanco()
        );
    }

    // Classe interna para transportar dados
    public static class DadosMembroFamiliar {
        private String nome;
        private String idade;
        private String parentesco;
        private String escolaridade;
        private String emprego;
        private String renda;

        public DadosMembroFamiliar(String nome, String idade, String parentesco,
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
        public String getIdade() { return idade; }
        public String getParentesco() { return parentesco; }
        public String getEscolaridade() { return escolaridade; }
        public String getEmprego() { return emprego; }
        public String getRenda() { return renda; }

        @Override
        public String toString() {
            return "DadosMembroFamiliar{" +
                    "nome='" + nome + '\'' +
                    ", idade='" + idade + '\'' +
                    ", parentesco='" + parentesco + '\'' +
                    ", escolaridade='" + escolaridade + '\'' +
                    ", emprego='" + emprego + '\'' +
                    ", renda='" + renda + '\'' +
                    '}';
        }
    }
}