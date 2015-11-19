package application.controller;

import application.Recipe;
import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by haxxflaxx on 2015-11-03.
 */
public class RecipeController extends NavigationController implements Initializable {

    private Recipe recipe;

    @FXML    private Label recipeName;
    @FXML    private Label recipeType;
    @FXML    private Label recipeCuisine;
    @FXML    private Label recipeDifficulty;
    @FXML    private Label recipeTime;
    @FXML    private Label recipeDiet;
    @FXML    private TextArea recipeDescription;
    @FXML    private ListView recipeIngredients;
    @FXML    private Button editRecipe;



    /**
     * On view creation:
     * Get controller for main view.
     * Run update.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("- Initialize RecipeController");
        recipe = Recipe.getSelected();
        updateContent();
        updateIngredientList();
        System.out.println("- End of Initialize RecipeController");
    }


    /**
     * Fetches data from the database based on selected item in
     * MainController.recipList and puts the data into the fields for the view.
     */
    private void updateContent(){

        System.out.println("- Recipe updateContent");

        recipeName.setText(recipe.getName());
        recipeType.setText(recipe.getType());
        recipeCuisine.setText(recipe.getCuisine());
        recipeDifficulty.setText(recipe.getDifficulty());
        recipeTime.setText(recipe.getTime());
        recipeDiet.setText(recipe.getDiet());
        recipeDescription.setText(recipe.getDescription());

        System.out.println("- End of Recipe updateContent");

    }

    /**
     *  Method for updating the Ingredients in the RecipeView
     */
    private void updateIngredientList() {
        ArrayList<ArrayList<String>> dataSet;                     //Arraylist for storing the ingredients
        String condition = "RUI.IID = ingredients.ID AND RID = '" + recipe.getId() + "'";
        ObservableList<String> itemList = FXCollections.observableArrayList();

        try {
            System.out.println("- UpdateIngredientList");
            dataSet = Query.fetchData("RUI, ingredients", "Name, Quantity, Unit", condition);

            for (ArrayList<String> element : dataSet){            //Adds an element for every item in the dataset
                itemList.add(element.get(0)+ "  " + element.get(1)+ "  " + element.get(2));
                }
            recipeIngredients.setItems(itemList);                 //Sets the listview as the observable arraylist
            System.out.println("- End of UpdateIngredientList");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  ButtonMethod for loading the editRecipes.
     */
    public void editRecipeView(){
        System.out.println("- Load editRecipeview");
        VistaNavigator.loadVista(
                VistaNavigator.EDITRECIPES);                       //Load editRecipesView
        System.out.println("- end of Load editRecipeview");
    }

    @Override
    public String getFxml() {
        return VistaNavigator.RECIPE;
    }
}
