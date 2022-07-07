import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InventoryGUI extends JPanel implements ActionListener {

    private final boolean isArtifact;

    private Database[] contents;

    private ArrayList<JButton> items;
    private JLabel[] itemDetails;
    private final JButton order;
    private JPanel itemsBox;

    public InventoryGUI(Boolean isArtifact){
        this.setLayout(null);
        this.isArtifact = isArtifact;

        if (isArtifact) {
            contents = new Database[]{new Database("artifact_att.txt",39), new Database("artifact_val.txt", 20)};
        }else{
            contents = new Database[]{new Database("weapon_stats.txt",53)};
        }

        items = new ArrayList<>();

        itemDetails = new JLabel[(isArtifact ? 8 : 5)];
        order = new JButton("^");

        JButton homeButton = new JButton("Home");
        JButton artifactInventoryButton = new JButton("Artifacts");
        JButton weaponInventoryButton = new JButton("Weapons");
        JButton addNewItemButton = new JButton("Add New " + (isArtifact? "Artifact" : "Weapon"));
        JButton editItemButton = new JButton("Edit");
        JButton compareItemsButton = new JButton("Compare");
        JComboBox<String> sortOptions = new JComboBox<>(new String[]{"Name", "Level"});
        itemsBox = new JPanel();

        order.setBounds(200,500,50,50);
        homeButton.setBounds(0,0,70,50);
        artifactInventoryButton.setBounds(100,0,100,50);
        weaponInventoryButton.setBounds(200,0,100,50);
        addNewItemButton.setBounds(400,0,150,50);
        editItemButton.setBounds(1000,500,100,50);
        compareItemsButton.setBounds(1100,500,100,50);
        sortOptions.setBounds(0,500,200,50);
        for (int i = 0; i < itemDetails.length; i++) {
            itemDetails[i] = new JLabel();
            itemDetails[i].setBounds(900,100 + (i*20),200,20);
            this.add(itemDetails[i]);
        }

        itemsBox.setBounds(0,70,800,420);
        itemsBox.setLayout(new GridLayout(0,5));


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
    }

    public void loadItems(){
        itemsBox.removeAll();
        items.clear();
        for (int i = 0; i < contents[0].getRecordCount(); i++) {
            items.add(new JButton((i < 9 ? "0" : "") + (i+1) + ": " + contents[0].getRecord(i).substring(0,30).trim()));
            itemsBox.add(items.get(i));
            items.get(i).addActionListener(this);
        }
    }

    private void sort(String sortOption){
        //Selection sort
        //TODO: ADD FIELDS
    }

    private void populateDescriptionLabels(int buttonIndex){
        String[] fields = Database.recordToArray(contents[0].getRecord(buttonIndex),(isArtifact ? new int[]{30, 1, 2, 1, 1, 1, 1, 2} : new int[]{40, 1, 1, 3, 2, 2, 4}));
        itemDetails[0].setText("(" + (buttonIndex < 9 ? "0" : "") + (buttonIndex+1) + ") " + fields[0].trim());

        if (isArtifact){
            String[] values = Database.recordToArray(contents[1].getRecord(buttonIndex), new int[]{4, 4, 4, 4, 4});
            itemDetails[1].setText("Piece: " + new String[]{"Flower","Plume","Sands","Goblet","Circlet"}[Integer.parseInt(fields[1])]);
            itemDetails[2].setText("Level: " + fields[7]);
            itemDetails[3].setText(Equipment.intAttToStr(Integer.parseInt(fields[2].trim())) + ": " + values[0]);
            for (int i = 0; i < 4; i++) {
                itemDetails[i+4].setText(Equipment.intAttToStr(Integer.parseInt(fields[i+3].trim())) + ": " + values[i+1]);
            }
        }else{
            itemDetails[1].setText("Refinement Rank: " + fields[2]);
            itemDetails[2].setText("Level: " + fields[5]);
            itemDetails[3].setText("Base ATK: " + fields[3]);
            itemDetails[4].setText(Equipment.intAttToStr(Integer.parseInt(fields[4].trim())) + ": " + fields[6]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home" -> MainFrame.navigate((isArtifact ? 1 : 2), 0);
            case "Artifacts" -> MainFrame.navigate(2, 1);
            case "Weapons" -> MainFrame.navigate(1, 2);
            case "Add New Artifact" -> MainFrame.navigate(1, 3);
            case "Add New Weapon" -> MainFrame.navigate(2, 4);
            case "^" -> order.setText("v");
            case "v" -> order.setText("^");
            case "comboBoxChanged" -> sort((String) ((JComboBox<?>) e.getSource()).getSelectedItem());
            case "Edit" -> MainFrame.navigate((isArtifact ? 1 : 2), -1 * Integer.parseInt(itemDetails[0].getText().substring(1, 3)));
            default -> populateDescriptionLabels(Integer.parseInt(e.getActionCommand().substring(0, 2)) - 1);
        }
    }

}
