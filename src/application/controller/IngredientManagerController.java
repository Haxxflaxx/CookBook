package application.controller;

import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class for the ingredient manager view.
 * Allows for browsing through ingredients, adding new, updating and removing existing.
 *
 * Created by Daniel.
 */
public class IngredientManagerController extends NavigationController implements Initializable {

    @FXML private ListView<String> ingredientList;
    @FXML private TextField searchField;
    @FXML private TextField nameField;
    @FXML private TextField typeField;
    @FXML private TextField unitField;

    private String id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateIngredientList();

    }

    public void updateIngredientList() {
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> items = FXCollections.observableArrayList();
        String condition;

        condition = "Name LIKE '%" + searchField.getText() + "%'";

        try {
            dataSet = Query.fetchSorted("ingredients", "name", "name", condition);
            for (ArrayList<String> element : dataSet) items.add(element.get(0));
            ingredientList.setItems(items);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadIngredientData() {
        ArrayList<ArrayList<String>> dataset;
        String condition;

        condition = "Name='" + ingredientList.getSelectionModel().getSelectedItem() + "'";

        try {
            dataset = Query.fetchData("ingredients", "*", condition);

            id = dataset.get(0).get(0);
            nameField.setText(dataset.get(0).get(1));
            typeField.setText(dataset.get(0).get(2));
            unitField.setText(dataset.get(0).get(3));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSubmit() {
        String condition;
        String selected = ingredientList.getSelectionModel().getSelectedItem();
        condition = "Name='" + nameField.getText() + "'";

        if (selected == nameField.getText()){
            String[] columns = {"Name", "Type", "BaseUnit"};
            String[] values = {nameField.getText(), typeField.getText(), unitField.getText()};
            System.out.println("Submit");
            try {
                Query.updateData("ingredients", columns, values, condition);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            String values = "'" + nameField.getText() + "', " +
                    "'" + typeField.getText() + "', " +
                    "'" + unitField.getText() + "'";

            try {
                System.out.println("Add");
                Query.insertInto("ingredients", "Name, Type, BaseUnit", values);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        updateIngredientList();
    }

    public void remove() {
        String condition;
        condition = "Name='" + nameField.getText() + "'";

        try {
            if (Query.fetchData("RUI", "IID", "IID='" + id + "'").size() == 0)
            Query.deleteFrom("ingredients", condition);

            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ingredient in use");
                alert.setHeaderText("The selected ingredient is used by one or more recipes");
                alert.setContentText("Do you want to delete it anyway?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Query.deleteFrom("RUI", "IID='" + id + "'");
                    Query.deleteFrom("ingredients", condition);
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateIngredientList();
    }

    @Override
    public String getFxml() {
        return VistaNavigator.INGREDIENTMGR;
    }
}
