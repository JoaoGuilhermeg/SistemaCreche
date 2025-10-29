package com.salo.sistemacreche.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RematriculaController {

    @FXML private TextField fieldPesquisarAluno;
    @FXML private Button btnBuscarAluno;
    @FXML private VBox containerAlunos;

    @FXML
    public void initialize() {
        configurarBotoes();
        carregarAlunosParaRematricula();
    }

    private void configurarBotoes() {
        // As ações já estão configuradas no FXML via onAction
    }

    @FXML
    public void buscarAlunos() {
        String termo = fieldPesquisarAluno.getText();
        System.out.println("Buscando alunos para rematrícula: " + termo);
        // TODO: Implementar busca filtrada
        carregarAlunosParaRematricula();
    }

    @FXML
    public void realizarRematricula() {
        System.out.println("Realizando rematrícula...");
        // TODO: Implementar lógica de rematrícula
        mostrarMensagemSucesso("Rematrícula realizada com sucesso!");
    }

    private void carregarAlunosParaRematricula() {
        // TODO: Carregar alunos do banco que estão próximos do vencimento
        System.out.println("Carregando alunos para rematrícula...");

        // Por enquanto, vamos simular alguns dados
        containerAlunos.getChildren().clear();

        // Aqui você implementaria a lógica para buscar alunos do banco
        // e criar os cards dinamicamente
    }

    private void mostrarMensagemSucesso(String mensagem) {
        // TODO: Implementar alerta de sucesso
        System.out.println("SUCESSO: " + mensagem);
    }
}