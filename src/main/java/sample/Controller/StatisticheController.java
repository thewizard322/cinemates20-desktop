package sample.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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

    private void inizializzaBarChart(List<Statistica> statisticaList){
        XYChart.Series<String,Integer> series = new XYChart.Series<String,Integer>();
        for(Statistica statistica : statisticaList) {
            XYChart.Data<String,Integer> data = new XYChart.Data<String, Integer>(statistica.getNome(), statistica.getValore());
            series.getData().add(data);
        }
        barChartStatistiche.setAnimated(false);
        barChartStatistiche.getData().add(series);
        for(final XYChart.Data<String,Integer> data : series.getData()){
            Tooltip tooltip = new Tooltip();
            tooltip.setText(data.getYValue().toString());
            Tooltip.install(data.getNode(),tooltip);
        }
    }

    private void initializeLabels(List<Statistica> statisticaList){
        for(Statistica statistica : statisticaList){
            if(statistica.getNome().equals("film piu apprezzato"))
                labelFilmPiuApprezzato.setText(statistica.getValoreStringa());
            else if(statistica.getNome().equals("film meno visto"))
                labelFilmPiuInHype.setText(statistica.getValoreStringa());
        }
    }

}
