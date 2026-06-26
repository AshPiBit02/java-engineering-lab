package com.init;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;

public class Controller {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private TableView<Student> TableView;
    @FXML
    private TableColumn<Student, Integer> stuId;
    @FXML
    private TableColumn<Student, String> fullname;
    @FXML
    private TableColumn<Student, String> course;
    @FXML
    private TableColumn<Student, String> faculty;
    @FXML
    private TableColumn<Student, String> level;

    @FXML
    private TextField sqlField;
    @FXML
    private Button enterBtn;
    @FXML
    private Button refreshBtn;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        stuId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        fullname.setCellValueFactory(data -> data.getValue().nameProperty());
        course.setCellValueFactory(data -> data.getValue().courseProperty());
        faculty.setCellValueFactory(data -> data.getValue().facultyProperty());
        level.setCellValueFactory(data -> data.getValue().levelProperty());
        loadData("SELECT * FROM studentinfo ORDER BY id");
    }

    private void loadData(String sql) {
        studentList.clear();
        try (Connection conn = DBConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                studentList.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("course"),
                        rs.getString("faculty"), rs.getString("level")));
            }
            TableView.setItems(studentList);
        } catch (Exception e) {
            e.printStackTrace();
            alert.setContentText("Query run failed!");
            alert.show();
        }

    }

    @FXML
    public void handleEnter() {
        String sql = sqlField.getText().trim();
        if (!sql.isEmpty()) {
            loadData(sql);
        }
    }

    @FXML
    public void handleRefresh() {
        sqlField.clear();
        loadData("Select * FROM studentinfo ORDER BY id");
    }

}
