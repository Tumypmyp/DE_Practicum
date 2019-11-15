package solutions;

import javafx.scene.chart.XYChart;

public abstract class NumericalMethod implements IVPSolution {
    private int MinX;
    private int MaxX;
    private int Y0;
    private int N;
    private double H;
    private double[] y;
    private String name;

    NumericalMethod(int MinX, int MaxX, int Y0, int N, String name) {
        this.name = name;
        update(MinX, MaxX, Y0, N);
    }

    public void update(int MinX, int MaxX, int Y0, int N) {
        this.MinX = MinX;
        this.MaxX = MaxX;
        this.Y0 = Y0;
        this.N = N;
        H = (double) (MaxX - MinX) / (N - 1);
        fill();
    }

    private void fill() {
        y = new double[N];
        y[0] = Y0;
        for (int i = 1; i < N; i++) {
            y[i] = getNextValue(getKey(i - 1), y[i - 1]);
        }
    }

    abstract double getNextValue(double lastX, double lastY);

    double f(double x, double y) {
        if (Math.abs(x) < 1e-8)
            throw new ArithmeticException();
        return 1 + 2 * y / x;
    }

    @Override
    public double getKey(int i) {
        return MinX + i * H;
    }

    @Override
    public double getValue(int i) {
        return y[i];
    }

    @Override
    public void updateXYChart(XYChart.Series series) {
        series.getData().clear();
        for (int i = 0; i < N; ++i) {
            series.getData().add(new XYChart.Data(getKey(i), getValue(i)));
        }
    }

    public void print() {
        System.out.println(name);
        for (int i = 0; i < N; i++)
            System.out.print(getKey(i) + "|" + getValue(i) + " ");
        System.out.println();
    }

    double getH() {
        return H;
    }
}
