import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MainFrame extends JFrame{
    private static JPanel[] allPanels;

    public MainFrame(){
        int maxHeight = 700;

        setBounds(0,0, 1500, maxHeight);
        setResizable(true);

        allPanels = new JPanel[10];
        allPanels[0] = new MainMenuGUI();

        allPanels[1] = new InventoryGUI(true);
        allPanels[2] = new InventoryGUI(false);

        allPanels[3] = new AddEquipmentGUI(true);
        allPanels[4] = new AddEquipmentGUI(false);

        allPanels[5] = new ModifyArtifactGUI();
        allPanels[6] = new ModifyWeaponGUI();

        allPanels[7] = new CompareEquipmentGUI(true);
        allPanels[8] = new CompareEquipmentGUI(false);

        allPanels[9] = new CalculateArtifactGUI();

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
        allPanels[hidePanel].setVisible(false);
        allPanels[showPanel].setVisible(true);
    }

    public static void navigate(int hidePanel, int showPanel, int[] loadData) {
        if (loadData == null){
            ((InventoryGUI) allPanels[showPanel]).loadItems();
        }else if (loadData.length == 1) {
            ((ModifyEquipmentGUI) allPanels[showPanel]).importEquipment(loadData[0]);
        }else{
            ((CompareEquipmentGUI) allPanels[showPanel]).loadItems(loadData);
        }

        navigate(hidePanel, showPanel);
    }
}
