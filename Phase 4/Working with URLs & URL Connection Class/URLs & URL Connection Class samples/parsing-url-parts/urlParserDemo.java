import java.net.URL;
import java.net.MalformedURLException;

public class urlParserDemo {
    public static void main(String[] args) {
        try {
            URL url = new URL(
                    "https://www.demonstration.com:9000/products/list.html?category=books&sort-price#reviews");
            System.out.println("Protocol         : " + url.getProtocol());
            System.out.println("Host             : " + url.getHost());
            System.out.println("Port             : " + url.getPort());
            System.out.println("Default Port:    : " + url.getDefaultPort());
            System.out.println("Path             : " + url.getPath());
            System.out.println("Query            :" + url.getQuery());
            System.out.println("File             :" + url.getFile());
            System.out.println("Ref              :" + url.getRef());
            System.out.println("Full URL         :" + url.toExternalForm());
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL: " + e.getMessage());
        }
    }

}
