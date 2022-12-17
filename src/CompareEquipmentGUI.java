import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class CompareEquipmentGUI extends JPanel implements ActionListener {

    private final boolean isArtifact;
    private int[] fileIndices;

    private final JPanel firstBox;
    private Equipment firstItem;
    private final Stack<JLabel> firstLabels;

    private final JPanel secondBox;
    private Equipment secondItem;
    private final Stack<JLabel> secondLabels;

    private final JPanel firstAttributePanel;
    private final Stack<JLabel> firstAttributeLabels;

    private final JPanel secondAttributePanel;
    private final Stack<JLabel> secondAttributeLabels;

    private final JLabel recommendation;

    public CompareEquipmentGUI(boolean isArtifact) {
        this.setLayout(null);
        this.isArtifact = isArtifact;
        firstBox = new JPanel();
        firstLabels = new Stack<>();
        secondBox = new JPanel();
        secondLabels = new Stack<>();
        firstAttributePanel = new JPanel();
        firstAttributeLabels = new Stack<>();
        secondAttributePanel = new JPanel();
        secondAttributeLabels = new Stack<>();
        recommendation = new JLabel();
        JButton homeButton = new JButton("Home");
        JButton inventoryButton = new JButton("Inventory");

        firstBox.setBounds(0,0, 300, 700);
        secondBox.setBounds(900,0, 300, 700);
        firstAttributePanel.setBounds(300,0,300,400);
        secondAttributePanel.setBounds(600,0,300,400);
        firstBox.setLayout(new BoxLayout(firstBox, BoxLayout.Y_AXIS));
        secondBox.setLayout(new BoxLayout(secondBox, BoxLayout.Y_AXIS));
        firstAttributePanel.setLayout(new BoxLayout(firstAttributePanel, BoxLayout.Y_AXIS));
        secondAttributePanel.setLayout(new BoxLayout(secondAttributePanel, BoxLayout.Y_AXIS));
        recommendation.setBounds(525, 450, 100, 50);
        homeButton.setBounds(500,500,100,70);
        inventoryButton.setBounds(600,500,100,70);


        homeButton.addActionListener(this);
        inventoryButton.addActionListener(this);

        this.add(homeButton);
        this.add(inventoryButton);
        this.add(firstBox);
        this.add(secondBox);
        this.add(firstAttributePanel);
        this.add(secondAttributePanel);
        this.add(recommendation);
    }

    public void loadItems(int[] fileIndices){
        //get equipment to compare
        this.fileIndices = fileIndices;
        if (isArtifact){
            Database attributeDatabase = new Database("artifact_att.txt",39);
            Database valueDatabase = new Database("artifact_val.txt", 20);

            firstItem = new Artifact(attributeDatabase.getRecord(fileIndices[0]),valueDatabase.getRecord(fileIndices[0]));
            secondItem = new Artifact(attributeDatabase.getRecord(fileIndices[1]),valueDatabase.getRecord(fileIndices[1]));
        }else{
            Database attributeDatabase = new Database("weapon_stats.txt",53);
            firstItem = new Weapon(attributeDatabase.getRecord(fileIndices[0]));
            secondItem = new Weapon(attributeDatabase.getRecord(fileIndices[1]));
        }

        firstBox.removeAll();
        firstLabels.clear();

        secondBox.removeAll();
        secondLabels.clear();

        populateDescriptionLabels();
    }

    private void populateDescriptionLabels(){
        //all labels filled in, including comparison labels
        firstBox.removeAll();
        secondBox.removeAll();
        firstAttributePanel.removeAll();
        secondAttributePanel.removeAll();
        firstLabels.clear();
        secondLabels.clear();
        firstAttributeLabels.clear();
        secondAttributeLabels.clear();
        HashSet<Integer> unionOfAttributes = new HashSet<>();

        if (isArtifact){
            firstLabels.push(new JLabel("(" + (fileIndices[0] < 9 ? "0" : "") + (fileIndices[0]+1) + ") " + firstItem.getName()));
            firstLabels.peek().setForeground(Color.blue);
            firstLabels.push(new JLabel("Piece: " + Artifact.getTypes()[firstItem.getType()]));
            firstLabels.push(new JLabel("Level: " + firstItem.getLevel()));
            firstLabels.push(new JLabel(Equipment.intAttToStr(firstItem.getPrimaryAttribute()) + ": " + firstItem.getPrimaryValue()));
            unionOfAttributes.add(firstItem.getPrimaryAttribute());

            for (int i = 0; i < 4; i++) {
                firstLabels.push(new JLabel(Equipment.intAttToStr
                        (((Artifact)firstItem).getSecondaryAttributes()[i]) + ": " +
                        ((Artifact)firstItem).getSecondaryValues()[i]));
                firstLabels.peek().setForeground(Color.MAGENTA);
                unionOfAttributes.add(((Artifact)firstItem).getSecondaryAttributes()[i]);
            }

            secondLabels.push(new JLabel("(" + (fileIndices[1] < 9 ? "0" : "") + (fileIndices[1]+1) + ") " + secondItem.getName()));
            secondLabels.peek().setForeground(Color.orange);
            secondLabels.push(new JLabel("Piece: " + Artifact.getTypes()[secondItem.getType()]));
            secondLabels.push(new JLabel("Level: " + secondItem.getLevel()));
            secondLabels.push(new JLabel(Equipment.intAttToStr(secondItem.getPrimaryAttribute()) + ": " + secondItem.getPrimaryValue()));
            unionOfAttributes.add(secondItem.getPrimaryAttribute());

            for (int i = 0; i < 4; i++) {
                secondLabels.push(new JLabel(Equipment.intAttToStr
                        (((Artifact)secondItem).getSecondaryAttributes()[i]) + ": " +
                        ((Artifact)secondItem).getSecondaryValues()[i]));
                secondLabels.peek().setForeground(Color.MAGENTA);
                unionOfAttributes.add(((Artifact)secondItem).getSecondaryAttributes()[i]);
            }

            //Favours CR CD ATK%
            int usefulFirstCount = 0;
            int usefulSecondCount = 0;
            ArrayList<Integer> usefulAttributes = new ArrayList<>();
            usefulAttributes.add(Equipment.strAttToInt("CR"));
            usefulAttributes.add(Equipment.strAttToInt("CD"));
            usefulAttributes.add(Equipment.strAttToInt("ATK%"));

            for (int i = 0; i < unionOfAttributes.size(); i++) {
                Integer attribute = (Integer) unionOfAttributes.toArray()[i];
                //If both sides have the same value for an attribute
                if (higherValueArtifact(attribute).equals("Equal")){
                    firstAttributeLabels.push(new JLabel(Equipment.intAttToStr(attribute)));
                    secondAttributeLabels.push(new JLabel(Equipment.intAttToStr(attribute)));

                    //Index of top item in the stack as an integer is different for both label collections
                    secondAttributeLabels.peek().setAlignmentX(Component.CENTER_ALIGNMENT);
                    firstAttributeLabels.peek().setAlignmentX(Component.CENTER_ALIGNMENT);

                    firstAttributeLabels.peek().setForeground(Color.darkGray);
                    secondAttributeLabels.peek().setForeground(Color.darkGray);

                    firstAttributePanel.add(firstAttributeLabels.peek());
                    secondAttributePanel.add(secondAttributeLabels.peek());
                }else if (higherValueArtifact(attribute).equals(firstItem.getName())){
                    firstAttributeLabels.add(new JLabel(Equipment.intAttToStr(attribute)));
                    firstAttributeLabels.peek().setAlignmentX(Component.CENTER_ALIGNMENT);
                    firstAttributeLabels.peek().setForeground(Color.blue);
                    firstAttributePanel.add(firstAttributeLabels.peek());
                    if (usefulAttributes.contains(attribute)){
                        usefulFirstCount++;
                    }
                }else{
                    secondAttributeLabels.push(new JLabel(Equipment.intAttToStr(attribute)));
                    secondAttributeLabels.peek().setAlignmentX(Component.CENTER_ALIGNMENT);
                    secondAttributeLabels.peek().setForeground(Color.orange);
                    secondAttributePanel.add(secondAttributeLabels.peek());
                    if (usefulAttributes.contains(attribute)){
                        usefulSecondCount++;
                    }
                }
            }

            if (usefulFirstCount == usefulSecondCount){
                recommendation.setText("Same");
            }else if (usefulFirstCount > usefulSecondCount){
                recommendation.setText(firstItem.getName());
            }else{
                recommendation.setText(secondItem.getName());
            }

        }else{
            //Recommend higher base atk
            firstLabels.push(new JLabel("(" + (fileIndices[0] < 9 ? "0" : "") + (fileIndices[0]+1) + ") " + firstItem.getName()));
            firstLabels.get(0).setForeground(Color.blue);
            firstLabels.push(new JLabel("Refinement Rank: " + ((Weapon)firstItem).getRefinementRank()));
            firstLabels.push(new JLabel("Level: " + firstItem.getLevel()));
            firstLabels.push(new JLabel("Base ATK: " + ((Weapon)firstItem).getBaseATK()));
            firstLabels.push(new JLabel(Equipment.intAttToStr(firstItem.getPrimaryAttribute()) + ": " + firstItem.getPrimaryValue()));

            secondLabels.push(new JLabel("(" + (fileIndices[1] < 9 ? "0" : "") + (fileIndices[1]+1) + ") " + secondItem.getName()));
            secondLabels.get(0).setForeground(Color.orange);
            secondLabels.push(new JLabel("Refinement Rank: " + ((Weapon)secondItem).getRefinementRank()));
            secondLabels.push(new JLabel("Level: " + secondItem.getLevel()));
            secondLabels.push(new JLabel("Base ATK: " + ((Weapon)secondItem).getBaseATK()));
            secondLabels.push(new JLabel(Equipment.intAttToStr(secondItem.getPrimaryAttribute()) + ": " + secondItem.getPrimaryValue()));
            if (((Weapon)firstItem).getBaseATK() > ((Weapon)secondItem).getBaseATK()) {
                recommendation.setText(firstItem.getName());
            }else{
                recommendation.setText(secondItem.getName());
            }

        }

        for (int i = 0; i < firstLabels.size(); i++) {
            firstBox.add(firstLabels.get(i));
            secondBox.add(secondLabels.get(i));
            firstLabels.get(i).setAlignmentX(Component.CENTER_ALIGNMENT);
            secondLabels.get(i).setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        firstBox.revalidate();
        secondBox.revalidate();
        firstBox.repaint();
        secondBox.repaint();
    }

    private String higherValueArtifact(int comparisonAttribute){
        //State which of the two artifacts has the higher value of the given attribute.
        ArrayList<Integer> firstAttributes = new ArrayList<>();
        ArrayList<Integer> secondAttributes = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            firstAttributes.add(((Artifact)firstItem).getSecondaryAttributes()[i]);
            secondAttributes.add(((Artifact)secondItem).getSecondaryAttributes()[i]);
        }
        firstAttributes.add(firstItem.getPrimaryAttribute());
        secondAttributes.add(secondItem.getPrimaryAttribute());

        int firstValueIndex = firstAttributes.indexOf(comparisonAttribute);
        int secondValueIndex = secondAttributes.indexOf(comparisonAttribute);

        double firstValue, secondValue;
        if (firstValueIndex == -1) {
            firstValue = 0.0;
        }else if (firstValueIndex == 4) {
            firstValue = firstItem.getPrimaryValue();
        }else{
            firstValue = ((Artifact)firstItem).getSecondaryValues()[firstValueIndex];
        }
        if (secondValueIndex == -1) {
            secondValue = 0.0;
        }else if (secondValueIndex == 4) {
            secondValue = secondItem.getPrimaryValue();
        }else{
            secondValue = ((Artifact)secondItem).getSecondaryValues()[secondValueIndex];
        }

        if (firstValue > secondValue) {
            return firstItem.getName();
        }else if (firstValue < secondValue) {
            return secondItem.getName();
        }else{
            return "Equal";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home" -> MainFrame.navigate(isArtifact ? 7 : 8, 0);
            case "Inventory" -> MainFrame.navigate(isArtifact ? 7 : 8, isArtifact ? 1 : 2, null);
        }
    }
}
