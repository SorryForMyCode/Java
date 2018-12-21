package root;

import java.math.BigInteger;

public class Main {

    private static final int n = 4;
    private static Fraction[] cof = new Fraction[n];
    private static int[][] triangle = new int[n][];

    public static void main(String[] args) throws CorrectException {

        cof(n);
        PascalTriangle();
        System.out.println(new Equation(I(n) + "= 0", "f"));

    }
    private static String I(int num){
        String[] I = new String[num + 1];// 0 элемент
        I[0] = "f";

        for (int i = 1; i <= num; i++) {
            I[i] = I[i-1] + "+" + cof[i - 1] + "*(" + delta_f(i) + ")";
        }
        return I[num];
    }

    private static String delta_f(int num){
        StringBuilder str = new StringBuilder();

        for (int i = 0; i <= num; i++) {

            if(i > 0)
                str.append(i%2 == 0 ? "+" : "-");

            str.append(triangle[num - 1][i]).append("*");

            for (int j = 0; j <= i; j++)
                str.append(j == 0 ? "f" : "*f");

        }
        return str.toString();
    }

    private static void PascalTriangle(){
        for (int i = 0; i < n; i++) {
            triangle[i] = new int[i + 2];

            for (int j = 0; j < i + 2; j++) {
                if(j == 0 || j == i + 1) triangle[i][j] = 1;
                else triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }
    }

    private static String stringEquation(int i){
        StringBuilder str = new StringBuilder("t");

        for (int j = 1; j <= i; j++)
            str.append("*(t+" + j + ")");

        return str.toString() + " = 0";
    }

    private static void  cof(int num) throws CorrectException {
        Equation equation;
        long fac = 1;

        for (int i = 0, inc = 1; i < num; i++, ++inc) {
            if(i > 0) fac *= inc;

            equation = new Equation(stringEquation(i)  , "t");
            cof[i] = equation.integrate(0, 1);
            cof[i].divide(fac);
        }
    }
}
