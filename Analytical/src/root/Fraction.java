package root;

public class Fraction {
    long numerator;//TODO bigInteger
    long denominator;

    Fraction(){
        numerator = 0;
        denominator = 0;
    }

    Fraction(long numerator, long denominator){
        this.numerator = numerator;
        this.denominator = denominator;
        this.reduceFraction();
    }

    Fraction(long number){
        numerator = number;
        denominator = 1;
    }

    Fraction(Double number){
        int wholeLengthAndDot = String.valueOf(number.intValue()).length() + 1,
                totalLength = number.toString().length(),
                numberOfZeros = totalLength - wholeLengthAndDot;
        long decades = (long)Math.pow(10, numberOfZeros);

        number *= decades;

        numerator = number.longValue();
        denominator = decades;

        reduceFraction();
    }

    public boolean equals(Fraction fraction){
        this.reduceFraction();
        fraction.reduceFraction();

        return this.numerator == fraction.numerator &&
                this.denominator == fraction.denominator;
    }

    public boolean equals(Double degree){
        Fraction fraction = new Fraction(degree);
        return this.equals(fraction);
    }


    public void reduceFraction(){
        if(numerator == 0 || denominator == 0) return;
        for(long multiply = gcd(numerator, denominator);
            multiply != 1 && multiply != 0;
            multiply = gcd(numerator, denominator)){
            numerator /= multiply;
            denominator /= multiply;
        }
    }

    public void add(double number){
        Fraction fraction = new Fraction(number);
        fraction.reduceFraction();

        this.numerator = this.numerator * fraction.denominator + this.denominator * fraction.numerator;
        this.denominator *= fraction.denominator;
        this.reduceFraction();
    }

    public void add(Fraction fraction){
        this.numerator = this.numerator * fraction.denominator + this.denominator * fraction.numerator;
        this.denominator *= fraction.denominator;
        this.reduceFraction();
    }

    public void sub(Fraction fraction){
        this.numerator = this.numerator * fraction.denominator - this.denominator * fraction.numerator;
        this.denominator *= fraction.denominator;
        this.reduceFraction();
    }

    public void multiply(Fraction fraction){
        this.numerator *= fraction.numerator;
        this.denominator *= fraction.denominator;
    }

    static Double toDouble(Fraction fraction){
        return (double)fraction.numerator / fraction.denominator;
    }

    public static boolean compare(Fraction a, Fraction b){
        return Fraction.toDouble(a) > Fraction.toDouble(b);
    }

    public void multiply(Double number){
        int wholeLengthAndDot = String.valueOf(number.intValue()).length() + 1,
                totalLength = number.toString().length(),
                numberOfZeros = totalLength - wholeLengthAndDot;
        long decades = (long)Math.pow(10, numberOfZeros);

        number *= decades;

        numerator *= number.longValue();
        denominator *= decades;

        reduceFraction();
    }

    public void divide(Double number){
        int wholeLengthAndDot = String.valueOf(number.intValue()).length() + 1,
                totalLength = number.toString().length(),
                numberOfZeros = totalLength - wholeLengthAndDot;
        long decades = (long)Math.pow(10, numberOfZeros);

        number *= decades;

        denominator *= number.longValue();
        numerator *= decades;

        reduceFraction();
    }

    private static long lcm(long a, long b){
        return a / gcd(a, b) * b;
    }

    private static long gcd(long a, long b){
        while(b != 0){
            a %= b;
            long buf = a;
            a = b;
            b = buf;
        }
        return a;
    }

    public void power(long pow){
        Fraction multiplier = new Fraction(this.numerator, this.denominator);
        for (int i = 0; i < pow; i++) {
            this.multiply(multiplier);
        }
    }

    @Override
    public String toString() {
        return denominator == 1 ? "" + numerator :
                numerator + "/" + denominator;
    }


}
