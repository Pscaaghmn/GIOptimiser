import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;

public class ModifyEquipmentGUI extends AddEquipmentGUI implements ActionListener, ChangeListener {//TODO:Complete weapon half

    private int fileIndex;
    private final JLabel[] itemDetails;
    private final Database[] contents;
    private final JSlider levelSlider;
    private JComboBox<String> mainAttributes;
    private final JLabel mainValue;
    private JComboBox<String>[] secondaryAttributes;
    private JFormattedTextField[] secondaryValues;

    public ModifyEquipmentGUI(Boolean isArtifact){
        super(isArtifact);

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
        mainValue = new JLabel();
        if (isArtifact) {
            mainAttributes = new JComboBox<>(new String[]{"ATK", "ATK%", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge", "Healing Bonus", "Physical DMG Bonus", "Pyro DMG Bonus", "Electro DMG Bonus", "Cryo DMG Bonus", "Hydro DMG Bonus", "Anemo DMG Bonus", "Geo DMG Bonus", "Dendro DMG Bonus"});

            JLabel secondaryAttributesLabel = new JLabel("Substats:");
            secondaryAttributes = new JComboBox[4];

            secondaryValues = new JFormattedTextField[4];
            for (int i = 0; i < 4; i++) {
                secondaryAttributes[i] = new JComboBox<>(new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge"});
                secondaryAttributes[i].setBounds(0, 270 + (50*i), 200, 50);
                secondaryValues[i] = new JFormattedTextField(DecimalFormat.getInstance());
                secondaryValues[i].setBounds(210, 280 + (50 * i), 70, 30);
                secondaryValues[i].setColumns(4);

                this.add(secondaryAttributes[i]);
                this.add(secondaryValues[i]);
            }

            mainAttributes.setBounds(0,170,200,50);
            mainValue.setBounds(210,180, 70, 30);
            secondaryAttributesLabel.setBounds(0, 220, 70, 50);

            this.add(secondaryAttributesLabel);
        }else{
            String[] weaponPrimaryAttributeNames = new String[]{"ATK%", "DEF%", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge", "Physical DMG Bonus"};
            mainAttributes = new JComboBox<>(weaponPrimaryAttributeNames);
        }

        levelLabel.setBounds(0,100,70,50);
        levelSlider.setBounds(70,100,200,50);

        mainAttributes.addActionListener(this);
        levelSlider.addChangeListener(this);
        for (int i = 0; i < itemDetails.length; i++) {
            itemDetails[i] = new JLabel();
            itemDetails[i].setBounds(900,100 + (i*20),200,20);
            this.add(itemDetails[i]);
        }

        this.add(levelLabel);
        this.add(levelSlider);
        this.add(mainAttributes);
        this.add(mainValue);
    }

    public void importEquipment(int fileIndex){
        this.fileIndex = fileIndex;
        populateFields();
    }

    private void populateFields(){
        String[] fields = Database.recordToArray(contents[0].getRecord(fileIndex),(isArtifact ? new int[]{30, 1, 2, 1, 1, 1, 1, 2} : new int[]{40, 1, 1, 3, 2, 2, 4}));
        itemDetails[0].setText("(" + (fileIndex < 9 ? "0" : "") + (fileIndex+1) + ") " + fields[0].trim());

        nameComboBox.setSelectedItem(fields[0].trim());
        typeComboBox.setSelectedItem(new String[]{"Flower","Plume","Sands","Goblet","Circlet"}[Integer.parseInt(fields[1])]);

        if (isArtifact){
            String[] values = Database.recordToArray(contents[1].getRecord(fileIndex), new int[]{4, 4, 4, 4, 4});

            levelSlider.setValue(Integer.parseInt(fields[7].trim()));
            mainAttributes.setSelectedIndex(Integer.parseInt(fields[2].trim()));
            mainValue.setText(values[0].trim());

            itemDetails[1].setText("Piece: " + new String[]{"Flower","Plume","Sands","Goblet","Circlet"}[Integer.parseInt(fields[1])]);
            itemDetails[2].setText("Level: " + fields[7]);
            itemDetails[3].setText(Equipment.intAttToStr(Integer.parseInt(fields[2].trim())) + ": " + values[0]);
            for (int i = 0; i < 4; i++) {
                secondaryAttributes[i].setSelectedIndex(Integer.parseInt(fields[i+3].trim()));
                secondaryValues[i].setValue(Double.parseDouble(values[i+1].trim()));
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

    private void updateComboBoxes(ActionEvent e){
        if (e.getSource() == typeComboBox) {

            if (isArtifact) {
                String[] available;

                switch ((String) Objects.requireNonNull(typeComboBox.getSelectedItem())){
                    case "Flower" -> available = new String[]{"HP"};
                    case "Plume" -> available = new String[]{"ATK"};
                    case "Sands" -> available = new String[]{"ATK%", "DEF%", "HP%", "Elemental Mastery", "Energy Recharge"};
                    case "Goblet" -> available = new String[]{"ATK%", "DEF%", "HP%", "Elemental Mastery", "Physical DMG Bonus", "Pyro DMG Bonus", "Electro DMG Bonus", "Cryo DMG Bonus", "Hydro DMG Bonus", "Anemo DMG Bonus", "Geo DMG Bonus", "Dendro DMG Bonus"};
                    default -> available = new String[]{"ATK%", "DEF%", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Healing Bonus"};
                }
                mainAttributes.removeAllItems();
                for (String attribute:
                        available) {
                    mainAttributes.addItem(attribute);
                }

            } else {
                Database names = new Database(((String) Objects.requireNonNull(typeComboBox.getSelectedItem())).toLowerCase() + "s.txt", 40);
                nameComboBox.removeAllItems();
                for (int i = 0; i < names.getRecordCount(); i++) {
                    nameComboBox.addItem(names.getRecord(i).trim());
                }
            }

        }else if(e.getSource() == mainAttributes){

            for (int i = 0; i < 4; i++) {
                secondaryAttributes[i].removeAllItems();
                String[] available = Arrays.stream(new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge"})
                        .filter(a -> !a.equals(mainAttributes.getSelectedItem())).toArray(String[]::new);
                for (String attribute:
                        available) {
                    secondaryAttributes[i].addItem(attribute);
                }
            }

            updateMainValue();
        }
    }

    private void updateMainValue(){
        if (isArtifact && mainAttributes.getSelectedItem() != null){
            int attributeAsInt = Equipment.strAttToInt((String)mainAttributes.getSelectedItem());
            String valueAsString = String.format("%1$-4s",(Artifact.getPrimaryBase()[attributeAsInt] +
                    (levelSlider.getValue() * Artifact.getPrimaryIncrement()[attributeAsInt]))).substring(0,4);
            if (valueAsString.charAt(3) == '.'){
                valueAsString = valueAsString.substring(0,3);
            }
            mainValue.setText(valueAsString);
        }
    }

    private void saveEquipmentChanges(){
        if (isArtifact) {
            String[] collectedAttributes = new String[4];
            double[] collectedValues = new double[4];
            for (int i = 0; i < 4; i++) {
                collectedAttributes[i] = (String)secondaryAttributes[i].getSelectedItem();
                collectedValues[i] = Double.parseDouble(secondaryValues[i].getText());
            }

            Database attributes = new Database("artifact_att.txt", 39);
            Database values = new Database("artifact_val.txt", 20);
            Artifact newArtifact = new Artifact((String) nameComboBox.getSelectedItem(), typeComboBox.getSelectedIndex(), (String)mainAttributes.getSelectedItem(), collectedAttributes, collectedValues, levelSlider.getValue());

            attributes.replaceRecord(fileIndex, newArtifact.toString(true));
            values.replaceRecord(fileIndex, newArtifact.toString(false));

        }else{

            Database weaponStats = new Database("weapon_stats.txt", 53);
            Weapon newWeapon = new Weapon((String) nameComboBox.getSelectedItem(), typeComboBox.getSelectedIndex());

            weaponStats.replaceRecord(fileIndex, newWeapon.toString());
        }

        MainFrame.navigate(isArtifact ? 5 : 6, isArtifact ? 1 : 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home" -> MainFrame.navigate(isArtifact ? 5 : 6,0);
            case "Inventory" -> MainFrame.navigate(isArtifact ? 5 : 6, isArtifact ? 1 : 2);
            case "Save" -> saveEquipmentChanges();
            case "comboBoxChanged" -> updateComboBoxes(e);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        updateMainValue();
    }
}
