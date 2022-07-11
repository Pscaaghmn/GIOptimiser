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
    private final JPanel labelBox;
    private final ArrayList<JLabel> itemLabels;
    private final JButton order;
    private final JPanel itemsBox;

    private boolean compareMode;

    public InventoryGUI(Boolean isArtifact){
        this.setLayout(null);
        this.isArtifact = isArtifact;
        compareMode = false;

        labelBox = new JPanel();
        itemButtons = new ArrayList<>();

        itemLabels = new ArrayList<>();
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

        labelBox.setBounds(900,100,200,200);
        labelBox.setLayout(new BoxLayout(labelBox, BoxLayout.Y_AXIS));

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
        this.add(labelBox);
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
        labelBox.removeAll();
        itemLabels.clear();
        if (isArtifact){
            Artifact target = (Artifact) items.get(buttonIndex);
            itemLabels.add(new JLabel("(" + (buttonIndex < 9 ? "0" : "") + (buttonIndex+1) + ") " + target.getName()));
            itemLabels.add(new JLabel("Piece: " + Artifact.getTypes()[target.getType()]));
            itemLabels.add(new JLabel("Level: " + target.getLevel()));
            itemLabels.add(new JLabel(Equipment.intAttToStr(target.getPrimaryAttribute()) + ": " + target.getPrimaryValue()));

            for (int i = 0; i < 4; i++) {
                itemLabels.add(new JLabel(Equipment.intAttToStr
                        (target.getSecondaryAttributes()[i]) + ": " +
                        target.getSecondaryValues()[i]));
                itemLabels.get(i+4).setForeground(Color.MAGENTA);
            }
        }else{
            Weapon target = (Weapon) items.get(buttonIndex);
            itemLabels.add(new JLabel("(" + (buttonIndex < 9 ? "0" : "") + (buttonIndex+1) + ") " + target.getName()));
            itemLabels.add(new JLabel("Refinement Rank: " + target.getRefinementRank()));
            itemLabels.add(new JLabel("Level: " + target.getLevel()));
            itemLabels.add(new JLabel("Base ATK: " + target.getBaseATK()));
            itemLabels.add(new JLabel(Equipment.intAttToStr(target.getPrimaryAttribute()) + ": " + target.getPrimaryValue()));
        }

        for (JLabel l:
             itemLabels) {
            labelBox.add(l);
        }

        labelBox.revalidate();
        labelBox.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home" -> MainFrame.navigate((isArtifact ? 1 : 2), 0);
            case "Artifacts" -> MainFrame.navigate(2, 1, null);
            case "Weapons" -> MainFrame.navigate(1, 2, null);
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
            case "Edit" -> {
                if (itemLabels.size() != 0) {
                    MainFrame.navigate((isArtifact ? 1 : 2), (isArtifact ? 5 : 6),
                            new int[]{Integer.parseInt(itemLabels.get(0).getText().substring(1, 3)) - 1});
                }
            }
            case "Compare" -> {
                if (itemLabels.size() != 0) {
                    if (compareMode) {
                        for (JButton b:
                             itemButtons) {
                            b.setEnabled(true);
                        }

                        compareMode = false;
                    } else {
                        int i = 0;
                        while (i < items.size()){
                            if (i == Integer.parseInt(itemLabels.get(0).getText().substring(1, 3)) - 1){
                                itemButtons.get(i).setEnabled(false);
                            }
                            i++;
                        }
                        compareMode = true;
                    }
                }
            }
            default -> {
                if (compareMode){
                    compareMode = false;
                    MainFrame.navigate((isArtifact ? 1 : 2), (isArtifact ? 7 : 8),
                            new int[]{Integer.parseInt(itemLabels.get(0).getText().substring(1, 3)) - 1, Integer.parseInt(e.getActionCommand().substring(0, 2)) - 1});
                }else{
                    populateDescriptionLabels(Integer.parseInt(e.getActionCommand().substring(0, 2)) - 1);
                }
            }
        }
    }
}