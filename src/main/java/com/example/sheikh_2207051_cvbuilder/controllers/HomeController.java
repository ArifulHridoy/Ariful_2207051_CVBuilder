package com.example.sheikh_2207051_cvbuilder.controllers;
import com.example.sheikh_2207051_cvbuilder.db.CVDao;
import com.example.sheikh_2207051_cvbuilder.model.CVModel;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.util.Callback;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HomeController
{
    @FXML private ListView<CVModel> cvListView;

    private final ObservableList<CVModel> items = FXCollections.observableArrayList();

    @FXML public void initialize() {
        cvListView.setItems(items);
        cvListView.setCellFactory(new Callback<>(){
            @Override
            public ListCell<CVModel> call(ListView<CVModel> param) {
                return new ListCell<>(){
                    @Override
                    protected void updateItem(CVModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) setText(null);
                        else setText((item.getFullName()==null?"(No name)":item.getFullName()) + "  [#" + item.getId() + "]");
                    }
                };
            }
        });

        cvListView.setOnMouseClicked(ev -> {
            if (ev.getClickCount() == 2) {
                CVModel sel = cvListView.getSelectionModel().getSelectedItem();
                if (sel != null) openPreview(sel, ev);
            }
        });

        loadList();
    }

    @FXML private void onCreateNewCV(ActionEvent event) throws IOException {
        Parent createRoot=FXMLLoader.load(getClass().getResource("/fxml/CreateCV.fxml"));
        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(createRoot,900,750));
    }

    @FXML private void onRefresh(ActionEvent event) { loadList(); }

    private void loadList() {
        Task<List<CVModel>> t = new Task<>(){
            @Override
            protected List<CVModel> call() throws Exception {
                CVDao dao = new CVDao();
                return dao.getAll();
            }
        };
        t.setOnSucceeded(e -> {
            items.setAll(t.getValue());
        });
        t.setOnFailed(e -> {
            t.getException().printStackTrace();
        });
        new Thread(t).start();
    }

    private void openPreview(CVModel model, javafx.scene.input.MouseEvent ev) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/Preview.fxml"));
            Parent root=loader.load();
            com.example.sheikh_2207051_cvbuilder.controllers.PreviewController pc = loader.getController();
            pc.setModel(model);
            Stage stage=(Stage)((Node)ev.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,900,700));
        } catch (IOException ex) { ex.printStackTrace(); }
    }
}
