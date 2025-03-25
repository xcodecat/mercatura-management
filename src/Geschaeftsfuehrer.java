import java.util.LinkedList;

public class Geschaeftsfuehrer extends Controller {
	
	private LinkedList<Double> tage;
	private double umsatzGestern;
	
	public Geschaeftsfuehrer(Datenbank datenbank) {
		
		umsatzGestern = datenbank.getUmsatz();
		this.datenbank = datenbank;
		tage = new LinkedList<Double>();

	}
	public void produktEinfuegen(int lageranzahl, int regalanzahl, double preis, double einkaufspreis, String name, String ort, int verkaufszahlen, int einkaufszahlen) {
	
		Produkt produkt = new Produkt(name, ort, lageranzahl, regalanzahl, preis, verkaufszahlen, einkaufspreis, einkaufszahlen);
		datenbank.produktEinfuegen(produkt);
	
	}

	public void produktEntfernen(String name) {
	
		datenbank.produktEntfernen(name);

	}

	public Produkt produktSuchen (String name) {
	
		return datenbank.produktSuchen(name);

	}

	/*public void produktPreisVeraendern(String name, double preis) {
	 * 
	datenbank.produktPreisVeraendern(name, preis);
	
	}*/

	public double tagBeenden() {
	
		double tag = datenbank.getUmsatz() - umsatzGestern;
		tage.add(tag);
		umsatzGestern = datenbank.getUmsatz();
		return tag;
 
	}
	
	public double finanzenGestern() {
		
		return tage.getLast();
		
	
	}

	public LinkedList<Double> finanzenAusgeben(){
	
		return this.tage;

	}

}
