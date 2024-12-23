import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JSONDocParserTest {


    @Test
    public void testCorrectResume() throws myJSONExceptions {
        Document document = JSONDocParser.parseMyDoc("src/test/resources/validResume.json");
        assertTrue(document instanceof Resume);
    }

    @Test
    public void testOfMissingID() {
        Exception exception = assertThrows(myJSONExceptions.class, () -> {
            JSONDocParser.parseMyDoc("src/test/resources/missingID.json");
        });
        assertEquals("Your JSON document doesn't have 'id', missing or invalid", exception.getMessage());
    }

    @Test
    public void testOfMissingDocType() {
        Exception exception = assertThrows(myJSONExceptions.class, () -> {
            JSONDocParser.parseMyDoc("src/test/resources/missingDocType.json");
        });
        assertEquals("Your JSON document doesn't have 'document_type' field", exception.getMessage());
    }


    @Test
    public void testOfUnsupportedDocumentType() {
        Exception exception = assertThrows(myJSONExceptions.class, () -> {
            JSONDocParser.parseMyDoc("src/test/resources/unsupportedType.json");
        });
        assertEquals("Unsupported docType", exception.getMessage());
    }

    @Test
    public void testToAddDuplicateDocument() throws myJSONExceptions {
        myLibr<Document> library = new myLibr<>();
        Document document = JSONDocParser.parseMyDoc("src/test/resources/validResume.json");

        library.addDocument(document);

        Exception exception = assertThrows(myJSONExceptions.class, () -> {
            library.addDocument(document);
        });

        assertEquals("JSON Doc with this ID already exists", exception.getMessage());
    }

    @Test
    public void testToRemoveNonExistDoc() {
        myLibr<Document> library = new myLibr<>();
        Exception exception = assertThrows(myJSONExceptions.class, () -> {
            library.removeDocument("abcd");
        });
        assertEquals("Document not found", exception.getMessage());
    }


}
