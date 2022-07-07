import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JPanel implements ActionListener {

    public MainMenuGUI(){
        JLabel title = new JLabel("Genshin Impact Optimiser");
        JButton viewInventory = new JButton("View Inventory");
        JButton calculateArtifact = new JButton("Calculate predicted Artifact with time or resin");
        JButton calculateTime = new JButton("Calculate predicted time or resin");

        title.setBounds(475,150,500,100);
        viewInventory.setBounds(125,500,300,100);
        calculateArtifact.setBounds(525,500,300,100);
        calculateTime.setBounds(925,500,300,100);

        viewInventory.addActionListener(this);
        calculateArtifact.addActionListener(this);
        calculateTime.addActionListener(this);
        this.setLayout(null);

        this.add(title);
        this.add(viewInventory);
        this.add(calculateArtifact);
        this.add(calculateTime);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "View Inventory" -> MainFrame.navigate(0, 1);
            case "Calculate predicted Artifact with time or resin" -> MainFrame.navigate(0, 7); //TODO: Change
            case "Calculate predicted time or resin" -> MainFrame.navigate(0, 8);
        }
    }
}
