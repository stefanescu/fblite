package login;

import login.DAO.PersonDAO;
import login.models.Person;
import org.mongodb.morphia.query.QueryResults;

public class daotestmain {
    public static void main(String[] args) {
        MongoConn conn = MongoConn.getInstance();
        PersonDAO dao = new PersonDAO(conn.getDatastore());

        dao.save(new Person("stfn", "pass", "stfn"));
        dao.save(new Person("stfn2", "pass", "stfn2"));
//        QueryResults a = dao.find();
//
//
//        PostDAO pDAO = new PostDAO(conn.getDatastore());
//        pDAO.save(new Post("post1", "haha4"));
//
//        Query<Post> q = conn.getDatastore().createQuery(Post.class);
//        List<Post> posts = q.asList();

//        for (Post p : posts)
//            Util.print(p.getTitle());

//        System.out.println(Arrays.toString(a.asList().toArray()));

//        ObjTestDao dao2 = new ObjTestDao(conn.getDatastore());
//        dao2.save(new ObjTest(2));

//        ObjectId id = new ObjectId();
//
//        ArrayList<Post> postList = new ArrayList<Post>();
//
//        postList.add(new Post("a", "b"));
//
//        ArrayList<Person> friendsList = new ArrayList<Person>();
//
//        friendsList.add(new Person(id, "b","a","a","a",
//                postList,friendsList,true, true, true, true));
//
//        dao.save(new Person(id, "a","a","a","a",
//                postList,friendsList,true, true, true, true));
//        Util.print("result: " + a);
    }

}
