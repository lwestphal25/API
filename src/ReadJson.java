import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


// video to load jar
//https://www.youtube.com/watch?v=QAJ09o3Xl_0

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Program for print data in JSON format.
public class ReadJson {
    public static void main(String args[]) throws ParseException {
        // In java JSONObject is used to create JSON object
        // which is a subclass of java.util.HashMap.

        JSONObject file = new JSONObject();
        file.put("Full Name", "Ritu Sharma");
        file.put("Roll No.", new Integer(1704310046));
        file.put("Tution Fees", new Double(65400));


        // To print in JSON format.
        System.out.print(file.get("Tution Fees"));
        pull();

    }

    public static void pull() throws ParseException {
        String output = "abc";
        String totlaJson="";
        try {

            URL url = new URL("https://last-airbender-api.fly.dev/api/v1/characters");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                totlaJson+=output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        org.json.simple.JSONArray jsonObjectArray = (org.json.simple.JSONArray) parser.parse(totlaJson);
        System.out.println(jsonObjectArray);

        try {
            System.out.println(jsonObjectArray.get(0));

            JSONObject secretTunnelGuy = (JSONObject) jsonObjectArray.get(0);
            System.out.println(secretTunnelGuy.get("name"));
            org.json.simple.JSONArray secretTunnelGuyAllies = (JSONArray) secretTunnelGuy.get("allies");
            System.out.println(secretTunnelGuyAllies.get(0));

            JSONObject guy2 = (JSONObject) jsonObjectArray.get(1);
            System.out.println(guy2.get("name"));
            org.json.simple.JSONArray guy2Allies = (JSONArray) guy2.get("allies");
            System.out.println(guy2Allies.get(0));

            for (int i=0;i<jsonObjectArray.size();i++){
                JSONObject guy = (JSONObject) jsonObjectArray.get(i);
                System.out.println("Person "+(i+1)+ ":" + guy.get("name"));
                org.json.simple.JSONArray guy_allies = (JSONArray) guy.get("allies");
                for (int a = 0; a< guy_allies.size();a++){
                    System.out.println("Person "+(i+1)+ " Ally:" +guy_allies.get(a));
                }
            }


            // String name = (String)jsonObjectArray.get("name");

          //  org.json.simple.JSONArray msg = (org.json.simple.JSONArray) jsonObjectArray.get("abilities");
           // int n =   msg.size(); //(msg).length();
          //  for (int i = 0; i < n; ++i) {
           //     String test =(String) msg.get(i);
           //     System.out.println(test);
         //   }
         //   System.out.println(name);
        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }
}


