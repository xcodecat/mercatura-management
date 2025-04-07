package Supermarkt.src;

import java.util.LinkedList;

public class Kunde extends Controller {
	/**
	 * Datenbank des Supermarkts
	 */
	private Datenbank datenbank;
	/**
	 * Ordnet die beim erstellen angegebene Datenbank dem eigenen Atribut zu
	 * @param datenbank
	 */
	public Kunde (Datenbank datenbank) {
		
		this.datenbank = datenbank;
		
	}
	/**
	 * Gibt das gesuchte Produkt an den View weiter
	 * @param name Name des gesuchten Produkts
	 * @return Element der Klasse Produkt
	 */
	public Produkt produktSuchen(String name) { 
		
		return datenbank.produktSuchen(name);
	}
	/**
	 * Gibt die LinkedList der Produkte an den View weiter
	 * @return Element der Klasse LinkedList
	 */
	public LinkedList<Produkt> produkteAusgeben() {
		
		return datenbank.produkteAusgeben();
		
		}
	
}