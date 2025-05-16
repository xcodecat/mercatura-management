import java.util.LinkedList;

public class Geschaeftsfuehrer extends Controller {

	private LinkedList<Double> tage; // Umsatzverlauf pro Tag
	private double umsatzGestern;
	protected Datenbank datenbank;
	private boolean tagBeendet = false; // wurde der Tag bereits beendet?

	public Geschaeftsfuehrer(Datenbank datenbank) {
		this.datenbank = datenbank;
		this.umsatzGestern = datenbank.getUmsatz();
		this.tage = datenbank.finanzenAusgeben(); // aus CSV laden
	}

	public void produktEinfuegen(int lageranzahl, int regalanzahl, double preis, double einkaufspreis, String name, String ort, int verkaufszahlen, int einkaufszahlen) {
		Produkt produkt = new Produkt(name, ort, lageranzahl, regalanzahl, preis, verkaufszahlen, einkaufspreis, einkaufszahlen);
		datenbank.produktEinfuegen(produkt);
	}

	public void produktEntfernen(String name) {
		datenbank.produktEntfernen(name);
	}

	public Produkt produktSuchen(String name) {
		return datenbank.produktSuchen(name);
	}

	/**
	 * Beendet den aktuellen Tag und berechnet den Tagesumsatz (Differenz zum Vortag).
	 * Der Umsatz wird in der Datenbank gespeichert.
	 * @return Umsatz des Tages
	 */
	public double tagBeenden() {
		if (tagBeendet) return -1;

		double heutigerUmsatz = datenbank.getUmsatz();
		double tagesUmsatz = heutigerUmsatz - umsatzGestern;
		umsatzGestern = heutigerUmsatz;

		datenbank.tagAbschliessen();            // Umsatz in CSV speichern
		this.tage = datenbank.finanzenAusgeben(); // CSV neu laden

		tagBeendet = true;
		return tagesUmsatz;
	}

	public void neuerTagStartet() {
		tagBeendet = false;
	}

	public boolean istTagBeendet() {
		return tagBeendet;
	}

	/**
	 * Gibt den zuletzt abgeschlossenen Tagesumsatz zurück.
	 * @return Umsatz von gestern
	 */
	public double finanzenGestern() {
		if (tage.isEmpty()) return 0.0;
		return tage.getLast();
	}

	/**
	 * Gibt die Historie aller abgeschlossenen Tagesumsätze zurück.
	 * @return Liste von Tagesumsätzen
	 */
	public LinkedList<Double> finanzenAusgeben() {
		return new LinkedList<>(tage); // Kopie zur Sicherheit
	}

	/**
	 * Gibt den aktuellen Gesamtumsatz zurück.
	 */
	public double gesamtUmsatz() {
		return datenbank.getUmsatz();
	}

	/**
	 * Durchschnittlicher Tagesumsatz.
	 */
	public double durchschnittsUmsatz() {
		if (tage.isEmpty()) return 0.0;
		return tage.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
	}

	/**
	 * Gibt die Anzahl abgeschlossener Verkaufstage zurück.
	 */
	public int anzahlTage() {
		return tage.size();
	}
}