import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InventoryGUI extends JPanel implements ActionListener {

    private ArrayList<JButton> items;
    private JLabel[] itemDetails;  //name, level, rarity, description, primary attribute, primary value
    JButton order;

    public InventoryGUI(){
        items = new ArrayList<>();
        itemDetails = new JLabel[6];
        order = new JButton("^");

        JButton homeButton = new JButton("Home");
        JButton artifactInventoryButton = new JButton("Artifacts");
        JButton weaponInventoryButton = new JButton("Weapons");
        JButton addNewItemButton = new JButton("Add New");
        JButton editItemButton = new JButton("Edit");
        JButton compareItemsButton = new JButton("Compare");
        JComboBox<String> sortOptions = new JComboBox<>(new String[]{"Name", "Level"});

        order.setBounds(300,700,100,50);
        homeButton.setBounds(0,0,50,50);
        artifactInventoryButton.setBounds(50,0,50,50);
        weaponInventoryButton.setBounds(100,0,50,50);
        addNewItemButton.setBounds(200,0,100,50);
        editItemButton.setBounds(500,700,100,50);
        compareItemsButton.setBounds(600,700,100,50);
        sortOptions.setBounds(200,700,100,50);

        order.addActionListener(this);
        homeButton.addActionListener(this);
        artifactInventoryButton.addActionListener(this);
        weaponInventoryButton.addActionListener(this);
        addNewItemButton.addActionListener(this);
        editItemButton.addActionListener(this);
        compareItemsButton.addActionListener(this);
        sortOptions.addActionListener(this);

        this.setLayout(null);

        this.add(order);
        this.add(homeButton);
        this.add(artifactInventoryButton);
        this.add(weaponInventoryButton);
        this.add(addNewItemButton);
        this.add(editItemButton);
        this.add(compareItemsButton);
        this.add(sortOptions);
    }

    private void sort(String sortOption){
        System.out.println(sortOption);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Artifacts":
                MainFrame.navigate(2,1);
                break;

            case "Weapons":
                MainFrame.navigate(1,2);
                break;

            case "^":
                order.setText("v");
                break;

            case "v":
                order.setText("^");
                break;

            case "comboBoxChanged":
                sort((String)((JComboBox<?>)e.getSource()).getSelectedItem());
                break;
        }
    }

}
