/*
This class is used to define and show three stages: one to view and/od update
a recipe, one to add a ne recipe, and one to delete a recipe.
*/
/*
 * Name: Zubaiyer Karim LABIB
 * Student ID = 18037966
 * Username = 18037966
 * Subject Code = CSE3OAD
 
*/

import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.scene.control.cell.*;
import javafx.beans.property.*;
import java.util.concurrent.atomic.AtomicBoolean; 
public class RecipeStageMaker
{
	private RecipeDSC recipeDSC;
	private ObservableList<Recipe> tableData;
	private TableView<Recipe> tableView;
	private Stage primaryStage;

	// id
	private Label idLB = new Label("Id: ");
	private TextField idTF = new TextField();
	private HBox idHBox = new HBox(idLB, idTF);

	// name
	private Label nameLB = new Label("Recipe Name: ");;
	private TextField nameTF = new TextField();
	private HBox nameHBox = new HBox(nameLB, nameTF);

	// serves
	private Label servesLB = new Label("Serves: ");
	private TextField servesTF = new TextField();
	private HBox servesHBox = new HBox(servesLB, servesTF);

	// ingredients
	private Label ingredientsLB = new Label("Ingredients: ");

	private TextField ingredientsTF = new TextField();

	private HBox ingredientsHBox = new HBox(ingredientsLB, ingredientsTF);

	// steps
	private TextArea stepsTA = new TextArea();
	private HBox stepsHBox = new HBox(stepsTA);

	// remarks
	private Label remarksLB = new Label("Remarks: ");
	private TextField remarksTF = new TextField();
	private HBox remarksHBox = new HBox(remarksLB, remarksTF);

	// action buttons
	private Button addBT = new Button("ADD Recipe");
	private Button updateBT = new Button("UPDATE Recipe");
	private Button deleteBT = new Button("DELETE Recipe");
	private Button cancelBT = new Button("EXIT/CANCEL");

	private HBox actionHBox = new HBox();


	// root, scene, local stage
	private VBox root = new VBox();
	private Scene scene = new Scene(root);
	private Stage stage = new Stage();

	public RecipeStageMaker(RecipeDSC recipeDSC, ObservableList<Recipe> tableData, TableView<Recipe> tableView, Stage primaryStage )
	{
      stage.initModality(Modality.APPLICATION_MODAL);  
     // stage.setX(primaryStage.getX() + primaryStage.getWidth());
     // stage.setY(primaryStage.getY() + primaryStage.getHeight());
      this.recipeDSC = recipeDSC;
      this.tableData = tableData;
      this.tableView = tableView;
      this.primaryStage = primaryStage;
		/*
		 * TO DO: Initilize the RecipeStageMaker
		 * (can include the setting of style rules if choose to do so)
		 */
	}

	public void showViewRecipeStage() 
	{
      try
      {
        stage.setX(primaryStage.getX() + primaryStage.getWidth());
        stage.setY(primaryStage.getY() + primaryStage.getHeight()); 
        recipeDSC = new RecipeDSC(); 
        Recipe recipe = tableView.getSelectionModel().getSelectedItem();
        idTF.setText(Integer.toString(recipe.getId()));
        nameTF.setText(recipe.getName());
        servesTF.setText(Integer.toString(recipe.getServes()));
        ingredientsTF.setText(recipe.getIngredients());
        stepsTA.setText(recipe.getSteps());
        remarksTF.setText(recipe.getRemarks());

        actionHBox.getChildren().clear();
        actionHBox.getChildren().addAll(updateBT, cancelBT); 
        updateBT.setOnAction((e) ->
        {
           try
           {
            String name = nameTF.getText();
            int serves = Integer.parseInt(servesTF.getText());
            String ingredients = ingredientsTF.getText();
            String steps = stepsTA.getText();
            String remarks = remarksTF.getText();
            Recipe recipe1 = recipeDSC.find(recipe.getId());
            recipe1.setName(name);
            recipe1.setServes(serves);
            recipe1.setIngredients(ingredients);
            recipe1.setSteps(steps);
            recipe1.setRemarks(remarks);
            recipeDSC.update(recipe1);
           
            List<Recipe> recipes = recipeDSC.findAll();
            tableData.clear();
            tableData.addAll(recipes);
           // tableView.setItems(tableData);
           // System.out.println("Your selected recipe has been updated ");
          //  tableView.getColumns().get(0).setVisible(false);
           // tableView.getColumns().get(0).setVisible(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Your selected recipe has been updated ");
            alert.showAndWait();
            stage.close();
          //  System.out.println("Your selected recipe has been updated ");
           }
           catch(SQLException m)
           {
              System.out.println("The recipe with that id doesnot exists in the database ");
           }
                
        });

        root.getChildren().clear();
        root.getChildren().addAll(idHBox, nameHBox, servesHBox, ingredientsHBox, stepsHBox, remarksHBox, actionHBox);
        stage.setScene(scene);
      //  stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();   
        cancelBT.setOnAction((e) ->
        {
           stage.close(); 
        });
        
     }
    
     catch(Exception f)
     {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setContentText("The recipe is not selected ");
       alert.showAndWait(); 
      // System.out.println("The recipe is not selected ");
     }
		/*
		 * TO DO: To present a stage to view and/or update the recipe selected
		 * in the table view
		 */
	}

