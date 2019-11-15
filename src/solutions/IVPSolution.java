package solutions;

import javafx.scene.chart.XYChart;

public interface IVPSolution {
    double getKey(int i);

    double getValue(int i);

    void updateXYChart(XYChart.Series series);

    void print();
}
