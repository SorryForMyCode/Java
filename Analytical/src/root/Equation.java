package root;

import java.util.*;

public class Equation {
    private String equation;
    private String variable;
    private List<Member> members = new LinkedList<>();
    private ArrayList<Unknown> unknowns = new ArrayList<>();

    private int placeOpeningBracket, placeClosingBracket;


    public Equation(String equation, String variable) throws CorrectException {
        this.equation = equation;
        this.variable = variable;

        removeUnnecessarySpaces();
        validationСheck();
        moveEverythingToLeft();
        renamingFunctions();
        openBrackets();
        fixSignsTrouble();
        fillMembers();
        sumMembersWithSameDegree();
        sortMembers();
    }

    public void integrate(){
        for (Member member: members){
            member.integrate();
        }
    }

    public Fraction integrate(double bottomBound, double upperBound){
        integrate();
        Fraction sum = new Fraction(0), bottomValue, upperValue;
        for (Member member: members){
            bottomValue = member.valueAtPoint(bottomBound);
            upperValue = member.valueAtPoint(upperBound);
            upperValue.sub(bottomValue);

            sum.add(upperValue);
        }
        return sum;
    }

    private void renamingFunctions(int startPos, int endPos){
        StringBuilder str = new StringBuilder(equation);
        str.delete(startPos, endPos);
        str.insert(startPos, "obj" + (unknowns.size() - 1));
        equation = str.toString();
    }

    private void renamingFunctions(){
        int startFunction, endFunction;
        for (int i = 0; i < equation.length(); i++) {
            if (Character.isLetter(equation.charAt(i))) {
                startFunction = i;
                endFunction = getEndOfFunction(startFunction);
                if (endFunction != -1) {
                    i += endFunction - startFunction;
                    unknowns.add(new Unknown(equation.substring(startFunction, endFunction), unknowns.size()));
                    renamingFunctions(startFunction, endFunction);
                }
            }
        }
    }

    private int getEndOfFunction(int startPos){
        int countBrackets = 0;
        boolean insideBrackets = false;
        for (int i = startPos; i < equation.length(); i++) {
            if(!insideBrackets && isSign(equation.charAt(i))) break;
            if(equation.charAt(i) == '('){
                countBrackets++;
                insideBrackets = true;
            } else if(equation.charAt(i) == ')'){
                countBrackets--;
            }
            if(insideBrackets && countBrackets == 0 && isSign(equation.charAt(i))){
                return i;
            }
        }
        return -1;
    }

    private void removeUnnecessarySpaces(){
        equation =  "+" + equation.replaceAll(" +", "");
    }

    private void validationСheck() throws CorrectException{
       isCorrectBracket();
       isCorrectFunctionNames();
       isCorrectSigns();
    }

    private void isCorrectSigns() throws CorrectException{
        int countEquals = 0;
        for (int i = 0; i < equation.length() - 1; i++) {
            if(equation.charAt(i) == '=') countEquals++;
            if(isSign(equation.charAt(i)) && isSign(equation.charAt(i + 1)) ||
                    equation.charAt(i) == '=' && (i == 0 || i == equation.length() - 1 ||
                    !isLetterOrDigitOrBracket(equation.charAt(i + 1), '(') ||
                    !isLetterOrDigitOrBracket(equation.charAt(i - 1), ')') ||
                    countEquals != 1) ){
                throw new CorrectException("unnecessary sign", i);
            }
        }
    }

    private boolean isLetterOrDigitOrBracket(char ch, char bracket){
        return Character.isLetter(ch) || ch == bracket || Character.isDigit(ch);
    }

    private boolean isSign(char ch){
        return ch == '-' || ch == '+' || ch == '*' || ch == '/' || ch == '^' || ch == '=';
    }

    private void isCorrectBracket() throws CorrectException{
        int countBracket = 0;
        for (int i = 0; i < equation.length(); i++) {
            if(equation.charAt(i) == '('){
                countBracket++;
            } else if(equation.charAt(i) == ')'){
                countBracket--;
            }
        }
        if(countBracket > 0){
            throw new CorrectException("Expect Bracket", equation.indexOf('('));
        } else if(countBracket < 0){
            throw new CorrectException("Expect Bracket", equation.lastIndexOf(')'));
        }
    }

    private void isCorrectFunctionNames() throws CorrectException{
        int start = -1, end;
        for (int i = 0; i < equation.length() - 1; i++) {
            if(Character.isLetter(equation.charAt(i)) && start == -1){
                start = i;
                if(equation.length() > 1 && !Character.isLetter(equation.charAt(i + 1))){
                    start = -1;
                }
            } else if(Character.isLetter(equation.charAt(i)) && equation.charAt(i + 1) == '('){
                end = i + 1;
                if(!Unknown.isCorrectFunction(equation.substring(start, end))){
                    throw new CorrectException("Wrong name of function", start);
                }
                start = -1;
            }
        }
    }

