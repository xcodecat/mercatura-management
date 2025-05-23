import java.util.LinkedList;

public class Kassierer extends Controller {

	private Beliebtheitsgraph beliebtheitsgraph;
	private LinkedList<WarenkorbEintrag> warenkorb;

	/**
	 * erstellt einen neuen Kassierer
	 * @param datenbank, auf die der Kassierer zugreifen kann
	 * @param beliebtheitsgraph, in den er die Warenkörbe schickt, wo sie dann gespeichert werden
	 */
	public Kassierer(Datenbank datenbank, Beliebtheitsgraph beliebtheitsgraph) {
		this.datenbank = datenbank;
		this.warenkorb = new LinkedList<>();
		this.beliebtheitsgraph = beliebtheitsgraph;
	}

	/**
	 * fügt das neue Produkt in den Warenkorb hinzu
	 * @param name, des gescannten Produkts
	 * @param anzahl, wie oft es gekauft wird
	 */
	public void kassieren(String name, int anzahl) {
		Produkt p = datenbank.produktSuchen(name);
		if (p != null) {
			warenkorb.add(new WarenkorbEintrag(p.getName(), anzahl));
		}
	}

	/**
	 * beendet den Warenkorb und übergibt die Liste an den Beliebtheitsgraoh weiter
	 */
	public void warenkorbBeenden() {
		for (WarenkorbEintrag eintrag : warenkorb) {
			datenbank.produktanzahlVeraendernK(eintrag.anzahl, eintrag.name);
		}
		datenbank.produkteSpeichern();

		// Beliebtheitsgraph aufbauen
		LinkedList<Produkt> produktWarenkorb = new LinkedList<>();
		for (WarenkorbEintrag eintrag : warenkorb) {
			Produkt p = datenbank.produktSuchen(eintrag.name);
			if (p != null) {
				produktWarenkorb.add(p);
			}
		}
		beliebtheitsgraph.warenkorbHinzufuegen(produktWarenkorb);

		warenkorb.clear();
	}

	/**
	 * sucht das Produkt in der Datenbank
	 * @param name des gesuchten Produkts
	 * @return das gesuchte Produkt, wenn es existiert
	 */
	public Produkt produktSuchen(String name) {
		return datenbank.produktSuchen(name);
	}

	/**
	 * lässt sich die Produkte als LinkedList von der Datenbank ausgeben
	 * @return diese LinkedList
	 */
	public LinkedList<Produkt> produkteAusgeben() {
		return datenbank.produkteAusgeben();
	}

	/**
	 * Klasse die den Warenkrobeintrag speichert
	 */
	private static class WarenkorbEintrag {
		String name;
		int anzahl;

		/**
		 * speichert den Warenkorbeintrag
		 * @param name des Produkts
		 * @param anzahl wie viele Produkte verkauft wurden
		 */
		public WarenkorbEintrag(String name, int anzahl) {
			this.name = name;
			this.anzahl = anzahl;
		}
	}
}