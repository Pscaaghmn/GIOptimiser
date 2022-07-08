import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ModifyEquipmentGUI extends AddEquipmentGUI implements ActionListener{//TODO:Complete weapon half

    protected int fileIndex;
    protected JSlider levelSlider;
    protected JComboBox<String> mainAttributes;
    protected JLabel mainValue;

    public ModifyEquipmentGUI(boolean isArtifact){ //TODO: Finish for weapon and delete functionality
        super(isArtifact);

        addButton.setText("Save");
        fileIndex = 0;

        JLabel levelLabel = new JLabel("Level: ");
        JButton deleteButton = new JButton("DELETE");

        levelSlider = new JSlider(0,1);
        levelSlider.setPaintTicks(true);
        levelSlider.setPaintLabels(true);
        mainValue = new JLabel();
        mainAttributes = new JComboBox<>();

        levelLabel.setBounds(0,100,70,50);
        deleteButton.setBounds(800,500,100,50);
        levelSlider.setBounds(70,100,200,50);
        mainAttributes.setBounds(0, 170, 200, 50);
        mainValue.setBounds(210,180,70,30);

        deleteButton.addActionListener(this);
        mainAttributes.addActionListener(this);

        this.add(levelLabel);
        this.add(deleteButton);
        this.add(levelSlider);
        this.add(mainAttributes);
        this.add(mainValue);
    }

    public void importEquipment(int fileIndex){
        this.fileIndex = fileIndex;
    }

}
