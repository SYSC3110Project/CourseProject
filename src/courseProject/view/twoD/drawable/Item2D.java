/**
 * 
 */
package courseProject.view.twoD.drawable;

import java.awt.Point;

import courseProject.model.Item;
import courseProject.model.ItemType;

/**
 * @author Matthew Smith
 * @version 01/11/2012
 */
public class Item2D extends Item implements Drawable2D {

	/**
	 * @param name
	 * @param description
	 * @param weight
	 * @param type
	 * @param value
	 */
	public Item2D(String name, String description, int weight, ItemType type,
			int value) {
		super(name, description, weight, type, value);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param toCopy
	 */
	public Item2D(Item toCopy) {
		super(toCopy);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Point getPoint() {
		// TODO Auto-generated method stub
		return null;
	}

}
