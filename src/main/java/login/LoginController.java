package login;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import login.DAO.PersonDAO;
import login.models.Person;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Controls the login screen */
public class LoginController {

    private MongoConn conn;

    private PersonDAO personDAO;


    @FXML private TextField user;
    @FXML private TextField password;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Button resetPassButton;

    public void initialize() {
        conn = MongoConn.getInstance();
        personDAO = new PersonDAO(conn.getDatastore());
    }

    public void initManager(final SceneManager sceneManager) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String sessionID = LoginController.this.authorize();
                if (sessionID != null) {
                    sceneManager.authenticated(sessionID);
                }
            }
        });

        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                sceneManager.showRegisterScreen();
            }
        });

        resetPassButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Reset your password");
                dialog.setHeaderText(null);
                dialog.setContentText("Your username:");


                Optional<String> result = dialog.showAndWait();

                final Query<Person> query = personDAO.getDatastore().createQuery(Person.class);
                final List<Person> persons = query.asList();

                List<Person> p = new ArrayList<>();

                result.ifPresent(username ->p.addAll(conn.getDatastore().createQuery(Person.class).field("username").equal(username).asList()));

                if (p.isEmpty()) {
                    Util.showErrorDialog("Username not found! Try again!");
                    return;
                }

                Person usr = p.get(0);
                usr.setPassword(Util.b64Encode("pass"));

                personDAO.save(usr);

                Util.showInfoDialog("Password reset! Your new password is \"pass\"");

            }
        });
    }

    /**
     * Check authorization credentials.
     *
     * If accepted, return a sessionID(user ObjectID) for the authorized session
     * otherwise, return null.
     */
    private String authorize() {

        String theUser = user.getText();
        String pw = password.getText();

        final Query<Person> query = conn.getDatastore().createQuery(Person.class);
        final List<Person> persons = query.asList();


        List<Person> p = conn.getDatastore().createQuery(Person.class).field("username").equal(theUser).asList();

        if (p.isEmpty())
            return null;

        Person pp = p.get(0);
        if (Util.b64Decode(pp.getPassword()).equals(pw)) {
            return pp.getId().toString();
        }

        return null;

//
//        boolean good = q.field("email").equal(theUser).field("password").equals
//
//        boolean goodUser = conn.getDatastore().createQuery(Person.class).field("email").equals(theUser);
//        boolean goodUser1 = q.and(q.criteria("email").equal(theUser), q.criteria("password").equal(pw));
//
//        boolean goodPass = conn.getDatastore().createQuery(Person.class).field("email").equals(theUser).field("password").equals(pw);


//        return
//                "open".equals(user.getText()) && "sesame".equals(password.getText())
//                        ? generateSessionID()
//                        : null;
    }

    private static int sessionID = 0;

    private String generateSessionID() {
        sessionID++;
        return "xyzzy - session " + sessionID;
    }
}