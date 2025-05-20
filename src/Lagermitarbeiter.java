import java.util.LinkedList;

public class Lagermitarbeiter extends Controller {
	
	/**
	 * Beliebtheitsgraph der Produkte
	 */
	private Beliebtheitsgraph beliebtheitsgraph;
	
	/**
	 * erstellt einen neuen Lagermitarbeiter
	 * @param datenbank, auf die der Lagermitarbeiter zugreift
	 */
	public Lagermitarbeiter(Datenbank datenbank, Beliebtheitsgraph beliebtheitsgraph) {
		
		this.datenbank = datenbank;
		this.beliebtheitsgraph = beliebtheitsgraph;
		
	}
	
	/**
	 * verändet den Produktorts
	 * @param ort neuer Ort des Produkts
	 * @param name des veränderten Produkts
	 * @return mögliche Fehlermeldungen (siehe datenbank.produktortVeraendern)
	 */
	public int produktortVeraendern(String ort, String name) {
		
		return datenbank.produktortVeraendern(ort, name);
		
	}
	
	/**
	 * fügt eine bestimmte Anzahl eines Produkts zum Regal hinzu
	 * @param anzahl Anzahl des Produkts
	 * @param name Name des Prddokts
	 * @return mögliche Fehlermeldungen (siehe datenbank.produktanzahlVerändern)
	 */
	public int zuRegalHinzufuegen(int anzahl, String name) {
		
		return datenbank.produktanzahlVeraendern(anzahl, false, name);		
		
	}
	
	/**
	 * fügt eine bestimmte Anzahl eines Produkts zum Lager hinzu
	 * @param anzahl Anzahl des Produkts
	 * @param name Name des Produkts
	 * @return mögliche Fehlermeldungen (siehe datenbank.produktanzahlVerändern)
	 */
	public int zuLagerHinzufuegen(int anzahl, String name) {
		
		int temp = datenbank.produktanzahlVeraendern(anzahl, true, name);
		datenbank.einkaufszahlenErhoehen(anzahl, name);
		return temp;
		
	}
	
	/**
	 * entfernt eine bestimmte Anzahl eines Produkts aus dem Regal
	 * @param anzahl Anzahl des Produkts
	 * @param name Name des Produkts
	 * @return mögliche Fehlermeldungen (siehe datenbank.produktanzahlVerändern)
	 */
	public int ausRegalEntfernen(int anzahl, String name) {
		
		return datenbank.produktanzahlVeraendern(-anzahl, false, name);
		
	}
	
	/**
	 * entfernt eine bestimmte Anzahl eines Produkts aus dem Lager
	 * @param anzahl Anzahl des Produkts
	 * @param name Name des Produkts
	 * @return mögliche Fehlermeldungen (siehe datenbank.produktanzahlVerändern)
	 */
	public int ausLagerEntfernen(int anzahl, String name) {
		
		return datenbank.produktanzahlVeraendern(-anzahl, true, name);
		
	}
	/**
	 * gibt für ein Produkt anhand des Beliebheitsgraphs den freien Ort aus, an dem das Produkt steht,
	 * dass am häufigsten mit dem eingegebenen Produkt gekauft wird
	 * @param name Name des einzuräumenden Produkts
	 * @return 	Name des besten Ortes
	 * 			null, wenn Name nicht vorhanden oder Produktort voll
	 */
	public String beliebtheitAusgeben(String name) {
		
		LinkedList<String> temp = beliebtheitsgraph.beliebtheitAusgeben(name, 5);
		
		if(temp == null) {
			
			return null;
			
		}
		
		for(int i = 0; i < 5;  i++) {	
			
			String temport = datenbank.getProduktort(temp.get(i));
			
			if(datenbank.ortChecken(temport) == true) {
			
				return temport;
		
			}
			
		}
		
		return null;
		
	}

}