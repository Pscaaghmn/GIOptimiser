import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InventoryGUI extends JPanel implements ActionListener {

    private final boolean isArtifact;
    private final String filePath;

    private Database[] contents;

    private ArrayList<JButton> items;
    private JLabel[] itemDetails;  //name, level, rarity, description, primary attribute, primary value
    private final JButton order;

    public InventoryGUI(Boolean isArtifact, String filePath){
        this.setLayout(null);
        this.isArtifact = isArtifact;

        this.filePath = filePath;
        if (isArtifact) {
            contents = new Database[]{new Database(filePath+"artifact_att.txt",44), new Database(filePath+"artifact_val.txt", 20)};
        }else{
            contents = new Database[]{new Database(filePath+"weaponstats.txt",44)};
        }

        items = new ArrayList<>();
        for (int i = 0; i < contents[0].getRecordCount(); i++) {
            items.add(new JButton();//TODO: substring the getrecord
        }

        itemDetails = new JLabel[6];
        order = new JButton("^");

        JButton homeButton = new JButton("Home");
        JButton artifactInventoryButton = new JButton("Artifacts");
        JButton weaponInventoryButton = new JButton("Weapons");
        JButton addNewItemButton = new JButton("Add New " + (isArtifact? "Artifact" : "Weapon"));
        JButton editItemButton = new JButton("Edit");
        JButton compareItemsButton = new JButton("Compare");
        JComboBox<String> sortOptions = new JComboBox<>(new String[]{"Name", "Level"});
        JPanel itemsBox = new JPanel();

        order.setBounds(200,500,50,50);
        homeButton.setBounds(0,0,70,50);
        artifactInventoryButton.setBounds(100,0,100,50);
        weaponInventoryButton.setBounds(200,0,100,50);
        addNewItemButton.setBounds(400,0,150,50);
        editItemButton.setBounds(1000,500,100,50);
        compareItemsButton.setBounds(1100,500,100,50);
        sortOptions.setBounds(0,500,200,50);
        for (int i = 0; i < 6; i++) {
            itemDetails[i] = new JLabel();
            itemDetails[i].setBounds(900,100 + (i*50),200,50);
            this.add(itemDetails[i]);
        }
        itemsBox.setBounds(0,70,1200,470);

        order.addActionListener(this);
        homeButton.addActionListener(this);
        artifactInventoryButton.addActionListener(this);
        weaponInventoryButton.addActionListener(this);
        addNewItemButton.addActionListener(this);
        editItemButton.addActionListener(this);
        compareItemsButton.addActionListener(this);
        sortOptions.addActionListener(this);

        artifactInventoryButton.setEnabled(!isArtifact);
        weaponInventoryButton.setEnabled(isArtifact);

        this.add(order);
        this.add(homeButton);
        this.add(artifactInventoryButton);
        this.add(weaponInventoryButton);
        this.add(addNewItemButton);
        this.add(editItemButton);
        this.add(compareItemsButton);
        this.add(sortOptions);
        this.add(itemsBox);

        itemsBox.setLayout(new GridLayout(3,7));
        for (JButton item:
                items) {

        }
    }

    private void sort(String sortOption){
        //Selection sort
        //TODO: ADD FIELDS
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Home":
                MainFrame.navigate((isArtifact? 1:2),0);
                break;

            case "Artifacts":
                MainFrame.navigate(2,1);
                break;

            case "Weapons":
                MainFrame.navigate(1,2);
                break;

            case "Add New Artifact":
                MainFrame.navigate(1,3);
                break;

            case "Add New Weapon":
                MainFrame.navigate(2,4);
                break;

            case "^":
                order.setText("v");
                break;

            case "v":
                order.setText("^");
                break;

            case "comboBoxChanged":
                sort((String)((JComboBox<?>)e.getSource()).getSelectedItem());
                break;
        }
    }

}
