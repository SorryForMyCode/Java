import javax.swing.*;
import java.awt.*;

public class JCell extends Cell{
    private JPanel jPanel;
    private JTextArea jPredict;
    private JTextField jValue;

    JCell(String value){
        super(value);
        writeJPredict();
        writeJValue();
        writeJPanel();
    }

    @Override
    void removePredict(Integer val) {
        super.removePredict(val);
        writeJPredict();
    }

    private void writeJPredict(){
        String predict = super.predict.toString();
        predict = predict.substring(0, 15) + '\n' + predict.substring(15);

        jPredict = new JTextArea(predict);
        jPredict.setEditable(false);
    }

    private void writeJValue(){
        jValue = new JTextField(super.value.toString());
        jValue.setFont(new Font("brush script mt", Font.ITALIC, 30));
    }

    private void writeJPanel(){
        jPanel = new JPanel(new BorderLayout());
        jPanel.add(jPredict, BorderLayout.NORTH);
        jPanel.add(jValue, BorderLayout.CENTER);
    }
}
