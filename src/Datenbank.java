import java.io.*;
import java.util.LinkedList;

/**
 * Zentrale Datenhaltung für Produkte und Finanzen des Supermarkts
 */
public class Datenbank {
	/** Liste aller Produkte des Supermarkts */
	private LinkedList<Produkt> produkte;
	/** gesamter Umsatz */
	private double umsatz;
	/** Liste aller Tagesumsätze */
	private LinkedList<Double> tage;

	/**
	 * erzeugt eine neue Datenbank, lädt Produkte und Finanzen aus Dateien
	 */
	public Datenbank() {
		this.produkte = produkteLaden();
		this.umsatz = 0.0;
		this.tage = finanzenLaden();
	}

	public LinkedList<Produkt> produkteAusgeben() {
		return produkte;
	}

	public Produkt[] arrayAusgeben() {
		Produkt[] ptemp = new Produkt[produkte.size()];
		for (int i = 0; i < produkte.size(); i++) {
			ptemp[i] = produkte.get(i);
		}
		return ptemp;
	}

	public Produkt produktSuchen(String name) {
		for (Produkt p : produkte) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public boolean produktEntfernen(String name) {
		Produkt p = produktSuchen(name);
		if (p != null) {
			produkte.remove(p);
			produkteSpeichern();
			return true;
		}
		return false;
	}

	public void produktEinfuegen(Produkt produkt) {
		produkte.add(produkt);
		produkteSpeichern();
	}

	public double getUmsatz() {
		return umsatz;
	}

	public void umsatzErhoehen(double betrag) {
		this.umsatz += betrag;
	}

	public LinkedList<Double> finanzenAusgeben() {
		return tage;
	}

	public void tagAbschliessen() {
		tage.add(umsatz);
		finanzenSpeichern();
	}

	public int produktortVeraendern(String ort, String name) {
		if (ortChecken(ort)) {
			Produkt p = produktSuchen(name);
			if (p == null) return 2;
			p.setOrt(ort);
			produkteSpeichern();
			return 0;
		} else {
			return 1;
		}
	}

	public boolean ortChecken(String ort) {
		int z = 0;
		for (Produkt p : produkte) {
			if (p.getOrt().equals(ort)) {
				z++;
			}
		}
		return z <= 3;
	}

	public int produktanzahlVeraendernK(int anzahl, String name) {
		Produkt p = produktSuchen(name);
		if (p != null) {
			p.setRegalanzahl(p.getRegalanzahl() - anzahl);
			produkteSpeichern();
			umsatz += p.getPreis() * anzahl;
			return 0;
		}
		return 1;
	}

	public int produktanzahlVeraendern(int anzahl, boolean ort, String name) {
		Produkt p = produktSuchen(name);
		if (p == null) return 1;

		if (ort) { // Lager
			if (p.getLageranzahl() + anzahl < 0) return 2;
			p.setLageranzahl(p.getLageranzahl() + anzahl);
		} else { // Regal
			if (p.getOrt().equals("Lager")) return 4;
			if (anzahl > 0 && p.getLageranzahl() - anzahl < 0) return 5;
			if (p.getRegalanzahl() + anzahl < 0) return 3;
			if (anzahl > 0) p.setLageranzahl(p.getLageranzahl() - anzahl);
			p.setRegalanzahl(p.getRegalanzahl() + anzahl);
		}

		produkteSpeichern();
		return 0;
	}

	public void einkaufszahlenErhoehen(int anzahl, String name) {
		Produkt p = produktSuchen(name);
		if (p != null) {
			p.setEinkaufszahlen(p.getEinkaufszahlen() + anzahl);
			produkteSpeichern();
		}
	}

	/**
	 * Speichert alle Produktdaten in die Datei "produkte.csv".
	 * Jedes Produkt wird als eine Zeile mit Semikolon-getrennten Werten geschrieben.
	 */
	public void produkteSpeichern() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("produkte.csv"))) {

			// Iteriere über alle Produkte in der Liste
			for (Produkt p : produkte) {
				// Schreibe alle Eigenschaften des Produkts in einer Zeile, getrennt durch Semikolon
				// Format: Name;Ort;Lageranzahl;Regalanzahl;Preis;Verkaufszahlen;Einkaufspreis;Einkaufszahlen
				bw.write(p.getName() + ";" +
						p.getOrt() + ";" +
						p.getLageranzahl() + ";" +
						p.getRegalanzahl() + ";" +
						p.getPreis() + ";" +
						p.getVerkaufszahlen() + ";" +
						p.getEinkaufspreis() + ";" +
						p.getEinkaufszahlen());
				bw.newLine(); // neue Zeile für das nächste Produkt
			}

		} catch (IOException e) {
			// Fehler beim Schreiben der Datei – z. B. wenn Datei gesperrt oder nicht vorhanden ist
			e.printStackTrace();
		}
	}

	/**
	 * Lädt die Produktdaten aus der Datei "produkte.csv".
	 * Erwartet eine Kopfzeile mit Spaltennamen in der ersten Zeile.
	 * @return Liste von Produkten aus der Datei
	 */
	private LinkedList<Produkt> produkteLaden() {
		LinkedList<Produkt> list = new LinkedList<>();
		File file = new File("produkte.csv");

		// Wenn Datei nicht existiert, gib leere Liste zurück
		if (!file.exists()) return list;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String zeile;
			boolean ersteZeile = true;

			while ((zeile = br.readLine()) != null) {
				// Erste Zeile überspringen (Kopfzeile mit Spaltennamen)
				if (ersteZeile) {
					ersteZeile = false;
					continue;
				}

				// Spalten anhand des Trennzeichens (;) aufteilen
				String[] teile = zeile.split(";");

				// Prüfen, ob genau 8 Spalten vorhanden sind
				if (teile.length == 8) {
					String name = teile[0];
					String ort = teile[1];
					int lager = Integer.parseInt(teile[2]);
					int regal = Integer.parseInt(teile[3]);

					// Preise können Kommas statt Punkten enthalten → umwandeln
					double preis = Double.parseDouble(teile[4].replace(",", "."));
					int verkauf = Integer.parseInt(teile[5]);
					double ek = Double.parseDouble(teile[6].replace(",", "."));
					int ekZahl = Integer.parseInt(teile[7]);

					// Produkt zur Liste hinzufügen
					list.add(new Produkt(name, ort, lager, regal, preis, verkauf, ek, ekZahl));
				}
			}
		} catch (IOException e) {
			e.printStackTrace(); // Fehlerausgabe bei Leseproblemen
		}

		return list; // Rückgabe der geladenen Produkte
	}

	/**
	 * Lädt die Liste aller Tagesumsätze aus der Datei "finanzen.csv".
	 * Erwartet eine Zahl pro Zeile (ein Umsatzwert).
	 * @return LinkedList mit den bisherigen Tagesumsätzen
	 */
	private LinkedList<Double> finanzenLaden() {
		LinkedList<Double> liste = new LinkedList<>();
		File file = new File("finanzen.csv");

		// Wenn die Datei nicht existiert, gib leere Liste zurück
		if (!file.exists()) return liste;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String zeile;

			// Zeile für Zeile einlesen
			while ((zeile = br.readLine()) != null) {
				// Komma in Dezimalzahlen (z. B. 12,99) durch Punkt ersetzen
				zeile = zeile.replace(",", ".");

				// In double parsen und zur Liste hinzufügen
				liste.add(Double.parseDouble(zeile));
			}

		} catch (IOException e) {
			e.printStackTrace(); // Fehler bei Dateioperationen ausgeben
		}

		return liste; // Rückgabe der Umsatzliste
	}

	/**
	 * Speichert die Liste aller Tagesumsätze in die Datei "finanzen.csv".
	 * Jeder Umsatzwert wird in eine eigene Zeile geschrieben.
	 */
	public void finanzenSpeichern() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("finanzen.csv"))) {

			// Für jeden Tagesumsatz ...
			for (Double betrag : tage) {
				// Schreibe den Betrag in die Datei (z. B. 12.99)
				bw.write(String.valueOf(betrag));
				bw.newLine(); // neue Zeile für nächsten Wert
			}

		} catch (IOException e) {
			// Bei einem Fehler während des Schreibens in die Datei
			e.printStackTrace();
		}
	}
}
