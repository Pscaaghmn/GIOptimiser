import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    JPanel[] allPanels;

    public MainFrame(){
        allPanels = new JPanel[3];
        allPanels[0] = new MainMenuGUI();
        allPanels[1] = new ArtifactInventory();
        allPanels[2] = new WeaponInventory();

        setLayout(null);

        for (int i = 0; i < allPanels.length; i++) {
            allPanels[i].setBounds(0,0,1500, 800);
            allPanels[i].setPreferredSize(this.getPreferredSize());

            this.add(allPanels[i]);
            allPanels[i].setVisible(i == 0);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void navigate(int hidePanel, int showPanel){
        allPanels[hidePanel].setVisible(false);
        allPanels[showPanel].setVisible(true);
    }
}
