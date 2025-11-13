package com.example.sheikh_2207051_cvbuilder.controllers;
import com.example.sheikh_2207051_cvbuilder.model.CVModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CreateController{
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextArea educationArea;
    @FXML private TextArea skillsArea;
    @FXML private TextArea experienceArea;
    @FXML private TextArea projectsArea;
    @FXML private ImageView photoView;
    private Image photo;

    @FXML private void onBack(ActionEvent event) throws IOException{
        Parent home=FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(home,900,650));
    }
    @FXML private void onUploadPhoto(ActionEvent event){
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Choose Profile Photo");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files","*.png", "*.jpg","*.jpeg","*.gif")
        );

        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        File file=chooser.showOpenDialog(stage);

        if (file!=null){
            try(FileInputStream fis=new FileInputStream(file)){
                photo=new Image(fis);
                photoView.setImage(photo);
            } catch(Exception e){
                showAlert(Alert.AlertType.ERROR,"Unable to load image.");
            }
        }
    }
    @FXML private void onRemovePhoto(ActionEvent event){
        photo=null;
        photoView.setImage(null);
    }
    @FXML private void onGenerateCV(ActionEvent event) throws IOException {
        if(fullNameField.getText().isBlank() || emailField.getText().isBlank()){
            showAlert(Alert.AlertType.WARNING,"Please enter at least Full Name and Email!!!");
            return;
        }
        CVModel model=new CVModel();
        model.setFullName(fullNameField.getText().trim());
        model.setEmail(emailField.getText().trim());
        model.setPhone(phoneField.getText().trim());
        model.setAddress(addressField.getText().trim());
        model.setEducation(educationArea.getText().trim());
        model.setSkills(skillsArea.getText().trim());
        model.setExperience(experienceArea.getText().trim());
        model.setProjects(projectsArea.getText().trim());
        model.setPhoto(photo);

        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/Preview.fxml"));
        Parent previewRoot=loader.load();
        PreviewController pc=loader.getController();
        pc.setModel(model);
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(previewRoot,900,700));
    }
    private void showAlert(Alert.AlertType type,String message){
        Alert alert=new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
