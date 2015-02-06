package models;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import play.Logger;
import play.Play;

import java.net.UnknownHostException;

/**
 * Created by manoj on 2/4/15.
 */
public class JongoUtil {

    //public static Mongo connection;

    public static org.jongo.Jongo jongo;

    public static MongoClient m;

    static {
        try {
            m = new MongoClient(new MongoClientURI(Play.application().configuration().getString("mongo.uri")));

            /*
            connection = new Mongo(
                    Play.application().configuration().getString("mongo.host"),
                    Play.application().configuration().getInt("mongo.port"));
            */

            jongo = new org.jongo.Jongo(m.getDB(Play.application().configuration().getString("mongo.db")));
        } catch (UnknownHostException e) {
            Logger.error("Could not connect to mongodb", e);
        }
    }

}
