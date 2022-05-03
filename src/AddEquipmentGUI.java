import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class AddEquipmentGUI extends GUI implements ActionListener, DocumentListener {

    private JLabel nameLabel;
    private JLabel levelLabel;
    private JComboBox<String> nameComboBox;
    private JSlider levelSlider;
    private JButton testButton;

    public AddEquipmentGUI(int width, int height, boolean isArtifact) {
        super(width, height);
        nameLabel = new JLabel(isArtifact ? "Artifact Set:" : "Weapon Name:");
        nameComboBox = new JComboBox<>(new String[]{"Gladiator's Finale", "Wanderer's Troupe", "Thundersoother", "Thundering Fury", "Maiden Beloved", "Viridescent Venerer", "Crimson Witch of Flames", "Lavawalker", "Noblesse Oblige", "Bloodstained Chivalry", "Archaic Petra", "Retracing Bolide", "Blizzard Strayer", "Heart of Depth", "Tenacity of the Millelith", "Pale Flame", "Shimenawa's Reminiscence", "Emblem of Severed Fate", "Husk of Opulent Dreams", "Ocean-Hued Clam", "Vermillion Hereafter", "Echoes of an Offering"});
        levelLabel = new JLabel("Level:");
        levelSlider = new JSlider(JSlider.HORIZONTAL,1, 20, 20);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("1"));
        labelTable.put(4, new JLabel("4"));
        labelTable.put(8, new JLabel("8"));
        labelTable.put(12, new JLabel("12"));
        labelTable.put(16, new JLabel("16"));
        labelTable.put(20, new JLabel("20"));
        levelSlider.setLabelTable(labelTable);
        levelSlider.setPaintTicks(true);
        levelSlider.setPaintLabels(true);
        testButton = new JButton("button");

        nameLabel.setBounds(10,0, 100, 20);
        nameComboBox.setBounds(110,0, 400, 20);
        levelLabel.setBounds(10, 20, 100, 20);
        levelSlider.setBounds(110, 20, 400, 50);
        testButton.setBounds(10, 60, 100, 50);

        add(nameLabel);
        add(nameComboBox);
        add(levelLabel);
        add(levelSlider);
        add(testButton);

        testButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
