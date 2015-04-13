package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by manoj on 4/11/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    public int id;

    public String display_name;

    public String name;

    public String tag_type;

}
