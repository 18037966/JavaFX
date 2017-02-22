/*
 * Class Name:    Main
 *
 * Author:        Your Name
 * Creation Date: Sunday, September 04 2016, 19:09 
 * Last Modified: Thursday, September 08 2016, 20:01
 * 
 * Class Description:
 *
 */
import java.util.List;
public class Main
{
    public static void main(String[] args) throws Exception
    {
       System.out.println("Interact with Database Lab!");
       test1();
     //  System.out.println("---------------------------");
      // test2();
      // System.out.println("---------------------------");
      // test6();
      // System.out.println("---------------------------");
      // test3();
      // System.out.println("---------------------------");
      // test4();
    }

    public static void test1() throws Exception
    {
       RecipeDSC recipe = new RecipeDSC();
       int id = 1;
       System.out.println(recipe.find(id)); 
    }

    public static void test2() throws Exception
    {
       int sum;
       RecipeDSC recipe = new RecipeDSC();
       sum = recipe.count();
       System.out.println("The total number of records are " + sum);

    }

    public static void test3() throws Exception
    {
       RecipeDSC recipe = new RecipeDSC();
       System.out.println(recipe.findAll() + "\n");
    }

    public static void test4() throws Exception
    {
       RecipeDSC recipe = new RecipeDSC();
       int count = recipe.add("name 600", 6, "ingredients 500", "steps 1 2 3 4", "bad");
       System.out.println("The id of the recipe is " + count);
    }
    
    public static void test5() throws Exception
    {
       RecipeDSC recipe = new RecipeDSC();
       Recipe r = new Recipe(0, "name 800", 8, "ingredients 600", "steps 1 2 3 7", "average");
       recipe.add(r);
    }

    public static void test6() throws Exception
    {
       RecipeDSC dsc = new RecipeDSC();
       Recipe recipe = dsc.find(4);
       recipe.setName("Drunken chicken");
       recipe.setServes(10000);
       recipe.setIngredients("Drunken chicken 10 of them; RICE 100kg");
       recipe.setSteps("\n1. Cook chicken\n2.Cook rice");
       recipe.setRemarks("Enjoy the festival!");

       System.out.println(">>> updated recipe: " + recipe);
       dsc.update(recipe);

    }



}

