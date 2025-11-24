package com.example.sheikh_2207051_cvbuilder.controllers;

import com.example.sheikh_2207051_cvbuilder.db.CVDao;
import com.example.sheikh_2207051_cvbuilder.model.CVModel;
import com.example.sheikh_2207051_cvbuilder.util.JsonUtil;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

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

    @FXML private void onDelete(ActionEvent event) throws IOException {
        if (model == null || model.getId() <= 0) {
            Alert a = new Alert(Alert.AlertType.WARNING, "This CV is not saved or cannot be deleted.");
            a.show();
            return;
        }
        CVDao dao = new CVDao();
        Task<Void> t = new Task<>(){
            @Override
            protected Void call() throws Exception {
                dao.delete(model.getId());
                return null;
            }
        };
        t.setOnSucceeded(x -> {
            try {
                Parent home = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
                Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(home,900,650));
                Alert a=new Alert(Alert.AlertType.INFORMATION, "CV deleted");
                a.show();
            } catch (IOException ex) { ex.printStackTrace(); }
        });
        t.setOnFailed(x -> {
            Throwable ex = t.getException();
            ex.printStackTrace();
            Alert a=new Alert(Alert.AlertType.ERROR, "Failed to delete: " + ex.getMessage());
            a.show();
        });
        new Thread(t).start();
    }

    @FXML private void onExport(ActionEvent event) {
        if (model == null) {
            new Alert(Alert.AlertType.WARNING, "Nothing to export").show();
            return;
        }
        FileChooser chooser = new FileChooser();
        chooser.setInitialFileName((model.getFullName()==null?"cv":model.getFullName()) + ".json");
        File f = chooser.showSaveDialog(((Node)event.getSource()).getScene().getWindow());
        if (f != null) {
            Task<Void> t = new Task<>(){
                @Override
                protected Void call() throws Exception {
                    JsonUtil.exportToFile(model, f);
                    return null;
                }
            };
            t.setOnSucceeded(x -> new Alert(Alert.AlertType.INFORMATION, "Exported to " + f.getAbsolutePath()).show());
            t.setOnFailed(x -> new Alert(Alert.AlertType.ERROR, "Export failed: " + t.getException().getMessage()).show());
            new Thread(t).start();
        }
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
        com.example.sheikh_2207051_cvbuilder.controllers.CreateController cc = loader.getController();
        cc.setModel(model);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(createRoot,900,750));
    }

    @FXML private void onBackHome(ActionEvent event) throws IOException{
        Parent home=FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(home,900,650));
    }
}
