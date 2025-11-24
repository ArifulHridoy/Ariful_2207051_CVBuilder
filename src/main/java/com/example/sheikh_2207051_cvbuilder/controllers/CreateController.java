package com.example.sheikh_2207051_cvbuilder.controllers;

import com.example.sheikh_2207051_cvbuilder.db.CVDao;
import com.example.sheikh_2207051_cvbuilder.model.CVModel;
import com.example.sheikh_2207051_cvbuilder.model.CVData;
import javafx.concurrent.Task;
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
import java.sql.SQLException;

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
    private CVModel editingModel = null;

    @FXML public void initialize(){loadData();}

    @FXML private void onBack(ActionEvent event) throws IOException{
        saveData();
        Parent home=FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(home,900,750));
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
        if(fullNameField.getText().isBlank()||phoneField.getText().isBlank()||addressField.getText().isBlank()||educationArea.getText().isBlank()||skillsArea.getText().isBlank()||experienceArea.getText().isBlank()||projectsArea.getText().isBlank()|| emailField.getText().isBlank()){
            showAlert(Alert.AlertType.WARNING,"Please enter the full form blank field is not allowed!!!");
            return;
        }

        CVModel model = editingModel == null ? new CVModel() : editingModel;
        model.setFullName(fullNameField.getText().trim());
        model.setEmail(emailField.getText().trim());
        model.setPhone(phoneField.getText().trim());
        model.setAddress(addressField.getText().trim());
        model.setEducation(educationArea.getText().trim());
        model.setSkills(skillsArea.getText().trim());
        model.setExperience(experienceArea.getText().trim());
        model.setProjects(projectsArea.getText().trim());
        model.setPhoto(photo);
        saveData();

        // Save to database in background (insert or update)
        CVDao dao = new CVDao();
        Task<Integer> task = new Task<>(){
            @Override
            protected Integer call() throws Exception {
                if (model.getId() > 0) {
                    dao.update(model);
                    return model.getId();
                } else {
                    return dao.insert(model);
                }
            }
        };
        task.setOnSucceeded(t -> {
            int id = task.getValue();
            if (id > 0) model.setId(id);
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/Preview.fxml"));
                Parent previewRoot=loader.load();
                PreviewController pc=loader.getController();
                pc.setModel(model);
                Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(previewRoot,900,700));
                Alert a=new Alert(Alert.AlertType.INFORMATION, "CV Saved and Created Successfully");
                a.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        task.setOnFailed(t -> {
            Throwable ex = task.getException();
            ex.printStackTrace();
            Alert a=new Alert(Alert.AlertType.ERROR, "Failed to save CV: " + ex.getMessage());
            a.show();
        });
        new Thread(task).start();
    }

    public void setModel(CVModel model) {
        this.editingModel = model;
        if (model == null) return;
        fullNameField.setText(model.getFullName());
        emailField.setText(model.getEmail());
        phoneField.setText(model.getPhone());
        addressField.setText(model.getAddress());
        educationArea.setText(model.getEducation());
        skillsArea.setText(model.getSkills());
        experienceArea.setText(model.getExperience());
        projectsArea.setText(model.getProjects());
        if (model.getPhoto() != null) {
            this.photo = model.getPhoto();
            photoView.setImage(photo);
        }
    }
    private void showAlert(Alert.AlertType type,String message){
        Alert alert=new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void saveData() {
        CVData data = CVData.getInstance();
        data.fullName = fullNameField.getText();
        data.email = emailField.getText();
        data.phone = phoneField.getText();
        data.address = addressField.getText();
        data.education = educationArea.getText();
        data.skills = skillsArea.getText();
        data.experience = experienceArea.getText();
        data.projects = projectsArea.getText();
        data.photo=photo;
    }
    private void loadData()
    {
        CVData data =CVData.getInstance();

        fullNameField.setText(data.fullName);
        emailField.setText(data.email);
        phoneField.setText(data.phone);
        addressField.setText(data.address);
        educationArea.setText(data.education);
        skillsArea.setText(data.skills);
        experienceArea.setText(data.experience);
        projectsArea.setText(data.projects);
        if(data.photo!=null) {
            photo=data.photo;
            photoView.setImage(photo);
        }
    }

}
