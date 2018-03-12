//Author: Elad Finish

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GoogleFormInputs {

    public static void main(String[] args) {
        String formId = "********";
        String url = "https://docs.google.com/forms/d/e/" + formId + "/viewform";
        printGoogleFormInputTextIds(url);
    }

    private static void printGoogleFormInputTextIds(String url) {

        String source = "";

        try {
            source = getUrlSource(url);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        String regex = "(?:<textarea|<input type=\"text\"|<input type=\"email\")" +
                ".*?aria-label=\"(.+?)\".*?name=\"(entry.\\d+)\".*?>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);

        int counter = 0;
        while (matcher.find()) {
            counter++;
            System.out.print("final String KEY" + counter + " = \"" + matcher.group(2) + "\";");
            System.out.print(" //" + matcher.group(1));
            System.out.println();
        }
    }

    private static String getUrlSource(String url) throws IOException {
        URL page = new URL(url);
        URLConnection urlConnection = page.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            stringBuilder.append(inputLine);
        in.close();

        return stringBuilder.toString();
    }
}
