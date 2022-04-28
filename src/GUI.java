import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel {
    // canvas for other GUI widgets
    public GUI(int width, int height) {
        System.out.println("GUI constructor");
        this.setPreferredSize(new Dimension(width, height));
        setLayout(null);
    }
}