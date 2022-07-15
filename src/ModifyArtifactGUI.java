import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.*;

public class ModifyArtifactGUI extends ModifyEquipmentGUI implements ActionListener, ChangeListener {

    private final JLabel mainValue;
    private final JComboBox<String>[] secondaryAttrComboBoxes;
    private final JFormattedTextField[] secondaryValueFields;

    public ModifyArtifactGUI(){
        super(true);

        JLabel secondaryAttributesLabel = new JLabel("Substats:");
        secondaryAttrComboBoxes = new JComboBox[4];
        mainValue = new JLabel();

        secondaryValueFields = new JFormattedTextField[4];
        for (int i = 0; i < 4; i++) {
            secondaryAttrComboBoxes[i] = new JComboBox<>(new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge"});
            secondaryAttrComboBoxes[i].setBounds(0, 270 + (50*i), 200, 50);
            secondaryValueFields[i] = new JFormattedTextField(DecimalFormat.getInstance());
            secondaryValueFields[i].setBounds(210, 280 + (50 * i), 70, 30);
            secondaryValueFields[i].setColumns(4);

            secondaryAttrComboBoxes[i].addActionListener(this);

            this.add(secondaryAttrComboBoxes[i]);
            this.add(secondaryValueFields[i]);
        }

        itemLabels = new JLabel[8];
        for (int i = 0; i < 8; i++) {
            itemLabels[i] = new JLabel();
            itemLabels[i].setBounds(900,100 + (i*20),200,20);
            this.add(itemLabels[i]);
        }

        levelSlider.setMinimum(0);
        levelSlider.setMaximum(20);
        levelSlider.setMajorTickSpacing(4);
        levelSlider.addChangeListener(this);

        for (String attribute:
                Artifact.getAttributes()) {
            mainAttributes.addItem(attribute);
        }

        secondaryAttributesLabel.setBounds(0, 220, 70, 50);
        mainValue.setBounds(210,180,70,30);

        this.add(secondaryAttributesLabel);
        this.add(mainValue);
    }

    public void importEquipment(int fileIndex){
        this.fileIndex = fileIndex;
        Database attributesDatabase = new Database("artifact_att.txt", 39);
        Database valuesDatabase = new Database("artifact_val.txt", 20);
        target = new Artifact(attributesDatabase.getRecord(fileIndex),valuesDatabase.getRecord(fileIndex));

        populateFields();
    }

    protected void populateFields(){
        super.populateFields();
        itemLabels[1].setText("Piece: " + Artifact.getTypes()[target.getType()]);

        mainValue.setText("" + target.getPrimaryValue());
        for (int i = 0; i < 4; i++) {
            secondaryAttrComboBoxes[i].setSelectedItem(((Artifact)target).getSecondaryAttributes()[i]);
            secondaryValueFields[i].setValue(((Artifact)target).getSecondaryValues()[i]);
            itemLabels[i+4].setForeground(Color.MAGENTA);
            itemLabels[i+4].setText(Equipment.intAttToStr
                    (((Artifact)target).getSecondaryAttributes()[i]) + ": " +
                    ((Artifact)target).getSecondaryValues()[i]);
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


        }else if(e.getSource() == mainAttributes && secondaryAttrComboBoxes[0].getItemCount() > 0){

            for (int i = 0; i < 4; i++) {
                secondaryAttrComboBoxes[i].removeAllItems();
                String[] available = Arrays.stream(new String[]{"ATK", "ATK%", "DEF", "DEF%", "HP", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Energy Recharge"})
                        .filter(a -> !a.equals(mainAttributes.getSelectedItem())).toArray(String[]::new);
                for (String attribute:
                        available) {
                    secondaryAttrComboBoxes[i].addItem(attribute);
                }
            }

            updateMainValue();
        }else if (((JComboBox<?>)e.getSource()).getForeground() == Color.RED){
            ((JComboBox<?>) e.getSource()).setForeground(Color.BLACK);
        }
    }

    private void updateMainValue(){
        if (mainAttributes.getSelectedItem() != null){
            DecimalFormat df = new DecimalFormat("###.##");
            int attributeAsInt = Equipment.strAttToInt((String)df.format(mainAttributes.getSelectedItem()));
            String valueAsString = String.valueOf(Artifact.getPrimaryBase()[attributeAsInt] +
                    (levelSlider.getValue() * Artifact.getPrimaryIncrement()[attributeAsInt]));
            mainValue.setText(valueAsString);
        }
    }

    private void saveEquipmentChanges(){
        ArrayList<Object> collectedAttributes = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
                collectedAttributes.add(secondaryAttrComboBoxes[i].getSelectedItem());
        }

        Set<Object> setOfAttributes = new HashSet<>(collectedAttributes);
        if (collectedAttributes.size() > setOfAttributes.size()) {
            for (int i = 0; i < setOfAttributes.size(); i++) {
                secondaryAttrComboBoxes[i].setSelectedItem(setOfAttributes.toArray()[i]);
            }
            for (int i = setOfAttributes.size(); i < 4; i++) {
                secondaryAttrComboBoxes[i].setForeground(Color.RED);
            }
        } else {

            String[] collectedAttributesArray = new String[4];
            double[] collectedValues = new double[4];
            for (int i = 0; i < 4; i++) {
                collectedAttributesArray[i] = (String) secondaryAttrComboBoxes[i].getSelectedItem();
                collectedValues[i] = Double.parseDouble(secondaryValueFields[i].getText());
            }

            Database attributes = new Database("artifact_att.txt", 39);
            Database values = new Database("artifact_val.txt", 20);
            Artifact newArtifact = new Artifact((String) nameComboBox.getSelectedItem(), typeComboBox.getSelectedIndex(), (String) mainAttributes.getSelectedItem(), collectedAttributesArray, collectedValues, levelSlider.getValue());

            attributes.replaceRecord(fileIndex, newArtifact.toString(true));
            values.replaceRecord(fileIndex, newArtifact.toString(false));

            MainFrame.navigate(5, 1, null);
        }

    }

    private void deleteEquipment(){
        Database attributes = new Database("artifact_att.txt", 39);
        Database values = new Database("artifact_val.txt", 20);
        attributes.deleteRecord(fileIndex);
        values.deleteRecord(fileIndex);
        MainFrame.navigate(5,1, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home" -> MainFrame.navigate(5,0);
            case "Inventory" -> MainFrame.navigate(5,1, null);
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
