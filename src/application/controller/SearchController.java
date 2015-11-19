package application.controller;

import application.Recipe;
import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Haxxflaxx on 2015-11-06.
 */
public class SearchController extends NavigationController implements Initializable {


    @FXML TableView searchResult;
    @FXML TableColumn id;
    @FXML TableColumn name;
    @FXML TableColumn type;
    @FXML TableColumn diet;
    @FXML TableColumn cuisine;
    @FXML TableColumn difficulty;
    @FXML TableColumn prepTime;


    //Search conditions
    String search;
    String nameCondition;
    String typeCondition;
    String dietCondition;
    String cuisineCondition;
    String difficultyCondition;
    String condition;

    /**
     * Get MainController instance and generate a selection condition for query.
     */
    public SearchController() {
        super();

        search = VistaNavigator.getMainController().getSearch();

        String nameCondition = "Name LIKE '%" + search + "%' ";
        String typeCondition = "Type LIKE '%" + search + "%' ";
        String dietCondition = "Diet LIKE '%" + search + "%' ";
        String cuisineCondition = "Cuisine LIKE '%" + search + "%' ";
        String difficultyCondition = "Difficulty LIKE '%" + search + "%';";

        condition = nameCondition + " OR " +
                typeCondition + " OR " +
                dietCondition + " OR " +
                cuisineCondition + " OR " +
                difficultyCondition;
    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        searchResult.setRowFactory(tv -> {
            TableRow<Recipe> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Recipe.setSelected(row.getItem());
                    VistaNavigator.loadVista(
                            VistaNavigator.RECIPE
                    );
                }
            });
            return row;
        });
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

    /**
     * Get data from recipes based on search query.
     * Put data into TableView.
     */
    private void updateResultTable(){
        ArrayList<ArrayList<String>> dataSet = new ArrayList<>();
        ObservableList<Recipe> items = FXCollections.observableArrayList();

        try {
            dataSet = Query.fetchData("recipes", "*", condition);
            System.out.println(condition);

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

            searchResult.setItems(items);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFxml() {
        return VistaNavigator.SEARCH;
    }
}