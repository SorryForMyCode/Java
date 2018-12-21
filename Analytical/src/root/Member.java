package root;

import java.math.BigInteger;
import java.util.ArrayList;

public class Member {
    private String temporary;
    private String variable;
    private char sign;
    private String unknowns = "";

    private Fraction degreeOfVariable = new Fraction(0, 1);
    private Fraction fraction = new Fraction(1, 1);

    public Member(String temporary, String variable) {

        sign = temporary.charAt(0);
        this.temporary = temporary.substring(1);
        this.variable = variable;

        extractVariables();
        numbers();

    }


    private boolean isSign(char ch){
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    public Member(Member member) {

        temporary = member.temporary;
        variable = member.variable;
        sign = member.sign;

        degreeOfVariable = member.degreeOfVariable;
        fraction = member.fraction;
    }

    public void add(Member member){
        if(equalsDegreeOfVariable(member)){
            if(member.sign == sign) {
                fraction.add(member.fraction);
            } else {
                fraction.sub(member.fraction);
            }
        }
    }

    void integrate(){
        degreeOfVariable.add(1);
        fraction.divide(degreeOfVariable);
    }

    Fraction valueAtPoint(double point){
        Fraction value = new Fraction(point);
        value.power(degreeOfVariable.numerator.intValue());
        value.multiply(fraction);

        return value;
    }

    public boolean equalsDegreeOfVariable(Member member){
        return this.degreeOfVariable.equals(member.degreeOfVariable);
    }

    public boolean equalsDegreeOfVariable(Double degree){
        return this.degreeOfVariable.equals(degree);
    }

    private void numbers(){
        int start = 0, end = 0;
        boolean started = false;

        for (int i = temporary.length() - 1; i >= 0; i--) {/////////////////////////
            if(!started && i == 0){
                start = i;
            }
            if(!started && (Character.isDigit(temporary.charAt(i))) ){
                end = i + 1;
                started = true;
            } else if(!Character.isDigit(temporary.charAt(i)) && !Character.isLetter(temporary.charAt(i)) ){
                start = i + 1;
                started = false;
            }
            if(!Character.isLetter(temporary.charAt(i)) && !Character.isDigit(temporary.charAt(i)) && isObj(temporary.substring(start, end))){

                if(start == 0){
                    unknowns += temporary.substring(start, end);
                    removeStr(start, end);
                }
                else {
                    unknowns += temporary.substring(start, end);
                    removeStr(start - 1, end);
                }
                continue;
            }
            if(i == 0 || temporary.charAt(i) == '*') {
                fraction.multiply(new BigInteger(temporary.substring(start, end)));//fraction.multiply(Double.parseDouble(temporary.substring(start, end)));
                removeStr(start, end);
                if(i != 0) removeLastChar();
            } else if(temporary.charAt(i) == '/'){
                fraction.divide(new BigInteger(temporary.substring(start, end)));
                removeStr(start, end);
                removeLastChar();
            } else if(temporary.charAt(i) == '-'){
                fraction.add(new BigInteger(temporary.substring(start, end)));
            }
        }
    }

    private boolean isObj(String obj){
        return obj.contains("obj");
    }

    private void extractVariables(){
        int pos = temporary.lastIndexOf(variable);

        do{
            if(pos == -1) break;

            int startBracket = -1, endBracket = -1, countBrackets = 0;
            for (int i = temporary.length() - 1; i >= 0; i--) {
                if(temporary.charAt(i) == ']'){
                    startBracket = i;
                    countBrackets++;
                } else if(temporary.charAt(i) == '['){
                    countBrackets--;
                    if(countBrackets == 0){
                        endBracket = i;
                    }
                }
                if(!(startBracket < pos && endBracket > pos + variable.length())){
                    removeVariable(pos);
                    degreeOfVariable.add(1);
                    break;
                }
            }

            pos = temporary.lastIndexOf(variable);
        }while(pos != -1);

    }

    private void removeStr(int posBeg, int posEnd){
        StringBuilder str = new StringBuilder(temporary);
        str.delete(posBeg, posEnd);
        temporary = str.toString();
    }

    private void removeLastChar(){
        StringBuilder str = new StringBuilder(temporary);
        str.deleteCharAt(temporary.length() - 1);
        temporary = str.toString();
    }

    private void removeVariable(int pos){
        StringBuilder str = new StringBuilder(temporary);
        str.delete(pos, pos + variable.length());
        str.insert(pos, '1');
        temporary = str.toString();
    }

    static boolean compare(Member a, Member b){
        return Fraction.compare(a.degreeOfVariable, b.degreeOfVariable);
    }

    @Override
    public String toString() {
        String degree;
        if(degreeOfVariable.toString().equals("0")){
            degree = "";
        } else if(degreeOfVariable.toString().equals("1")){
            degree = variable;
        } else {
            degree = variable + "^" + degreeOfVariable;
        }

        String coefficient;
        if(fraction.toString().startsWith("0")){
            coefficient = "";

        } else if(fraction.toString().equals("1") && !degree.equals("")){
            coefficient = String.valueOf(sign) + " " + degree;

        } else if(degree.equals("")){
            coefficient = String.valueOf(sign) + " " + fraction.toString();

        } else {
            coefficient = String.valueOf(sign) + " " + fraction + "*" + degree;
        }

        return coefficient;
    }

}
