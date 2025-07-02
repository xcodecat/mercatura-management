import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Zentrale Datenhaltung für Produkte und Finanzen des Supermarkts.
 */
public class Datenbank {

    /**
     * Liste aller Produkte des Supermarkts
     */
    private LinkedList<Produkt> produkte;
    
    /**
     * Liste aller Views
     */
    private LinkedList<View> views;

    /**
     * gesamter Umsatz
     */
    private double umsatz;

    /**
     * Liste aller Tagesumsätze
     */
    private LinkedList<Double> tage;

    /**
     * Pfad zum Datenordner relativ zur laufenden JAR-Datei.
     * Wird automatisch beim Start ermittelt und ggf. angelegt.
     */
    private static final Path BASIS_VERZEICHNIS = initialisiereDatenordner();

    /**
     * Pfade zu den einzelnen Dateien im Datenordner
     */
    private static final Path DATEI_PRODUKTE = BASIS_VERZEICHNIS.resolve("produkte.csv");
    private static final Path DATEI_FINANZEN = BASIS_VERZEICHNIS.resolve("finanzen.csv");
    private static final Path DATEI_STATUS = BASIS_VERZEICHNIS.resolve("tagstatus.txt");

    /**
     * Erzeugt eine neue Datenbank, lädt Produkte und Finanzen aus Dateien
     */
    public Datenbank() {
        this.produkte = produkteLaden();
        this.umsatz = 0.0;
        this.tage = finanzenLaden();
        views = new LinkedList<View>();
    }

    /**
     * Ermittelt den Pfad zum aktuellen JAR-Verzeichnis und erstellt den Unterordner "daten".
     *
     * @return Pfad zum Datenverzeichnis
     */
    private static Path initialisiereDatenordner() {
        try {
            Path jarVerzeichnis = Paths.get(Datenbank.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()).getParent();

            Path datenVerzeichnis = jarVerzeichnis.resolve("daten");
            Files.createDirectories(datenVerzeichnis); // Ordner anlegen falls nicht vorhanden
            return datenVerzeichnis;
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback: lokal im Projektverzeichnis
            return Paths.get("daten");
        }
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

    public void produktEinfuegen(Produkt produkt) {
        produkte.add(produkt);
        produkteSpeichern();
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

    public Produkt produktSuchen(String name) {
        for (Produkt p : produkte) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public void einkaufszahlenErhoehen(int anzahl, String name) {
        Produkt p = produktSuchen(name);
        if (p != null) {
            p.setEinkaufszahlen(p.getEinkaufszahlen() + anzahl);
            produkteSpeichern();
        }
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

    public String getProduktort(String name) {
        Produkt temp = produktSuchen(name);
        return temp.getOrt();
    }

    /**
     * Speichert alle Produktdaten in die Datei "produkte.csv".
     * Jedes Produkt wird als eine Zeile mit Semikolon-getrennten Werten geschrieben.
     */
    public void produkteSpeichern() {
        produkte.sort(Comparator.comparing(Produkt::getName, String.CASE_INSENSITIVE_ORDER));
        try (BufferedWriter bw = Files.newBufferedWriter(DATEI_PRODUKTE)) {
            for (Produkt p : produkte) {
                bw.write(p.getName() + ";" +
                        p.getOrt() + ";" +
                        p.getLageranzahl() + ";" +
                        p.getRegalanzahl() + ";" +
                        p.getPreis() + ";" +
                        p.getVerkaufszahlen() + ";" +
                        p.getEinkaufspreis() + ";" +
                        p.getEinkaufszahlen());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        update();
    }

    /**
     * Lädt die Produktdaten aus der Datei "produkte.csv".
     * Erwartet keine Kopfzeile.
     *
     * @return Liste von Produkten aus der Datei
     */
    private LinkedList<Produkt> produkteLaden() {
        LinkedList<Produkt> list = new LinkedList<>();
        if (!Files.exists(DATEI_PRODUKTE)) return list;

        try (BufferedReader br = Files.newBufferedReader(DATEI_PRODUKTE)) {
            String zeile;
            while ((zeile = br.readLine()) != null) {
                String[] teile = zeile.split(";");
                if (teile.length == 8) {
                    String name = teile[0];
                    String ort = teile[1];
                    int lager = Integer.parseInt(teile[2]);
                    int regal = Integer.parseInt(teile[3]);
                    double preis = Double.parseDouble(teile[4].replace(",", "."));
                    int verkauf = Integer.parseInt(teile[5]);
                    double ek = Double.parseDouble(teile[6].replace(",", "."));
                    int ekZahl = Integer.parseInt(teile[7]);
                    list.add(new Produkt(name, ort, lager, regal, preis, verkauf, ek, ekZahl));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Lädt die Liste aller Tagesumsätze aus der Datei "finanzen.csv".
     * Erwartet eine Zahl pro Zeile (ein Umsatzwert).
     *
     * @return LinkedList mit den bisherigen Tagesumsätzen
     */
    private LinkedList<Double> finanzenLaden() {
        LinkedList<Double> liste = new LinkedList<>();
        if (!Files.exists(DATEI_FINANZEN)) return liste;

        try (BufferedReader br = Files.newBufferedReader(DATEI_FINANZEN)) {
            String zeile;
            while ((zeile = br.readLine()) != null) {
                zeile = zeile.replace(",", ".");
                liste.add(Double.parseDouble(zeile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return liste;
    }

    /**
     * Speichert die Liste aller Tagesumsätze in die Datei "finanzen.csv".
     * Jeder Umsatzwert wird in eine eigene Zeile geschrieben.
     */
    public void finanzenSpeichern() {
        try (BufferedWriter bw = Files.newBufferedWriter(DATEI_FINANZEN)) {
            for (Double betrag : tage) {
                bw.write(String.valueOf(betrag));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Speichert das Datum des letzten abgeschlossenen Tages in die Datei "tagstatus.txt"
     *
     * @param datum Datum des Tagesabschlusses
     */
    public void speichereLetztesDatum(LocalDate datum) {
        try (BufferedWriter writer = Files.newBufferedWriter(DATEI_STATUS)) {
            writer.write(datum.toString());
        } catch (IOException e) {
            System.err.println("Fehler beim Speichern des Tagesabschluss-Datums: " + e.getMessage());
        }
    }

    /**
     * Lädt das Datum des letzten abgeschlossenen Tages aus der Datei "tagstatus.txt"
     *
     * @return LocalDate des letzten Abschlusses oder null, wenn keine Datei vorhanden ist
     */
    public LocalDate ladeLetztesDatum() {
        try {
            if (!Files.exists(DATEI_STATUS)) return null;
            String zeile = Files.readString(DATEI_STATUS).trim();
            if (!zeile.isEmpty()) {
                return LocalDate.parse(zeile);
            }
        } catch (IOException e) {
            // Datei existiert nicht oder konnte nicht gelesen werden
        }
        return null;
    }
    
    /**
     * fügt View in Liste der Views hinzu
     * @param view View, der hinzugefügt wird
     */
    public void viewEinfuegen(View view) {
    	
    	views.add(view);
    	
    }
    
    /**
     * aktualisiert alle Views
     */
    public void update() {
    	
    	for(int i = 0; i < views.size(); i++) {
    		
    		views.get(i).update();
    		
    	}
    	
    }
    
}