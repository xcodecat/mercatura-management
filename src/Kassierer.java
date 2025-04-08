import java.util.LinkedList;

public class Kassierer extends Controller {
	
	private LinkedList<Produkt> warenkorb;
	private Beliebtheitsgraph beliebtheitsgraph;

	public Kassierer (Datenbank datenbank, Beliebtheitsgraph beliebtheitsgraph) {
		this.beliebtheitsgraph = beliebtheitsgraph;
		this.datenbank = datenbank;
		warenkorb = new LinkedList<Produkt>();
 
	}

	public void kassieren (String name, int anzahl) {
		
		datenbank.produktanzahlVeraendernK(anzahl, name);
		warenkorb.add(datenbank.produktSuchen(name));
	}
 
	public void warenkorbBeenden() {
		
		beliebtheitsgraph.warenkorbHinzufuegen(warenkorb);
		warenkorb.clear();
		
	}

}
