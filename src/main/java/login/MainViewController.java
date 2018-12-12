package login;

import animation.animation.AnimationType;
import animation.control.cell.AnimatedListCell;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import login.DAO.PersonDAO;
import login.models.Person;
import login.models.Post;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import prettyParts.PrettyListView;

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
    @FXML private Label friendsLabel;
    @FXML private TextArea statusText;
    @FXML private ListView friendsList;
    @FXML private ListView postList;
    @FXML private Button homeButton;
    @FXML private Button logoutButton;
    @FXML private Button editStatus;
    @FXML private Button optionsButton;
    @FXML private TextField addFriendText;
    @FXML private Button addFriendButton;
    @FXML private Button removeFriendButton;
    @FXML private Button addPostButton;




//    @FXML private Label  sessionLabel;

    public void initialize() {
        conn = MongoConn.getInstance();
        personDAO = new PersonDAO(conn.getDatastore());

        addFriendText.setPromptText("add friend by username");
        setUpOnClick();


//        friendsList = new PrettyListView<String>();
//        friendsList.setCellFactory(AnimatedListCell.forListView(AnimationType.ROTATE_RIGHT, AnimationType.FLATTERN_OUT));
//
//        friendsList.setCellFactory(lv -> {
//
//            ListCell<String> cell = new ListCell<>();
//
//            ContextMenu contextMenu = new ContextMenu();
//
//
////            MenuItem editItem = new MenuItem();
////            editItem.textProperty().bind(Bindings.format("Edit \"%s\"", cell.itemProperty()));
////            editItem.setOnAction(event -> {
////                String item = cell.getItem();
////                // code to edit item...
////            });
//            MenuItem deleteItem = new MenuItem();
//            deleteItem.textProperty().bind(Bindings.format("Remove friend"));
//            deleteItem.setOnAction(event -> removeFriend());
//            contextMenu.getItems().add(deleteItem);
//
//            cell.textProperty().bind(cell.itemProperty());
//
//            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
//                if (isNowEmpty) {
//                    cell.setContextMenu(null);
//                } else {
//                    cell.setContextMenu(contextMenu);
//                }
//            });
//            return cell ;
//        });
//        friendsList.setItems(currentProfile.getFriends());

    }


    private void onAddPostClick() {

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
                if (currentProfile.addfriend(newFriend) == 0) {
                    newFriend.addfriend(currentProfile);

                    personDAO.save(currentProfile);
                    personDAO.save(newFriend);

                    friendsList.getItems().add(newFriend.getFirstName() + " " + newFriend.getLastName());

                    Util.showInfoDialog("Friend Added!");
                }
                else
                    Util.showInfoDialog(newFriend.getFirstName() + newFriend.getLastName() + " is already your friend!");

            }
        });

        addPostButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                final Query<Person> query = personDAO.getDatastore().createQuery(Person.class);
                final List<Person> persons = query.asList();

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add new post");
                dialog.setHeaderText(null);
                dialog.setContentText("New post:");


                Optional<String> result = dialog.showAndWait();

//                result.ifPresent(content -> currentProfile.addPost(new Post("%s said..".format(currentProfile.getFirstName()) , content));
                result.ifPresent(content -> processNewPost(content));

                personDAO.save(currentProfile);

//                List<Person> p = conn.getDatastore().createQuery(Person.class).field("username").equal(addFriendText.getText()).asList();

//                if (p.isEmpty()) {
//                    Util.showErrorDialog("Username not found!");
//                    return;
//                }

//                Person newFriend = p.get(0);
//                currentProfile.addfriend(newFriend);
//
//                newFriend.addfriend(currentProfile);
//
//
//                personDAO.save(currentProfile);
//                personDAO.save(newFriend);
//
//                friendsList.getItems().add(newFriend.getFirstName() + " " + newFriend.getLastName());
//
//                Util.showInfoDialog("Friend Added!");
            }
        });
        removeFriendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remove friend");
                alert.setHeaderText("You're about to remove this friend. Continue?");
                alert.setContentText(null);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Person you = personDAO.get(new ObjectId(sessionID));

                    if (you.removeFriend(currentProfile) == 0 ) { //success
                        currentProfile.removeFriend(you);
                        you.removeFriend(currentProfile);
                        personDAO.save(currentProfile);
                        personDAO.save(you);

                        homeButton.fire();
                    }
                }
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

    private void processNewPost(String content) {

        Post newP = new Post("%s said..".format(currentProfile.getFirstName()) , content);
        postList.getItems().add(newP.getTitle() + "\n" + newP.getContent());
        currentProfile.addPost(newP);
    }

    public void initSessionID(final SceneManager sceneManager, String sessionID, String newSessionId) {
//        sessionLabel.setText(sessionID);
        this.sessionID = sessionID;
        if (newSessionId == null)
            currentProfile = personDAO.get(new ObjectId(sessionID));
        else
            currentProfile = personDAO.get(new ObjectId(newSessionId));

        removeFriendButton.setVisible(false);
        fullNameText.setText(currentProfile.getFirstName() + " " + currentProfile.getLastName());
        userText.setText(currentProfile.getUsername());
        dobText.setText(currentProfile.getDob());
        statusText.setText(currentProfile.getStatus());

        ArrayList<Person> a = currentProfile.getFriends();
        for (Person p : a) {
            friendsList.getItems().add(p.getFirstName() + " " + p.getLastName());
        }

        ArrayList<Post> p = currentProfile.getPosts();
        for (Post pp : p) {
            postList.getItems().add(pp.getContent());
        }

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                sceneManager.logout();
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                sceneManager.checkOtherProfile(sessionID, null);
            }
        });

        friendsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    final Query<Person> query = personDAO.getDatastore().createQuery(Person.class);
                    final List<Person> persons = query.asList();

                    String[] fullName = newValue.toString().split(" ");
                    List<Person> p = conn.getDatastore().createQuery(Person.class).field("firstName").equal(fullName[0]).asList();

                    String otherPersonId = null;
                    for (Person f : p) {
                        if (f.getLastName().equals(fullName[1])) {
                            otherPersonId = f.getId();
                            break;
                        }
                    }

                    sceneManager.checkOtherProfile(sessionID, otherPersonId);
                }
            }
        });


        if (!currentProfile.getId().equals(sessionID)) {
            removeFriendButton.setVisible(true);
            logoutButton.setVisible(false);
            editStatus.setVisible(false);
            addFriendText.setVisible(false);
            addFriendButton.setVisible(false);
            addPostButton.setVisible(false);
        }
    }
}