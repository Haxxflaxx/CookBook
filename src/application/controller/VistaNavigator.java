package application.controller;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Stack;

/**
 * Created by Haxxflaxx on 2015-11-02.
 */


/**
 * Utility class for controlling navigation between vistas.
 *
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 */
public class VistaNavigator {

    /**
     * Constants holding path and name for views.
     */
    public static final String MAIN = "/application/view/mainView.fxml";
    public static final String RECIPE = "/application/view/recipeDetailsVista.fxml";
    public static final String WELCOME = "/application/view/welcomeVista.fxml";
    public static final String SEARCH = "/application/view/searchResultVista.fxml";
    public static final String EDITRECIPES = "/application/view/editRecipesView.fxml";
    public static final String REGISTER = "/application/view/registerVista.fxml";
    public static final String LOGINVISTA = "/application/view/loginVista.fxml";

    /** The main application layout controller. */
    private static MainController mainController;

    /**
     * Holds controllers for navigation history.
     */
    private static Stack<NavigationController> historyBack = new Stack<>();
    private static Stack<NavigationController> historyForward = new Stack<>();
    private static NavigationController activeController = null;

    public static void moveForward() {
        if (!historyForward.empty()) {
            historyBack.push(activeController);
            activeController = historyForward.pop();
        }
    }

    public static void moveBack() {
        if (!historyBack.empty()) {
            historyForward.push(activeController);
            System.out.println(historyBack.peek());
            activeController = historyBack.pop();
            System.out.println(historyBack.peek());
        }
    }

    public static void clearForward() {
        if (!historyForward.empty()) historyForward.clear();
    }

    public static NavigationController getActiveController() {
        return activeController;
    }

    public static void setActiveController(NavigationController activeController) {
        if (activeController != null) historyBack.add(VistaNavigator.activeController);
        VistaNavigator.activeController = activeController;
    }

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param mainController the main application layout controller.
     */
    public static void setMainController(MainController mainController) {
        VistaNavigator.mainController = mainController;
    }

    /**
     * Returns the main controller.
     *
     * @return MainController the main application layout controller.
     */
    public static MainController getMainController(){
        return mainController;
    }

    /**
     * Loads the vista specified by the fxml file into the
     * vistaHolder pane of the main application layout.
     *
     * @param fxml the fxml file to be loaded.
     */
    public static void loadVista(String fxml) {
        try {
            mainController.setVista(

                    FXMLLoader.load(
                            VistaNavigator.class.getResource(
                                    fxml
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the vista specified by the activeController
     * into the vistaHolder pane of the main application layout.
     * Uses activeController as controller.
     */
    public static void reloadVista(){
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setControllerFactory(type -> {
            return activeController; // Let the FXMLLoader handle construction...
        });

        fxmlLoader.setLocation(
                VistaNavigator.class.getResource(
                        activeController.getFxml()
                )
        );

        try {
            mainController.setVista(
                    fxmlLoader.load()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}