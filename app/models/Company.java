package models;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.libs.Json;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by manoj on 2/4/15.
 */

public class Company {

//    @Id
//    @ObjectId
    public ObjectId id;

    public String name;

//    public String blog_url;
//
    public String thumb_url;
//
//    public String product_desc;
//
//    public String twitter_url;
//
//    public String company_url;
//
    public String high_concept;
//
//    public String video_url;
//
//    public String linkedin_url;
//
//    public String logo_url;
//
//    public String crunchbase_url;
//
//    public String status;
//
//    public String company_size;
//
//    public String facebook_url;
//
//    public boolean hidden;
//
//    public boolean community_profile;
//
//    public int follower_count;
//
//    public Date created_at;
//
//    public Date updated_at;
//
//    public int quality;

    protected static MongoCollection coll = JongoUtil.jongo.getCollection("companies");

    //private static JacksonDBCollection<Company, String> coll = MongoDB.getCollection("companies", Company.class, String.class);

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public static Iterable<Company> all() {
        return Company.coll.find("{}").as(Company.class);
    }

    public static void create(Company task) {
        Company.coll.save(task);
    }

    public static void create(String name){
        create(new Company(name));
    }

    public static void delete(ObjectId id) {
        //org.bson.types.ObjectId oid =  new org.bson.types.ObjectId(id);
        Company company = Company.coll.findOne(id).as(Company.class);
        if (company != null)
            Company.coll.remove(id);
    }

    public static void removeAll(){
        Company.coll.drop();
    }

    public static long getTotalNumberOfRecords() {
        return Company.coll.count();
    }

    public static ObjectNode getPaginatedResults(Map<String, String[]> params) {
        String filter ="";
        if(params.get("sSearch") != null) {
            filter = params.get("sSearch")[0];
        }
        System.out.println(filter);
        Integer pageSize = Integer.valueOf(params.get("iDisplayLength")[0]);

//      Integer page = Integer.valueOf(params.get("iDisplayStart")[0]) / pageSize;

        Integer start = Integer.valueOf(params.get("iDisplayStart")[0]);

        String sortBy = "name";
        String order = params.get("sSortDir_0")[0];
        int orderId = 1;
        if(order.equals("desc")) {
            orderId = -1;
        }

        switch(Integer.valueOf(params.get("iSortCol_0")[0])) {
            case 0 : sortBy = "name"; break;
        }

        Iterable<Company> recordsToDisplay;
        if(Strings.isNullOrEmpty(filter)) {
            recordsToDisplay = Company.coll.find("{}").skip(start).sort("{" + sortBy + ": "+orderId+"}").as(Company.class);
        } else {
            recordsToDisplay = Company.coll.find("{name: {$regex: '.*"+filter+".*'}}").skip(start).sort("{"+sortBy+": "+orderId+"}").as(Company.class);
        }

        ArrayList<Company> recordsToDisplayAsList = Lists.newArrayList(recordsToDisplay);

        Integer iTotalDisplayRecords = recordsToDisplayAsList.size();

        ObjectNode result = Json.newObject();
        result.put("sEcho", Integer.valueOf(params.get("sEcho")[0]));
        result.put("iTotalRecords", Company.getTotalNumberOfRecords());
        result.put("iTotalDisplayRecords", iTotalDisplayRecords);

        ArrayNode an = result.putArray("aaData");
        int i = 0;
        for(Company c: recordsToDisplayAsList) {
            if(i >= pageSize)
                break;
            ObjectNode resultC = Json.newObject();
            resultC.put("name", c.name);
            resultC.put("high_concept", c.high_concept);
            resultC.put("thumb_url", "<img class='company_thumb_url' src='"+c.thumb_url+"'>");

            an.add(resultC);

            //an.add(Json.toJson(c));
            i++;
        }

        return result;
    }
}
