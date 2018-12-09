package login.DAO;

import login.models.Person;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

public class PersonDAO extends BasicDAO<Person, ObjectId> {

    public PersonDAO(Datastore ds) {
        super(ds);
    }

}