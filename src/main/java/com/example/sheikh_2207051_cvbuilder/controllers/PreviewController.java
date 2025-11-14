package com.example.sheikh_2207051_cvbuilder.controllers;
import com.example.sheikh_2207051_cvbuilder.model.CVModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class PreviewController
{
    @FXML private Label nameLabel;
    @FXML private Label contactLabel;
    @FXML private Label addressLabel;
    @FXML private Label educationLabel;
    @FXML private Label skillsLabel;
    @FXML private Label experienceLabel;
    @FXML private Label projectsLabel;
    @FXML private ImageView photoPreview;

    private CVModel model;

    public void setModel(CVModel model)
    {
        this.model=model;
        bindModel();
    }

    private void bindModel()
    {
        if (model==null) return;
        nameLabel.setText(model.getFullName());
        contactLabel.setText(model.getEmail() +" | "+model.getPhone());
        addressLabel.setText(model.getAddress());
        educationLabel.setText(formatMultiline(model.getEducation()));
        skillsLabel.setText(formatMultiline(model.getSkills()));
        experienceLabel.setText(formatMultiline(model.getExperience()));
        projectsLabel.setText(formatMultiline(model.getProjects()));
        if (model.getPhoto()!=null) photoPreview.setImage(model.getPhoto());
    }

    private String formatMultiline(String text)
    {
        if(text==null || text.isBlank()) return "-";
        String[] lines=text.split("\\r?\\n");
        StringBuilder sb=new StringBuilder();
        for(String l:lines) {
            if(!l.isBlank()) sb.append("• ").append(l.trim()).append("\n");
        }
        return sb.toString().trim();
    }

    @FXML private void onEdit(ActionEvent event) throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/CreateCV.fxml"));
        Parent createRoot=loader.load();
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(createRoot,900,650));
    }

    @FXML private void onBackHome(ActionEvent event) throws IOException{
        Parent home=FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(home,900,650));
    }
}
