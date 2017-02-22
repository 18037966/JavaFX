/*
 * Name = Zubaiyer Karim LABIB
 * Student ID = 18037966
 * Username = 18037966
 * Subject Code = CSE3OAD

*/
import javafx.application.Application;
import javafx.stage.Stage;
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
import java.sql.SQLException;

public class RecipeMain extends Application
{
	private ObservableList<Recipe> tableData;
	private TableView<Recipe> tableView;
	private RecipeDSC recipeDSC;

	public void start(Stage primaryStage) throws Exception
	{
		build(primaryStage);
		primaryStage.setTitle(getClass().getName());
		primaryStage.show();
	}

	public void build(Stage primaryStage) throws Exception
	{
		loadData();

	//	Node searchArea = makeSearchArea();
		Node tableViewArea = makeTableView();
      Node searchArea = makeSearchArea();
		Node addViewDeleteArea = makeViewAddDeleteArea(primaryStage);

		VBox root = new VBox();
		root.getChildren().addAll(searchArea, tableViewArea, addViewDeleteArea);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);

		// visual aspects
		{
			root.setStyle("-fx-alignment: center");
			scene.getStylesheets().add("recipe.css");
		}
	}


	// To load data from the database into tableData and then to tableView
	//
	private void loadData() throws Exception
	{
		recipeDSC = new RecipeDSC();
		List<Recipe> recipes = recipeDSC.findAll();

		tableData = FXCollections.observableArrayList();
		tableData.clear();
		tableData.addAll(recipes);

		tableView = new TableView<Recipe>();
		tableView.setItems(tableData);
	}


	// TO DO: Create and return a VBox to be the root of the scene graph
	//
	private VBox makeSceneRoot()
	{
		return null;
	}


	// TO DO: Create the area to allow the user to search the table view
	// The program should provide option to apply the search (a) to every
	// field, (b) to the ingredient field, or (c) the name field.
	//
	private Node makeSearchArea()
	{
      
      
        Label search = new Label("Enter search Text: ");
        TextField searchField = new TextField();
        HBox searchBox = new HBox(search, searchField);
        searchBox.setStyle("-fx-alignment: center");

        Label searchBy = new Label("Search By: ");
        RadioButton field = new RadioButton("Any Field");
        RadioButton ingredients = new RadioButton("Ingredients");
        RadioButton recipeName = new RadioButton("Recipe Name");
        field.setSelected(true);

        ToggleGroup group = new ToggleGroup();
        field.setToggleGroup(group);
        ingredients.setToggleGroup(group);
        recipeName.setToggleGroup(group);

        FilteredList<Recipe> filteredList = new FilteredList<>(tableData, p -> true);
        SortedList<Recipe> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
        
       // RadioButton selected = (RadioButton) group.getSelectedToggle();
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
        {
           filteredList.setPredicate(recipe ->
           {
             if(newValue == null || newValue.isEmpty())
             {
               return true;
             }
             String filterString = newValue.toUpperCase();

             RadioButton selected = (RadioButton) group.getSelectedToggle();
            
           
           //  if(selected.isSelected())
            // {     
               if(selected.getText().equals("Any Field"))
               {
                 if(recipe.getName().toUpperCase().contains(filterString) || recipe.getIngredients().toUpperCase().contains(filterString) || recipe.getRemarks().toUpperCase().contains(filterString))
                 {
                    return true;
                 }
                 else
                 {
                    return false;
                 }
                   
               }
               if(selected.getText().equals("Ingredients"))
               {
                 if(recipe.getIngredients().toUpperCase().contains(filterString))
                 {
                    return true;
                 } 
                 else
                 {
                    return false;
                 }
               }
               else
               {
                 if(recipe.getName().toUpperCase().contains(filterString))
                 {
                   return true;
                 }
                 else
                 {
                   return false;
                 }
               }
            // }
            // else
            // {
              //  System.out.println("You need to select a search field in order to filter the tableView");
               // return true;
            // }
            
            
            
               
            
           });
        });
        HBox radioBox = new HBox(searchBy, field, ingredients, recipeName);
        radioBox.setStyle("-fx-alignment: center");

        VBox searchArea = new VBox(searchBox, radioBox);
        searchArea.setStyle("-fx-alignment: center");


         
		  return searchArea;
	}


	//
	// TO DO: Define the table view and put it in a HBox. Return the HBox
	//
	private Node makeTableView()
	{
         TableColumn<Recipe, Integer> idColumn = new TableColumn<Recipe, Integer>("Id");
         idColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Integer>("Id"));
         
         TableColumn<Recipe, String> nameColumn = new TableColumn<Recipe, String>("Recipe Name");    
         nameColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("Name"));

         TableColumn<Recipe, Integer> serveColumn = new TableColumn<Recipe, Integer>("Serves");
         serveColumn.setCellValueFactory(new PropertyValueFactory<Recipe, Integer>("Serves"));


		//	return null;

			
			// To enable text wrap around, specify the call method of the
			// Callback interface of the column's cellFactory property
			//
		   TableColumn<Recipe, String> ingredientsColumn = new TableColumn<Recipe, String>("Ingredients");
         ingredientsColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("Ingredients")); 
      	ingredientsColumn.setCellFactory(
				// method call of Callback
				(TableColumn<Recipe, String> param) ->
				{
					return new TableCell<Recipe, String>()
					{
						 @Override
						 public void updateItem(String item, boolean empty)
						 {
							super.updateItem(item, empty);
							Text text = new Text(item);
							text.wrappingWidthProperty().bind(ingredientsColumn.widthProperty());
							this.setWrapText(true);
							setGraphic(text);
						 }               
			      };
            }
            );

         TableColumn<Recipe, String> remarksColumn = new TableColumn<Recipe, String>("Remarks");
         remarksColumn.setCellValueFactory(new PropertyValueFactory<Recipe, String>("Remarks"));

         tableView = new TableView<Recipe>();

         tableView.getColumns().add(idColumn);
         tableView.getColumns().add(nameColumn);
         tableView.getColumns().add(serveColumn);
         tableView.getColumns().add(ingredientsColumn);
         tableView.getColumns().add(remarksColumn);

         tableView.setItems(tableData);

         tableView.setMinWidth(600);
         tableView.setMaxWidth(800);
         idColumn.setMinWidth(100);
         nameColumn.setMinWidth(100);
         serveColumn.setMinWidth(100);
         ingredientsColumn.setMinWidth(100);
         remarksColumn.setMinWidth(100);
         
         return tableView; 
			
	}


	// TO DO: Create the area with the buttons to view, add and delete recipes
	//
	private Node makeViewAddDeleteArea(Stage primaryStage)
	{
      Button viewBT = new Button("View/Update Selected Recipe");
      Button addBT = new Button("Add Recipe");
      Button deleteBT = new Button("Delete Selected Recipe");
      recipeDSC = new RecipeDSC();
     // Recipe recipe = tableView.getSelectionModel().getSelectedModel();

      RecipeStageMaker stageMaker = new RecipeStageMaker(recipeDSC, tableData, tableView, primaryStage);

      viewBT.setOnAction((e) ->
      {
         try
         {
          stageMaker.showViewRecipeStage();   
         }
         catch(Exception f)
         {
            System.out.println("The recipe with that id doesnot exists ");
         }
      });
      addBT.setOnAction((e) ->
      {
         stageMaker.showAddRecipeStage();
      });
      deleteBT.setOnAction((e) ->
      {
         stageMaker.showDeleteRecipeStage();
      });
      
      HBox bottomBox = new HBox(viewBT, addBT, deleteBT); 
      bottomBox.setStyle("-fx-alignment: center");
		return bottomBox;
	}
}