    private void sortMembers(){
        for (int i = 0; i < members.size(); i++) {
            for (int j = i; j < members.size() - 1; j++) {
                if(Member.compare(members.get(i + 1), members.get(i))){
                    Member buf = new Member(members.get(i));
                    members.set(i,members.get(i + 1));
                    members.set(i + 1, buf);
                }
            }
        }
    }

    private void sumMembersWithSameDegree(){

        for (int i = 0; i < members.size(); i++){
            for (int j = i + 1; j < members.size(); j++) {
                if(i != j  && members.get(i).equalsDegreeOfVariable(members.get(j))){
                    members.get(i).add(members.get(j));
                    members.remove(j);
                    j--;
                }
            }
        }
    }

    private void fillMembers(){
        int start = 0, end;
        boolean flag = false, bracketIsOpen = false;

        for (int i = 0; i < equation.length(); i++) {
            if(equation.charAt(i) == '['){
                bracketIsOpen = true;
            }else if(equation.charAt(i) == ']'){
                bracketIsOpen = false;
            }
            if(!flag){
                start = i == 0 ? i : i - 1;
                flag = true;
            }else if(!bracketIsOpen && (equation.charAt(i) == '+' ||
                    equation.charAt(i) == '-' ||
                    equation.charAt(i) == '=') ){
                flag = false;
                end = i;
                members.add(new Member(equation.substring(start, end), variable));
            }
        }
    }

    private void fixSignsTrouble(){
        while(equation.contains("-+") || equation.contains("+-")){
            equation = equation.replace("-+", "+");
            equation = equation.replace("+-", "-");
        }
    }

    private void moveEverythingToLeft(){
        StringBuilder newEquation = new StringBuilder(equation);
        int placeEquals =  equation.indexOf('=');

        if(equation.charAt(placeEquals + 1) != '0') {
            newEquation = newEquation.delete(placeEquals, newEquation.length())
                    .append("-(")
                    .append(equation.substring(placeEquals + 1, equation.length()))
                    .append(')')
                    .append("=0");

            equation = newEquation.toString();
        }
    }

    private void openBrackets(){
        while(placeOpeningBracket != -1){
            multiplyLeftRight();
            divideRight();
            plusAndMinusLeft();
        }
    }

    private int findPlaceClosingBracket(){
        if(placeOpeningBracket == -1) return -1;

        for (int i = placeOpeningBracket, count = 0; i < equation.length(); i++) {
            if(equation.charAt(i) == '('){
                count++;
            }
            if(equation.charAt(i) == ')'){
                count--;
            }
            if(count == 0) return i;
        }
        return -1;
    }

    private void plusAndMinusLeft(){
        refreshBracketsPosition();
        if(placeOpeningBracket == -1) return;

        if(leftOfBracket('+') &&
                (rightOfBracket('-') || rightOfBracket('+') || rightOfBracket('=')) ){
            removeBrackets(new StringBuilder(equation));
            refreshBracketsPosition();
        }
        if(placeOpeningBracket == -1) return;

        if (leftOfBracket('-') &&
                (rightOfBracket('-') || rightOfBracket('+') || rightOfBracket('=')) ){
            minusLeft();
        }
    }

    private void minusLeft(){
        StringBuilder newEquation = new StringBuilder(equation);
        int countBrackets = 0;

        for (int i = placeOpeningBracket + 1; i < placeClosingBracket; i++) {
            if(newEquation.charAt(i) == '('){
                countBrackets++;
            } else if(newEquation.charAt(i) == ')'){
                countBrackets--;
            }
            if(countBrackets == 0){
                if(newEquation.charAt(i) == '-'){
                    newEquation.setCharAt(i, '+');

                }else if(newEquation.charAt(i) == '+'){
                    newEquation.setCharAt(i, '-');
                }
            }

        }
        removeBrackets(newEquation);
    }

    private void refreshBracketsPosition(){
        placeOpeningBracket = equation.indexOf("(");
        placeClosingBracket = findPlaceClosingBracket();
    }

    private void multiplyLeftRight(){
        refreshBracketsPosition();
        if(placeOpeningBracket == -1) return;

        if(leftOfBracket('*')){
            String multiplier = multiplierLeft(placeOpeningBracket - 1);

            refreshBracketsPosition();
            multiplyBracket(multiplier);
            refreshBracketsPosition();
        }

        if(rightOfBracket('*')){
            String multiplier = multiplierRight(placeClosingBracket + 1);
            multiplyBracket(multiplier);
        }
    }

