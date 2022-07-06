import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    private static JPanel[] allPanels;

    public MainFrame(){
        int maxHeight = 700;

        setBounds(0,0, 1500, maxHeight);
        setResizable(true);

        allPanels = new JPanel[9];
        allPanels[0] = new MainMenuGUI();

        allPanels[1] = new InventoryGUI(true);
        allPanels[2] = new InventoryGUI(false);

        allPanels[3] = new AddEquipmentGUI(true);
        allPanels[4] = new AddEquipmentGUI(false);

        allPanels[5] = new ModifyEquipmentGUI(true);
        allPanels[6] = new ModifyEquipmentGUI(false);

        allPanels[7] = new CalculateArtifactGUI();
        allPanels[8] = new CalculateTimeGUI();

        setLayout(null);

        for (int i = 0; i < allPanels.length; i++) {
            this.add(allPanels[i]);
            allPanels[i].setBounds(0,0,1500,maxHeight);

            allPanels[i].setVisible(i == 0);
        }

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void navigate(int hidePanel, int showPanel){
        if (showPanel == 1 || showPanel == 2){
            ((InventoryGUI) allPanels[showPanel]).loadItems();
        }

        if (showPanel < 0){
            ((ModifyEquipmentGUI) allPanels[4 + hidePanel]).importEquipment(-1 * showPanel - 1);
            showPanel = 4 + hidePanel;
        }

        allPanels[hidePanel].setVisible(false);
        allPanels[showPanel].setVisible(true);
    }
}
