package login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import login.DAO.PersonDAO;
import login.models.Person;

import java.time.format.DateTimeFormatter;

public class RegisterController {
    private MongoConn conn;

    private PersonDAO personDAO;

    @FXML private TextField fName;
    @FXML private TextField lName;
    @FXML private DatePicker dob;
    @FXML private TextField user;
    @FXML private TextField password;
    @FXML private TextField confirmPassword;

    @FXML private Button createAccountButton;
    @FXML private Button cancelButton;
    @FXML private Label errorLabel;



    public void initialize() {
        conn = MongoConn.getInstance();
        personDAO = new PersonDAO(conn.getDatastore());
    }

    private void createAccount(Person p) {
        personDAO.save(p);
    }

    public void initSessionID(final SceneManager sceneManager) {

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                sceneManager.logout();
            }
        });

        createAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {

                if (dob.getValue() == null)
                    return;


                String fn = fName.getText();
                String ln = lName.getText();
                String date = dob.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String usr = user.getText();
                String pass = password.getText();
                String confirmPass = confirmPassword.getText();

                if( fn.isEmpty() || ln.isEmpty() ||
                        date.isEmpty() || usr.isEmpty() ||
                       pass.isEmpty() || confirmPass.isEmpty() ) {

                    errorLabel.setText("Make sure all fields are filled in!");
                    return;
                }

                if (!pass.equals(confirmPass)) {
                    errorLabel.setText("Passwords don't match!");
                    return;
                }


                createAccount(new Person(fn, ln, date, usr, Util.b64Encode(pass)));
                sceneManager.showLoginScreen();
//                sceneManager.logout();
            }
        });
    }
}
