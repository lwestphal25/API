import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import java.io.DataOutputStream;

public class Bored implements ActionListener {

    public int pokemon_counter=0;
    private static final String API_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    // Your OpenAI API Key
    private static final String API_KEY = "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";
    private JFrame mainFrame;
    private JTextArea activityTA, minATA, maxATA, typeTA, minPTA, maxPTA, participantTA;

    private JPanel accessibilityPanel, typePanel, participantsPanel, pricePanel, infoPanel, outputPanel, minAPanel, maxAPanel, minPPanel, maxPPanel;

    private JLabel maxALabel, minALabel, typeLabel, participantsLabel, minPLabel, maxPLabel, activityLabel;
    private org.json.simple.JSONObject jsonObject;
    private int WIDTH=800;
    private int HEIGHT=700;


    public Bored() throws ParseException{
        prepareGUI();
    }

    public static void main(String[] args) throws  ParseException {
        Bored StarWars = new Bored();
        StarWars.showEventDemo();
        System.out.println(generateResponse("How do you work?"));
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Layout");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(2, 1));

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        mainFrame.setVisible(true);
    }

    private void showEventDemo() {

        JButton activityButton = new JButton("Display Activity");
        JButton previousButton = new JButton("Previous");


        minALabel = new JLabel("Min Accesibility");
        maxALabel = new JLabel("Max Accesibility");
        typeLabel = new JLabel("Type of Activity");
        participantsLabel = new JLabel("Number of Participants");
        minPLabel = new JLabel("Min Price");
        maxPLabel = new JLabel("Max Price");
        activityLabel = new JLabel("Activity");



        infoPanel = new JPanel();
        accessibilityPanel = new JPanel();
        minAPanel = new JPanel();
        maxAPanel = new JPanel();
        outputPanel = new JPanel();
        pricePanel = new JPanel();
        minPPanel = new JPanel();
        maxPPanel = new JPanel();
        participantsPanel = new JPanel();
        typePanel = new JPanel();

        infoPanel.setLayout(new GridLayout(1, 4));
        accessibilityPanel.setLayout(new GridLayout(2,1));
        minAPanel.setLayout(new BorderLayout());
        maxAPanel.setLayout(new BorderLayout());
        outputPanel.setLayout(new BorderLayout());
        pricePanel.setLayout(new GridLayout(2,1));
        minPPanel.setLayout(new BorderLayout());
        maxPPanel.setLayout(new BorderLayout());
        participantsPanel.setLayout(new BorderLayout());
        typePanel.setLayout(new BorderLayout());

        activityButton.setActionCommand("Next");




        activityButton.addActionListener(new ButtonClickListener());





        activityTA = new JTextArea();
        typeTA = new JTextArea();
        participantTA = new JTextArea();
        minATA = new JTextArea();
        maxATA = new JTextArea();
        minPTA = new JTextArea();
        maxPTA = new JTextArea();

        mainFrame.add(infoPanel);
        mainFrame.add(outputPanel);


        infoPanel.add(typePanel);
        infoPanel.add(participantsPanel);
        infoPanel.add(accessibilityPanel);
        infoPanel.add(pricePanel);

        typePanel.add(typeLabel, BorderLayout.NORTH);
        typePanel.add(typeTA, BorderLayout.CENTER);

        participantsPanel.add(participantsLabel, BorderLayout.NORTH);
        participantsPanel.add(participantTA, BorderLayout.CENTER);

        accessibilityPanel.add(minAPanel);
        accessibilityPanel.add(maxAPanel);
        minAPanel.add(minALabel, BorderLayout.NORTH);
        minAPanel.add(minATA, BorderLayout.CENTER);
        maxAPanel.add(maxALabel, BorderLayout.NORTH);
        maxAPanel.add(maxATA, BorderLayout.CENTER);


        pricePanel.add(minPPanel);
        pricePanel.add(maxPPanel);
        minPPanel.add(minPLabel, BorderLayout.NORTH);
        minPPanel.add(minPTA, BorderLayout.CENTER);
        maxPPanel.add(maxPLabel, BorderLayout.NORTH);
        maxPPanel.add(maxPTA, BorderLayout.CENTER);

        outputPanel.add(activityButton, BorderLayout.NORTH);
        outputPanel.add(activityTA, BorderLayout.CENTER);




        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void pull(String user_type, String user_minprice, String user_maxprice, String user_minaccesibility, String user_maxaccesibility, String user_participants) throws ParseException {
        String output = "abc";
        String totlaJson="";
        try {


            URL url = new URL("http://www.boredapi.com/api/activity/?type="+user_type+"&&participants="+user_participants+"&&minprice="+user_minprice+"&&maxprice="+user_maxprice+"&&minaccessibility="+user_minaccesibility+"&&maxaccessibility="+user_maxaccesibility);
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




        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }


    public static String generateResponse(String prompt) {
        try {
            // Create request body
            String requestBody = "{\"model\":\"gpt-3.5-turbo-1106\",\"messages\":[{\"role\":\"user\",\"content\":\"" + prompt + "\"}]}";

            // Create connection
            URL url = new URL(API_ENDPOINT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setDoOutput(true);

            // Send request
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(requestBody);
                wr.flush();
            }

            // Get response
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());
            JSONArray choices = (JSONArray) jsonResponse.get("choices");
            JSONObject choice = (JSONObject) choices.get(0);
            JSONObject message = (JSONObject) choice.get("message");
            String content = (String) message.get("content");

            // Return the content
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Failed to generate response.";
        }
    }




    private class ButtonClickListener implements ActionListener  {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

                if (command.equals("Next")) {
                    try {
                        pull(typeTA.getText(), minPTA.getText(), maxPTA.getText(), minATA.getText(), maxATA.getText(), participantTA.getText());
                        System.out.println(minATA.getText()+", "+maxATA.getText());
                    } catch (ParseException c){
                        //  System.out.println(c);
                    }

                    String activity = (String)jsonObject.get("activity");
                    String link = (String)jsonObject.get("link");
                    if (activity == null){
                        activityTA.setText("No activity aligns with the conditions you provided. Consider changing any of" +
                                "the four conditions: type (\"education\", \"recreational\", \"social\", \"diy\", \"charity\"," +
                                " \"cooking\", \"relaxation\", \"music\", \"busywork\"), " +
                                "participants (the number of participants, any number greater than " +
                                "1), accessibility (a number between 0.0 and 1.0 describing how possible an event " +
                                "is to do with zero being the most accessible), and price (a number describing  price between 0.0" +
                                " and 1.0, with 0 being free)");
                        activityTA.setLineWrap(true);
                        activityTA.setWrapStyleWord(true);;


                    }else {
                        activityTA.setText(activity +"\n");
                        activityTA.append(generateResponse("Give me a short summary of how to do this activity: "+activity)+"\n");
                        if (!link.equals("")){
                            activityTA.append("Here is a link to help: "+link);
                        }
                    }
                } else {


            }

        }
    }





}

