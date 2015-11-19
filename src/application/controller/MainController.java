package application.controller;

import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

import javax.swing.text.TableView;
import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.insertInto;

/**
 * Main controller class for the layout.
 */
public class MainController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("- Intialize mainController");
        updateRecipList();
        recipList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            VistaNavigator.loadVista(
                    VistaNavigator.RECIPE
            );
            System.out.println("- End of Initialize mainController");
        });
    }

    /** Holder of a switchable vista. */
    @FXML private StackPane vistaHolder;

    /** List holding the recipe names from the DB. */
    @FXML protected ListView recipList;

    /** Field for entering search criteria. */
    @FXML private TextField recipSearch;

    /** Holder of Login view */
    @FXML private StackPane loginHolder;

    @FXML private TableView recipeTable;

    /**
     * Replaces the vista displayed in the vista holder with a new vista.
     *
     * @param node the vista node to be swapped in.
     */
    public void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }

    public void setLogin(Node node){
        loginHolder.getChildren().setAll(node);
    }

    public String getSearch(){
        return recipSearch.getText();
    }


    /**
     * Loads the name of all recipes and puts them into recipList.
     */
    public void updateRecipList(){
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> itemList = FXCollections.observableArrayList();

        try {
            System.out.println("- Main updateRecipList");

            dataSet = Query.fetchData("recipes", "Name");

            for (ArrayList<String> element : dataSet){
                itemList.add(element.get(0));
            }

            System.out.println("- end of Main updateRecipList");
        } catch (SQLException e) {
            e.printStackTrace();
        }
            recipList.setItems(itemList);
    }

    /**
     * Loads the name of all recipes and puts them into recipList.
     *
     * @param search input for selection based on name with wildcard at the end.
     */
    public void updateRecipList(String search){
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> itemList = FXCollections.observableArrayList();
        String condition = "Name LIKE '" +"%" + search + "%'";
        System.out.println(condition);

        try {
            System.out.println("- updateRecipList");
            dataSet = Query.fetchData("recipes", "Name", condition);

            for (ArrayList<String> element : dataSet){
                itemList.add(element.get(0));
                System.out.println("- End of updateRecipList");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recipList.setItems(itemList);
    }

    /**
     * Run search recipe based on name.
     */
    public void search(){
        VistaNavigator.loadVista(
                VistaNavigator.SEARCH
        );
    }

    /**
     * Button for adding new Recipes.
     */
    public void addNewRecipeclick(){

        try {
            System.out.println("- addNewRecipeButton");
            insertInto("Recipes", "Name", "'--New--'");
            updateRecipList();
            recipList.getSelectionModel().select("--New--");
            System.out.println("- End of addNewRecipeButton");
    }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigation method for going back to a previous view
     */
    public void back(){
        System.out.println(VistaNavigator.getActiveController());
        VistaNavigator.moveBack();
        System.out.println(VistaNavigator.getActiveController());
        VistaNavigator.reloadVista();
    }

    /**
     * Navigation method for going forward to a previous view
     */
    public void forward(){
        System.out.println(VistaNavigator.getActiveController());
        VistaNavigator.moveForward();
        System.out.println(VistaNavigator.getActiveController());
        VistaNavigator.reloadVista();
    }

    /**
     *  The browser menu
     */
    public void allRecipesclick(){
        VistaNavigator.loadVista(
                VistaNavigator.WELCOME
        );

        System.out.println("Load All Recipes");

    }

    public void topRatedclick(){
        System.out.println("Load Top Rated Recipes");
    }

    public void newRecipesclick(){
        System.out.println("Load New Recipes");
    }

    public void myRecipesclick(){
        System.out.println("Load My Recipes");
    }

}

