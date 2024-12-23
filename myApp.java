
public class myApp {

    public static void main(String[] args) {
        JSONDocParser parser = new JSONDocParser();
        try{
        Document document = parser.parseMyDoc("src/test/resources/sample.json");
        System.out.println("Parsed document ID: " + document.getId());
            
            if (document instanceof Contract) {
                System.out.println("Parsed document type: CONTRACT");
            } else if (document instanceof Receipt) {
                System.out.println("Parsed document type: RECEIPT");
            } else if (document instanceof Resume) {
                System.out.println("Parsed document type: RESUME");
            }
         } catch(Exception e){
             System.out.println(e.getMessage());
    }
    }
}
