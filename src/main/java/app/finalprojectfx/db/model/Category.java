package app.finalprojectfx.db.model;

import app.finalprojectfx.db.DB;
import app.finalprojectfx.db.parser.CategoryParser;

import java.util.ArrayList;

public class Category {
    private int id;
    private String name;

    private int itemId;

    private ArrayList<Item> items;


    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * This method is used to search the database and find a category by id
     *
     * @param id
     * @return category
     */
    public static Category findCategoryById(int id) {
        return DB.getInstance()
                .executeSelect(
                        "select * from categories where id = ? limit 1",
                        DB.makeParams(id),
                        CategoryParser::parseCategoryFromResultSet
                );
    }


    public static ArrayList<Category> getAllCategories() {
        return DB.getInstance().executeSelect("select * from categories", CategoryParser::parseCategoriesFromResultSet);
    }


    public void loadItems() {
        this.items = Item.findByCategoryId(this.id);
    }

    public ArrayList<Item> getItems() {
        this.loadItems();
        return items;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }
}
