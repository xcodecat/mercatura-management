import java.util.LinkedList;

public class Kassierer extends Controller {

	private Beliebtheitsgraph beliebtheitsgraph;
	private LinkedList<WarenkorbEintrag> warenkorb;

	public Kassierer(Datenbank datenbank, Beliebtheitsgraph beliebtheitsgraph) {
		this.datenbank = datenbank;
		this.warenkorb = new LinkedList<>();
		this.beliebtheitsgraph = beliebtheitsgraph;
	}

	public void kassieren(String name, int anzahl) {
		Produkt p = datenbank.produktSuchen(name);
		if (p != null) {
			warenkorb.add(new WarenkorbEintrag(p.getName(), anzahl));
		}
	}

//	public void warenkorbBeenden() {
//		for (WarenkorbEintrag eintrag : warenkorb) {
//			datenbank.produktanzahlVeraendernK(eintrag.anzahl, eintrag.name);
//		}
//		datenbank.produkteSpeichern(); // Speichern nach allen Änderungen
//		warenkorb.clear();
//	}

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

//		// Beliebteste 3 Produkte neben dem ersten ausgeben (z.B. basierend auf erstem Produkt im Warenkorb)
//		if (!produktWarenkorb.isEmpty()) {
//			String name = produktWarenkorb.getFirst().getName();
//			//System.out.println("Beliebte Produkte zu „" + name + "“:");
//			beliebtheitsgraph.beliebtheitAusgeben(name, 3);
//		}

		warenkorb.clear();
	}

	private static class WarenkorbEintrag {
		String name;
		int anzahl;

		public WarenkorbEintrag(String name, int anzahl) {
			this.name = name;
			this.anzahl = anzahl;
		}
	}
}