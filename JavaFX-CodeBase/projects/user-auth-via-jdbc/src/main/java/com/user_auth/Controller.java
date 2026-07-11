package com.user_auth;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class Controller {

    @FXML
    private StackPane rootpane;

    @FXML
    private TextField inputUsername;

    @FXML
    private TextField inputPassword;

    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    @FXML
    private ImageView backgroundImg;

    @FXML
    private ImageView logoImg;

    @FXML
    private ImageView logImg;

    @FXML
    private AnchorPane loginpane;

    @FXML
    private void initialize() {

        Image rootimg = new Image(getClass().getResource("/Images/background.jpg").toExternalForm());
        backgroundImg.setImage(rootimg);
        backgroundImg.fitWidthProperty().bind(rootpane.widthProperty());
        backgroundImg.fitHeightProperty().bind(rootpane.heightProperty());

        Image logo = new Image(getClass().getResource("/Images/logodoss.png").toExternalForm());
        logoImg.setImage(logo);

        Image log = new Image(getClass().getResource("/Images/login3.jpg").toExternalForm());
        logImg.setImage(log);

    }

    @FXML
    private void handleLoginButtonAction() {
        String username = inputUsername.getText();
        String password = inputPassword.getText();
        System.out.println("Login Pressed");
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Fields can't be empty!");
        } else {

        }
    }

    @FXML
    void handleRegisterButtonAction() {
        loginpane.setVisible(false);
    }

}
