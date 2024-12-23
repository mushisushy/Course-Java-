import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class JSONDocParser {
    
    public static Document parseMyDoc(String filename) throws myJSONExceptions {
        StringBuffer myDocuContent = new StringBuffer();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String newline;
            while ((newline = reader.readLine()) != null) {
                myDocuContent.append(newline.trim());
            }
        } catch (Exception e) {
            throw new myJSONExceptions("File can't be found/be read");
        }

        String docToJSONStr = myDocuContent.toString();
        if (!docToJSONStr.contains("document_type")) {
            throw new myJSONExceptions("Your JSON document doesn't have 'document_type' field");
        }
        
        if (!docToJSONStr.contains("id")) {
            throw new myJSONExceptions("Your JSON document doesn't have 'id', missing or invalid");
        }

        String id = getDocField(docToJSONStr, "id");
        String documentType = getDocField(docToJSONStr, "document_type");
        
        switch (documentType) {
            case "CONTRACT":
                return new Contract(id);
            case "RECEIPT":
                return new Receipt(id);
            case "RESUME":
                return new Resume(id);
            default:
                throw new myJSONExceptions("Unsupported docType");
        }
    }


    private static String getDocField(String docToJSONStr, String fieldName) throws myJSONExceptions {
        String findField = "\"" + fieldName + "\":";
        int startIndex = docToJSONStr.indexOf(findField);
        if (startIndex == -1) {
            throw new myJSONExceptions("'" + fieldName + "' is missing/invalid field");
        }
        startIndex += findField.length();
        while (startIndex < docToJSONStr.length() && (docToJSONStr.charAt(startIndex) == ' ' || docToJSONStr.charAt(startIndex) == '\"')) {
            startIndex++;
        }
        int endIndex = docToJSONStr.indexOf("\"", startIndex);
        if (endIndex == -1) {
            throw new myJSONExceptions("Field '" + fieldName + "' is in wrong format");
        }
        return docToJSONStr.substring(startIndex, endIndex);
    }
}

abstract class Document {
    private String id;
    
    public Document(String id) {

        this.id = id;
    }
    
    public String getId() {

        return id;
    }
}

class Contract extends Document {
    public Contract(String id) {

        super(id);
    }
}

class Receipt extends Document {
    public Receipt(String id) {

        super(id);
    }
}

class Resume extends Document {
    public Resume(String id) {

        super(id);
    }
}
