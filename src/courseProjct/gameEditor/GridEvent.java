package courseProjct.gameEditor;

public class GridEvent {
	private Object source;
	private int x;
	private int y;
	
	public GridEvent(Object source, int x, int y) {
		this.source = source;
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the source
	 */
	public Object getSource() {
		return source;
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
}
