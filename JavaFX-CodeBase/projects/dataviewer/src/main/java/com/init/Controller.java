package com.init;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.scene.control.TableColumn;

public class Controller {
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
        fullname.setCellValueFactory(data -> data.getValue().nameProperty().asObject());
        course.setCellValueFactory(data -> data.getValue().courseProperty().asObject());
        faculty.setCellValueFactory(data -> data.getValue().facultyProperty().asObject());
        level.setCellValueFactory(data -> data.getValue().levelProperty().asObject());
        loadData("SELECT * FROM studentInfo");
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
        loadData("Select * FROM studentInfo");
    }

}
