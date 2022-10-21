package manager;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.util.*;

public class FileManagerController implements Initializable {
    @FXML public ComboBox<String> diskComboBox;
    @FXML public TextField pathTextField;
    @FXML public MenuItem fileMenu;
    @FXML public Button upButton;
    @FXML public TableView<FileInfo> filesTable;
    @FXML public Button copyButton;
    @FXML public Button moveButton;
    @FXML public Button deleteButton;
    @FXML public Button exitButton;

    private EventHandler<ActionEvent> exitEvent = event -> Platform.exit();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFileTable();
        exitButton.setOnAction(exitEvent);
        fileMenu.setOnAction(exitEvent);

        List<File> files = Arrays.asList(File.listRoots());
        files.forEach(file -> diskComboBox.getItems().add(file.getPath()));
        updateFileTable(new File("./"));
    }

    private void initFileTable() {
        TableColumn<FileInfo, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
        typeColumn.setPrefWidth(24);

        TableColumn<FileInfo, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        nameColumn.setPrefWidth(240);

//        TableColumn<FileInfo, Long> sizeColumn = new TableColumn<>("Size");
//        sizeColumn.setCellValueFactory(param -> new SimpleLongProperty((new Long(param.getValue().getSize())));
//        sizeColumn.setPrefWidth(24);
//
//        TableColumn<FileInfo, String> typeColumn = new TableColumn<>("Type");
//        typeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
//        typeColumn.setPrefWidth(24);
//
        filesTable.getColumns().addAll(typeColumn, nameColumn); //, sizeColumn, lastColumn);
    }

    private void updateFileTable(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            filesTable.getItems().add(new FileInfo(f));
        }
    }
}