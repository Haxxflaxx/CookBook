package application;

import application.dbTools.Query;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by haxxflaxx on 2015-11-09.
 *
 * Container for holding a recipe.
 */
public class Recipe {
    private static Recipe selected;

    String id;
    String name;
    String type;
    String cuisine;
    String difficulty;
    String ratings;
    String diet;
    String time;
    String timeUnit;
    String description;

    public Recipe(String id, String name, String type, String cuisine, String difficulty, String ratings, String diet, String time, String timeUnit, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.cuisine = cuisine;
        this.difficulty = difficulty;
        this.ratings = ratings;
        this.diet = diet;
        this.time = time;
        this.timeUnit = timeUnit;
        this.description = description;
    }

    public static void setSelectedByID(String id) {
        ArrayList<ArrayList<String>> dataset;

        try {
            dataset = Query.fetchData("recipes", "*", "'ID'=" + id + "'");

            setSelected(
                    new Recipe(
                        dataset.get(0).get(0),
                        dataset.get(0).get(1),
                        dataset.get(0).get(2),
                        dataset.get(0).get(3),
                        dataset.get(0).get(4),
                        dataset.get(0).get(5),
                        dataset.get(0).get(6),
                        dataset.get(0).get(7),
                        dataset.get(0).get(8),
                        dataset.get(0).get(9)
                    )
            );
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public static Recipe getSelected() {
        return selected;
    }

    public static void setSelected(Recipe selected) {
        Recipe.selected = selected;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getRatings() {
        return ratings;
    }

    public String getDiet() {
        return diet;
    }

    public String getTime() {
        return time;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public String getDescription() {
        return description;
    }
}