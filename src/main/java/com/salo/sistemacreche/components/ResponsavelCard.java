package com.salo.sistemacreche.components;

import com.salo.sistemacreche.entidades.Responsavel;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class ResponsavelCard extends VBox {

    private Responsavel responsavel;
    private Runnable onEditAction;

    public ResponsavelCard(Responsavel responsavel) {
        this.responsavel = responsavel;
        initializeComponents();
        setupLayout();
        applyStyles();
        loadResponsavelData();
    }

    private void initializeComponents() {
        // Componentes serão criados no loadResponsavelData()
    }

    private void setupLayout() {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(10);
    }

    private void applyStyles() {
        this.setStyle("-fx-background-color: #e8f5e8; " +
                "-fx-border-color: #c8e1e6; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 8; " +
                "-fx-padding: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
    }

    private void loadResponsavelData() {
        this.getChildren().clear();

        // Nome do Responsável
        Label labelNome = new Label(responsavel.getPessoa().getNome());
        labelNome.setStyle("-fx-text-fill: #0f766e; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 16;");

        // CPF do Responsável
        Label labelCpf = new Label("CPF: " + formatarCPF(responsavel.getPessoa().getCpf()));
        labelCpf.setStyle("-fx-text-fill: #666666; " +
                "-fx-font-size: 14;");

        // Container principal para as informações
        VBox infoContainer = new VBox(5);
        infoContainer.setAlignment(Pos.CENTER_LEFT);
        infoContainer.getChildren().addAll(labelNome, labelCpf);

        // Botão de editar
        Button btnEditar = new Button("Editar");
        btnEditar.setStyle("-fx-background-color: #0f766e; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5; " +
                "-fx-padding: 5 15 5 15; " +
                "-fx-cursor: hand;");

        btnEditar.setOnAction(e -> {
            if (onEditAction != null) {
                onEditAction.run();
            }
        });

        // Container horizontal para informações + botão
        HBox mainContainer = new HBox();
        mainContainer.setSpacing(15);
        mainContainer.setAlignment(Pos.CENTER_LEFT);
        mainContainer.getChildren().addAll(infoContainer, btnEditar);

        this.getChildren().add(mainContainer);
    }

    private String formatarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf != null ? cpf : "Não informado";
        }

        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    // Método para definir a ação de editar
    public void setOnEditAction(Runnable onEditAction) {
        this.onEditAction = onEditAction;
    }

    // Getter para o responsável
    public Responsavel getResponsavel() {
        return responsavel;
    }

    // Método para atualizar os dados do card
    public void updateResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
        loadResponsavelData();
    }
}