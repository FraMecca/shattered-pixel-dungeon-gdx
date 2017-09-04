package api.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralResponse {
    private long id;
    private String name;
    private String attr;

    public GeneralResponse(String name, String attr){
        this.id = 0;
        this.name = name;
        this.attr = attr;
    };

    public GeneralResponse(String name, String attr, long id) {
        this.id = id;
        this.name = name;
        this.attr = attr;
    }

    @JsonProperty
    public long getId(){
        return this.id;
    }

    @JsonProperty
    public String getAttr(){
        return this.attr;
    }

    @JsonProperty
    public String getName(){
        return this.name;
    }
}
