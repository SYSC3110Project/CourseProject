package courseProject.view.twoD.drawable;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;


/**
 * SerializableBufferedImage is used to be able to serialize an image (in this case, keep the path of the file and load the buffered image using
 * the file path, this way the image itself doesn't need to be serialized)
 * @author Denis Dionne
 * @version 03/12/2012
 *
 */
public class SerializableBufferedImage implements Serializable {
	private String pathName;
	transient private BufferedImage Image;


	
	/**
	 * Constructor for the SerializedBufferedImage
	 * Stores the pathName and uses it to initialize the bufferedImage
	 * @param pathName used to initialize the bufferedImage
	 */
	public SerializableBufferedImage(String pathName) {
		this.pathName = pathName;
		try {
			Image = ImageIO.read(new File(pathName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * getter method that returns the bufferedImage
	 * @return BufferedImage Image
	 */
	public BufferedImage getImage(){
		return Image;
	}
	
	/**
	 * Serialization method used to write the object
	 * (use the default method, since the bufferedImage isn't going to be stored because it's transient)
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException{
		//ImageIO.write(Image,"png",ImageIO.createImageOutputStream(out));
		out.defaultWriteObject();
	}
	
	/**
	 * Serialization method to read the object from the file
	 * Uses the default readObject method to read in the pathName of the Image
	 * then uses this pathName to reinitialize the BufferedImage
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		
		//Image =ImageIO.read(ImageIO.createImageInputStream(in));
		in.defaultReadObject();
		Image = ImageIO.read(new File(pathName));
		
	}

}
