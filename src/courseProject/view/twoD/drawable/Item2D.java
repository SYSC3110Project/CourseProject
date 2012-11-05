/**
 * 
 */
package courseProject.view.twoD.drawable;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

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
	public Point2D.Double getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocation(Point2D.Double point) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle2D.Double getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBounds(Rectangle2D.Double bounds) {
		// TODO Auto-generated method stub

	}

	@Override
	public BufferedImage getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSprite(BufferedImage image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D graphics2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean collidesWith(Drawable2D other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveTo(Double point) {
		// TODO Auto-generated method stub

	}

}