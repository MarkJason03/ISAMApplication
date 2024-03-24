package com.example.fyp_application.Controllers.Admin.DashboardControllers;

import com.example.fyp_application.Model.UserDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class AdminHomePageController implements Initializable {



    @FXML
    private AnchorPane contentAP;

    @FXML
    private AnchorPane dashboardStatAP;

    @FXML
    private PieChart userPieChart;

    @FXML
    private LineChart<String,Number> sampleLineChart;


    @FXML
    private BarChart<String,Number> sampleBarChart;
    @FXML
    private void setUserPieChart() {
        int activeUser = UserDAO.countActiveUsers();
        int inactiveUser = UserDAO.countInactiveUsers();
        int expiredUser = UserDAO.countExpiredUsers();

        // Creating the pie chart data
        PieChart.Data activeData = new PieChart.Data("Active", activeUser);
        PieChart.Data inactiveData = new PieChart.Data("Inactive", inactiveUser);
        PieChart.Data expiredData = new PieChart.Data("Expired", expiredUser);

        userPieChart.setTitle("User Status");
        userPieChart.getData().addAll(activeData, inactiveData, expiredData);

    }

    @FXML
    private void setUserLineChart() {
        // Assuming we're setting this up for three time points: T1, T2, and T3
        // In your real application, these would be actual data points like months or years
        String[] timePoints = {"T1", "T2", "T3"};

        // Example user counts at three different time points
        // For demonstration, using static data; replace these with your actual data retrieval
        int[][] userCounts = {
                {UserDAO.countActiveUsers(), 120, 130}, // Simulated counts for active users over time
                {UserDAO.countInactiveUsers(), 80, 90}, // Simulated counts for inactive users
                {UserDAO.countExpiredUsers(), 50, 60}   // And for expired users
        };

        XYChart.Series<String, Number> seriesActive = new XYChart.Series<>();
        seriesActive.setName("Active Users");

        XYChart.Series<String, Number> seriesInactive = new XYChart.Series<>();
        seriesInactive.setName("Inactive Users");

        XYChart.Series<String, Number> seriesExpired = new XYChart.Series<>();
        seriesExpired.setName("Expired Users");

        for (int i = 0; i < timePoints.length; i++) {
            seriesActive.getData().add(new XYChart.Data<>(timePoints[i], userCounts[0][i]));
            seriesInactive.getData().add(new XYChart.Data<>(timePoints[i], userCounts[1][i]));
            seriesExpired.getData().add(new XYChart.Data<>(timePoints[i], userCounts[2][i]));
        }

        sampleLineChart.getData().addAll(seriesActive, seriesInactive, seriesExpired);
    }


    @FXML
    private void setUserBarChart() {
        int activeUser = UserDAO.countActiveUsers();
        int inactiveUser = UserDAO.countInactiveUsers();
        int expiredUser = UserDAO.countExpiredUsers();

        System.out.println("Active users: " + activeUser);
        System.out.println("Inactive users: " + inactiveUser);
        System.out.println("Expired users: " + expiredUser);

        CategoryAxis xAxis = (CategoryAxis) sampleBarChart.getXAxis();
        xAxis.setLabel("User Status");

        NumberAxis yAxis = (NumberAxis) sampleBarChart.getYAxis();
        yAxis.setLabel("User Count");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Account Status");

        series.getData().add(new XYChart.Data<>("Active", activeUser));
        series.getData().add(new XYChart.Data<>("Inactive", inactiveUser));
        series.getData().add(new XYChart.Data<>("Expired", expiredUser));
        xAxis.getCategories().addAll("Active", "Inactive", "Expired");

        sampleBarChart.getData().clear();
        sampleBarChart.layout();
        sampleBarChart.getData().add(series);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //loadFlowPaneData();
        Platform.runLater(this::setUserPieChart);
        Platform.runLater(this::setUserLineChart);
        setUserBarChart();

    }


}
