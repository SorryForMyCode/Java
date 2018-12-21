package root;

import java.math.BigInteger;

public class Fraction {
    BigInteger numerator;
    BigInteger denominator;

    Fraction(){
        numerator = new BigInteger("0");
        denominator = new BigInteger("0");
    }

    Fraction(long numerator, long denominator){
        this.numerator = new BigInteger(String.valueOf(numerator));
        this.denominator = new BigInteger(String.valueOf(denominator));
        this.reduceFraction();
    }

    Fraction(long number){
        numerator = new BigInteger(String.valueOf(number));
        denominator = new BigInteger("1");
    }

    Fraction(Double number){
        int wholeLengthAndDot = String.valueOf(number.intValue()).length() + 1,
                totalLength = number.toString().length(),
                numberOfZeros = totalLength - wholeLengthAndDot;
        long decades = (long)Math.pow(10, numberOfZeros);

        number *= decades;

        numerator = new BigInteger(String.valueOf(number.longValue()));
        denominator = new BigInteger(String.valueOf(decades));

        reduceFraction();
    }

    boolean equals(Fraction fraction){
        reduceFraction();
        fraction.reduceFraction();

        return numerator.equals(fraction.numerator) &&
                denominator.equals(fraction.denominator);
    }

    private void reduceFraction(){
        BigInteger gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
    }

    void add(long number){
        Fraction fraction = new Fraction(number);
        fraction.reduceFraction();

        numerator = numerator.multiply(fraction.denominator).add(denominator.multiply(fraction.numerator));
        denominator = denominator.multiply(fraction.denominator);
        reduceFraction();
    }

    void add(Fraction fraction){
        numerator = numerator.multiply(fraction.denominator).add(denominator.multiply(fraction.numerator));
        denominator = denominator.multiply(fraction.denominator);
        reduceFraction();
    }

    void add(BigInteger number){
        number = number.multiply(this.denominator);
        numerator = numerator.add(number);
        reduceFraction();
    }

    void sub(Fraction fraction){
        numerator = numerator.multiply(fraction.denominator).subtract(denominator.multiply(fraction.numerator));
        denominator = denominator.multiply(fraction.denominator);
        reduceFraction();
    }

    void multiply(Fraction fraction){
        numerator = numerator.multiply(fraction.numerator);
        denominator = denominator.multiply(fraction.denominator);
    }

    void multiply(BigInteger number){
        numerator = numerator.multiply(number);
    }

    static boolean compare(Fraction a, Fraction b){
        int cmp = a.numerator.multiply(b.denominator).compareTo(b.numerator.multiply(a.numerator));
        return  cmp > 0;
    }

    void divide(Fraction fraction){
        numerator = numerator.multiply(fraction.denominator);
        denominator = denominator.multiply(fraction.numerator);
        reduceFraction();
    }

    void divide(BigInteger number){
        denominator = denominator.multiply(number);
        reduceFraction();
    }

    void divide(long number){
        Fraction fraction = new Fraction(number);
        divide(fraction);
        reduceFraction();
    }

    void power(int pow){
        numerator = numerator.pow(pow);
        denominator = denominator.pow(pow);
    }

    @Override
    public String toString() {
        return denominator.toString().equals("1") ? numerator.toString() :
                numerator + "/" + denominator;
    }


}
