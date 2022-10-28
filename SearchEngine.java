import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {

    List<String>  words = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("This is the homepage");
        } else if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                words.add(parameters[1]);
                return parameters[1] + " added";
            }
        } else {
            if (url.getPath().contains("/search")) {
                String[] param = url.getQuery().split("=");
                if (param[0].equals("s")) {
                    List<String> wordsFound = new ArrayList<>();
                    String res = "words found: \n";
                    for (int i=0; i<words.size();i++ ){
                        if (words.get(i).contains(param[1])){
                            wordsFound.add(words.get(i));
                        }
                    }

                    for (int j=0; j<wordsFound.size();j++){
                        res = res + wordsFound.get(j) + "\n";
                    }
                    return res;
                }
            }
        }
        return "404 Not Found!";
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