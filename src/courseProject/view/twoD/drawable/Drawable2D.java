/**
 * The Drawable2D.java file contains the interface which all drawable objects will implement.
 */
package courseProject.view.twoD.drawable;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * This interface will be implemented by objects in the game which should be drawn to the screen. 
 * It provides mutators and methods which all drawable objects will be accessed with. 
 * 
 * @author Matthew Smith
 * @version 02/11/2012
 */
public interface Drawable2D {

	/**
	 * Gets the point on the screen where the drawable object will be drawn.
	 * @return the point where the drawable object is drawn.
	 */
	public Point2D.Double getLocation();

	/**
	 * Sets the point on the screen where the drawable object will be drawn.
	 * @param point The new location of the drawable object.
	 */
	public void setLocation(Point2D.Double point);

	/**
	 * Gets the bounds of the drawable object.
	 * @return the bounds of the drawable object.
	 */
	public Rectangle2D.Double getBounds();

	/**
	 * Sets the bounds of the drawable object.
	 * @param bounds the new bounds of the drawable object.
	 */
	public void setBounds(Rectangle2D.Double bounds);

	/**
	 * Gets the BufferedImage which will represent this object.
	 * @return the sprite image which will be drawn for this object.
	 */
	public BufferedImage getSprite();

	/**
	 * Sets the BufferedImage which will represent this object.
	 * @param image the sprite image which will be drawn for this object.
	 */
	public void setSprite(BufferedImage image);

	/**
	 * Draw this 2D object to the passed graphics. 
	 * Will draw the object at the <code>getLocation()</code> point.
	 * @param graphics2D The graphics which this object will be drawn to.
	 */
	public void draw(Graphics2D graphics2D);	

	/**
	 * Checks the bounds of the two drawable objects to see if they are colliding.
	 * @param other the other 2D object to check collision with.
	 * @return <code>true</code> if the bounds of the other object overlap this object's bounds.
	 */
	public boolean collidesWith(Drawable2D other);

	/**
	 * Interpolate the drawable object from where is is now to where it is going.
	 * @param point The point for the object to move to.
	 */
	public void moveTo(Point2D.Double point);
}