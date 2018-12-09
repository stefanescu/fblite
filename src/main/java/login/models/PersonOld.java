package login.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;

@Entity
public  class PersonOld
{
    @Id
    private ObjectId id;
    private SimpleStringProperty email;



    private SimpleStringProperty password;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty dob;
    private SimpleStringProperty status;
    private SimpleListProperty<Post> posts;
    private SimpleListProperty<PersonOld> friends;
    private SimpleBooleanProperty isDOBHidden;
    private SimpleBooleanProperty isPostsHidden;
    private SimpleBooleanProperty isStatusHidden;
    private SimpleBooleanProperty isFriendsHidden;

    public PersonOld()
    {
        id = new ObjectId();
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        dob = new SimpleStringProperty();
        status = new SimpleStringProperty();

        ObservableList<Post> obsList = FXCollections.observableArrayList();
        this.posts = new SimpleListProperty<Post>(obsList);

        ObservableList<PersonOld> obsList2 = FXCollections.observableArrayList();
        this.friends = new SimpleListProperty<PersonOld>(obsList2);

        isDOBHidden = new SimpleBooleanProperty();
        isPostsHidden = new SimpleBooleanProperty();
        isStatusHidden = new SimpleBooleanProperty();
        isFriendsHidden = new SimpleBooleanProperty();

        id = new ObjectId();
    }

    public PersonOld(String firstName)
    {
        id =  new ObjectId();
        lastName = new SimpleStringProperty();
        dob = new SimpleStringProperty();
        status = new SimpleStringProperty();

        ObservableList<Post> obsList = FXCollections.observableArrayList();
        this.posts = new SimpleListProperty<Post>(obsList);

        ObservableList<PersonOld> obsList2 = FXCollections.observableArrayList();
        this.friends = new SimpleListProperty<PersonOld>(obsList2);

        isDOBHidden = new SimpleBooleanProperty();
        isPostsHidden = new SimpleBooleanProperty();
        isStatusHidden = new SimpleBooleanProperty();
        isFriendsHidden = new SimpleBooleanProperty();

        this.id = new ObjectId();
        this.firstName = new SimpleStringProperty();
        this.firstName.set(firstName);
    }

    public PersonOld(ObjectId id, String firstName, String lastName, String dob, String status,
                     ArrayList<Post> posts, ArrayList<PersonOld> friends, Boolean isDOBHidden, Boolean isPostsHidden,
                     Boolean isStatusHidden, Boolean isFriendsHidden) {
        this.id = new ObjectId();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.dob = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.isDOBHidden = new SimpleBooleanProperty();
        this.isPostsHidden = new SimpleBooleanProperty();
        this.isStatusHidden = new SimpleBooleanProperty();
        this.isFriendsHidden = new SimpleBooleanProperty();


        this.firstName.set(firstName);
        this.id = id;

        this.lastName.set(lastName);
        this.dob.set(dob);
        this.status.set(status);

        ObservableList<Post> obsList = FXCollections.observableArrayList(posts);
        this.posts = new SimpleListProperty<Post>(obsList);


        ObservableList<PersonOld> obsList2 = FXCollections.observableArrayList(friends);
        this.friends = new SimpleListProperty<PersonOld>(obsList2);


        this.isDOBHidden.set(isDOBHidden);
        this.isPostsHidden.set(isPostsHidden);
        this.isStatusHidden.set(isStatusHidden);
        this.isFriendsHidden .set(isFriendsHidden);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getDob() {
        return dob.get();
    }

    public SimpleStringProperty dobProperty() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob.set(dob);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public ObservableList<Post> getPosts() {
        return posts.get();
    }

    public SimpleListProperty<Post> postsProperty() {
        return posts;
    }

    public void setPosts(ObservableList<Post> posts) {
        this.posts.set(posts);
    }

    public ObservableList<PersonOld> getFriends() {
        return friends.get();
    }

    public SimpleListProperty<PersonOld> friendsProperty() {
        return friends;
    }

    public void setFriends(ObservableList<PersonOld> friends) {
        this.friends.set(friends);
    }

    public boolean isIsDOBHidden() {
        return isDOBHidden.get();
    }

    public SimpleBooleanProperty isDOBHiddenProperty() {
        return isDOBHidden;
    }

    public void setIsDOBHidden(boolean isDOBHidden) {
        this.isDOBHidden.set(isDOBHidden);
    }

    public boolean isIsPostsHidden() {
        return isPostsHidden.get();
    }

    public SimpleBooleanProperty isPostsHiddenProperty() {
        return isPostsHidden;
    }

    public void setIsPostsHidden(boolean isPostsHidden) {
        this.isPostsHidden.set(isPostsHidden);
    }

    public boolean isIsStatusHidden() {
        return isStatusHidden.get();
    }

    public SimpleBooleanProperty isStatusHiddenProperty() {
        return isStatusHidden;
    }

    public void setIsStatusHidden(boolean isStatusHidden) {
        this.isStatusHidden.set(isStatusHidden);
    }

    public boolean isIsFriendsHidden() {
        return isFriendsHidden.get();
    }

    public SimpleBooleanProperty isFriendsHiddenProperty() {
        return isFriendsHidden;
    }

    public void setIsFriendsHidden(boolean isFriendsHidden) {
        this.isFriendsHidden.set(isFriendsHidden);
    }

    public ObjectId getId() {
        return id.get();
    }
}

