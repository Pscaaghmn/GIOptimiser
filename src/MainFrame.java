import javax.swing.*;

public class MainFrame extends JFrame{
    private static JPanel[] allPanels;

    public MainFrame(){
        allPanels = new JPanel[5];
        allPanels[0] = new MainMenuGUI();

        allPanels[1] = new ArtifactInventoryGUI();
        allPanels[2] = new WeaponInventoryGUI();

        allPanels[3] = new CalculateArtifactGUI();
        allPanels[4] = new CalculateTimeGUI();

        setLayout(null);

        for (int i = 0; i < allPanels.length; i++) {
            allPanels[i].setBounds(0,0,1500, 800);
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
