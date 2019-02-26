import javax.swing.*;
import java.awt.*;

class MyGrid {
    private MyCell[][] cells = new MyCell[9][9];
    private MySubGrid[][] subGrid = new MySubGrid[3][3];

    MyCell[][] getCells() {
        return cells;
    }

    MyGrid(){
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                cells[i][j] = new MyCell("0");


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                subGrid[i][j] = new MySubGrid(i, j, this);
            }
        }
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
                if (cells[i][j].predict.size() == 1 && cells[i][j].value.equals("")) {
                    cells[i][j].value = String.valueOf(cells[i][j].predict);
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
                removeExcessLinePredictions(i, j);
                //TOdo если можно поставить только туда но предикты еще есть
            }
        }
    }

    private void removeExcessLinePredictions(int row, int col){
        for (int i = 0; i < 9; i++) {
            //cells[row][i].predict.
        }
    }

    private void removeLinePredict(int row, int col){
        for (int i = 0; i < 9; i++) {
            cells[row][i].removePredict(cells[row][col].value);
            cells[i][col].removePredict(cells[row][col].value);
        }
    }

    private void removeSubGridPredict(int row, int col){
        subGrid[row / 3][col / 3].removeSubGrid(row % 3, col % 3); // координаты подсетки & координаты ячейки в подсетке
    }
}
