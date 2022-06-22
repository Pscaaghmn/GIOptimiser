import javax.swing.*;

public class MainFrame extends JFrame{
    private static JPanel[] allPanels;

    public MainFrame(String filePath){
        allPanels = new JPanel[7];
        allPanels[0] = new MainMenuGUI();

        allPanels[1] = new InventoryGUI(true, filePath);
        allPanels[2] = new InventoryGUI(false, filePath);
        allPanels[3] = new AddEquipmentGUI(true);
        allPanels[4] = new AddEquipmentGUI(false);

        allPanels[5] = new CalculateArtifactGUI();
        allPanels[6] = new CalculateTimeGUI();

        setLayout(null);

        for (int i = 0; i < allPanels.length; i++) {
            allPanels[i].setBounds(this.getBounds());
            allPanels[i].setPreferredSize(this.getPreferredSize());

            this.add(allPanels[i]);
            allPanels[i].setVisible(i == 0);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void navigate(int hidePanel, int showPanel){
        allPanels[hidePanel].setVisible(false);
        allPanels[showPanel].setVisible(true);
    }
}
