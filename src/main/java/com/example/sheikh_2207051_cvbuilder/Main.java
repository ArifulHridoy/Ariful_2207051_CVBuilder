package com.example.sheikh_2207051_cvbuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application
{
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    @Override public void start(Stage primaryStage) throws Exception{
        executor.submit(()->{
            try{
                Parent root=FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
                Platform.runLater(()->{
                    primaryStage.setTitle("CV Builder");
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                });
            } catch(Exception e){
                e.printStackTrace();
            }
        });
    }

    @Override public void stop() throws Exception{
        executor.shutdown();
        super.stop();
    }

    public static void main(String[] args){
        launch(args);
    }
}