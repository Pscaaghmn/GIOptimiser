import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEquipmentGUI extends JPanel implements ActionListener{

    protected JComboBox<String> nameComboBox;
    protected JComboBox<String> typeComboBox;
    protected JButton addButton;

    protected final boolean isArtifact;

    public AddEquipmentGUI(boolean isArtifact) {
        //Changes depending on artifact or weapon
        this.isArtifact = isArtifact;
        this.setLayout(null);

        Database names = new Database((isArtifact ? "artifacts.txt" : "swords.txt"), (isArtifact ? 30 : 40));
        String[] nameList = new String[names.getRecordCount()];
        for (int i = 0; i < nameList.length; i++) {
            nameList[i] = names.getRecord(i).trim();
        }

        JButton homeButton = new JButton("Home");
        JButton inventoryButton = new JButton("Inventory");
        addButton = new JButton("Add");
        JLabel nameLabel = new JLabel("Name:");
        JLabel typeLabel = new JLabel(isArtifact?"Piece:":"Type:");
        nameComboBox = new JComboBox<>(nameList);
        typeComboBox = new JComboBox<>((isArtifact? Artifact.getTypes():Weapon.getTypes()));

        homeButton.setBounds(1030,500,70,50);
        inventoryButton.setBounds(1100,500,70,50);
        addButton.setBounds(900,500,70,50);
        nameLabel.setBounds(0,0,70,50);
        typeLabel.setBounds(0,50,70,50);
        nameComboBox.setBounds(70,0,200,50);
        typeComboBox.setBounds(70,50,200,50);

        homeButton.addActionListener(this);
        inventoryButton.addActionListener(this);
        addButton.addActionListener(this);
        nameComboBox.addActionListener(this);
        typeComboBox.addActionListener(this);

        this.add(homeButton);
        this.add(inventoryButton);
        this.add(addButton);
        this.add(nameLabel);
        this.add(typeLabel);
        this.add(nameComboBox);
        this.add(typeComboBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home" -> MainFrame.navigate((isArtifact ? 3 : 4),0);
            case "Inventory" -> MainFrame.navigate((isArtifact ? 3 : 4),(isArtifact ? 1 : 2), null);
            case "Add" -> {
                if (isArtifact) {
                    //Enter files to add artifact
                    Database attributes = new Database("artifact_att.txt", 39);
                    Database values = new Database("artifact_val.txt", 20);
                    Artifact newArtifact = new Artifact((String) nameComboBox.getSelectedItem(), typeComboBox.getSelectedIndex());

                    attributes.appendRecord(newArtifact.toString(true));
                    values.appendRecord(newArtifact.toString(false));

                }else{
                    //Enter file to add weapon
                    Database weaponStats = new Database("weapon_stats.txt", 53);
                    Weapon newWeapon = new Weapon((String) nameComboBox.getSelectedItem(), typeComboBox.getSelectedIndex());

                    weaponStats.appendRecord(newWeapon.toString());
                }

            }
            case "comboBoxChanged" -> {
                if (((JComboBox<?>) e.getSource()).getItemCount() == 5 && !isArtifact){
                    //Changing possible weapon names from chosen weapon type
                    Database names = new Database(((String)typeComboBox.getSelectedItem()).toLowerCase() + "s.txt", 40);
                    nameComboBox.removeAllItems();
                    for (int i = 0; i < names.getRecordCount(); i++) {
                        nameComboBox.addItem(names.getRecord(i).trim());
                    }
                }

            }
        }
    }
}
