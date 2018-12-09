package login;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import login.models.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import static java.lang.String.format;


public class MongoConn {

    private static MongoConn instance = new MongoConn();

    private MongoClient mongo = null;
    private Datastore dataStore = null;
    private Morphia morphia = null;

    private MongoConn() {}

    public MongoClient getMongo() throws RuntimeException {
        if (mongo == null) {
//            logger.debug("Starting Mongo");
            MongoClientOptions.Builder options = MongoClientOptions.builder()
                    .connectionsPerHost(4)
                    .maxConnectionIdleTime((600000))
                    .maxConnectionLifeTime((1200 * 1000));
//                    .sslEnabled(true);
            ;

//            MongoClientURI uri = new MongoClientURI("mongodb://comp585-shard-00-00-3tsuh.gcp.mongodb.net:27017,comp585-shard-00-01-3tsuh.gcp.mongodb.net:27017," +
//                    "comp585-shard-00-02-3tsuh.gcp.mongodb.net:27017/db1?ssl=true", options);
//            logger.info("About to connect to MongoDB @ " + uri.toString());
//            MongoClientURI uri = new MongoClientURI("mongodb://comp585-shard-00-02-3tsuh.gcp.mongodb.net:27017/", options);
            MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017", options);

            try {
                mongo = new MongoClient(uri);
//                mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
            } catch (MongoException ex) {
//                logger.error("An error occoured when connecting to MongoDB", ex);
                Util.print("error: + {}".format(ex.toString()));
            } catch (Exception ex) {
//                logger.error("An error occoured when connecting to MongoDB", ex);
                Util.print("error: + {}".format(ex.toString()));
            }

            // To be able to wait for confirmation after writing on the DB
//            mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        }

        return mongo;
    }

    public Morphia getMorphia() {
        if (morphia == null) {
//            logger.debug("Starting Morphia");
            morphia = new Morphia();

//            logger.debug(format("Mapping packages for clases within %s", BaseMongoDO.class.getName()));
            morphia.map(Person.class);
            morphia.map(Post.class);
        }

        return morphia;
    }

    public Datastore getDatastore() {
        if (dataStore == null) {
            String dbName = "fblite";
//            logger.debug(format("Starting DataStore on DB: %s", dbName));
            dataStore = getMorphia().createDatastore(getMongo(), dbName);
        }

        return dataStore;
    }

    public void init() {
//        logger.debug("Bootstraping");
        getMongo();
        getMorphia();
        getDatastore();
    }

    public void close() {
//        logger.info("Closing MongoDB connection");
        if (mongo != null) {
            try {
                mongo.close();
//                logger.debug("Nulling the connection dependency objects");
                mongo = null;
                morphia = null;
                dataStore = null;
            } catch (Exception e) {
//                logger.error(format("An error occurred when closing the MongoDB connection\n%s", e.getMessage()));
                Util.print(format("An error occurred when closing the MongoDB connection\n%s", e.getMessage()));
            }
        } else {
//            logger.warn("mongo object was null, wouldn't close connection");
            Util.print("mongo object was null, no need to close connection");

        }
    }

    public static MongoConn getInstance() {
        return instance;
    }
}