package data;

/**
 * The Developer class represents a developer with a name.
 * FEEL FREE TO EXPAND THIS CLASS WITH ADDITIONAL ATTRIBUTES AND METHODS AS NEEDED.
 *
 * @author Dr.Gonzalez-Sanchez
 * @version 3.0
 */
public class Developer {
	
	private String name;
	
	public Developer(String name) {
		setName(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (name != null && !name.trim().isEmpty())
			this.name = name;
	}
	
}