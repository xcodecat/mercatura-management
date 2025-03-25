public class Kunde extends Views {
	
	private Datenbank datenbank;
	
	public Kunde (Datenbank datenbank) {
		
		this.datenbank = datenbank;
		
	}
	
	public void produktSuchen(String name) { //evtl Eingabeparameter, wenn nach Dropdow-Menu gebraucht
		
		Produkt produkt = datenbank.produktSuchen(name);
		produkt.getOrt();
		//produkt.getBild();
		
	}
	
}