public class Kunde extends Controller {
	
	private Datenbank datenbank;
	
	public Kunde (Datenbank datenbank) {
		
		this.datenbank = datenbank;
		
	}
	
	public Produkt produktSuchen(String name) { //evtl Eingabeparameter, wenn nach Dropdow-Menu gebraucht
		
		Produkt produkt = datenbank.produktSuchen(name);
		return produkt;
		
	}
	
}