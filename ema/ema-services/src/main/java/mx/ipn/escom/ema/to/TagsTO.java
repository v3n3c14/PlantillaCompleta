package mx.ipn.escom.ema.to;

import java.io.Serializable;
import java.util.List;

import com.google.appengine.api.datastore.Key;

public class TagsTO implements Serializable {
    
    private Long id;
    private String name;
    private String description;
    private List<Key> listAttributes;
    
 /*   
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
   */
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
    public List<Key> getListAttributes() {
        return listAttributes;
    }
    public void setListAttributes(List<Key> listAttributes) {
        this.listAttributes = listAttributes;
    }
    

}
