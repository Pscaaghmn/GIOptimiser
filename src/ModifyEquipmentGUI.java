import javax.swing.*;
import java.awt.event.ActionListener;

public class ModifyEquipmentGUI extends AddEquipmentGUI implements ActionListener{

    protected Equipment target;
    protected JLabel[] itemLabels;

    protected int fileIndex;
    protected JSlider levelSlider;
    protected JComboBox<String> mainAttributes;

    public ModifyEquipmentGUI(boolean isArtifact){
        super(isArtifact);

        addButton.setText("Save");
        fileIndex = 0;

        JLabel levelLabel = new JLabel("Level: ");
        JButton deleteButton = new JButton("DELETE");

        levelSlider = new JSlider(0,1);
        levelSlider.setPaintTicks(true);
        levelSlider.setPaintLabels(true);
        mainAttributes = new JComboBox<>();

        levelLabel.setBounds(0,100,70,50);
        deleteButton.setBounds(800,500,100,50);
        levelSlider.setBounds(70,100,200,50);
        mainAttributes.setBounds(0, 170, 200, 50);

        deleteButton.addActionListener(this);
        mainAttributes.addActionListener(this);

        this.add(levelLabel);
        this.add(deleteButton);
        this.add(levelSlider);
        this.add(mainAttributes);
    }

    public void importEquipment(int fileIndex){
    }

    protected void populateFields(){
        itemLabels[0].setText("(" + (fileIndex < 9 ? "0" : "") + (fileIndex+1) + ") " + target.getName());
        typeComboBox.setSelectedItem(Artifact.getTypes()[target.getType()]);
        nameComboBox.setSelectedItem(target.getName());

        levelSlider.setValue(target.getLevel());
        mainAttributes.setSelectedItem(Equipment.intAttToStr(target.getPrimaryAttribute()));
        itemLabels[2].setText("Level: " + target.getLevel());
        itemLabels[3].setText(Equipment.intAttToStr(target.getPrimaryAttribute()) + ": " + target.getPrimaryValue());
    }
}
