package mx.ipn.escom.ema.to;


import java.io.Serializable;

public class AttributesTO implements Serializable {

    private String name;
    private String description;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}
