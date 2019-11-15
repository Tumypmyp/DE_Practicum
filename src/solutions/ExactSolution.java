package solutions;

import javafx.scene.chart.XYChart;

public class ExactSolution implements IVPSolution {
    private int MinX;
    private int MaxX;
    private int Y0;
    private int N;
    private double H;

    public ExactSolution(int MinX, int MaxX, int Y0, int N) {
        this.MinX = MinX;
        this.MaxX = MaxX;
        this.Y0 = Y0;
        this.N = N;
        H = (double) (MaxX - MinX) / (N - 1);
    }

    public double getKey(int i) {
        return MinX + i * H;
    }

    public double getValue(int i) {
        if (Math.abs(MinX) < 1e-8)
            throw new ArithmeticException();
        double x = getKey(i);
        double c1 = (double) (Y0 + MinX) / (MinX * MinX);
        return c1 * x * x - x;
    }

    public void updateXYChart(XYChart.Series series) {
        series.getData().clear();
        for (int i = 0; i < N; ++i) {
            try {
                series.getData().add(new XYChart.Data(getKey(i), getValue(i)));
            } catch (Exception e) {
                System.out.print("division by zero");
                break;
            }
//            System.out.print(getKey(i) + "|" + getValue(i) + " ");
        }
    }

    public void print() {
        System.out.println("Exact");
        for (int i = 0; i < N; i++)
            System.out.print(getKey(i) + "|" + getValue(i) + " ");
        System.out.println();
    }
}
