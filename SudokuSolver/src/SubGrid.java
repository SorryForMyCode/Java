import javax.swing.*;
import java.awt.*;

class SubGrid {
    private int colPos, rowPos;
    private Cell[][] subGrid = new Cell[3][3];

    SubGrid(int row, int col, Grid grid){
        Cell[][] cells = grid.getCells();
        rowPos = row;
        colPos = col;

        for (int i = 0; i < 3; i++) {
            System.arraycopy(cells[3 * row + i], 3 * col, subGrid[i], 0, 3);
        }
    }

    void removeSubGrid(int row, int col){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(i != row && j != col)
                    subGrid[i][j].removePredict(subGrid[row][col].value);
            }
        }

    }

    JPanel getJPanel(){
        JPanel panel = new JPanel(new GridLayout(3, 3, 4 ,4 ));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                panel.add(subGrid[i][j].getJPanel());
            }
        }
        return panel;
    }

}
