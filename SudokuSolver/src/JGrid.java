import javax.swing.*;
import java.awt.*;

public class JGrid extends Grid {
    JPanel jGridPanel;
    JPanel[][] jSubGridPanel = new JPanel[3][3];

    JGrid(){
        super();
        jGridPanel = new JPanel(new GridLayout(3, 3, 17 , 17));
    }
}
