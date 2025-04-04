public class Produkt {
	
	/**
	 * Name des Produkts
	 */
	private String name;
	/**
	 * Ort des Produkts ("Lager" oder Regalort "BuchstabeZahl")
	 */
	private String ort;
	/**
	 * Anzahl der der einzelnen Ausführungen des Produkts im Lager
	 */
	private int lageranzahl;
	/**
	 * Anzahl der der einzelnen Ausführungen des Produkts im Regal
	 */
	private int regalanzahl;
	/**
	 * Verkaufspreis des Produkts
	 */
	private double preis;
	/**
	 * Anzahl der verkauften Stücke
	 */
	private int verkaufszahlen;
	/**
	 * Einkaufspreis des Produkts
	 */
	private double einkaufspreis;
	/**
	 * Anzahl der gekauften Stücke
	 */
	private int einkaufszahlen;
	
	/**
	 * Erstellt ein neues Produkt mit den gegebenen Paramentern
	 * @param name Name des Produkts
	 * @param ort Ort des Produkts ("Lager" oder Regalort "BuchstabeZahl")
	 * @param lageranzahl Anzahl der der einzelnen Ausführungen des Produkts im Lager
	 * @param regalanzahl Anzahl der der einzelnen Ausführungen des Produkts im Regal
	 * @param preis Verkaufspreis des Produkts
	 * @param verkaufszahlen Anzahl der verkauften Stücke
	 * @param einkaufspreis Einkaufspreis des Produkts
	 * @param einkaufszahlen Anzahl der gekauften Stücke
	 */
	public Produkt(String name, String ort, int lageranzahl, int regalanzahl, double preis, int verkaufszahlen, double einkaufspreis, int einkaufszahlen) {
		
		this.name = name;
		this.ort = ort;
		this.lageranzahl = lageranzahl;
		this.regalanzahl = regalanzahl;
		this.preis = preis;
		this.verkaufszahlen = verkaufszahlen;
		this.einkaufspreis = einkaufspreis;
		this.einkaufszahlen = einkaufszahlen;
		
	}

	public String getName() {
		
		return name;
		
	}

	public void setName(String name) {
		
		this.name = name;
		
	}

	public String getOrt() {
		
		return ort;
		
	}

	public void setOrt(String ort) {
		
		this.ort = ort;
		
	}

	public int getLageranzahl() {
		
		return lageranzahl;
		
	}

	public void setLageranzahl(int lageranzahl) {
		
		this.lageranzahl = lageranzahl;
		
	}

	public int getRegalanzahl() {
		
		return regalanzahl;
		
	}

	public void setRegalanzahl(int regalanzahl) {
		
		this.regalanzahl = regalanzahl;
		
	}

	public double getPreis() {
		
		return preis;
		
	}

	public void setPreis(double preis) {
		
		this.preis = preis;
		
	}

	public int getVerkaufszahlen() {
		
		return verkaufszahlen;
		
	}

	public void setVerkaufszahlen(int verkaufszahlen) {
		
		this.verkaufszahlen = verkaufszahlen;
		
	}

	public double getEinkaufspreis() {
		
		return einkaufspreis;
		
	}

	public void setEinkaufspreis(double einkaufspreis) {
		
		this.einkaufspreis = einkaufspreis;
		
	}

	public int getEinkaufszahlen() {
		
		return einkaufszahlen;
		
	}

	public void setEinkaufszahlen(int einkaufszahlen) {
		
		this.einkaufszahlen = einkaufszahlen;
		
	}

}