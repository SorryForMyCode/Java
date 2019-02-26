import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MyCell {
    Set<Integer> predict = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    String value;
    boolean used = false;

    MyCell(String value){
        this.value = value;
    }

    void removePredict(Integer val){
        predict.remove(val);
    }

    void removePredict(String val){
        predict.remove(Integer.valueOf(val));
    }

    @Override
    public String toString() {
        return value;
    }
}
