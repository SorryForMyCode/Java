import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TestFrame extends JFrame {
    private Grid grid;
    private JButton btnStart;

    TestFrame(){
        settings();
        grid = new Grid();
        add(grid.getJPanel(), BorderLayout.CENTER);
        add(btnStart, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void settings(){
        setLocation(200, 1);
        setSize(1320, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SudokuSolver");
        setLayout(new BorderLayout());
        setBtnStart();
    }

    private void setBtnStart(){
        btnStart = new JButton("Start");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.calculate();
            }
        });
    }
}
