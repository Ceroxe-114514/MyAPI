package api.ceroxe.math.factor;

public interface Computable {
    double getDouble();

    void add(Computable number);

    void reduce(Computable number);

    void multiplyBy(Computable number);

    void dividedBy(Computable number);
}
