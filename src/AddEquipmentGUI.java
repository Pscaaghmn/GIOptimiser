import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class AddEquipmentGUI extends JPanel implements ActionListener, DocumentListener {

    private JLabel nameLabel;
    private JLabel levelLabel;
    private JComboBox<String> nameComboBox;
    private JSlider levelSlider;
    private JButton testButton;

    public AddEquipmentGUI(int width, int height, boolean isArtifact) {


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
