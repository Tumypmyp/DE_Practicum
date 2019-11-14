package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private int MinX;
    private int MaxX;
    private int Y0;
    private int N;
    private double H;
    private XYChart.Series exact = new XYChart.Series();
    private XYChart.Series euler = new XYChart.Series();
    private XYChart.Series improvedEuler = new XYChart.Series();
    private XYChart.Series rungeKutta = new XYChart.Series();

    @FXML
    private LineChart<Number, Number> mainLineChart;

    @FXML
    private Slider sliderX0;

    @FXML
    private Slider sliderXN;

    @FXML
    private Slider sliderY0;

    @FXML
    private Slider sliderN;

    @FXML
    private Text textX0;

    @FXML
    private Text textXN;

    @FXML
    private Text textY0;

    @FXML
    private Text textN;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exact.setName("Exact");
        mainLineChart.getData().add(exact);
        rungeKutta.setName("Runge-Kutta");
        mainLineChart.getData().add(rungeKutta);
        improvedEuler.setName("Improved Euler");
        mainLineChart.getData().add(improvedEuler);
        euler.setName("Euler");
        mainLineChart.getData().add(euler);

        updateGraph();
    }

    private boolean updateValues() {
        int newMinX = (int) sliderX0.getValue();
        int newMaxX = (int) sliderXN.getValue();
        int newY0 = (int) sliderY0.getValue();
        int newN = (int) sliderN.getValue();


        if (MinX == newMinX && MaxX == newMaxX && Y0 == newY0 && N == newN) {
            return false;
        }

        MinX = newMinX;
        MaxX = newMaxX;
        Y0 = newY0;
        N = newN;
//        TODO change formula
        H = Math.max(0.001, (double) (MaxX - MinX) / N);
//        System.out.println("update to " + MinX + " " + MaxX + " " + Y0 + " " + N + " " + H);
        return true;
    }


    @FXML
    public void updateGraph() {
        if (!updateValues())
            return;

        textX0.setText("X0: " + MinX);
        textXN.setText("XN: " + MaxX);
        textY0.setText("Y0: " + Y0);
        textN.setText("N: " + N);

        updateExactSeries();
        updateEulerSeries();
        updateImprovedEulerSeries();
        updateRungeKuttaSeries();

    }

    private void updateExactSeries() {
        exact.getData().clear();
        if (MinX == 0)
            return;
        for (double x = MinX; x <= MaxX; x += H) {
            exact.getData().add(new XYChart.Data(x, getExactValue(x)));
//            System.out.print(x+ "|" + getExactValue(x) + " ");

        }
//        System.out.println();
    }

    private double getExactValue(double x) {
        double c1 = (double) (Y0 + MinX) / (MinX * MinX);
        return c1 * x * x - x;
    }

    private void updateEulerSeries() {
        euler.getData().clear();
        for (double x = MinX, y = Y0, d; x <= MaxX; x += H) {
            euler.getData().add(new XYChart.Data(x, y));
//            System.out.print(x + "|" + y + " ");
            try {
                y += H * f(x, y);
            } catch (Exception e) {
                System.out.print("break?? ");
                break;
            }
        }
//        System.out.println();

    }

    private void updateImprovedEulerSeries() {
        improvedEuler.getData().clear();

        for (double x = MinX, y = Y0, k1, k2; x <= MaxX; x += H) {
            improvedEuler.getData().add(new XYChart.Data(x, y));
//            System.out.print(x + "|" + y + " ");
            try {
                k1 = f(x, y);
                k2 = f(x + H, y + k1 * H);
                y += (k1 + k2) * H / 2;
            } catch (Exception e) {
                System.out.print("break?? ");
                break;
            }
        }
        System.out.println();

    }
    private void updateRungeKuttaSeries() {
        rungeKutta.getData().clear();

        for (double x = MinX, y = Y0, k1, k2, k3, k4; x <= MaxX; x += H) {
            rungeKutta.getData().add(new XYChart.Data(x, y));
//            System.out.print(x + "|" + y + " ");
            try {
                k1 = f(x, y);
                k2 = f(x + H / 2, y + k1 * H / 2);
                k3 = f(x + H / 2, y + k2 * H / 2);
                k4 = f(x + H, y + k3 * H);
                y += (k1 + k2 * 2 + 2 * k3 + k4) * H / 6;
            } catch (Exception e) {
                System.out.print("break?? ");
                break;
            }
        }
//        System.out.println();

    }

    private double f(double x, double y) {
        if (Math.abs(x) < 1e-8)
            throw new ArithmeticException();
        return 1 + 2 * y / x;
    }

}
