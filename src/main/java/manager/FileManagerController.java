package manager;

import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.time.format.DateTimeFormatter;
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

    private final EventHandler<ActionEvent> exitEvent = event -> Platform.exit();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFileTable();
        exitButton.setOnAction(exitEvent);
        fileMenu.setOnAction(exitEvent);

        List<File> files = Arrays.asList(File.listRoots());
        files.forEach(file -> diskComboBox.getItems().add(file.getPath()));

        updateFileTable(new File("./"));
        //Paths.get("", "")
    }

    private void initFileTable() {
        TableColumn<FileInfo, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
        typeColumn.setPrefWidth(50);

        TableColumn<FileInfo, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        nameColumn.setPrefWidth(240);

        TableColumn<FileInfo, Long> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSize()));
        sizeColumn.setCellFactory(new Callback<TableColumn<FileInfo, Long>, TableCell<FileInfo, Long>>() {
            @Override
            public TableCell<FileInfo, Long> call(TableColumn<FileInfo, Long> param) {
                return new TableCell<FileInfo, Long>() {
                    @Override
                    protected void updateItem(Long item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            this.setText(null);
                            this.setStyle("");
                        } else {
                            String cell = String.format("%,d bytes", item);
                            if (item == -1L) {
                                cell = "[DIR]";
                            }
                            this.setText(cell);
                        }
                    }
                };
            }
        });
        sizeColumn.setPrefWidth(120);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        TableColumn<FileInfo, String> dateColumn = new TableColumn<>("Date modified");
        dateColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLast().format(dateTimeFormatter)));
        dateColumn.setPrefWidth(120);

        filesTable.getColumns().addAll(typeColumn, nameColumn, sizeColumn, dateColumn);
        filesTable.getSortOrder().add(typeColumn);
        filesTable.getSortOrder().add(nameColumn);
    }

    private void updateFileTable(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            filesTable.getItems().add(new FileInfo(f));
        }
        filesTable.sort();
        //Alert
    }
}