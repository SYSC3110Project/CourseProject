/**
 * The Drawable2D.java file contains the interface which all drawable 2D objects will implement.
 */
package courseProject.view.twoD.drawable;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This interface will be implemented by objects in the game which should be drawn to the screen. 
 * It provides mutators and methods which all drawable objects will be accessed with. 
 * 
 * @author Matthew Smith
 * @version 02/11/2012
 */
public interface Drawable2D {
	
	/**The Starting X Location for the Drawable Object*/
	public static final int DEFAULT_X = 0;
	
	/**The Starting Y Location for the Drawable Object*/
	public static final int DEFAULT_Y = 0;
	
	/**The amount of time between interpolation updates in nano seconds*/
	public static final float INTERPOLATION_STEPS = 1000000f;
	
	/**
	 * Gets the point on the screen where the drawable object will be drawn.
	 * @return the point where the drawable object is drawn.
	 */
	public Point getLocation();
	
	/**
	 * Sets the point on the screen where the drawable object will be drawn.
	 * @param point The new location of the drawable object.
	 */
	public void setLocation(Point point);
	
	/**
	 * Gets the bounds of the drawable object.
	 * @return the bounds of the drawable object.
	 */
	public Rectangle getBounds();
	
	/**
	 * Sets the bounds of the drawable object.
	 * @param bounds the new bounds of the drawable object.
	 */
	public void setBounds(Rectangle bounds);
	
	/**
	 * Gets the SerializableBufferedImage which will represent this object.
	 * @return the sprite image which will be drawn for this object.
	 */
	public SerializableBufferedImage getSprite();
	
	/**
	 * Sets the SerializableBufferedImage which will represent this object.
	 * @param image the sprite image which will be drawn for this object.
	 */
	public void setSprite(SerializableBufferedImage image);
	
	/**
	 * Draw this 2D object to the passed graphics. 
	 * Will draw the object at the <code>getLocation()</code> point.
	 * @param graphics2D The graphics which this object will be drawn to.
	 */
	public void draw(Graphics2D graphics2D);	
	
	/**
	 * Update the 2D object if, for example, it is interpolating after <code>moveTo(Point)</code> was called.
	 * @param delta The change in time since the last time this update was called.
	 */
	public void update(double delta);
	
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
	public void moveTo(Point point);
}
