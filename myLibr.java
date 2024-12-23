import java.util.HashMap;
import java.util.Map;

public class myLibr<T extends Document> {
    private Map<String, T> JSONDoc = new HashMap<>();
    
    public void addDocument(T document) throws myJSONExceptions {
        if (JSONDoc.containsKey(document.getId())) {
            throw new myJSONExceptions("JSON Doc with this ID already exists");
        }
        JSONDoc.put(document.getId(), document);
    }
    
    public T getDocument(String id) throws myJSONExceptions {
        if (!JSONDoc.containsKey(id)) {
            throw new myJSONExceptions("Document not found");
        }
        return JSONDoc.get(id);
    }
    
    public void removeDocument(String id) throws myJSONExceptions {
        if (!JSONDoc.containsKey(id)) {
            throw new myJSONExceptions("Document not found");
        }
        JSONDoc.remove(id);
    }
}
