import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyEquipmentGUI extends AddEquipmentGUI implements ActionListener, ChangeListener {
    
    private int fileIndex;
    private JLabel[] itemDetails;
    private final Database[] contents;
    private JSlider levelSlider;
    private JComboBox<String> mainAttributes;

    public ModifyEquipmentGUI(Boolean isArtifact){
        super(isArtifact);
        String[] attributeNames = new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge", "Healing Bonus", "Physical DMG Bonus", "Pyro DMG Bonus", "Electro DMG Bonus", "Cryo DMG Bonus", "Hydro DMG Bonus", "Anemo DMG Bonus", "Geo DMG Bonus", "Dendro DMG Bonus"};
        String[] artifactPrimaryAttributeNames = new String[]{"ATK", "ATK%", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge", "Healing Bonus", "Physical DMG Bonus", "Pyro DMG Bonus", "Electro DMG Bonus", "Cryo DMG Bonus", "Hydro DMG Bonus", "Anemo DMG Bonus", "Geo DMG Bonus", "Dendro DMG Bonus"};
        String[] weaponPrimaryAttributeNames = new String[]{"ATK", "ATK%", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge", "Healing Bonus", "Physical DMG Bonus", "Pyro DMG Bonus", "Electro DMG Bonus", "Cryo DMG Bonus", "Hydro DMG Bonus", "Anemo DMG Bonus", "Geo DMG Bonus", "Dendro DMG Bonus"};
        //TODO: Correct weapon and save appropriate attributes

        addButton.setText("Save");

        fileIndex = 0;

        if (isArtifact) {
            contents = new Database[]{new Database("artifact_att.txt",39), new Database("artifact_val.txt", 20)};
        }else{
            contents = new Database[]{new Database("weapon_stats.txt",53)};
        }

        itemDetails = new JLabel[(isArtifact ? 8 : 5)];

        JLabel levelLabel = new JLabel("Level: ");
        levelSlider = new JSlider(JSlider.HORIZONTAL,0, isArtifact ? 20 : 90, 0);
        levelSlider.setMajorTickSpacing(isArtifact ? 4 : 10);
        levelSlider.setPaintTicks(true);
        levelSlider.setPaintLabels(true);

        if (isArtifact) {
            String[] mainAttributeNames = new String[attributeNames.length-1];
            mainAttributeNames[0] = attributeNames[0];
            mainAttributeNames[1] = attributeNames[1];
            System.arraycopy(attributeNames, 3, mainAttributeNames, 2, mainAttributeNames.length - 2);

            mainAttributes = new JComboBox<>(mainAttributeNames);
        }else{
            String[] mainAttributeNames = new String[attributeNames.length-1];
            mainAttributeNames[0] = attributeNames[0];
            mainAttributeNames[1] = attributeNames[1];
            System.arraycopy(attributeNames, 3, mainAttributeNames, 2, mainAttributeNames.length - 2);

            mainAttributes = new JComboBox<>(mainAttributeNames);
        }

        levelLabel.setBounds(0,100,70,50);
        levelSlider.setBounds(70,100,200,50);

        levelSlider.addChangeListener(this);
        for (int i = 0; i < itemDetails.length; i++) {
            itemDetails[i] = new JLabel();
            itemDetails[i].setBounds(900,100 + (i*20),200,20);
            this.add(itemDetails[i]);
        }

        this.add(levelLabel);
        this.add(levelSlider);
    }

    public void importEquipment(int fileIndex){
        this.fileIndex = fileIndex;
        populateFields(fileIndex);
    }

    private void populateFields(int buttonIndex){
        String[] fields = Database.recordToArray(contents[0].getRecord(buttonIndex),(isArtifact ? new int[]{30, 1, 2, 1, 1, 1, 1, 2} : new int[]{40, 1, 1, 3, 2, 2, 4}));
        itemDetails[0].setText("(" + (buttonIndex < 10 ? "0" : "") + (buttonIndex+1) + ") " + fields[0].trim());

        nameComboBox.setSelectedItem(fields[0].trim());
        typeComboBox.setSelectedItem(new String[]{"Flower","Plume","Sands","Goblet","Circlet"}[Integer.parseInt(fields[1])]);

        if (isArtifact){
            String[] values = Database.recordToArray(contents[1].getRecord(buttonIndex), new int[]{4, 4, 4, 4, 4});

            levelSlider.setValue(Integer.parseInt(fields[7].trim()));

            itemDetails[1].setText("Piece: " + new String[]{"Flower","Plume","Sands","Goblet","Circlet"}[Integer.parseInt(fields[1])]);
            itemDetails[2].setText("Level: " + fields[7]);
            itemDetails[3].setText(Equipment.intAttToStr(Integer.parseInt(fields[2].trim())) + ": " + values[0]);
            for (int i = 0; i < 4; i++) {
                itemDetails[i+4].setText(Equipment.intAttToStr(Integer.parseInt(fields[i+3].trim())) + ": " + values[i+1]);
            }
        }else{
            levelSlider.setValue(Integer.parseInt(fields[5].trim()));

            itemDetails[1].setText("Refinement Rank: " + fields[2]);
            itemDetails[2].setText("Level: " + fields[5]);
            itemDetails[3].setText("Base ATK: " + fields[3]);
            itemDetails[4].setText(Equipment.intAttToStr(Integer.parseInt(fields[4].trim())) + ": " + fields[6]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home" -> MainFrame.navigate(isArtifact ? 5 : 6,0);
            case "Inventory" -> MainFrame.navigate(isArtifact ? 5 : 6, isArtifact ? 1 : 2);
            case "Save" -> {
                if (isArtifact) {

                    Database attributes = new Database("artifact_att.txt", 39);
                    Database values = new Database("artifact_val.txt", 20);
                    Artifact newArtifact = new Artifact((String) nameComboBox.getSelectedItem(), typeComboBox.getSelectedIndex());

                    attributes.replaceRecord(fileIndex, newArtifact.toString(true));
                    values.replaceRecord(fileIndex, newArtifact.toString(false));

                }else{

                    Database weaponStats = new Database("weapon_stats.txt", 53);
                    Weapon newWeapon = new Weapon((String) nameComboBox.getSelectedItem(), typeComboBox.getSelectedIndex());

                    weaponStats.replaceRecord(fileIndex, newWeapon.toString());
                }

                MainFrame.navigate(isArtifact ? 5 : 6, isArtifact ? 1 : 2);
            }
            case "comboBoxChanged" -> {
                if (((JComboBox<?>) e.getSource()).getItemCount() == 5 && !isArtifact){
                    Database names = new Database(((String)typeComboBox.getSelectedItem()).toLowerCase() + "s.txt", 40);
                    nameComboBox.removeAllItems();
                    for (int i = 0; i < names.getRecordCount(); i++) {
                        nameComboBox.addItem(names.getRecord(i).trim());
                    }
                }

            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        itemDetails[2].setText("Level: " + ((JSlider)e.getSource()).getValue());
    }
}
