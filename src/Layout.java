import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Layout implements ActionListener {
    private JFrame mainFrame;
    private JTextArea pokemon, allies;
    private int WIDTH=800;
    private int HEIGHT=700;


    public Layout() {
        prepareGUI();
    }

    public static void main(String[] args) {
        Layout layout = new Layout();
        layout.showEventDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Layout");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLayout(new GridLayout(4, 1));

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



        nextButton.setActionCommand("button 1");
        previousButton.setActionCommand("button 2");





        pokemon = new JTextArea("Pokemon");
        allies = new JTextArea("Allies");

        mainFrame.add(pokemon);
        mainFrame.add(allies);
        mainFrame.add(nextButton);
        mainFrame.add(previousButton);


        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }



}
