package solutions;

import javafx.scene.chart.XYChart;

public class ExactSolution implements IVPSolution {
    private int MinX;
    private int MaxX;
    private int Y0;
    private int N;
    private double H;

    public ExactSolution(int MinX, int MaxX, int Y0, int N) {
        update(MinX, MaxX, Y0, N);
    }

    public void update(int MinX, int MaxX, int Y0, int N) {
        this.MinX = MinX;
        this.MaxX = MaxX;
        this.Y0 = Y0;
        this.N = N;
        H = (double) (MaxX - MinX) / (N - 1);
    }


    @Override
    public double getKey(int i) {
        return MinX + i * H;
    }

    @Override
    public double getValue(int i) {
        double x = getKey(i);
        return solve(x);
    }

    private double solve(double x) {
        if (Math.abs(MinX) < 1e-8)
            throw new ArithmeticException();
        double c1 = (double) (Y0 + MinX) / (MinX * MinX);
        return c1 * x * x - x;
    }

    @Override
    public void updateXYChart(XYChart.Series series) {
        series.getData().clear();
        for (int i = 0; i < N; ++i) {
            series.getData().add(new XYChart.Data(getKey(i), getValue(i)));
        }
    }

    public void updateLocalErrorXYChart(XYChart.Series series, NumericalMethod numericalMethod) {
        series.getData().clear();
        for (int i = 0; i < N; ++i) {
            series.getData().add(new XYChart.Data(i, getValue(i) - numericalMethod.getValue(i)));
        }
    }

    public void updateTotalErrorXYChart(XYChart.Series series, NumericalMethod numericalMethod, int N1, int N2) {
        series.getData().clear();
        for (int n = N1; n <= N2; ++n) {
            double maxError = 0;
            numericalMethod.update(MinX, MaxX, Y0, n);
            for (int i = 0; i < n; ++i) {
                maxError = Math.max(maxError, Math.abs(solve(numericalMethod.getKey(i)) - numericalMethod.getValue(i)));
            }
            series.getData().add(new XYChart.Data(n, maxError));
        }
    }

    public void print() {
        System.out.println("Exact");
        for (int i = 0; i < N; i++)
            System.out.print(getKey(i) + "|" + getValue(i) + " ");
        System.out.println();
    }
}
