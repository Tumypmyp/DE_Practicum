package sample;

public class ImprovedEulerSolution extends NumericalMethod {
    ImprovedEulerSolution(int MinX, int MaxX, int Y0, int N, String name) {
        super(MinX, MaxX, Y0, N, name);
    }

    @Override
    double getNextValue(double lastX, double lastY) {
        double k1 = f(lastX, lastY);
        double k2 = f(lastX + getH(), lastY + k1 * getH());
        return lastY + (k1 + k2) * getH() / 2;
    }
}
