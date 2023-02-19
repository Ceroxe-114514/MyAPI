package api.ceroxe.math.factor;

public class Factor implements Computable {

    private double numerator = 1;
    private double denominator = 1;

    public double getNumerator() {
        return numerator;
    }

    public void setNumerator(double numerator) {
        this.numerator = numerator;
    }

    public double getDenominator() {
        return denominator;
    }

    public void setDenominator(double denominator) {
        this.denominator = denominator;
    }

    @Override
    public double getDouble() {
        return numerator / denominator;
    }

    @Override
    public void add(Computable number) {

    }

    @Override
    public void reduce(Computable number) {

    }

    @Override
    public void multiplyBy(Computable number) {

    }

    @Override
    public void dividedBy(Computable number) {

    }

}
