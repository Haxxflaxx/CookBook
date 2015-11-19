package application.controller;


import application.Recipe;
import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Pierre on 2015-11-18.
 */
public class BrowseController implements Initializable {

    @FXML private TableView recipeTable;
    @FXML TableColumn id;
    @FXML TableColumn name;
    @FXML TableColumn type;
    @FXML TableColumn diet;
    @FXML TableColumn cuisine;
    @FXML TableColumn difficulty;
    @FXML TableColumn prepTime;


    @Override

    public void initialize(URL location, ResourceBundle resources){
        id.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("id")
        );
        name.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("name")
        );
        type.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("type")
        );
        diet.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("diet")
        );
        cuisine.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("cuisine")
        );
        difficulty.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("difficulty")
        );
        prepTime.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("time")
        );

        updateResultTable();
    }

    private void updateResultTable(){
        ArrayList<ArrayList<String>> dataSet = new ArrayList<>();
        ObservableList<Recipe> items = FXCollections.observableArrayList();

        try {
            dataSet = Query.fetchData("recipes", "*");

            for (ArrayList<String> element : dataSet){
                items.add(new Recipe(
                                element.get(0),
                                element.get(1),
                                element.get(2),
                                element.get(3),
                                element.get(4),
                                element.get(5),
                                element.get(6),
                                element.get(7),
                                element.get(8),
                                element.get(9)
                        )
                );
            }

            recipeTable.setItems(items);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
