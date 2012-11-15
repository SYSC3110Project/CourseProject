package courseProject.view.mapD;

import javax.swing.JPanel;

import courseProject.controller.InputEvent;
import courseProject.controller.InputListener;
import courseProject.model.ModelChangeEvent;
import courseProject.model.ModelListener;
import courseProject.view.View;

public class ViewMap implements View, ModelListener {
	
	private JPanel drawArea;

	public ViewMap() {
		drawArea = new JPanel();
	}

	@Override
	public void displayMessage(String message) {
	}

	@Override
	public void update(double delta) {
		drawArea.repaint();
	}

	@Override
	public void addInputListener(InputListener listener) {
	}

	@Override
	public void notifyInputListeners(InputEvent event) {
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void handleModelChangeEvent(ModelChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
