import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.*;

public class ModifyArtifactGUI extends ModifyEquipmentGUI implements ActionListener, ChangeListener {

    private final JLabel[] itemDetails;
    private final JComboBox<String>[] secondaryAttributes;
    private final JFormattedTextField[] secondaryValues;

    public ModifyArtifactGUI(){
        super(true);

        JLabel secondaryAttributesLabel = new JLabel("Substats:");
        secondaryAttributes = new JComboBox[4];

        secondaryValues = new JFormattedTextField[4];
        for (int i = 0; i < 4; i++) {
            secondaryAttributes[i] = new JComboBox<>(new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge"});
            secondaryAttributes[i].setBounds(0, 270 + (50*i), 200, 50);
            secondaryValues[i] = new JFormattedTextField(DecimalFormat.getInstance());
            secondaryValues[i].setBounds(210, 280 + (50 * i), 70, 30);
            secondaryValues[i].setColumns(4);

            secondaryAttributes[i].addActionListener(this);

            this.add(secondaryAttributes[i]);
            this.add(secondaryValues[i]);
        }

        itemDetails = new JLabel[8];
        for (int i = 0; i < 8; i++) {
            itemDetails[i] = new JLabel();
            itemDetails[i].setBounds(900,100 + (i*20),200,20);
            this.add(itemDetails[i]);
        }

        levelSlider.setMinimum(0);
        levelSlider.setMaximum(20);
        levelSlider.setMajorTickSpacing(4);
        levelSlider.addChangeListener(this);

        String[] possibleMainAttributes = new String[]{"ATK", "ATK%", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge", "Healing Bonus", "Physical DMG Bonus", "Pyro DMG Bonus", "Electro DMG Bonus", "Cryo DMG Bonus", "Hydro DMG Bonus", "Anemo DMG Bonus", "Geo DMG Bonus", "Dendro DMG Bonus"};
        for (String attribute:
                possibleMainAttributes) {
            mainAttributes.addItem(attribute);
        }

        secondaryAttributesLabel.setBounds(0, 220, 70, 50);

        this.add(secondaryAttributesLabel);

    }

    public void importEquipment(int fileIndex){
        this.fileIndex = fileIndex;
        populateFields();
    }

    private void populateFields(){
        Database attributesDatabase = new Database("artifact_att.txt", 39);
        Database valuesDatabase = new Database("artifact_val.txt", 20);

        String[] attributes = Database.recordToArray(attributesDatabase.getRecord(fileIndex),(new int[]{30, 1, 2, 1, 1, 1, 1, 2}));

        itemDetails[0].setText("(" + (fileIndex < 9 ? "0" : "") + (fileIndex+1) + ") " + attributes[0].trim());
        nameComboBox.setSelectedItem(attributes[0].trim());

        typeComboBox.setSelectedItem(new String[]{"Flower","Plume","Sands","Goblet","Circlet"}[Integer.parseInt(attributes[1])]);

        String[] attributeNames = new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge", "Healing Bonus", "Physical DMG Bonus", "Pyro DMG Bonus", "Electro DMG Bonus", "Cryo DMG Bonus", "Hydro DMG Bonus", "Anemo DMG Bonus", "Geo DMG Bonus", "Dendro DMG Bonus"};

        String[] values = Database.recordToArray(valuesDatabase.getRecord(fileIndex), new int[]{4, 4, 4, 4, 4});

        levelSlider.setValue(Integer.parseInt(attributes[7].trim()));
        mainAttributes.setSelectedItem(attributeNames[Integer.parseInt(attributes[2].trim())]);
        mainValue.setText(values[0].trim());

        itemDetails[1].setText("Piece: " + new String[]{"Flower","Plume","Sands","Goblet","Circlet"}[Integer.parseInt(attributes[1])]);
        itemDetails[2].setText("Level: " + attributes[7]);
        itemDetails[3].setText(Equipment.intAttToStr(Integer.parseInt(attributes[2].trim())) + ": " + values[0]);
        for (int i = 0; i < 4; i++) {
            secondaryAttributes[i].setSelectedItem(attributeNames[Integer.parseInt(attributes[i+3].trim())]);
            secondaryValues[i].setValue(Double.parseDouble(values[i+1].trim()));
            itemDetails[i+4].setText(Equipment.intAttToStr(Integer.parseInt(attributes[i+3].trim())) + ": " + values[i+1]);
        }

    }

    private void updateComboBoxes(ActionEvent e){
        if (e.getSource() == typeComboBox) {

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


        }else if(e.getSource() == mainAttributes && secondaryAttributes[0].getItemCount() > 0){

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
        }else if (((JComboBox<?>)e.getSource()).getForeground() == Color.RED){
            ((JComboBox<?>) e.getSource()).setForeground(Color.BLACK);
        }
    }

    private void updateMainValue(){
        if (mainAttributes.getSelectedItem() != null){
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
        ArrayList<Object> collectedAttributes = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
                collectedAttributes.add(secondaryAttributes[i].getSelectedItem());
        }

        Set<Object> setOfAttributes = new HashSet<>(collectedAttributes);
        if (collectedAttributes.size() > setOfAttributes.size()) {
            for (int i = 0; i < setOfAttributes.size(); i++) {
                secondaryAttributes[i].setSelectedItem(setOfAttributes.toArray()[i]);
            }
            for (int i = setOfAttributes.size(); i < 4; i++) {
                secondaryAttributes[i].setForeground(Color.RED);
            }

        } else {

            String[] collectedAttributesArray = new String[4];
            double[] collectedValues = new double[4];
            for (int i = 0; i < 4; i++) {
                collectedAttributesArray[i] = (String) secondaryAttributes[i].getSelectedItem();
                collectedValues[i] = Double.parseDouble(secondaryValues[i].getText());
            }

            Database attributes = new Database("artifact_att.txt", 39);
            Database values = new Database("artifact_val.txt", 20);
            Artifact newArtifact = new Artifact((String) nameComboBox.getSelectedItem(), typeComboBox.getSelectedIndex(), (String) mainAttributes.getSelectedItem(), collectedAttributesArray, collectedValues, levelSlider.getValue());

            attributes.replaceRecord(fileIndex, newArtifact.toString(true));
            values.replaceRecord(fileIndex, newArtifact.toString(false));

            MainFrame.navigate(5, 1);
            }

    }

    private void deleteEquipment(){
        Database attributes = new Database("artifact_att.txt", 39);
        Database values = new Database("artifact_val.txt", 20);
        attributes.deleteRecord(fileIndex);
        values.deleteRecord(fileIndex);
        MainFrame.navigate(5,1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home" -> MainFrame.navigate(5,0);
            case "Inventory" -> MainFrame.navigate(5,1);
            case "Save" -> saveEquipmentChanges();
            case "DELETE" -> deleteEquipment();
            case "comboBoxChanged" -> updateComboBoxes(e);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        updateMainValue();
    }
}
