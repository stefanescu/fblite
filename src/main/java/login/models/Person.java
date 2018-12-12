package login.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public  class Person
{

    @Id
    private ObjectId id;

    @Indexed(options = @IndexOptions(unique = true))
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String dob;
    private String status;
    private ArrayList<Post> posts;
    @Reference
    private ArrayList<Person> friends;
    private Boolean isDOBHidden;
    private Boolean isPostsHidden;
    private Boolean isStatusHidden;
    private Boolean isFriendsHidden;

    public Person()
    {
        id = new ObjectId();
        this.posts = new ArrayList<Post>();
        this.friends = new ArrayList<Person>();

        isDOBHidden = false;
        isPostsHidden = false;
        isStatusHidden = false;
        isFriendsHidden = false;

    }

    public Person(String firstName)
    {
        this();
        this.firstName = firstName;
    }

    public Person(String user, String pw, String firstName)
    {
        this();
        this.firstName = firstName;
        this.username = user;
        this.password = pw;
    }

    //THIS ONE
    public Person(String firstName, String lastName, String dob, String user, String pw)
    {
        this();
        this.firstName = firstName;
        this.lastName= lastName;
        this.dob = dob;
        this.username = user;
        this.password = pw;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(String newId) {
        this.id = new ObjectId(newId);
    }

    public void setId(ObjectId newId) {
        this.id = newId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Person> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Person> friends) {
        this.friends = friends;
    }

    public Boolean getDOBHidden() {
        return isDOBHidden;
    }

    public void setDOBHidden(Boolean DOBHidden) {
        isDOBHidden = DOBHidden;
    }

    public Boolean getPostsHidden() {
        return isPostsHidden;
    }

    public void setPostsHidden(Boolean postsHidden) {
        isPostsHidden = postsHidden;
    }

    public Boolean getStatusHidden() {
        return isStatusHidden;
    }

    public void setStatusHidden(Boolean statusHidden) {
        isStatusHidden = statusHidden;
    }

    public Boolean getFriendsHidden() {
        return isFriendsHidden;
    }

    public void setFriendsHidden(Boolean friendsHidden) {
        isFriendsHidden = friendsHidden;
    }

    public int addfriend(Person person) {
        if (person != null) {
//            if (!friends.contains(person))
//                    friends.add(person);

            for (Person p : friends) {
                if (p.getId().toString().equals(person.getId()))
                    return 1;
            }
            friends.add(person);
            return 0;
        }
        return 1;
    }


    public int removeFriend(Person person) {
        if (person != null) {

//            friends.remove(person);
            for (Person p : friends) {
                if (p.getId().toString().equals(person.getId())) {
                    friends.remove(p);
                    return 0;
                }
            }
            return 1;
        }
        return 1;
    }

    public void addPost(Post p) {
        if (p != null)
            posts.add(p);
    }

    public void removePost(Post p) {
        if (p != null)
            posts.remove(p);
    }
}

