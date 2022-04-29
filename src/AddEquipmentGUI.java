import javax.swing.*;
import java.util.Hashtable;

public class AddEquipmentGUI extends GUI{

    private JLabel nameLabel;
    private JLabel levelLabel;
    private JComboBox<String> nameComboBox;
    private JSlider levelSlider;

    public AddEquipmentGUI(int width, int height, boolean isArtifact) {
        super(width, height);
        nameLabel = new JLabel(isArtifact ? "Artifact Set:" : "Weapon Name:");
        nameComboBox = new JComboBox<>(new String[]{"Gladiator's Finale", "Wanderer's Troupe", "Thundersoother", "Thundering Fury", "Maiden Beloved", "Viridescent Venerer", "Crimson Witch of Flames", "Lavawalker", "Noblesse Oblige", "Bloodstained Chivalry", "Archaic Petra", "Retracing Bolide", "Blizzard Strayer", "Heart of Depth", "Tenacity of the Millelith", "Pale Flame", "Shimenawa's Reminiscence", "Emblem of Severed Fate", "Husk of Opulent Dreams", "Ocean-Hued Clam", "Vermillion Hereafter", "Echoes of an Offering"});
        levelLabel = new JLabel("Level:");
        slider = new JSlider(JSlider.HORIZONTAL,1, 20, 20);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("1"));
        labelTable.put(4, new JLabel("4"));
        labelTable.put(8, new JLabel("8"));
        labelTable.put(12, new JLabel("12"));
        labelTable.put(16, new JLabel("16"));
        labelTable.put(20, new JLabel("20"));
        slider.setLabelTable(labelTable);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        label1.setBounds(10,0, 100, 20);
        comboBox.setBounds(110,0, 400, 20);
        label2.setBounds(10, 20, 100, 20);
        slider.setBounds(110, 20, 400, 50);

        add(label1);
        add(comboBox);
        add(label2);
        add(slider);
    }

}
