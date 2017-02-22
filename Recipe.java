/*
 * Name = Zubaiyer Karim LABIB
 * Student ID = 18037966
 * Username = 18037966
 * Subject Code = CSE3OAD
*/
public class Recipe
{
	private int id;
	private String name;
	private int serves;
	private String ingredients;
	private String steps;
	private String remarks;

	public Recipe(int id, String name, int serves, String ingredients, String steps, String remarks)
	{
		this.id = id;
		this.name = name;
		this.serves = serves;
		this.ingredients = ingredients;
		this.steps = steps;
		this.remarks = remarks;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getServes()
	{
		return serves;
	}

	public void setServes(int serves)
	{
		this.serves = serves;
	}

	public String getIngredients()
	{
		return ingredients;
	}

	public void setIngredients(String ingredients)
	{
		 this.ingredients = ingredients;
	}

	public String getSteps()
	{
		return steps;
	}

	public void setSteps(String steps)
	{
		this.steps = steps;
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	public void update(String name, int serves, String ingredients,
		String steps, String remarks)
	{
		this.name = name;
		this.serves = serves;
		this.ingredients = ingredients;
		this.steps = steps;
		this.remarks = remarks;
	}

	@Override
	public String toString()
	{
		return "\nRecipe{\n\t" +
		  "id: " + id + "\n\t" +
		  "name: " + name + "\n\t" +
		  "serves: " + serves + "\n\t" +
		  "ingredients: " + ingredients + "\n\t" +
		  "steps: " +  steps  + "\n\t" +
		  "remarks = " + remarks + "}\n";
	}

	@Override
	public boolean equals(Object obj)
	{
		return
			obj != null &&
			obj instanceof Recipe &&
			((Recipe) obj).getId() == this.id;
	}
}
