import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddEquipmentGUI extends JPanel implements ActionListener{

    private String[] nameList;

    public AddEquipmentGUI(boolean isArtifact) {
        this.setLayout(null);

        Database names = new Database((isArtifact ? "artifacts.txt" : "swords.txt"), (isArtifact ? 30 : 40));
        nameList = new String[names.getRecordCount()];
        for (int i = 0; i < nameList.length; i++) {
            nameList[i] = names.getRecord(i).trim();
        }

        JButton homeButton = new JButton("Home");
        JButton inventoryButton = new JButton("Inventory");
        JButton addButton = new JButton("Add");
        JLabel nameLabel = new JLabel("Name:");
        JLabel typeLabel = new JLabel(isArtifact?"Piece:":"Type:");
        JComboBox<String> nameComboBox = new JComboBox<>(nameList);
        JComboBox<String> typeComboBox =
                new JComboBox<>((isArtifact? "Flower,Plume,Sands,Goblet,Circlet"
                        :"All,Sword,Claymore,Polearm,Bow,Catalyst").split(","));

        homeButton.setBounds(1030,500,70,50);
        inventoryButton.setBounds(1100,500,70,50);
        addButton.setBounds(900,500,70,50);
        nameLabel.setBounds(0,0,70,50);
        typeLabel.setBounds(0,100,70,50);
        nameComboBox.setBounds(70,0,200,50);
        typeComboBox.setBounds(70,100,200,50);

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

    }
}
