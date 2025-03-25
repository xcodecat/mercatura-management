import java.io.*;
import java.util.LinkedList;

public class ProduktSpeicher {
    private static final String DATEI = "produkte.txt";

    public static void speichern(LinkedList<Produkt> produkte) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATEI))) {
            for (Produkt p : produkte) {
                writer.println(
                    p.getName() + ";" +
                    p.getOrt() + ";" +
                    p.getLageranzahl() + ";" +
                    p.getRegalanzahl() + ";" +
                    p.getPreis() + ";" +
                    p.getVerkaufszahlen() + ";" +
                    p.getEinkaufspreis() + ";" +
                    p.getEinkaufszahlen()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<Produkt> laden() {
        LinkedList<Produkt> produkte = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATEI))) {
            String zeile;
            while ((zeile = reader.readLine()) != null) {
                String[] teile = zeile.split(";");
                if (teile.length == 8) {
                    String name = teile[0];
                    String ort = teile[1];
                    int lageranzahl = Integer.parseInt(teile[2]);
                    int regalanzahl = Integer.parseInt(teile[3]);
                    double preis = Double.parseDouble(teile[4]);
                    int verkaufszahlen = Integer.parseInt(teile[5]);
                    double einkaufspreis = Double.parseDouble(teile[6]);
                    int einkaufszahlen = Integer.parseInt(teile[7]);

                    Produkt p = new Produkt(name, ort, lageranzahl, regalanzahl, preis, verkaufszahlen, einkaufspreis, einkaufszahlen);
                    produkte.add(p);
                }
            }
        } catch (IOException e) {
        }
        return produkte;
    }
}