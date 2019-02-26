import javax.swing.*;
import java.awt.*;

class MySubGrid {
    private MyCell[][] subGrid = new MyCell[3][3];

    MySubGrid(int row, int col, MyGrid grid){
        MyCell[][] cells = grid.getCells();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                subGrid[i][j] = cells[3 * row + i][3 * col + j];
            }
        }
    }

    // удалить предикт у всей подсетки значения клетки cell[row][col]
    void removeSubGrid(int row, int col){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(i != row && j != col)
                    subGrid[i][j].removePredict(subGrid[row][col].value);
            }
        }

    }
}
