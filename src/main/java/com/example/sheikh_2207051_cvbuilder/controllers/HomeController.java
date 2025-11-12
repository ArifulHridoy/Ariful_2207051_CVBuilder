package com.example.sheikh_2207051_cvbuilder.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

public class HomeController
{
    @FXML private void onCreateNewCV(ActionEvent event) throws IOException {
        Parent createRoot=FXMLLoader.load(getClass().getResource("/fxml/CreateCV.fxml"));
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(createRoot,1000,700));
    }
}
