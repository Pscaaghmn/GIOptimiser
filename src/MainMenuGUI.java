import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JPanel implements ActionListener {

    private final int VIEW_INVENTORY_BUTTON = 0;
    private JLabel title;
    private JButton viewInventory;
    private JButton calculateArtifact;
    private JButton calculateTime;

    public MainMenuGUI(){
        title = new JLabel("Genshin Impact Optimiser");
        title.setBounds(50,10, 500,250);

        viewInventory = new JButton("View Inventory");
        viewInventory.setBounds(0,400, 300,250);

        calculateArtifact = new JButton("View Inventory");
        calculateArtifact.setBounds(300,400, 300,250);

        calculateTime = new JButton("View Inventory");
        calculateTime.setBounds(600,400, 300,250);

        this.setBounds(0,0,1500, 800);

        GridLayout layout = new GridLayout(0,3);
        setLayout(layout);

        add(title);
        add(viewInventory);
        add(calculateArtifact);
        add(calculateTime);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
