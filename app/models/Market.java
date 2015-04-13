package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by manoj on 4/7/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Market {
    public int id;

    public String display_name;

    public String name;

    public String angellist_url;

    public String tag_type;

    public Market(){

    }

}
