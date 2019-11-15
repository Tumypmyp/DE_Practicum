package sample;

public class RungeKuttaSolution extends NumericalMethod {
    RungeKuttaSolution(int MinX, int MaxX, int Y0, int N, String name) {
        super(MinX, MaxX, Y0, N, name);
    }

    @Override
    double getNextValue(double lastX, double lastY) {
        double k1 = f(lastX, lastY);
        double k2 = f(lastX + getH() / 2, lastY + k1 * getH() / 2);
        double k3 = f(lastX + getH() / 2, lastY + k2 * getH() / 2);
        double k4 = f(lastX + getH(), lastY + k3 * getH());
        return lastY + (k1 + k2 * 2 + 2 * k3 + k4) * getH() / 6;
    }
}
