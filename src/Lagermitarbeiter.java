public class Lagermitarbeiter extends Controller {
	
	/**
	 * erstellt einen neuen Lagermitarbeiter
	 * @param datenbank, auf die der Lagermitarbeiter zugreift
	 */
	public Lagermitarbeiter(Datenbank datenbank) {
		
		this.datenbank = datenbank;
		
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

}