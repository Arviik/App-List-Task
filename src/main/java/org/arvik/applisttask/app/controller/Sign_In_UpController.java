package org.arvik.applisttask.app.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.arvik.applisttask.MainApp;
import org.arvik.applisttask.modele.User;
import org.arvik.applisttask.repository.UserRepository;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class Sign_In_UpController {
    @FXML private MFXTextField loginEmail;
    @FXML private MFXPasswordField loginPassword;
    @FXML private Label loginValidationLabel;

    @FXML void onLoginButtonClick() {
        UserRepository userRepo = new UserRepository();
        User user = userRepo.connexion(loginEmail.getText(), loginPassword.getText());
        if (user != null) {
            MainApp.changeScene("/view/main-view", new MainController(user));
        } else {
            loginValidationLabel.setVisible(true);
        }
    }

    @FXML
    void onLoginFieldTyped() {
        loginValidationLabel.setVisible(false);
    }

    @FXML private MFXTextField signInName;
    @FXML private MFXTextField signInFirstName;
    @FXML private MFXTextField signInEmail;
    @FXML private MFXPasswordField signInPassword;
    @FXML private Label signInValidationLabel;

    @FXML void onSignInButtonClick() {
        UserRepository userRepo = new UserRepository();
        User user = null;
        try {
            user = userRepo.save(new User(
                    signInName.getText(),
                    signInFirstName.getText(),
                    signInEmail.getText(),
                    signInPassword.getText()
            ));
        } catch (SQLIntegrityConstraintViolationException e){
            System.out.println("mail en double");
            signInValidationLabel.setVisible(true);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert user != null;
        if (user.getId_compte() != 0) {
            MainApp.changeScene("/view/main-view", new MainController(user));
        }
    }

    @FXML void onSignInFieldTyped() {
        signInValidationLabel.setVisible(false);
    }
}