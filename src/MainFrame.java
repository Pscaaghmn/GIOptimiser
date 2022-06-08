import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    JPanel[] allPanels;

    public MainFrame(){
        allPanels = new JPanel[3];
        allPanels[0] = new MainMenuGUI();
        allPanels[1] = new ArtifactInventory();
        allPanels[2] = new WeaponInventory();

        for (int i = 0; i < allPanels.length; i++) {
            this.add(allPanels[i]);
            allPanels[i].setVisible((i == 0));
        }

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void navigate(int hidePanel, int showPanel){
        allPanels[hidePanel].setVisible(false);
        allPanels[showPanel].setVisible(true);
    }
}
