package login;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import login.DAO.PersonDAO;
import login.models.Person;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Controls the main application screen */
public class MainViewController {

    private String sessionID;
    private Person currentProfile;
    private MongoConn conn;
    private PersonDAO personDAO;

    @FXML private Label fullNameText;
    @FXML private Label userText;
    @FXML private Label dobText;
    @FXML private TextArea statusText;
    @FXML private ListView friendsList;
    @FXML private Button homeButton;
    @FXML private Button logoutButton;
    @FXML private Button editStatus;
    @FXML private Button optionsButton;
    @FXML private TextField addFriendText;
    @FXML private Button addFriendButton;




//    @FXML private Label  sessionLabel;

    public void initialize() {
        conn = MongoConn.getInstance();
        personDAO = new PersonDAO(conn.getDatastore());

        addFriendText.setPromptText("add friend by username");
        setUpOnClick();

//        friendsList.setItems(currentProfile.getFriends());

    }


    private void setUpOnClick() {

        addFriendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                final Query<Person> query = personDAO.getDatastore().createQuery(Person.class);
                final List<Person> persons = query.asList();

                List<Person> p = conn.getDatastore().createQuery(Person.class).field("username").equal(addFriendText.getText()).asList();

                if (p.isEmpty()) {
                    Util.showErrorDialog("Username not found!");
                    return;
                }

                Person newFriend = p.get(0);
                currentProfile.addfriend(newFriend);

                newFriend.addfriend(currentProfile);


                personDAO.save(currentProfile);
                personDAO.save(newFriend);

                friendsList.getItems().add(newFriend.getFirstName() + " " + newFriend.getLastName());

                Util.showInfoDialog("Friend Added!");
            }
        });

        editStatus.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                TextInputDialog dialog = new TextInputDialog(statusText.getText());
                dialog.setTitle("Edit your Status");
                dialog.setHeaderText(null);
                dialog.setContentText("New Status:");


                Optional<String> result = dialog.showAndWait();

                result.ifPresent(status -> statusText.setText(status));

                currentProfile.setStatus(statusText.getText());
                personDAO.save(currentProfile);
            }
        });

        optionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                // Create the custom dialog.
                Dialog<ArrayList<Object>> dialog = new Dialog<>();
                dialog.setTitle("Settings");
                dialog.setHeaderText(null);

// Set the icon (must be included in the project).
//                dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

// Set the button types.
                ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

// Create the username and password labels and fields.
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField fName = new TextField(currentProfile.getFirstName());
                fName.setPromptText("First Name");
                TextField lName = new TextField(currentProfile.getLastName());
                lName.setPromptText("Last Name");
                TextField dob = new TextField(currentProfile.getDob());
                dob.setPromptText("Date of Birth");

                CheckBox hideDob = new CheckBox("Hide Date of Birth");
                hideDob.setSelected(currentProfile.getDOBHidden());


                grid.add(new Label("First Name:"), 0, 0);
                grid.add(fName, 1, 0);
                grid.add(new Label("Last Name:"), 0, 1);
                grid.add(lName, 1, 1);
                grid.add(new Label("Date of Birth:"), 0, 2);
                grid.add(dob, 1, 2);
                grid.add(hideDob, 2, 2);



// Enable/Disable login button depending on whether a username was entered.
//                Node loginButton = dialog.getDialogPane().lookupButton(saveButton);
//                loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
//                fName.textProperty().addListener((observable, oldValue, newValue) -> {
//                    loginButton.setDisable(newValue.trim().isEmpty());
//                });

                dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
//                Platform.runLater(() -> fName.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == saveButton) {
                        ArrayList<Object> a = new ArrayList<>();
                        a.add(fName.getText());
                        a.add(lName.getText());
                        a.add(dob.getText());
                        a.add(hideDob.isSelected());

                        return a;
//                        return new ArrayList<String>(fName.getText(), lName.getText());
                    }
                    return null;
                });

                Optional<ArrayList<Object>> result = dialog.showAndWait();

                result.ifPresent(a -> {
                    fullNameText.setText(a.get(0).toString() + " " + a.get(1).toString());
                    dobText.setText(a.get(2).toString());

                    currentProfile.setFirstName(a.get(0).toString());
                    currentProfile.setLastName(a.get(1).toString());
                    currentProfile.setDob(a.get(2).toString());
                    currentProfile.setDOBHidden((Boolean) a.get(3));

                    personDAO.save(currentProfile);

//                    System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
                });

            }
        });

    }

    public void initSessionID(final SceneManager sceneManager, String sessionID) {
//        sessionLabel.setText(sessionID);
        this.sessionID = sessionID;
        currentProfile = personDAO.get(new ObjectId(sessionID));
        fullNameText.setText(currentProfile.getFirstName() + " " + currentProfile.getLastName());
        userText.setText(currentProfile.getUsername());
        dobText.setText(currentProfile.getDob());
        statusText.setText(currentProfile.getStatus());

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                sceneManager.logout();
            }
        });
    }
}