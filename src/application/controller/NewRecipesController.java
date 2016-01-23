package application.controller;

import application.Ingredient;
import application.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.*;


/**
 * Created by haxxflaxx on 2015-11-03.
 */

public class NewRecipesController implements Initializable{


    MainController mainController;
    private String recipeID;
    private ObservableList<Ingredient> items = FXCollections.observableArrayList();


    @FXML
    public TextField recipeName;
    @FXML
    private TextField recipeType;
    @FXML
    private TextField recipeCuisine;
    @FXML
    private TextField recipeDifficulty;
    @FXML
    private TextField recipeDiet;
    @FXML
    private TextField recipeTime;
    @FXML
    private TextArea recipeDescription;
    @FXML
    private ListView recipeIngredients;
    @FXML
    private Button recipeSubmit;
    @FXML
    private TextField ingredientAmount;
    @FXML
    private ChoiceBox ingredientUnit;
    @FXML
    private TextField ingredientSearch;
    @FXML
    private Button addIngredients;
    @FXML
    private TableView addedIngredientTable;
    @FXML
    private TableColumn Name;
    @FXML
    private TableColumn Amount;
    @FXML
    private TableColumn Unit;

    /**
     * Updates updateEditRecipeList and UpdateIngredients when loading vista.
     * Sets cellValueFactory with the column names for the tableview.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialize EditRecipesController");
        mainController = VistaNavigator.getMainController();
        updateIngredients();
        Name.setCellValueFactory(
                new PropertyValueFactory<Ingredient, String>("name")
        );
        Amount.setCellValueFactory(
                new PropertyValueFactory<Ingredient, String>("amount")
        );
        Unit.setCellValueFactory(
                new PropertyValueFactory<Ingredient, String>("unit")
        );
        System.out.println("- End of Initialize EditRecipesController");
    }

    /**
     * ButtonMethod for updating data into the recipes
     */
    public void SubmitButtonAction() {
        String values = "";
        String columns = "Name, Type, Cuisine, Difficulty, Diet, Time, Description";
        String[] fields = {recipeName.getText(), recipeType.getText(), recipeCuisine.getText(), recipeDifficulty.getText(),
                recipeDiet.getText(), recipeTime.getText(), recipeDescription.getText()};//Array with Values

        for (String value : fields) values += "'" + value + "',";
        values = values.substring(0, values.length()-1);

        try {
            System.out.println("- SubmitButtonAction");
            insertInto("Recipes", columns, values);                   //Update data in columns with values
            System.out.println("- End of SubmitButtonAction");

            recipeID = fetchData("recipes", "ID", "Name='" + recipeName.getText() + "'").get(0).get(0);

            for (Object o : addedIngredientTable.getItems()) {                      //Foreach object in the list, getItems
                String iName = Name.getCellData(o).toString();                      //iName = ingredientName
                String iAmount = Amount.getCellData(o).toString();                  //iAmount = ingredientAmount
                String iUnit = Unit.getCellData(o).toString();                      //iUnit = ingredientUnit

                System.out.println("TESTING" + iName);

                String currentId = fetchData("Ingredients", "ID", "Name='" + iName + "'").toString();   //Fetches id where name
                currentId = currentId.replaceAll("\\[", "").replaceAll("\\]", "");                      //is column-name

                System.out.println("RECIPE ID"+ recipeID);
                System.out.println("Current ID"+ currentId);

                insertInto("RUI", "RID, IID, Quantity, Unit", "'" + recipeID + "','" + currentId + "','" + iAmount + "','" + iUnit + "'");

                                                                                    //Inserts recipe id, current id,
                }                                                                   //amount, and unit
            }
         catch (SQLException e) {
            e.printStackTrace();
        }

        Recipe.setSelectedByID(recipeID);
        VistaNavigator.loadVista(
                VistaNavigator.RECIPE
        );
    }

    /**
     * Loads the name of all ingredients and puts them in ingredientlist.
     */
    public void updateIngredients() {
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> itemList = FXCollections.observableArrayList();      //Observable arraylist for the listview

        try {
            System.out.println("- Main updateIngredientsRecipList");

            dataSet = fetchData("Ingredients", "Name");                             //Gives the arraylist the ingredientnames

            for (ArrayList<String> element : dataSet) {
                itemList.add(element.get(0));                                       //For every element in the array, get name
            }

            System.out.println("- end of Main updateIngredientsRecipList");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recipeIngredients.setItems(itemList);                                       //Sets the Listview to show the obs arraylist

        itemList.clear();
        try {
            dataSet = fetchData("Units", "*");

            for (ArrayList<String> element: dataSet){
                itemList.add(element.get(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ingredientUnit.setItems(itemList);
    }


    /**
     * ButtonMethod for add ingredients from the ingredientList into the tableview
     */
    public void addIngredientButton() {

        String selectedIngredient = recipeIngredients.getSelectionModel().getSelectedItems().toString();
        selectedIngredient = selectedIngredient.replaceAll("\\[", "").replaceAll("\\]", "");

        items.add(new Ingredient(selectedIngredient, ingredientAmount.getText(), ingredientUnit.getSelectionModel().getSelectedItem().toString()));    //Creates new
        addedIngredientTable.setItems(items);               //Object of the type Ingredient and adds it to the tableView.

    }

    /**
     * Searchfield for searching ingredients in the ingredientList
     */
    public void updateIngredientSearch(){
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> itemList = FXCollections.observableArrayList();
        String condition = "Name LIKE '" +"%" + ingredientSearch.getText() + "%'";
        System.out.println("Condition " + condition);

        try {

            dataSet = fetchData("Ingredients", "Name", condition);

            for (ArrayList<String> element : dataSet){
                itemList.add(element.get(0));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recipeIngredients.setItems(itemList);               //Sets the listview to show the ingredients that matches the search
    }                                                       //Criteria
}
