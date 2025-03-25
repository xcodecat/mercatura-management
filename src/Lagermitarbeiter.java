public class Lagermitarbeiter extends Controller {
 
	public Lagermitarbeiter(Datenbank datenbank) {
		
		this.datenbank = datenbank;
		
	}
 
	public boolean produktortVeraendern(String ort, String name) {
		
		return datenbank.produktortVeraendern(ort, name);
		
	}
 
	public boolean zuRegalHinzufuegen(int anzahl, String name) {
		
		boolean temp = datenbank.produktanzahlVeraendern(anzahl, false, name);
		return temp;
		
	}
	
	public boolean zuLagerHinzufuegen(int anzahl, String name) {
		
		boolean temp = datenbank.produktanzahlVeraendern(anzahl, true, name);
		datenbank.einkaufszahlenErhoehen(anzahl, name);
		return temp;
		
	}
	
	public boolean ausRegalEntfernen(int anzahl, String name) {
		
		boolean temp = datenbank.produktanzahlVeraendern(-anzahl, false, name);
		return temp;
		
	}
	
	public boolean ausLagerEntfernen(int anzahl, String name) {
		
		boolean temp = datenbank.produktanzahlVeraendern(-anzahl, true, name);
		return temp;
		
	}

}