    private boolean leftOfBracket(char ch){
        return equation.charAt(placeOpeningBracket - 1) == ch;
    }

    private boolean rightOfBracket(char ch){
        return equation.charAt(placeClosingBracket + 1) == ch;
    }

    private void divideRight(){
        refreshBracketsPosition();
        if(placeOpeningBracket == -1) return;

        if(rightOfBracket('/')){
            String divisor = divisorRight(placeClosingBracket + 1);
            divideBracket(divisor);
            refreshBracketsPosition();
        }
    }

    private String divisorRight(int place){
        StringBuilder newEquation = new StringBuilder(equation);
        String divisor = "";
        int start = place + 1, end = equation.length();

        for (int i = start; i < end; i++) {
            if(equation.charAt(i) == '('){
                i = equation.indexOf(')', i);
                end = i + 1;
                divisor = equation.substring(start, end);
                break;
            }
            if(equation.charAt(i) == '+' ||
                    equation.charAt(i) == '-' ||
                    equation.charAt(i) == '=' ||
                    equation.charAt(i) == '*' ||
                    equation.charAt(i) == '/'){
                end = i;
                divisor = equation.substring(start, end);
                break;
            }
        }
        if(divisor.charAt(0) == '('){
            divisor = "[" + divisor.substring(1, divisor.length() - 1) + "]";
        }
        equation = newEquation.delete(start - 1, end).toString();
        return divisor;
    }

    private void divideBracket(String divisor){
        StringBuilder newEquation = new StringBuilder(equation);

        for (int i = placeClosingBracket ; i >= placeOpeningBracket; i--) {
            if(newEquation.charAt(i) == '+' ||
                    newEquation.charAt(i) == '-' ||
                    newEquation.charAt(i) == '('){

                newEquation.insert(i + 1, "1/" + divisor + "*" );
            }
        }
        equation = newEquation.toString();
    }

    private String multiplierLeft(int end){
        StringBuilder newEquation = new StringBuilder(equation);
        String multiplier = "";
        int start = 0;

        for (int i = end; i >= start; i--) {
            if(equation.charAt(i) == '+' ||
                    equation.charAt(i) == '-'){

                start = i + 1;
                multiplier = equation.substring(start, end);
                break;
            }
        }
        equation = newEquation.delete(start, end + 1).toString();
        return multiplier;
    }

    private String multiplierRight(int place){
        StringBuilder newEquation = new StringBuilder(equation);
        String multiplier = "";
        int start = place + 1, end = equation.length() - 1, countBrackets = 0;

        for (int i = start; i < end; i++) {
            if(equation.charAt(i) == '('){
                countBrackets++;
                continue;
            }else if(equation.charAt(i) == ')'){
                countBrackets--;
                continue;
            }
            if(countBrackets == 0 && (equation.charAt(i) == '+' ||
                    equation.charAt(i) == '-' ||
                    equation.charAt(i) == '=') ){
                end = i;
                multiplier = equation.substring(start, end);
                break;
            }
        }
        equation = newEquation.delete(start - 1, end).toString();
        return multiplier;
    }

    private void removeBrackets(StringBuilder newEquation){
        newEquation.deleteCharAt(placeOpeningBracket);
        newEquation.deleteCharAt(placeClosingBracket - 1);
        equation = newEquation.toString();
    }

    private void multiplyBracket(String multiplier){
        StringBuilder newEquation = new StringBuilder(equation);

        if(isMinusNumber()){
            newEquation.insert(placeClosingBracket , "*" + multiplier);
        } else {

            for (int i = placeClosingBracket ; i >= placeOpeningBracket; i--) {
                if(newEquation.charAt(i) == '+' || newEquation.charAt(i) == '-' || newEquation.charAt(i) == '('){
                    newEquation.insert(i + 1, multiplier + "*");
                }
            }
        }

        equation = newEquation.toString();
    }

    private boolean isMinusNumber(){
        for (int i = placeClosingBracket - 1; i > placeOpeningBracket; i--) {
            if(Character.isDigit(equation.charAt(i))){
                continue;
            } else if((equation.charAt(i) == '-' || equation.charAt(i) == '+' ||
                    Character.isDigit(equation.charAt(i))) &&
                            i - 1 == placeOpeningBracket){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < members.size(); i++)
            if(i == 0 && members.get(0).toString().charAt(0) == '+') str = str.concat(members.get(i).toString().substring(2) + " ");
            else str = str.concat(members.get(i) + " ");
        return str + " = 0";
    }
}
