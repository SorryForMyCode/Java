import javax.swing.*;
import java.awt.*;

class Grid {
    private Cell[][] cells = new Cell[9][9];
    private SubGrid[][] subGrid = new SubGrid[3][3];

    public Cell[][] getCells() {
        return cells;
    }

    Grid(){
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                cells[i][j] = new Cell("0");


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                subGrid[i][j] = new SubGrid(i, j, this);
            }
        }
    }

    JPanel getJPanel(){
        JPanel panel = new JPanel(new GridLayout(3, 3, 17, 17));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                panel.add(subGrid[i][j].getJPanel());
            }
        }
        return panel;
    }

    void calculate(){
        handleFillValue();
        handleRemovePredict();
    }

    private void handleFillValue() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) fillValue();
            }
        }).start();
    }

    private void fillValue(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].predict.size() == 1 && cells[i][j].value == 0) {
                    cells[i][j].value = cells[i][j].predict.hashCode();
                    cells[i][j].jValue.setText(cells[i][j].value.toString());
                }
            }
        }
    }

    private void handleRemovePredict(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) removePredict();
            }
        }).start();
    }

    private void removePredict(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                    removeLinePredict(i, j);
                    removeSubGridPredict(i, j);
                    removeBusyLinePredict();
                    //TOdo если можно поставить только туда но предикты еще есть
            }
        }
    }

//    boolean belongsToLine(){
//
//    }
//
//    boolean belongsToColumn(){
//
//    }

    private void removeBusyLinePredict(){
        for (int i = 0; i < 9; i++) {
            
        }
    }

    private void removeLinePredict(int row, int col){
        for (int i = 0; i < 9; i++) {
            cells[row][i].removePredict(cells[row][col].value);
            cells[i][col].removePredict(cells[row][col].value);
        }

    }

    private void removeSubGridPredict(int row, int col){
        int countRow = 0, countCol = 0;
        while (row >= 3){
            ++countRow;
            row -= 3;
        }

        while (col >= 3){
            ++countCol;
            col -= 3;
        }

        subGrid[countRow][countCol].removeSubGrid(row, col);
    }
}
