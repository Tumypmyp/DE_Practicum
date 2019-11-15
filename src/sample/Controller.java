package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import solutions.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private int MinX = 1;
    private int MaxX = 1;
    private int Y0 = 1;
    private int N = 1;
    private int N1 = 1;
    private int N2 = 1;

    private XYChart.Series exact = new XYChart.Series();
    private XYChart.Series euler = new XYChart.Series();
    private XYChart.Series improvedEuler = new XYChart.Series();
    private XYChart.Series rungeKutta = new XYChart.Series();

    private XYChart.Series eulerLE = new XYChart.Series();
    private XYChart.Series improvedEulerLE = new XYChart.Series();
    private XYChart.Series rungeKuttaLE = new XYChart.Series();

    private XYChart.Series eulerTE = new XYChart.Series();
    private XYChart.Series improvedEulerTE = new XYChart.Series();
    private XYChart.Series rungeKuttaTE = new XYChart.Series();

    private ExactSolution exactSol = new ExactSolution(MinX, MaxX, Y0, N);
    private NumericalMethod eulerSol = new EulerSolution(MinX, MaxX, Y0, N, "Euler");
    private NumericalMethod improvedEulerSol = new ImprovedEulerSolution(MinX, MaxX, Y0, N, "improved Euler");
    private NumericalMethod rungeKuttaSol = new RungeKuttaSolution(MinX, MaxX, Y0, N, "Runge-Kutta");

    @FXML
    private LineChart<Number, Number> plots;

    @FXML
    private LineChart<Number, Number> localError;

    @FXML
    private LineChart<Number, Number> totalError;

    @FXML
    private Slider sliderX0;

    @FXML
    private Slider sliderXN;

    @FXML
    private Slider sliderY0;

    @FXML
    private Slider sliderN;

    @FXML
    private Slider sliderN1;

    @FXML
    private Slider sliderN2;

    @FXML
    private Text textX0;

    @FXML
    private Text textXN;

    @FXML
    private Text textY0;

    @FXML
    private Text textN;

    @FXML
    private Text textN1;

    @FXML
    private Text textN2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rungeKutta.setName("Runge-Kutta");
        plots.getData().add(rungeKutta);
        improvedEuler.setName("Improved Euler");
        plots.getData().add(improvedEuler);
        euler.setName("Euler");
        plots.getData().add(euler);
        exact.setName("Exact");
        plots.getData().add(exact);

        rungeKuttaLE.setName("Runge-Kutta");
        localError.getData().add(rungeKuttaLE);
        improvedEulerLE.setName("Improved Euler");
        localError.getData().add(improvedEulerLE);
        eulerLE.setName("Euler");
        localError.getData().add(eulerLE);

        rungeKuttaTE.setName("Runge-Kutta");
        totalError.getData().add(rungeKuttaTE);
        improvedEulerTE.setName("Improved Euler");
        totalError.getData().add(improvedEulerTE);
        eulerTE.setName("Euler");
        totalError.getData().add(eulerTE);

        updateGraph();
    }

    private boolean newValues() {
        int newMinX = (int) sliderX0.getValue();
        int newMaxX = (int) sliderXN.getValue();
        int newY0 = (int) sliderY0.getValue();
        int newN = (int) sliderN.getValue();
        int newN1 = (int) sliderN1.getValue();
        int newN2 = (int) sliderN2.getValue();
        if (MinX == newMinX && MaxX == newMaxX && Y0 == newY0
                && N == newN && N1 == newN1 && N2 == newN2) {
            return false;
        }

        MinX = newMinX;
        MaxX = newMaxX;
        Y0 = newY0;
        N = newN;
        N1 = newN1;
        N2 = newN2;
        System.out.println("update to " + MinX + " " + MaxX + " " + Y0 + " " + N + " " + N1 + " " + N2);
        return true;
    }


    @FXML
    public void updateGraph() {
        if (!newValues())
            return;

        textX0.setText("X0: " + MinX);
        textXN.setText("XN: " + MaxX);
        textY0.setText("Y0: " + Y0);
        textN.setText("N: " + N);
        textN1.setText("N1: " + N1);
        textN2.setText("N2: " + N2);

        exactSol.update(MinX, MaxX, Y0, N);
        exactSol.updateXYChart(exact);

        eulerSol.update(MinX, MaxX, Y0, N);
        eulerSol.updateXYChart(euler);

        improvedEulerSol.update(MinX, MaxX, Y0, N);
        improvedEulerSol.updateXYChart(improvedEuler);

        rungeKuttaSol.update(MinX, MaxX, Y0, N);
        rungeKuttaSol.updateXYChart(rungeKutta);

        exactSol.updateLocalErrorXYChart(eulerLE, eulerSol);
        exactSol.updateLocalErrorXYChart(improvedEulerLE, improvedEulerSol);
        exactSol.updateLocalErrorXYChart(rungeKuttaLE, rungeKuttaSol);

        exactSol.updateTotalErrorXYChart(eulerTE, eulerSol, N1, N2);
        exactSol.updateTotalErrorXYChart(improvedEulerTE, improvedEulerSol, N1, N2);
        exactSol.updateTotalErrorXYChart(rungeKuttaTE, rungeKuttaSol, N1, N2);
    }
}
