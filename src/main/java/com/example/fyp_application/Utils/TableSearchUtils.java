package com.example.fyp_application.Utils;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import java.util.function.BiPredicate;


public class TableSearchUtils {

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



}