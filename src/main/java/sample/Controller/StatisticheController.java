package sample.Controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sample.DAO.StatisticaDAO;
import sample.Model.Statistica;

import java.util.List;

public class StatisticheController {

    @FXML
    BarChart barChartStatistiche;

    @FXML
    Label labelFilmPiuApprezzato;

    @FXML
    Label labelFilmPiuInHype;

    @FXML
    public void initialize(){
        new Thread(){
            public void run(){
                StatisticaDAO statisticaDAO = new StatisticaDAO();
                List<Statistica> statisticaList = statisticaDAO.prelevaStatistiche();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        inizializzaBarChart(statisticaList);
                    }
                });
            }
        }.start();
        new Thread(){
            public void run(){
                StatisticaDAO statisticaDAO = new StatisticaDAO();
                List<Statistica> statisticaList = statisticaDAO.prelevaStatisticheStringhe();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        initializeLabels(statisticaList);
                    }
                });
            }
        }.start();
    }

    private void initializeLabels(List<Statistica> statisticaList){
        for(Statistica statistica : statisticaList){
            if(statistica.getNome().equals("film piu apprezzato"))
                labelFilmPiuApprezzato.setText(statistica.getValoreStringa());
            else if(statistica.getNome().equals("film meno visto"))
                labelFilmPiuInHype.setText(statistica.getValoreStringa());
        }
    }

    private void inizializzaBarChart(List<Statistica> statisticaList){
        XYChart.Series<String,Integer> series = new XYChart.Series<String,Integer>();
        for(Statistica statistica : statisticaList) {
            XYChart.Data<String,Integer> data = new XYChart.Data<String, Integer>(statistica.getNome(), statistica.getValore());
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
                    if (node != null) {
                        displayLabelForData(data);
                    }
                }
            });
            series.getData().add(data);
        }
        barChartStatistiche.setAnimated(false);
        barChartStatistiche.setLegendVisible(false);
        barChartStatistiche.getData().add(series);
        for(final XYChart.Data<String,Integer> data : series.getData()) {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(data.getYValue().toString());
            tooltip.setShowDelay(Duration.millis(400));
            Tooltip.install(data.getNode(), tooltip);
        }
    }

    private void displayLabelForData(XYChart.Data<String, Integer> data) {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
                if (null != parent) {
                    Group parentGroup = (Group) parent;
                    parentGroup.getChildren().add(dataText);
                }
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                dataText.setLayoutX(
                        Math.round(
                                bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                        )
                );
                dataText.setLayoutY(
                        Math.round(
                                bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                        )
                );
            }
        });
    }

}
