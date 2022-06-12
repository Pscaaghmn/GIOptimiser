import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddEquipmentGUI extends JPanel implements ActionListener{

    private ArrayList<String> nameList;

    public AddEquipmentGUI(boolean isArtifact) {

        JButton homeButton = new JButton("Home");
        JButton inventoryButton = new JButton("Inventory");
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        JLabel nameLabel = new JLabel("Name:");
        JLabel typeLabel = new JLabel(isArtifact?"Piece:":"Type:");
        JComboBox<String> nameComboBox;
        JComboBox<String> typeComboBox = new JComboBox<>((isArtifact? "Flower,Plume,Sands,Goblet,Circlet":"All,Sword,Claymore,Polearm,Bow,Catalyst").split(","));


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
