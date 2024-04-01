package com.example.fyp_application.Controllers.Admin.DashboardControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Utils.ConfigPropertiesUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.*;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OverviewStatisticsController implements Initializable {

    @FXML
    private BarChart<String, Number> assetCondition_chart;

    @FXML
    private AnchorPane contentAP;

    @FXML
    private AnchorPane dashboardStatAP;


    @FXML
    private LineChart<String,Number> ticketVolume_chart;

    @FXML
    private Label totalBreachedCounter_lbl;

    @FXML
    private Label totalOverdueCollection_lbl;

    @FXML
    private Label totalRemainingBudget_lbl;

    @FXML
    private Label totalUserCount_lbl;

    @FXML
    private PieChart userPieChart;


    @FXML
    private LineChart<String,Number>procurementLineChart;
    @FXML
    private DatePicker sampleDP;


    @FXML
    private Label editableBreachCall_lbl;

    @FXML
    private ChoiceBox<String> yearCB;

    @FXML
    private void setMiniDashboard() {
        totalUserCount_lbl.setText(String.valueOf(UserDAO.countAllUsers()));
        totalRemainingBudget_lbl.setText(String.valueOf(ConfigPropertiesUtils.getPropertyValue("DEPARTMENT_BUDGET")));
        totalOverdueCollection_lbl.setText(String.valueOf(AssetAllocationDAO.countTotalOverdueAssets()));
        totalBreachedCounter_lbl.setText(String.valueOf(TicketDAO.countTotalBreachedTicketsForMonth(LocalDate.now())));
        System.out.println(LocalDate.now());
    }



    @FXML
    private void setUserPieChart() {
        int activeUser = UserDAO.countActiveUsers();
        int inactiveUser = UserDAO.countInactiveUsers();
        int expiredUser = UserDAO.countExpiredUsers();

        // Creating the pie chart data

        PieChart.Data inactiveData = new PieChart.Data("Inactive", inactiveUser);
        PieChart.Data activeData = new PieChart.Data("Active", activeUser);
        PieChart.Data expiredData = new PieChart.Data("Expired", expiredUser);

        userPieChart.getData().addAll(inactiveData,expiredData, activeData);

    }

    private void setAssetBarChart() {
        // Asset counts
        int excellentAssetConditionTotal = AssetDAO.countExcellentAssetCondition();
        int goodAssetConditionTotal = AssetDAO.countGoodAssetCondition();
        int fairAssetConditionTotal = AssetDAO.countFairAssetCondition();
        int poorAssetConditionTotal = AssetDAO.countPoorAssetCondition();

        // Axes setup
        CategoryAxis xAxis = (CategoryAxis) assetCondition_chart.getXAxis();
        NumberAxis yAxis = (NumberAxis) assetCondition_chart.getYAxis();

        // Series and data points
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        XYChart.Data<String, Number> excellentData = new XYChart.Data<>("Excellent", excellentAssetConditionTotal);
        XYChart.Data<String, Number> goodData = new XYChart.Data<>("Good", goodAssetConditionTotal);
        XYChart.Data<String, Number> fairData = new XYChart.Data<>("Fair", fairAssetConditionTotal);
        XYChart.Data<String, Number> poorData = new XYChart.Data<>("Poor", poorAssetConditionTotal);

        xAxis.setLabel("Asset Condition");
        yAxis.setLabel("Number of Assets");


        series.getData().addAll(excellentData, goodData, fairData, poorData);

        assetCondition_chart.getData().add(series);

        // Remove the legend
        assetCondition_chart.setLegendVisible(false);

        // This ensures that styles are applied after the chart has been fully rendered
        Platform.runLater(() -> applyStylesToChartNodes(series));
    }

    private void applyStylesToChartNodes(XYChart.Series<String, Number> series) {
        for (XYChart.Data<String, Number> data : series.getData()) {
            final Node node = data.getNode();
            if (node != null) {
                // Watch for the node to be created
                node.getStyleClass().add(getCssClassForCategory(data.getXValue()));
            }
        }
    }

    private String getCssClassForCategory(String category) {
        return switch (category) {
            case "Excellent" -> "excellent-condition";
            case "Good" -> "good-condition";
            case "Fair" -> "fair-condition";
            case "Poor" -> "poor-condition";
            default -> "";
        };
    }


    @FXML
    private void refreshChart() {
        ticketVolume_chart.getData().clear();
        CategoryAxis xAxis = (CategoryAxis) ticketVolume_chart.getXAxis();
        xAxis.getCategories().clear(); // Clear existing categories

        CategoryAxis yAxis = (CategoryAxis) ticketVolume_chart.getXAxis();
        yAxis.getCategories().clear(); // Clear existing categories

        sampleDP.setValue(null);

    }

    public void setMonthlyTicketVolumeChart() {
       ticketVolume_chart.getData().clear();
        List<XYChart.Data<String, Number>> dataPoints = TicketDAO.getTicketVolumeForMonth(sampleDP.getValue());

        System.out.println(dataPoints);
        if (dataPoints.isEmpty()) {
            return;
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(dataPoints);

        CategoryAxis xAxis = (CategoryAxis) ticketVolume_chart.getXAxis();
        xAxis.getCategories().clear(); // Clear existing categories

        List<String> dateCategories = dataPoints.stream()
                .map(XYChart.Data::getXValue)
                .distinct()
                .collect(Collectors.toList());
        xAxis.setCategories(FXCollections.observableArrayList(dateCategories));

        ticketVolume_chart.getXAxis().setLabel("Month");
        ticketVolume_chart.getYAxis().setLabel("Number of Tickets");
        ticketVolume_chart.setLegendVisible(false);
        ticketVolume_chart.layout(); //
        ticketVolume_chart.getData().add(series);
    }

    @FXML
    private void dateFilterSelector() {

        if(sampleDP.getValue() == null) {
            return;
        }
        setMonthlyTicketVolumeChart();

    }

    public void setMonthlySpendingChart(String inputYear) {
        procurementLineChart.getData().clear();



        LocalDate startDate = LocalDate.of(Integer.parseInt(inputYear), 1, 1); // Example start date
        LocalDate endDate = LocalDate.now(); // Current date as the end date
        List<XYChart.Data<String, Number>> monthlySpending = ProcurementBasketDAO.getMonthlyProcurementSpending(startDate, endDate);

        System.out.println(monthlySpending);
        if (monthlySpending.isEmpty()) {
            return;
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(monthlySpending);

        CategoryAxis xAxis = (CategoryAxis) procurementLineChart.getXAxis();
        xAxis.getCategories().clear(); // Clear existing categories



        List<String> dateCategories = monthlySpending.stream()
                .map(XYChart.Data::getXValue)
                .distinct()
                .collect(Collectors.toList());

        if (dateCategories.isEmpty()) {
            xAxis = (CategoryAxis) procurementLineChart.getXAxis();
            xAxis.getCategories().clear(); // Clear existing categories

            return;
        }
        xAxis.setCategories(FXCollections.observableArrayList(dateCategories));

        procurementLineChart.getXAxis().setLabel("Month");
        procurementLineChart.getYAxis().setLabel("Spending");
        procurementLineChart.setLegendVisible(false);
        procurementLineChart.layout();
        procurementLineChart.getData().add(series);
    }

/*

    public void displayMonthlyProcurementSpending() {
        LocalDate startDate = LocalDate.now().minusMonths(3); // Adjust based on your needs
        LocalDate endDate = LocalDate.now();
        List<XYChart.Data<String, Number>> dataPoints = ProcurementBasketDAO.getMonthlyProcurementSpending(startDate);

        System.out.println(dataPoints);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Procurement Spending");

        LocalDate current = startDate.withDayOfMonth(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        while (!current.isAfter(endDate)) {
            String month = current.format(formatter);
            Optional<XYChart.Data<String, Number>> dataPoint = dataPoints.stream()
                    .filter(dp -> dp.getXValue().equals(month))
                    .findFirst();

            // Add the data point if it exists, otherwise add a point with value 0
            series.getData().add(dataPoint.orElseGet(() -> new XYChart.Data<>(month, 0)));

            current = current.plusMonths(1);
        }

        // Setting the chart data
        procurementLineChart.getData().clear(); // Clear any existing data
        procurementLineChart.getData().add(series);

        // Optional: Customize the X-axis to improve readability
        CategoryAxis xAxis = (CategoryAxis) procurementLineChart.getXAxis();
        xAxis.setLabel("Month");
        xAxis.setTickLabelRotation(45); // Rotate labels for better fit
    }

*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up your chart initially


        yearCB.setItems(FXCollections.observableArrayList("2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"));
        yearCB.setValue("2021");
        setMonthlySpendingChart(yearCB.getValue());
        setMiniDashboard();
        setUserPieChart();
        setAssetBarChart();

        // Set the date picker value change listener


        yearCB.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                setMonthlySpendingChart(newValue);
            }
        });

        sampleDP.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                totalBreachedCounter_lbl.setText(String.valueOf(TicketDAO.countTotalBreachedTicketsForMonth(newValue)));
                int monthNumber = newValue.getMonthValue(); // Get the month number
                String monthName = Month.of(monthNumber).getDisplayName(TextStyle.FULL, Locale.getDefault()); // Get the month name
                editableBreachCall_lbl.setText("Total Breach Call On " + monthName);
            }
        });
    }
}


