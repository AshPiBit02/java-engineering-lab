package com.user_auth;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {
    @FXML
    private TextField inputUsername;

    @FXML
    private TextField inputPassword;

    @FXML
    private Button loginBtn;

    @FXML
    private ImageView backgroundImg;

    @FXML
    private void initizalize() {
        System.out.println(getClass().getResource("/Images/background.jpg"));

        Image img = new Image(getClass().getResource("/Images/background.jpg").toExternalForm());
        backgroundImg.setImage(img);

    }

    @FXML
    private void handleLoginButtonAction() {
        String username = inputUsername.getText();
        String password = inputPassword.getText();

        System.out.println("Login Pressed");
        System.out.println("Username " + username);
        System.out.println("Password " + password);
    }

}
