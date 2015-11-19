package application.controller;

/**
 * Created by haxxflaxx on 2015-11-17.
 */
public abstract class NavigationController {

    public NavigationController(){
        VistaNavigator.setActiveController(this);
        VistaNavigator.clearForward();
    }

    public abstract String getFxml();
}
