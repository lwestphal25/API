import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

public class Layout implements ActionListener {

    public int pokemon_counter=0;
    private JFrame mainFrame;
    private JTextArea pokemon, allies;

    private JPanel buttonPanel, mainPanel, personPanel, allyPanel;

    private JLabel personLabel, allyLabel;
   private org.json.simple.JSONArray jsonObjectArray;
    private int WIDTH=800;
    private int HEIGHT=700;


    public Layout() throws ParseException{
        prepareGUI();
        pull();
    }

    public static void main(String[] args) throws  ParseException {
        Layout layout = new Layout();
        layout.showEventDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Layout");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new BorderLayout());

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        mainFrame.setVisible(true);
    }

    private void showEventDemo() {

        JButton nextButton = new JButton("Next");
        JButton previousButton = new JButton("Previous");


        personLabel = new JLabel("Person");
        allyLabel = new JLabel("Ally");

        buttonPanel = new JPanel();
        mainPanel = new JPanel();
        personPanel = new JPanel();
        allyPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        mainPanel.setLayout(new GridLayout(2, 1));
        personPanel.setLayout(new BorderLayout());
        allyPanel.setLayout(new BorderLayout());

        nextButton.setActionCommand("Next");
        previousButton.setActionCommand("Previous");



        nextButton.addActionListener(new ButtonClickListener());
        previousButton.addActionListener(new ButtonClickListener());





        pokemon = new JTextArea();
        allies = new JTextArea();

        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        buttonPanel.add(nextButton);
        buttonPanel.add(previousButton);
        mainPanel.add(personPanel);
        mainPanel.add(allyPanel);
        personPanel.add(personLabel, BorderLayout.NORTH);
        personPanel.add(pokemon, BorderLayout.CENTER);
        allyPanel.add(allyLabel, BorderLayout.NORTH);
        allyPanel.add(allies, BorderLayout.CENTER);


        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }



    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Next")) {
                pokemon.setText("");
                allies.setText("");
                pokemon_counter++;
                personLabel.setText("Person "+(pokemon_counter-1));
                allyLabel.setText("Ally "+(pokemon_counter-1));
                if (pokemon_counter > jsonObjectArray.size()){

                    pokemon_counter = 1;
                }
                JSONObject guy = (JSONObject) jsonObjectArray.get(pokemon_counter-1);
                pokemon.append((String) guy.get("name"));
                org.json.simple.JSONArray guy_allies = (JSONArray) guy.get("allies");
                for (int a = 0; a< guy_allies.size();a++){
                    allies.append((String)guy_allies.get(a));
                }
            } else if (command.equals("Previous")) {
                pokemon.setText("");
                allies.setText("");
                pokemon_counter--;
                personLabel.setText("Person "+(pokemon_counter-1));
                allyLabel.setText("Ally "+(pokemon_counter-1));
                if (pokemon_counter < 1){

                    pokemon_counter = jsonObjectArray.size();
                }
                JSONObject guy = (JSONObject) jsonObjectArray.get(pokemon_counter-1);
                pokemon.append((String)guy.get("name"));
                org.json.simple.JSONArray guy_allies = (JSONArray) guy.get("allies");
                for (int a = 0; a< guy_allies.size();a++){
                    allies.append((String)guy_allies.get(a));
                }
            }
            else {

            }
        }
    }

    public  void pull() throws ParseException {
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
        jsonObjectArray = (org.json.simple.JSONArray) parser.parse(totlaJson);
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
