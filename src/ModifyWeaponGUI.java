import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class ModifyWeaponGUI extends ModifyEquipmentGUI implements ActionListener{

    private final JFormattedTextField mainValue;
    private final JFormattedTextField refinementRank;
    private final JFormattedTextField baseATK;

    public ModifyWeaponGUI(){
        super(false);

        JLabel refinementRankLabel = new JLabel("Refinement Rank:");
        JLabel baseATKLabel = new JLabel("Base ATK:");
        refinementRank = new JFormattedTextField(NumberFormat.getInstance());
        baseATK = new JFormattedTextField(NumberFormat.getInstance());
        mainValue = new JFormattedTextField(DecimalFormat.getInstance());
        refinementRank.setColumns(1);
        baseATK.setColumns(4);
        mainValue.setColumns(4);

        itemLabels = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            itemLabels[i] = new JLabel();
            itemLabels[i].setBounds(900,100 + (i*20),200,20);
            this.add(itemLabels[i]);
        }

        levelSlider.setMinimum(0);
        levelSlider.setMaximum(90);
        levelSlider.setMajorTickSpacing(10);

        for (String attribute:
                Weapon.getAttributes()) {
            mainAttributes.addItem(attribute);
        }

        baseATKLabel.setBounds(0,220,70,50);
        baseATK.setBounds(70,230,70,30);
        refinementRankLabel.setBounds(200,220,110,50);
        refinementRank.setBounds(310,230,70,30);
        mainValue.setBounds(210,180,70,30);

        this.add(baseATKLabel);
        this.add(baseATK);
        this.add(refinementRankLabel);
        this.add(refinementRank);
        this.add(mainValue);
    }

    public void importEquipment(int fileIndex){
        this.fileIndex = fileIndex;
        Database weaponStatsDatabase = new Database("weapon_stats.txt",53);
        target = new Weapon(weaponStatsDatabase.getRecord(fileIndex));

        populateFields();
    }

    protected void populateFields(){
        super.populateFields();
        itemLabels[1].setText("Refinement Rank: " + ((Weapon)target).getRefinementRank());
        refinementRank.setValue(((Weapon)target).getRefinementRank());
        mainValue.setValue(target.getPrimaryValue());
        baseATK.setValue(((Weapon)target).getBaseATK());
    }

    private void updateComboBoxes(ActionEvent e){
        if (e.getSource() == typeComboBox) {

            Database names = new Database(((String) Objects.requireNonNull(typeComboBox.getSelectedItem())).toLowerCase() + "s.txt", 40);
            nameComboBox.removeAllItems();
            for (int i = 0; i < names.getRecordCount(); i++) {
                nameComboBox.addItem(names.getRecord(i).trim());
            }
        }
    }

    private void saveEquipmentChanges(){
        Database weaponDatabase = new Database("weapon_stats.txt", 53);

        Weapon newWeapon = new Weapon((String)nameComboBox.getSelectedItem(),
                typeComboBox.getSelectedIndex(),
                Math.abs(Integer.parseInt(refinementRank.getText())),
                Math.abs((int) Long.parseLong(baseATK.getText())),
                (String) mainAttributes.getSelectedItem(),
                Math.abs(Double.parseDouble(mainValue.getText())),
                levelSlider.getValue());

        weaponDatabase.replaceRecord(fileIndex, newWeapon.toString());

        MainFrame.navigate(6, 2, null);
    }

    private void deleteEquipment(){
        Database weaponDatabase = new Database("weapon_stats.txt", 53);
        weaponDatabase.deleteRecord(fileIndex);
        MainFrame.navigate(6,2, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Home" -> MainFrame.navigate(6,0);
            case "Inventory" -> MainFrame.navigate(6,2, null);
            case "Save" ->{
                if(refinementRank.getText().contains(".") || Integer.parseInt(refinementRank.getText()) < 1 || Integer.parseInt(refinementRank.getText()) > 5){
                    refinementRank.setForeground(Color.RED);
                }else{
                    saveEquipmentChanges();
                }
            }
            case "DELETE" -> deleteEquipment();
            case "comboBoxChanged" -> updateComboBoxes(e);
        }
    }
}
