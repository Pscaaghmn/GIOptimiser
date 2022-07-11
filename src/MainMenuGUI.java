import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JPanel implements ActionListener {

    public MainMenuGUI(){
        JLabel title = new JLabel("Genshin Impact Optimiser");
        JButton viewInventory = new JButton("View Inventory");
        JButton calculateArtifact = new JButton("Calculate Expected Values");

        title.setBounds(475,150,500,100);
        viewInventory.setBounds(125,500,300,100);
        calculateArtifact.setBounds(525,500,300,100);

        viewInventory.addActionListener(this);
        calculateArtifact.addActionListener(this);
        this.setLayout(null);

        this.add(title);
        this.add(viewInventory);
        this.add(calculateArtifact);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "View Inventory" -> MainFrame.navigate(0, 1, null);
            case "Calculate Expected Values" -> MainFrame.navigate(0, 9);
        }
    }
}
