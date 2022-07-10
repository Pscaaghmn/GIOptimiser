import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class InventoryGUI extends JPanel implements ActionListener {

    private final boolean isArtifact;

    private ArrayList<Equipment> items;
    private final JComboBox<String> sortOptionsComboBox;
    private final ArrayList<JButton> itemButtons;
    private final JLabel[] itemLabels;
    private final JButton order;
    private final JPanel itemsBox;

    public InventoryGUI(Boolean isArtifact){
        this.setLayout(null);
        this.isArtifact = isArtifact;

        itemButtons = new ArrayList<>();

        itemLabels = new JLabel[(isArtifact ? 8 : 5)];
        order = new JButton("^");

        JButton homeButton = new JButton("Home");
        JButton artifactInventoryButton = new JButton("Artifacts");
        JButton weaponInventoryButton = new JButton("Weapons");
        JButton addNewItemButton = new JButton("Add New " + (isArtifact? "Artifact" : "Weapon"));
        JButton editItemButton = new JButton("Edit");
        JButton compareItemsButton = new JButton("Compare");

        String[] sortOptions = Stream.concat(Arrays.stream(new String[]{"Name", "Type", "Level"}),
                Arrays.stream(isArtifact ? Artifact.getAttributes()
                        : Weapon.getAttributes())).toArray(String[]::new);
        sortOptionsComboBox = new JComboBox<>(sortOptions);

        if (!isArtifact) {
            sortOptionsComboBox.addItem("Base ATK");
        }
        itemsBox = new JPanel();

        order.setBounds(200,500,50,50);
        homeButton.setBounds(0,0,70,50);
        artifactInventoryButton.setBounds(100,0,100,50);
        weaponInventoryButton.setBounds(200,0,100,50);
        addNewItemButton.setBounds(400,0,150,50);
        editItemButton.setBounds(1000,500,100,50);
        compareItemsButton.setBounds(1100,500,100,50);
        sortOptionsComboBox.setBounds(0,500,200,50);
        for (int i = 0; i < itemLabels.length; i++) {
            itemLabels[i] = new JLabel();
            itemLabels[i].setBounds(900,100 + (i*20),200,20);
            this.add(itemLabels[i]);
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
        sortOptionsComboBox.addActionListener(this);

        artifactInventoryButton.setEnabled(!isArtifact);
        weaponInventoryButton.setEnabled(isArtifact);

        this.add(order);
        this.add(homeButton);
        this.add(artifactInventoryButton);
        this.add(weaponInventoryButton);
        this.add(addNewItemButton);
        this.add(editItemButton);
        this.add(compareItemsButton);
        this.add(sortOptionsComboBox);
        this.add(itemsBox);
    }

    public void loadItems(){
        Database attributeDatabase;
        Database valueDatabase;
        if (isArtifact){
            attributeDatabase = new Database("artifact_att.txt",39);
            valueDatabase = new Database("artifact_val.txt", 20);
        }else{
            attributeDatabase = new Database("weapon_stats.txt",53);
            valueDatabase = null;
        }

        items = new ArrayList<>();
        itemsBox.removeAll();
        itemButtons.clear();

        for (int i = 0; i < attributeDatabase.getRecordCount(); i++) {
            if(isArtifact) {
                items.add(new Artifact(attributeDatabase.getRecord(i), valueDatabase.getRecord(i)));
            }else{
                items.add(new Weapon(attributeDatabase.getRecord(i)));
            }

            itemButtons.add(new JButton((i < 9 ? "0" : "") + (i+1) + ": " + items.get(i).getName()));
            itemsBox.add(itemButtons.get(i));
            itemButtons.get(i).addActionListener(this);
        }

        for (JLabel l:
                itemLabels) {
            l.setText("");
        }

        this.revalidate();
        this.repaint();
    }

    private void sort(String sortOption){
        ArrayList<String> stringsList = new ArrayList<>();
        ArrayList<Double> doublesList = new ArrayList<>();
        switch (sortOption){
            case "Name" -> {
                for (Equipment item : items) {
                    stringsList.add(item.getName());
                }
            }
            case "Type" -> {
                for (Equipment item : items) {
                    doublesList.add((double) item.getType());
                }
            }
            case "Level" -> {
                for (Equipment item : items) {
                    doublesList.add((double) item.getLevel());
                }
            }
            default -> {
                if (isArtifact) {
                    for (Equipment item : items) {
                        Artifact current = (Artifact) item;

                        ArrayList<Integer> attributes = new ArrayList<>();
                        for (int s:
                                current.getSecondaryAttributes()) {
                            attributes.add(s);
                        }
                        attributes.add(current.getPrimaryAttribute());

                        int valueIndex = attributes.indexOf(Equipment.strAttToInt(sortOption));
                        if (valueIndex == -1) {
                            doublesList.add(0.0);
                        } else if (valueIndex == 4) {
                            doublesList.add(current.getPrimaryValue());
                        }else{
                            doublesList.add(current.getSecondaryValues()[valueIndex]);
                        }
                    }
                }else if(sortOption.equals("Base ATK")){
                    for (Equipment item : items) {
                        doublesList.add((double) ((Weapon)item).getBaseATK());
                    }
                }else{
                    for (Equipment item : items) {
                        if (item.getPrimaryAttribute() != Equipment.strAttToInt(sortOption)) {
                            doublesList.add(0.0);
                        }else{
                            doublesList.add(item.getPrimaryValue());
                        }
                    }
                }
            }
        }

        //Selection Sort
        int minimumI;
        if (stringsList.size() > 0){
            for (int unsortedI = 0; unsortedI < stringsList.size() - 1; unsortedI++) {
                minimumI = unsortedI;
                //Finding the smallest number in unsorted partition
                if (order.getText().equals("^")) {
                    for (int searchI = unsortedI + 1; searchI < stringsList.size(); searchI++) {
                        if (stringsList.get(searchI).compareTo(stringsList.get(minimumI)) < 0){
                            minimumI = searchI;
                        }
                    }
                }else{
                    for (int searchI = unsortedI + 1; searchI < stringsList.size(); searchI++) {
                        if (stringsList.get(searchI).compareTo(stringsList.get(minimumI)) > 0){
                            minimumI = searchI;
                        }
                    }
                }

                //Swap
                Equipment tempEquipment = items.get(unsortedI);
                String tempValue = stringsList.get(unsortedI);

                items.set(unsortedI, items.get(minimumI));
                stringsList.set(unsortedI, stringsList.get(minimumI));

                items.set(minimumI, tempEquipment);
                stringsList.set(minimumI, tempValue);
            }
        }else{
            for (int unsortedI = 0; unsortedI < doublesList.size() - 1; unsortedI++) {
                minimumI = unsortedI;
                //Finding the smallest number in unsorted partition
                if (order.getText().equals("^")) {
                    for (int searchI = unsortedI + 1; searchI < doublesList.size(); searchI++) {
                        if (doublesList.get(searchI) < (doublesList.get(minimumI))){
                            minimumI = searchI;
                        }
                    }
                }else{
                    for (int searchI = unsortedI + 1; searchI < doublesList.size(); searchI++) {
                        if (doublesList.get(searchI) > (doublesList.get(minimumI))){
                            minimumI = searchI;
                        }
                    }
                }


                //Swap
                Equipment tempEquipment = items.get(unsortedI);
                Double tempValue = doublesList.get(unsortedI);

                items.set(unsortedI, items.get(minimumI));
                doublesList.set(unsortedI, doublesList.get(minimumI));

                items.set(minimumI, tempEquipment);
                doublesList.set(minimumI, tempValue);
            }
        }

        Database attributeDatabase;

        if (isArtifact){
            attributeDatabase = new Database("artifact_att.txt",39);
            Database valueDatabase = new Database("artifact_val.txt", 20);
            for (int i = 0; i < items.size(); i++) {
                attributeDatabase.replaceRecord(i, ((Artifact)items.get(i)).toString(true));
                valueDatabase.replaceRecord(i, ((Artifact)items.get(i)).toString(false));
            }
        }else{
            attributeDatabase = new Database("weapon_stats.txt",53);
            for (int i = 0; i < items.size(); i++) {
                attributeDatabase.replaceRecord(i, (items.get(i)).toString());
            }
        }

        loadItems();
    }

    private void populateDescriptionLabels(int buttonIndex){
        if (isArtifact){
            Artifact target = (Artifact) items.get(buttonIndex);
            itemLabels[0].setText("(" + (buttonIndex < 9 ? "0" : "") + (buttonIndex+1) + ") " + target.getName());
            itemLabels[1].setText("Piece: " + Artifact.getTypes()[target.getType()]);
            itemLabels[2].setText("Level: " + target.getLevel());
            itemLabels[3].setText(Equipment.intAttToStr(target.getPrimaryAttribute()) + ": " + target.getPrimaryValue());

            for (int i = 0; i < 4; i++) {
                itemLabels[i+4].setForeground(Color.MAGENTA);
                itemLabels[i+4].setText(Equipment.intAttToStr
                        (target.getSecondaryAttributes()[i]) + ": " +
                        target.getSecondaryValues()[i]);
            }
        }else{
            Weapon target = (Weapon) items.get(buttonIndex);
            itemLabels[0].setText("(" + (buttonIndex < 9 ? "0" : "") + (buttonIndex+1) + ") " + target.getName());
            itemLabels[1].setText("Refinement Rank: " + target.getRefinementRank());
            itemLabels[2].setText("Level: " + target.getLevel());
            itemLabels[3].setText("Base ATK: " + target.getBaseATK());
            itemLabels[4].setText(Equipment.intAttToStr(target.getPrimaryAttribute()) + ": " + target.getPrimaryValue());
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
            case "^" -> {
                order.setText("v");
                sort((String) Objects.requireNonNull(sortOptionsComboBox.getSelectedItem()));
            }
            case "v" -> {
                order.setText("^");
                sort((String) Objects.requireNonNull(sortOptionsComboBox.getSelectedItem()));
            }
            case "comboBoxChanged" -> sort((String) Objects.requireNonNull(sortOptionsComboBox.getSelectedItem()));
            case "Edit" -> MainFrame.navigate((isArtifact ? 1 : 2), -1 * Integer.parseInt(itemLabels[0].getText().substring(1, 3)));
            default -> populateDescriptionLabels(Integer.parseInt(e.getActionCommand().substring(0, 2)) - 1);
        }
    }

}
