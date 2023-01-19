import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> searchStrings = new ArrayList<String>();  ;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("There are this many strings you can search for: %d", searchStrings.size());
        }
        else {
        System.out.println("Path: " + url.getPath());
        if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                searchStrings.add(parameters[1]);
                return("String " + parameters[1] + " added as a search result");
            }
        }
        if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                for (int i = 0; i < searchStrings.size(); i++) {
                    if (searchStrings.get(i).contains(parameters[1])) {
                        System.out.print(searchStrings.get(i));
                    }

                }
                return("All Strings containing substring printed");
            }
        }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
