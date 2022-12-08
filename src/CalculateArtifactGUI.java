import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

public class CalculateArtifactGUI extends JPanel implements ActionListener {

    private final JFormattedTextField timeInDays;
    private final JLabel result;
    private final JComboBox<String> pieceOptions;
    private final JComboBox<String> attributesComboBox;

    public CalculateArtifactGUI(){
        this.setLayout(null);

        JLabel mainAttributeLabel = new JLabel("Look for: ");
        JLabel timeLabel = new JLabel("Time (days): ");
        JButton homeButton = new JButton("Home");
        JButton calculateButton = new JButton("Calculate");
        attributesComboBox = new JComboBox<>();
        pieceOptions = new JComboBox<>(Artifact.getTypes());
        timeInDays = new JFormattedTextField(NumberFormat.getInstance());
        timeInDays.setColumns(3);
        result = new JLabel();

        homeButton.setBounds(0,0,100,50);
        mainAttributeLabel.setBounds(0,50,200,50);
        attributesComboBox.setBounds(0,100,200,50);
        pieceOptions.setBounds(200,100,200,50);
        timeLabel.setBounds(0,150,90,50);
        timeInDays.setBounds(90,150,70,50);
        calculateButton.setBounds(0,200,100,50);
        result.setBounds(0,250,500,50);

        homeButton.addActionListener(this);
        calculateButton.addActionListener(this);
        pieceOptions.addActionListener(this);

        this.add(mainAttributeLabel);
        this.add(timeLabel);
        this.add(homeButton);
        this.add(calculateButton);
        this.add(attributesComboBox);
        this.add(pieceOptions);
        this.add(timeInDays);
        this.add(result);
    }

    private void calculateResult(){
        //Output expected number of artifacts and expected time to get one of artifact.
        double expectedValue = 0;
        int time = Math.abs(Integer.parseInt(timeInDays.getText().replace(",","")));
        switch (pieceOptions.getSelectedIndex()){
            case 0,1 -> expectedValue = 0.9 * time;
            case 2 -> expectedValue = 0.9 * new double[]{0.2666,0.2666,0.2668,0.1,0.1}[attributesComboBox.getSelectedIndex()] * time;
            case 3 -> expectedValue = 0.9 * new double[]{0.2125,0.2,0.2125,0.025,0.5,0.5,0.5,0.5,0.5,0.5,0.5}[attributesComboBox.getSelectedIndex()] * time;
            case 4 -> expectedValue = 0.9 * new double[]{0.22,0.22,0.22,0.1,0.1,0.04,0.1}[attributesComboBox.getSelectedIndex()] * time;
        }
        DecimalFormat df = new DecimalFormat("###.##");
        result.setText("You will get " + df.format(expectedValue) + " artifacts. It would take " + df.format(time/expectedValue) + " days to get one artifact.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home" -> MainFrame.navigate(9, 0);
            case "Calculate" -> {
                if (attributesComboBox.getItemCount() > 0) {
                    //only if attribute selected
                    calculateResult();
                }
            }
            case "comboBoxChanged" -> {
                //Changing attribute options given artifact type
                if (e.getSource() == pieceOptions){
                    String[] available;

                    switch ((String) Objects.requireNonNull(pieceOptions.getSelectedItem())){
                        case "Flower" -> available = new String[]{"HP"};
                        case "Plume" -> available = new String[]{"ATK"};
                        case "Sands" -> available = new String[]{"ATK%", "DEF%", "HP%", "Elemental Mastery", "Energy Recharge"};
                        case "Goblet" -> available = new String[]{"ATK%", "DEF%", "HP%", "Elemental Mastery", "Physical DMG Bonus", "Pyro DMG Bonus", "Electro DMG Bonus", "Cryo DMG Bonus", "Hydro DMG Bonus", "Anemo DMG Bonus", "Geo DMG Bonus", "Dendro DMG Bonus"};
                        default -> available = new String[]{"ATK%", "DEF%", "HP%", "Crit Rate", "Crit Damage", "Elemental Mastery", "Healing Bonus"};
                    }
                    attributesComboBox.removeAllItems();
                    for (String attribute:
                            available) {
                        attributesComboBox.addItem(attribute);
                    }
                }
            }
        }

    }
}
