import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Cell {
    Set<Integer> predict = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    Integer value;
    boolean used = false;
    TextArea jPredict;
    TextField jValue;
    JPanel jPanel;

    Cell(String value){
        this.value = Integer.valueOf(value);
        writeJPredict();
        writeJValue();
        writeJPanel();

        handleTextField();
    }

    JPanel getJPanel() {
        return jPanel;
    }

    void removePredict(Integer val){
        if(predict.remove(val))
            writeJPredict();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    private void writeJPredict(){
        String predict = this.predict.toString();
        predict = predict.substring(0, predict.length()/2) + '\n' + predict.substring(predict.length()/2);

        jPredict = new TextArea(predict);
        jPredict.setEditable(false);
    }

    private void writeJValue(){
        jValue = new TextField( value.toString());
        jValue.setFont(new Font("brush script mt", Font.ITALIC, 30));
    }

    private void writeJPanel(){
        jPanel = new JPanel(new BorderLayout());
        //jPanel.add(jPredict, BorderLayout.NORTH);
        jPanel.add(jValue, BorderLayout.CENTER);
    }

    private void handleTextField(){
        jValue.addTextListener(new TextListener() {
            @Override
            public void textValueChanged(TextEvent e) {
                updateText();
                used = true;
            }
        });
    }

    private void updateText(){
        try {
            value = Integer.valueOf(jValue.getText());
            removePredict(value);
        } catch (Exception e){
            System.out.println("Trouble");
        }

    }
}
