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

public class Bored implements ActionListener {

    public int pokemon_counter=0;
    private JFrame mainFrame;
    private JTextArea activityTextArea;

    private JPanel buttonPanel, mainPanel, personPanel, allyPanel;

    private JLabel personLabel, allyLabel;
    private org.json.simple.JSONObject jsonObject;
    private int WIDTH=800;
    private int HEIGHT=700;


    public Bored() throws ParseException{
        prepareGUI();
    }

    public static void main(String[] args) throws  ParseException {
        Bored StarWars = new Bored();
        StarWars.showEventDemo();
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





        activityTextArea = new JTextArea();


        mainFrame.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        buttonPanel.add(nextButton);
        buttonPanel.add(previousButton);
        mainPanel.add(personPanel);
        mainPanel.add(allyPanel);
        personPanel.add(personLabel, BorderLayout.NORTH);
        personPanel.add(activityTextArea, BorderLayout.CENTER);
        allyPanel.add(allyLabel, BorderLayout.NORTH);


        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void pull(String user_type, String user_minprice, String user_maxprice, String user_minaccesibility, String user_maxaccesibility, String user_participants) throws ParseException {
        String output = "abc";
        String totlaJson="";
        try {


            URL url = new URL("http://www.boredapi.com/api/activity/?type="+user_type+"&&participants="+user_participants+"&&minprice="+user_minprice+"&&maxprice="+user_maxprice+"&&minaccesibility="+user_minaccesibility+"&&maxaccesibility="+user_maxaccesibility);
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
        jsonObject = (org.json.simple.JSONObject) parser.parse(totlaJson);
        System.out.println(jsonObject);

        try {


            String type = (String)jsonObject.get("type");
            String activity = (String)jsonObject.get("activity");
            System.out.println(type);
            System.out.println(activity);


        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }



    private class ButtonClickListener implements ActionListener  {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("Next")) {
                try {
                    pull("social", "", "", "", "", "3");
                } catch (ParseException c){
                  //  System.out.println(c);
                }
                String activity = (String)jsonObject.get("activity");
                activityTextArea.setText(activity);
            } else {

            }
        }
    }





}

