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
        this.reduceFraction();
        fraction.reduceFraction();

        return this.numerator == fraction.numerator &&
                this.denominator == fraction.denominator;
    }

    private void reduceFraction(){
        BigInteger gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
    }

    void add(long number){
        Fraction fraction = new Fraction(number);
        fraction.reduceFraction();

        this.numerator = this.numerator.multiply(fraction.denominator).add(this.denominator.multiply(fraction.numerator));
        this.denominator = this.denominator.multiply(fraction.denominator);
        this.reduceFraction();
    }

    void add(Fraction fraction){
        this.numerator = this.numerator.multiply(fraction.denominator).add(this.denominator.multiply(fraction.numerator));
        this.denominator = this.denominator.multiply(fraction.denominator);
        this.reduceFraction();
    }

    void sub(Fraction fraction){
        this.numerator = this.numerator.multiply(fraction.denominator).subtract(this.denominator.multiply(fraction.numerator));
        this.denominator = this.denominator.multiply(fraction.denominator);
        this.reduceFraction();
    }

    void multiply(Fraction fraction){
        this.numerator = this.numerator.multiply(fraction.numerator);
        this.denominator = this.denominator.multiply(fraction.denominator);
    }

//    static Double toDouble(Fraction fraction){
//        return (double)fraction.numerator / fraction.denominator;
//    }

//    public static boolean compare(Fraction a, Fraction b){
//        return Fraction.toDouble(a) > Fraction.toDouble(b);
//    }

//    void multiply(Double number){
//        int wholeLengthAndDot = String.valueOf(number.intValue()).length() + 1,
//                totalLength = number.toString().length(),
//                numberOfZeros = totalLength - wholeLengthAndDot;
//        long decades = (long)Math.pow(10, numberOfZeros);
//
//        number *= decades;
//
//        numerator *= number.longValue();
//        denominator *= decades;
//
//        reduceFraction();
//    }

    void divide(Fraction fraction){
        numerator = numerator.multiply(fraction.denominator);
        denominator = denominator.multiply(fraction.numerator);
        reduceFraction();
    }

    void divide(BigInteger number){
        denominator = denominator.multiply(number);
        reduceFraction();
    }

    void power(int pow){
        numerator = numerator.pow(pow);
        denominator = denominator.pow(pow);
    }

    @Override
    public String toString() {
        return denominator.toString() == "1" ? numerator.toString() :
                numerator + "/" + denominator;
    }


}
