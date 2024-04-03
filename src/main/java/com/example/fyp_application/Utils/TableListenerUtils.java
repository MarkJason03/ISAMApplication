package com.example.fyp_application.Utils;

import com.example.fyp_application.Model.MessageHistoryModel;
import com.example.fyp_application.Model.TicketAttachmentModel;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.util.Iterator;

public class TableListenerUtils {

    // Search for asset details
    // Using a BiPredicate to compare the asset details with the search bar input
    // Using <T> to allow for any type of TableView to be passed in
    public static <T> void searchTableDetails(
            TextField searchBar,
            TableView<T> tableViewTable,
            ObservableList<T> observableList,
            BiPredicate<T, String> filterCondition) {
        // Listen for changes in the search bar
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            // If the search bar is empty, show all data
            if (newValue == null || newValue.isEmpty()) {
                tableViewTable.setItems(observableList); // Reset to show all data
            } else {
                // If the search bar is not empty, filter the data
                ObservableList<T> filteredList = FXCollections.observableArrayList();
                // Loop through the data and add the items that match the search to the filtered list
                for (T item : observableList) {
                    // If the item matches the search, add it to the filtered list
                    if (filterCondition.test(item, newValue)) {
                        filteredList.add(item);
                    }
                }
                tableViewTable.setItems(filteredList);
            }
        });
    }




    public static void addDoubleClickHandlerToAttachmentTable(TableView<TicketAttachmentModel> table, Consumer<String> onDoubleClick) {
        table.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                String selectedItem = table.getSelectionModel().getSelectedItem().getFilePath();
                onDoubleClick.accept(selectedItem);
            }
        });
    }

    public static void addDoubleClickHandlerToMessageHistoryTable(TableView<MessageHistoryModel> table, Consumer<Integer> onDoubleClick) {
        table.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                int selectedItem = table.getSelectionModel().getSelectedItem().getMessageID();
                onDoubleClick.accept(selectedItem);
            }
        });
    }


    public static void exportTableViewToExcel(TableView<?> tableView) throws IOException {
        // Create a Workbook
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create a Sheet
            Sheet sheet = workbook.createSheet("Data");

            // Create the header row
            Row headerRow = sheet.createRow(0);

            // Populate the header row
            for (int columnLength = 0; columnLength < tableView.getColumns().size(); columnLength++) {
                Cell cell = headerRow.createCell(columnLength);
                cell.setCellValue(tableView.getColumns().get(columnLength).getText());
            }

            // Populate the data rows
            // Loop through the items in the table view
            for (int itemRow = 0; itemRow < tableView.getItems().size(); itemRow++) {
                Row dataRow = sheet.createRow(itemRow + 1);

                // Loop through the columns in the table view
                for (int columnRow = 0; columnRow < tableView.getColumns().size(); columnRow++) {
                    Cell cell = dataRow.createCell(columnRow);
                    // Get the cell value
                    Object cellValue = tableView.getColumns().get(columnRow).getCellObservableValue(itemRow).getValue();
                    // Set the cell value
                    if (cellValue != null) {
                        cell.setCellValue(cellValue.toString());
                    } else {
                        // If the cell value is null, set the cell value to an empty string
                        cell.setCellValue("");
                    }
                }
            }

            // Autosize columns
            for (int columnIndex = 0; columnIndex < tableView.getColumns().size(); columnIndex++) {
                sheet.autoSizeColumn(columnIndex);
            }

            File reportsDir = new File("src/main/resources/reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs(); // Create the directory if it doesn't exist
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String now = LocalDateTime.now().format(dtf);

            File reportFile = new File(reportsDir, "reportDemo_" + now + ".xlsx");

            // Write the output to a file
            try (FileOutputStream fileOut = new FileOutputStream(reportFile)) {
                workbook.write(fileOut);
            } catch (FileNotFoundException e) {
                workbook.close();
                AlertNotificationUtils.showErrorMessageAlert("Error Exporting Data", "Transaction Cancelled");
            }
        }
    }
}