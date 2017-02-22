/*
 * Name = Zubaiyer Karim LABIB
 * Student ID = 18037966
 * Username = 18037966
 * Subject Code = CSE3OAD
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class RecipeDSC
{
	private  Connection connection;
	private  Statement statement;
	private  PreparedStatement preparedStatement;
	private  String password;

	public  void getPassword()
	{
		char [] pwd = System.console().readPassword("Password: ");
		password = new String(pwd);
	}

	public  void connect() throws SQLException
	{
		// String url = "jdbc:mysql://latcs7.cs.latrobe.edu.au:3306/username";
		// String user = "username";

		String url = "jdbc:mysql://latcs7.cs.latrobe.edu.au:3306/18037966";
		String user = "18037966";
      String password = "01712721";
		connection = DriverManager.getConnection(url, user, password);
		statement = connection.createStatement();
	}

	public  void disconnect() throws SQLException
	{
		if (preparedStatement != null) preparedStatement.close();
		if (statement != null) statement.close();
		if (connection != null) connection.close();
	}

	/*
	 * TODO: This method should find a Recipe with the given id.
	 * @param id The id of the Recipe to be found.
	 * @return The Recipe with the given id if it exists. Otherwise return null.
	 * @throws SQLException
	 *	(May or may not be needed for the GUI part)
	 */
	public Recipe find(int id) throws SQLException
	{
      connect();
      Recipe recipe = null;
      ResultSet rs = statement.executeQuery("select * from recipe where id = " + "'" + id + "'");
      while(rs.next())
      {
         int id1 = rs.getInt(1);
         String name = rs.getString(2);
         int serves = rs.getInt(3);
         String ingredients = rs.getString(4);
         String steps = rs.getString(5);
         String remarks = rs.getString(6);
         recipe = new Recipe(id, name, serves, ingredients, steps, remarks);
      }

      disconnect();
		return recipe;
	}


	/*
	 * TODO: This method should count the total number of Recipes in the database
	 * @return An int representing the number of Recipes
	 * @throws SQLException
	 * (May or may not be needed for the GUI part)
	 */
	public int count() throws SQLException
	{
      connect();
      int sum = 0;
      ResultSet rs = statement.executeQuery("select * from recipe ");  
      while(rs.next())
      {
         sum++;
      }
      disconnect();
		return sum;
	}


	/**
	 * TODO: This method should obtain and return  the list of all Recipes
	 * from the database
	 * @return The list of all stored Recipes
	 * @throws SQLException
	 */
	public List<Recipe> findAll() throws SQLException
	{
      connect();
      ArrayList<Recipe> recipes = new ArrayList<Recipe>();
      ResultSet rs = statement.executeQuery("select * from recipe ");
      while(rs.next())
      {
         int id1 = rs.getInt(1);
         String name = rs.getString(2);
         int serves = rs.getInt(3);
         String ingredients = rs.getString(4);
         String steps = rs.getString(5);
         String remarks = rs.getString(6);
         Recipe recipe = new Recipe(id1, name, serves, ingredients, steps, remarks);
         recipes.add(recipe);
      }
      disconnect();
		return recipes;
	}


	/*
	 * TODO: This method should try to add a new Recipe with the details
	 * provided by the parameters
	 *	@return The id of the recipe (which is generated)
	 * @param All the details of the recipe to be added (except the id)
	 * @throws SQLException
	 */
	public int add(String name, int serves, String ingredients, String steps, String remarks) throws SQLException
	{
     
      connect();
      statement.executeUpdate("insert into recipe values(null," + "'" + name + "'" +"," + "'" + serves + "'" +"," + "'" +ingredients + "'" +"," + "'" + steps + "'" + "," + "'" + remarks +"')", Statement.RETURN_GENERATED_KEYS);
     // ResultSet rs = statement.executeQuery("insert into recipe (name, serves, ingredients, steps, remarks) values(" + "'" + name + "' " + "'" + serves + "' " + "'" +ingredients + "' " + "'" + steps + "' " + "'" + remarks +"' )" );  
     // int count = null;
     /* String query = "insert into recipe (name, serves, ingredients, recipe, remarks) values(?,?,?,?,?)";
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, null);
      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, serves);
      preparedStatement.setString(3, ingredients);
      preparedStatement.setInt(4, serves);
      preparedStatement.setString(5, remarks); */

     // preparedStatement.executeUpdate();
      ResultSet rs = statement.getGeneratedKeys(); 
      rs.next();
      int count = rs.getInt(1);
      disconnect();
     // int count = rs.getInt(1);
		return count;
	}

	/*
	 * TODO: This method should try to add the given Recipe to the database.
	 * @param recipe The recipe instance, which contains details of the new recipe
	 * @throws SQLException
	 */

	public void add(Recipe recipe) throws SQLException
	{

       connect();
       statement.executeUpdate("insert into recipe values(null," + "'" + recipe.getName() + "'" +"," + "'" + recipe.getServes() + "'" +"," + "'" +recipe.getIngredients() + "'" +"," + "'" + recipe.getSteps() + "'" + "," + "'" + recipe.getRemarks() +"')");    
		// TO DO

		// The id of recipe should be simply ignored in the creation of the
		// new recipe
	}

	/**
	 * TODO: This method should try to update an existing Recipe using the
	 * details provided by the given Recipe parameter. All the details, except
	 * the id, can be updated
	 * @param recipe The Recipe instance that contains details to be used for
	 * updating
	 * @throws SQLException
	 */
	public void update(Recipe recipe) throws SQLException
	{
       Recipe recipe1 = find(recipe.getId());     
       if(recipe1 == null)
       {
          String msg = "Recipe doesnot exists ";
          throw new SQLException(msg); 
       }

       connect();

      
      String updateString = "update recipe " + "set name = ?, serves = ?, ingredients = ?, steps = ?, remarks = ? " + "where id = ? ";
       preparedStatement = connection.prepareStatement(updateString);
       preparedStatement.setString(1, recipe.getName());
       preparedStatement.setInt(2, recipe.getServes());
       preparedStatement.setString(3, recipe.getIngredients());
       preparedStatement.setString(4, recipe.getSteps());
       preparedStatement.setString(5, recipe.getRemarks());
       preparedStatement.setInt(6, recipe.getId());

       preparedStatement.executeUpdate();
       disconnect();

              
   }


      /**
       * TODO: This method should try to delete a Recipe record from the database
       * @param id The id of the Recipe to be deleted
       * @throws SQLException
       */

    public void delete(int id) throws SQLException
    {
          Recipe recipe = find(id);
          if(recipe == null)
           {
              String msg = "Recipe doesnot exists ";
              throw new SQLException(msg);
           }

           connect();
           String deleteStatement = "delete from recipe " + "where id = ? ";
           preparedStatement = connection.prepareStatement(deleteStatement);
           preparedStatement.setInt(1, id);
           preparedStatement.executeUpdate();

           disconnect();
 
    }


      /**
       * TODO: This method should try to delete a Recipe record from the database
       * @param Recipe The Recipe to be deleted
       * @throws SQLException If the ID of the Recipe doesn't already exist
       */
     public void delete(Recipe recipe) throws SQLException
     {
        Recipe recipe1 = find(recipe.getId());
        if(recipe1 == null)
        {
           String msg = "Recipe doesnot exists ";
           throw new SQLException(msg);
        }

        connect();
        String deleteStatement = "delete from recipe " + "where id = ? ";
        preparedStatement = connection.prepareStatement(deleteStatement);
        preparedStatement.setInt(1, recipe.getId());
        preparedStatement.executeUpdate();

        disconnect();
     }

     public static void main(String [] args) throws Exception
     {
         /*
         RecipeDSC dsc = new RecipeDSC();
         dsc.getPassword();

         List<Recipe> list = dsc.findAll();
         System.out.println(list);

         Recipe recipe = dsc.find(4);
         System.out.println(recipe);

         recipe = dsc.find(100);
         System.out.println(recipe);

         int id = dsc.add("name 200", 100, "ingredients 200", "step 1 , 2, 3, 4", "easy");
         System.out.println("id: " + id);

         recipe = dsc.find(4);
         recipe.setName("Drunken chicken");
         recipe.setServes(10000);
         recipe.setIngredients("Drunken chicken 10 of them; RICE 100kg");
         recipe.setSteps("\n1. Cook chicken\n2.Cook rice");
         recipe.setRemarks("Enjoy the festival!");

         System.out.println(">>> updated recipe: " + recipe);

         dsc.update(recipe);
         */
     }

 }
