import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ModifyWeaponGUI extends ModifyEquipmentGUI implements ActionListener{//TODO:Complete weapon half

    private final JLabel[] itemDetails;

    public ModifyWeaponGUI(){
        super(false);
        itemDetails = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            itemDetails[i] = new JLabel();
            itemDetails[i].setBounds(900,100 + (i*20),200,20);
            this.add(itemDetails[i]);
        }

        levelSlider.setMinimum(0);
        levelSlider.setMaximum(90);
        levelSlider.setMajorTickSpacing(10);


        //TODO: Finish for weapon and delete functionality
        String[] weaponPrimaryAttributeNames = new String[]{"ATK%", "DEF%", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge", "Physical DMG Bonus"};
        mainAttributes = new JComboBox<>(weaponPrimaryAttributeNames);
        //...
    }

    public void importEquipment(int fileIndex){
        this.fileIndex = fileIndex;
        populateFields();
    }

    private void populateFields(){
        Database weaponStatsDatabase = new Database("weapon_stats.txt",53);

        String[] fields = Database.recordToArray(weaponStatsDatabase.getRecord(fileIndex), new int[]{40, 1, 1, 3, 2, 2, 4});

        typeComboBox.setSelectedItem(new String[]{"Sword","Claymore","Polearm","Bow","Catalyst"}[Integer.parseInt(fields[1])]);

        levelSlider.setValue(Integer.parseInt(fields[5].trim()));

        itemDetails[1].setText("Refinement Rank: " + fields[2]);
        itemDetails[2].setText("Level: " + fields[5]);
        itemDetails[3].setText("Base ATK: " + fields[3]);
        itemDetails[4].setText(Equipment.intAttToStr(Integer.parseInt(fields[4].trim())) + ": " + fields[6]);

    }

    private void updateComboBoxes(ActionEvent e){
        if (e.getSource() == typeComboBox) {

            Database names = new Database(((String) Objects.requireNonNull(typeComboBox.getSelectedItem())).toLowerCase() + "s.txt", 40);
            nameComboBox.removeAllItems();
            for (int i = 0; i < names.getRecordCount(); i++) {
                nameComboBox.addItem(names.getRecord(i).trim());
            }
        }
    }

    private void saveEquipmentChanges(){
        //TODO: Weapon saving
    }

    private void deleteEquipment(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home" -> MainFrame.navigate(6,0);
            case "Inventory" -> MainFrame.navigate(6,2);
            case "Save" -> saveEquipmentChanges();
            case "DELETE" -> deleteEquipment();
            case "comboBoxChanged" -> updateComboBoxes(e);
        }
    }
}
