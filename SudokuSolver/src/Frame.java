//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//class Frame extends JFrame {
//    private JPanel grid,
//            subGrid[][],
//            jCell[][];
//
//    private Cell[][] cell;
//    private JTextArea[][] cellPredict = new JTextArea[9][9];
//    private TextField[][] cellValue = new TextField[9][9];
//    private JButton btnStart;
//
//    int posi, posj;
//
//    Frame(){
//        settings();
//        add(grid, BorderLayout.CENTER);
//        add(btnStart, BorderLayout.SOUTH);
//    }
//
//    private void settings(){
//        setLocation(350, 1);
//        setSize(1000, 860);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setTitle("SudokuSolver");
//        setLayout(new BorderLayout());
//        setGrid();
//        setBtnStart();
//        setVisible(true);
//    }
//
//    private void setBtnStart(){
//        btnStart = new JButton("Start");
//
//        btnStart.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                calculate();
//            }
//        });
//    }
//
//    private void setGrid(){
//        grid = new JPanel(new GridLayout(3, 3, 17 , 17));
//        subGrid = new JPanel[3][3];
//        jCell = new JPanel[9][9];
//        cell = new Cell[9][9];
//
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                jCell[i][j] =  new JPanel(new BorderLayout());
//                cell[i][j] = new Cell("0");
//
//                String predict = cell[i][j].predict.toString();
//                predict = predict.substring(0, 15) + '\n' + predict.substring(15);
//
//                cellPredict[i][j] = new JTextArea(predict);
//                cellPredict[i][j].setEditable(false);
//
//                cellValue[i][j] = new TextField(cell[i][j].value.toString());
//                cellValue[i][j].setFont(new Font("brush script mt", Font.ITALIC, 30));
//
//                jCell[i][j].add(cellPredict[i][j], BorderLayout.NORTH);
//                jCell[i][j].add(cellValue[i][j], BorderLayout.CENTER);
//            }
//        }
//
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                subGrid[i][j] = new JPanel(new GridLayout(3, 3, 3 , 3));
//                setSubGrid(subGrid[i][j], i, j);
//                grid.add(subGrid[i][j]);
//            }
//        }
//    }
//
//    private void setSubGrid(JPanel smallGrid, int I, int J){
//        for (int i = 3 * I; i < 3 * I + 3; i++) {
//            for (int j = 3 * J; j < 3 * J + 3; j++) {
//                smallGrid.add(jCell[i][j]);
//            }
//        }
//    }
//
//    private void calculate(){
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                cell[i][j].value = Integer.valueOf(cellValue[i][j].getText());
//            }
//        }
//
//        handleText();
//        handleFillValue();
//        handleRemovePredict();
//
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                System.out.print(cell[i][j] + " ");
//            }
//            System.out.println();
//        }
//
//    }
//
//    void handleText(){
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                posi = i;
//                posj = j;
//
//                cellValue[i][j].addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        cell[posi][posj].value = Integer.valueOf(cellValue[posi][posj].getText());
//                    }
//                }); //TODO:here
//            }
//        }
//
//    }
//
//    void handleFillValue(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                while (true){
//
//                    for(int i = 0; i < 9; i++){
//                        for (int j = 0; j < 9; j++) {
//                            if(cell[i][j].predict.size() == 1 && cell[i][j].value == 0){
//                                cell[i][j].value = cell[i][j].predict.hashCode();
//                                cellValue[i][j].setText(cell[i][j].value.toString());
//                            }
//                        }
//                    }
//
//                }
//            }
//        }).start();
//    }
//
//    void handleRemovePredict(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    for (int i = 0; i < 9; i++) {
//                        for (int j = 0; j < 9; j++) {
//                            if(!cell[i][j].used){
//                                removeLinePredict(i, j);
//                                String predict = cell[i][j].predict.toString();
//                                predict = predict.substring(0, 15) + '\n' + predict.substring(15);
//
//                                cellPredict[i][j].setText(predict);
//                            }
//                        }
//                    }
//                }
//            }
//        }).start();
//    }
//
//    private void removeLinePredict(int row, int col){
//        for (int i = 0; i < 9; i++) {
//            cell[row][i].removePredict(cell[row][col].value);
//        }
//
//        for (int i = 0; i < 9; i++) {
//            cell[i][col].removePredict(cell[row][col].value);
//        }
//
//
//    }
//}
