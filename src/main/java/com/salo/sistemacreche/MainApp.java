package com.salo.sistemacreche;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // 👇 Carrega o arquivo FXML (design da tela)
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));

        // 👇 Cria a cena (conteúdo da janela)
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);

        // 👇 Configura a janela
        stage.setTitle("Sistema Creche");
        stage.setScene(scene);
        stage.show();
    }

    // 👇 Método main - ponto de entrada do Java
    public static void main(String[] args) {
        launch(); // 👈 Inicia o JavaFX
    }
}
