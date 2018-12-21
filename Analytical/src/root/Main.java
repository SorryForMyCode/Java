package root;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) throws CorrectException {
        int n = 11;
        Fraction[] cof = new Fraction[n];
        BigInteger a = new BigInteger("6");
        BigInteger b = new BigInteger("3");
        a = a.gcd(b);
        System.out.println(a);

        Equation equation;//t*(t+1)*(t+2)*(t+3)*(t+4)*(t+5)*(t+6)
        long fac = 1;
        for (int i = 0, inc = 1; i < n; i++, ++inc) {
            if(i > 0) fac *= inc;

            equation = new Equation(stringEquation(i)  , "t");
            cof[i] = equation.integrate(0, 1);
            cof[i].divide(fac);

            System.out.println(cof[i]);
        }

        //        System.out.println(equation.integrate(0, 1));
//        delta_f = sum((-1)^i * f ^ i);//TODO bigInteger
//        I[i+1] = I[i] + cof * delta_f;
//
//        System.out.println(equation);
    }

    static String stringEquation(int i){
        StringBuilder str = new StringBuilder("t");

        for (int j = 1; j <= i; j++)
            str.append("*(t+" + j + ")");

        return str.toString() + " = 0";
    }
}
