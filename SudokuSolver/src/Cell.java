import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Cell {
    Set<Integer> predict = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    Integer value;
    boolean used = false;
    TextField jPredict = new TextField();
    TextField jValue = new TextField();
    JPanel jPanel;

    Cell(String value){
        this.value = Integer.valueOf(value);
        writeJPredict();
        writeJValue(value);
        writeJPanel();

        handleTextField();
    }

    JPanel getJPanel() {
        return jPanel;
    }

    void removePredict(Integer val){
        predict.remove(val);
    }

    private void writeJPredict(){
        String predict = this.predict.toString();
        predict = predict.substring(0, predict.length()/2) + '\n' + predict.substring(predict.length()/2);

        jPredict.setText(predict);
        jPredict.setEditable(false);
    }

    private void writeJValue(String text){
        jValue.setText(text);
        jValue.setFont(new Font("brush script mt", Font.ITALIC, 30));
    }

    private void writeJPanel(){
        jPanel = new JPanel(new BorderLayout());
        jPanel.add(jPredict, BorderLayout.NORTH);
        jPanel.add(jValue, BorderLayout.CENTER);
    }

    private void handleTextField(){
        jValue.addTextListener(new TextListener() {
            @Override
            public void textValueChanged(TextEvent e) {
                updateText();
            }
        });
    }

    private void updateText(){
        try {
            value = Integer.valueOf(jValue.getText());
            predict.clear();
            jPredict.setText("");

        } catch (Exception e){
            System.out.println("Trouble");
        }

    }

    @Override
    public String toString() {
        return value.toString();
    }
}
