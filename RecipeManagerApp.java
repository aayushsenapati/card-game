import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Model
class RecipeItem {
    private int id;
    private String name;
    private String ingredients;
    private int preparationTime;
    private String difficultyLevel;
    private double averageRating;
    private String instructions;

    public RecipeItem(int id, String name, String ingredients, int preparationTime, String difficultyLevel, double averageRating, String instructions) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.preparationTime = preparationTime;
        this.difficultyLevel = difficultyLevel;
        this.averageRating = averageRating;
        this.instructions = instructions;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getIngredients() { return ingredients; }
    public int getPreparationTime() { return preparationTime; }
    public String getDifficultyLevel() { return difficultyLevel; }
    public double getAverageRating() { return averageRating; }
    public String getInstructions() { return instructions; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
}

class RecipeRating {
    private int recipeId;
    private double rating;

    public RecipeRating(int recipeId, double rating) {
        this.recipeId = recipeId;
        this.rating = rating;
    }

    // Getters
    public int getRecipeId() { return recipeId; }
    public double getRating() { return rating; }
}

// View and Controller
class RecipeManagerUI extends JFrame {
    private JTable recipeTable;
    private JLabel nameLabel, ingredientsLabel, preparationTimeLabel, difficultyLabel, ratingLabel;
    private JTextArea instructionsTextArea;
    private JSlider ratingSlider;
    private JButton rateButton;
    private List<RecipeItem> recipes;
    private List<RecipeRating> ratings;

    public RecipeManagerUI() {
        super("Recipe Manager");
        setLayout(new BorderLayout());

        // Recipe table
        String[] columnNames = {"ID", "Name", "Ingredients", "Preparation Time", "Difficulty Level", "Average Rating"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        recipeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(recipeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Recipe details
        JPanel detailsPanel = new JPanel(new GridLayout(6, 2));
        nameLabel = new JLabel();
        ingredientsLabel = new JLabel();
        preparationTimeLabel = new JLabel();
        difficultyLabel = new JLabel();
        ratingLabel = new JLabel();
        instructionsTextArea = new JTextArea();
        instructionsTextArea.setEditable(false);
        JScrollPane instructionsScrollPane = new JScrollPane(instructionsTextArea);
        detailsPanel.add(new JLabel("Name:"));
        detailsPanel.add(nameLabel);
        detailsPanel.add(new JLabel("Ingredients:"));
        detailsPanel.add(ingredientsLabel);
        detailsPanel.add(new JLabel("Preparation Time:"));
        detailsPanel.add(preparationTimeLabel);
        detailsPanel.add(new JLabel("Difficulty Level:"));
        detailsPanel.add(difficultyLabel);
        detailsPanel.add(new JLabel("Average Rating:"));
        detailsPanel.add(ratingLabel);
        detailsPanel.add(new JLabel("Instructions:"));
        detailsPanel.add(instructionsScrollPane);
        add(detailsPanel, BorderLayout.WEST);

        // Rating
        JPanel ratingPanel = new JPanel(new BorderLayout());
        ratingSlider = new JSlider(1, 5, 3);
        ratingPanel.add(ratingSlider, BorderLayout.CENTER);
        rateButton = new JButton("Rate");
        ratingPanel.add(rateButton, BorderLayout.EAST);
        add(ratingPanel, BorderLayout.SOUTH);

        // Load recipe data
        recipes = new ArrayList<>();
        recipes.add(new RecipeItem(1, "Spaghetti Carbonara", "Pasta, Eggs, Cheese, Pancetta", 30, "Easy", 4.5,
                "1. Boil water and cook pasta according to package instructions.\n2. In a separate bowl, mix eggs, cheese, and cooked pancetta.\n3. Drain pasta and mix with the egg mixture.\n4. Serve immediately."));
        recipes.add(new RecipeItem(2, "Classic Ratatouille", "Eggplant, Zucchini, Tomato", 60, "Medium", 4.0,
                "1. Slice vegetables thinly.\n2. Layer them in a baking dish.\n3. Bake at 350°F for 45-50 minutes.\n4. Serve hot."));
        recipes.add(new RecipeItem(3, "Beef Wellington", "Beef, Mushrooms, Puff Pastry", 90, "Hard", 4.8,
                "1. Sear beef on all sides.\n2. Sauté mushrooms and wrap beef in puff pastry.\n3. Bake at 400°F for 30-40 minutes.\n4. Let it rest before slicing."));
        updateRecipeTable();

        ratings = new ArrayList<>();

        // Event listeners
        recipeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = recipeTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        RecipeItem selectedRecipe = recipes.get(selectedRow);
                        displayRecipeDetails(selectedRecipe);
                    }
                }
            }
        });

        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = recipeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    RecipeItem selectedRecipe = recipes.get(selectedRow);
                    double rating = ratingSlider.getValue();
                    rateRecipe(selectedRecipe, rating);
                }
            }
        });

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateRecipeTable() {
        DefaultTableModel tableModel = (DefaultTableModel) recipeTable.getModel();
        tableModel.setRowCount(0);
        for (RecipeItem recipe : recipes) {
            Object[] row = {
                recipe.getId(),
                recipe.getName(),
                recipe.getIngredients(),
                recipe.getPreparationTime() + " mins",
                recipe.getDifficultyLevel(),
                String.format("%.1f", recipe.getAverageRating())
            };
            tableModel.addRow(row);
        }
    }

    private void displayRecipeDetails(RecipeItem recipe) {
        nameLabel.setText(recipe.getName());
        ingredientsLabel.setText(recipe.getIngredients());
        preparationTimeLabel.setText(recipe.getPreparationTime() + " mins");
        difficultyLabel.setText(recipe.getDifficultyLevel());
        ratingLabel.setText(String.format("%.1f", recipe.getAverageRating()));
        instructionsTextArea.setText(recipe.getInstructions());
    }

    private void rateRecipe(RecipeItem recipe, double rating) {
        recipe.setAverageRating(rating);
        ratings.add(new RecipeRating(recipe.getId(), rating));
        updateRecipeTable();
    }
}

public class RecipeManagerApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RecipeManagerUI();
            }
        });
    }
}
