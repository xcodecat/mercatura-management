public class Produkt {
	
	private String name;
	private String ort;
	private int lageranzahl;
	private int regalanzahl;
	private double preis;
	private int verkaufszahlen;
	private double einkaufspreis;
	private int einkaufszahlen;
	
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