	public void showAddRecipeStage()
	{
      stage.setX(primaryStage.getX() + primaryStage.getWidth());
      stage.setY(primaryStage.getY() + primaryStage.getHeight());
      nameTF.clear();
      servesTF.clear();
      ingredientsTF.clear();
      stepsTA.clear();
      remarksTF.clear();

    try
    {
     // AtomicBoolean a = new AtomicBoolean(true);
      recipeDSC = new RecipeDSC();
      actionHBox.getChildren().clear();
      actionHBox.getChildren().addAll(addBT, cancelBT);
      addBT.setOnAction((e) ->
      {
         try
         {
           int serves = 0; 
          String name = nameTF.getText().trim();
          String present = servesTF.getText().trim();
         // int serves = 0;
          if(!present.equals(""))
          {
             serves = Integer.parseInt(present);
          }
         // else
         // {
           //  serves = 0;
         // }
          String ingredients = ingredientsTF.getText();
          String steps = stepsTA.getText();
          String remarks = remarksTF.getText();
          if(!name.equals("") && !present.equals("") && !ingredients.equals("") && !steps.equals("") && !remarks.equals(""))
          {
            Recipe recipe = new Recipe(0, name, serves, ingredients, steps, remarks); 
            recipeDSC.add(recipe);
            List<Recipe> recipes = recipeDSC.findAll();
            tableData.clear();
            tableData.addAll(recipes);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Your recipe has been added ");
            alert.showAndWait();
           // System.out.println("Your recipe has been added ");
            stage.close();
          }
          else
          {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setContentText("You need to fill all the fields of the recipe ");
            alert1.showAndWait(); 
           // System.out.println("You need to fill all the fields of the recipe ");
          }
         }
         catch(SQLException m)
         {
            System.out.println("The recipe with that id already exists in the database ");
         }

      });
      root.getChildren().clear();
      root.getChildren().addAll(nameHBox, servesHBox, ingredientsHBox, stepsHBox, remarksHBox, actionHBox);
    
      stage.setScene(scene);
      
     // stage.initModality(Modality.APPLICATION_MODAL);
      stage.show();
      cancelBT.setOnAction((e) ->
      {
         stage.close();
       //  a.set(false);
      });
     // if(a.get()== true)
     // {
      // stage.show();
     // }
     // addBT
    }
    catch(Exception f)
    {
       System.out.println("You didnot add all the fields of the recipe ");
    }

      
      


		/*
		 * TO DO: To present a stage to add a recipe
		 */
	}

	public void showDeleteRecipeStage()
	{
      try
      {
         stage.setX(primaryStage.getX() + primaryStage.getWidth());
         stage.setY(primaryStage.getY() + primaryStage.getHeight());
         recipeDSC = new RecipeDSC();
         Recipe recipe = tableView.getSelectionModel().getSelectedItem();
         idTF.setText(Integer.toString(recipe.getId()));
         nameTF.setText(recipe.getName());
         servesTF.setText(Integer.toString(recipe.getServes()));
         ingredientsTF.setText(recipe.getIngredients());
         stepsTA.setText(recipe.getSteps());
         remarksTF.setText(recipe.getRemarks());
         actionHBox.getChildren().clear();    
         actionHBox.getChildren().addAll(deleteBT, cancelBT);
         deleteBT.setOnAction((e) ->
         {
            try
            {
              // String name = nameTF.getText();
              // int serves = Integer.parseInt(servesTF.getText());
              // String ingredients = ingredientsTF.getText();
              // String steps = stepsTA.getText();
              // String remarks = remarksTF.getText();
              // Recipe recipe1 = recipeDSC.find(recipe.getId());
               recipeDSC.delete(recipe);
               List<Recipe> recipes = recipeDSC.findAll();
               tableData.clear();
               tableData.addAll(recipes);
              // tableView.getColumns().get(0).setVisible(false);
              // tableView.getColumns().get(0).setVisible(true);
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setContentText("Your selected recipe has been deleted ");
               alert.showAndWait();                     
               stage.close();
              // System.out.println("Your selected recipe has been deleted ");

            }
            catch(SQLException m)
            {
               System.out.println("The recipe with that id doesnot exists in the database ");
            }

         });
         root.getChildren().clear();
         root.getChildren().addAll(idHBox, nameHBox, servesHBox, ingredientsHBox, stepsHBox, remarksHBox, actionHBox);
         stage.setScene(scene);
      //   stage.initModality(Modality.APPLICATION_MODAL);
         stage.show();
         cancelBT.setOnAction((e) ->
         {
            stage.close();
         });
            


      }
      catch(Exception f)
      {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setContentText("The recipe is not selected ");
         alert.showAndWait();
        // System.out.println("The recipe is not selected ");
      }
     
		/*
		 * TO DO: To present a stage to delete the recipe selected in
		 * the table view
		 */
	}
}
