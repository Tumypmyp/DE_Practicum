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
    private XYChart.Series exact = new XYChart.Series();
    private XYChart.Series euler = new XYChart.Series();
    private XYChart.Series improvedEuler = new XYChart.Series();
    private XYChart.Series rungeKutta = new XYChart.Series();
    private XYChart.Series eulerLE = new XYChart.Series();
    private XYChart.Series improvedEulerLE = new XYChart.Series();
    private XYChart.Series rungeKuttaLE = new XYChart.Series();

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
        plots.getData().add(exact);
        rungeKutta.setName("Runge-Kutta");
        plots.getData().add(rungeKutta);
        improvedEuler.setName("Improved Euler");
        plots.getData().add(improvedEuler);
        euler.setName("Euler");
        plots.getData().add(euler);


        rungeKuttaLE.setName("Runge-Kutta");
        localError.getData().add(rungeKuttaLE);
        improvedEulerLE.setName("Improved Euler");
        localError.getData().add(improvedEulerLE);
        euler.setName("Euler");
        localError.getData().add(eulerLE);
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
//        System.out.println("update to " + MinX + " " + MaxX + " " + Y0 + " " + N);
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

        ExactSolution exactSol = new ExactSolution(MinX, MaxX, Y0, N);
        exactSol.updateXYChart(exact);

        NumericalMethod eulerSol = new EulerSolution(MinX, MaxX, Y0, N, "Euler");
        eulerSol.updateXYChart(euler);

        NumericalMethod improvedEulerSol = new ImprovedEulerSolution(MinX, MaxX, Y0, N, "improvedEuler");
        improvedEulerSol.updateXYChart(improvedEuler);

        NumericalMethod rungeKuttaSol = new RungeKuttaSolution(MinX, MaxX, Y0, N, "Runge-Kutta");
        rungeKuttaSol.updateXYChart(rungeKutta);
    }
}
