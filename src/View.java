import javax.swing.JFrame;

public abstract class View {
	
	/** Hauptfenster der Anwendung */
	protected JFrame frame;
	
	public View(){}

	protected abstract void update();

}