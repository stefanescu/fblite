package login;

import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

/** Manages control flow for logins */
public class SceneManager {
    private Scene scene;


    public SceneManager(Scene scene) {
        this.scene = scene;

//
//        dao.save(new Person("aaab"));
//        QueryResults a = dao.find();
    }

    /**
     * Callback method invoked to notify that a user has been authenticated.
     * Will show the main application screen.
     */
    public void authenticated(String sessionID) {
        showMainView(sessionID);
    }

    /**
     * Callback method invoked to notify that a user has logged out of the main application.
     * Will show the login application screen.
     */
    public void logout() {
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/splash.fxml")
//                    getClass().getResource("/login.fxml")
            );
            scene.setRoot((Parent) loader.load());
            LoginController controller =
                    loader.<LoginController>getController();
            controller.initManager(this);
        } catch (Exception ex) {
            Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showMainView(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/main_screen.fxml")
//                    getClass().getResource("/mainview.fxml")
            );
            scene.setRoot((Parent) loader.load());
            MainViewController controller =
                    loader.<MainViewController>getController();
            controller.initSessionID(this, sessionID);
        } catch (Exception ex) {
            Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showRegisterScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/register.fxml")
            );
            scene.setRoot((Parent) loader.load());
            RegisterController controller =
                    loader.<RegisterController>getController();
            controller.initSessionID(this);
        } catch (Exception ex) {
            Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void showResetPasswordScreen() {
//        try {
//            FXMLLoader loader = new FXMLLoader(
//                    getClass().getResource("/reset_pw.fxml")
//            );
//            scene.setRoot((Parent) loader.load());
//            ResetPasswordController controller =
//                    loader.<ResetPasswordController>getController();
//            controller.initSessionID(this);
//        } catch (Exception ex) {
//            Logger.getLogger(SceneManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}