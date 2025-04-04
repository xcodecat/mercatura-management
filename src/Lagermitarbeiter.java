public class Lagermitarbeiter extends Controller {
 
	public Lagermitarbeiter(Datenbank datenbank) {
		
		this.datenbank = datenbank;
		
	}
 
	public int produktortVeraendern(String ort, String name) {
		
		return datenbank.produktortVeraendern(ort, name);
		
	}
 
	public int zuRegalHinzufuegen(int anzahl, String name) {
		
		return datenbank.produktanzahlVeraendern(anzahl, false, name);		
		
	}
	
	public int zuLagerHinzufuegen(int anzahl, String name) {
		
		int temp = datenbank.produktanzahlVeraendern(anzahl, true, name);
		datenbank.einkaufszahlenErhoehen(anzahl, name);
		return temp;
		
	}
	
	public int ausRegalEntfernen(int anzahl, String name) {
		
		return datenbank.produktanzahlVeraendern(-anzahl, false, name);
		
	}
	
	public int ausLagerEntfernen(int anzahl, String name) {
		
		return datenbank.produktanzahlVeraendern(-anzahl, true, name);
		
	}

}