package courseProject.view.twoD.drawable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Drawable2DArea extends JPanel{
	
	private static final long serialVersionUID = 6321089636136386251L;
	List<Drawable2D> drawList;
	
	public Drawable2DArea(){
		super();
		drawList = new ArrayList<Drawable2D>();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		
		// here we clear everything
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
		
		Graphics2D graphics2D = (Graphics2D)g;
		for(Drawable2D drawable : drawList){
			drawable.draw(graphics2D);
		}
		
		
	}

	public void updateDrawable(List<Drawable2D> drawList) {
		this.drawList = drawList;
	}
	
}
