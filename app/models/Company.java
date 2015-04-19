package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import commons.StaticFunctions;
import controllers.routes;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.ObjectId;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by manoj on 2/4/15.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class Company {

//    @Id
    @JsonProperty("_id")
    @ObjectId
    public String id;

    public String name;

    public String blog_url;

    public String thumb_url;

    public String product_desc;

    public String twitter_url;

    public String company_url;

    public String high_concept;
//
//    public String video_url;

    public String linkedin_url;

//    public String logo_url;
//
//    public String crunchbase_url;
//
//    public String status;

    public String company_size;

    public String facebook_url;

//    public boolean hidden;
//
//    public boolean community_profile;

    public int follower_count;
//
//    public Date created_at;
//
//    public Date updated_at;

    public int quality;

    @JsonProperty("markets")
    public List<Market> markets;

    @JsonProperty("locations")
    public List<Location> locations;

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

    public static void removeAll(){
        Company.coll.drop();
    }

    public static long getTotalNumberOfRecords() {
        return Company.coll.count();
    }

    public static Company findCompanyByID(String id) {
        return Company.coll.findOne(new org.bson.types.ObjectId(id)).as(Company.class);
    }

    public static ObjectNode getPaginatedResults(Map<String, String[]> params) {
        String filter ="";
        if(params.get("sSearch") != null) {
            filter = params.get("sSearch")[0];
        }

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
            recordsToDisplay = Company.coll.find("{ $or: [ {name: {$regex: '.*"+filter+".*'}}, {high_concept: {$regex: '.*"+filter+".*'}} ] }").
                    skip(start).sort("{"+sortBy+": "+orderId+"}").as(Company.class);
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
            resultC.put("thumb_url", "<a href='"+ routes.CompanyController.show(c.id.toString()) +"'><img class='company_thumb_url' src='"+c.thumb_url+"' /></a>");
            resultC.put("markets", c.markets.toString());
            //resultC.put("company_url", c.company_url);
            //resultC.put("twitter_url", c.twitter_url);
            //resultC.put("facebook_url", c.facebook_url);
            //resultC.put("linkedin_url", c.linkedin_url);
            //resultC.put("product_desc", c.product_desc);

            an.add(resultC);
            i++;
        }

        return result;
    }

    public static ObjectNode getSimilarCompanies(String id) {
        int randInt = StaticFunctions.randInt(20, 4000);
        Iterable<Company> sComapnies = Company.coll.find("{}").skip(randInt).limit(10).as(Company.class);
        ArrayList<Company> sComapniesAsList = Lists.newArrayList(sComapnies);

        ObjectNode result = Json.newObject();
        ArrayNode an = result.putArray("similarCompanies");

        for(Company c: sComapniesAsList) {
            ObjectNode resultC = Json.newObject();
            resultC.put("id", c.id);
            resultC.put("name", c.name);
            resultC.put("high_concept", c.high_concept);
            resultC.put("thumb_url", "<a href='"+ routes.CompanyController.show(c.id.toString()) +"'><img class='company_thumb_url company_logo_small' src='"+c.thumb_url+"' /></a>");
            an.add(resultC);
        }

        return result;
    }
}