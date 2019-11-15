package sample;

public class EulerSolution extends NumericalMethod {
    EulerSolution(int MinX, int MaxX, int Y0, int N, String name) {
        super(MinX, MaxX, Y0, N, name);
    }

    @Override
    double getNextValue(double lastX, double lastY) {
        return getH() * f(lastX, lastY) + lastY;
    }
}
