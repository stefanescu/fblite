package login.DAO;

import login.models.Post;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class PostDAO extends BasicDAO<Post, ObjectId> {

    public PostDAO(Datastore ds) {
        super(ds);
    }

}