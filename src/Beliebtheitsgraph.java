import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class Beliebtheitsgraph {
	private int[][] graph;
	private Produkt[] produkte;
	private Datenbank datenbank;
	private LinkedList<Produkt> warenkorb;

	/**
	 * erstellt eine neuen Beliebtheitsgraph
	 * es holt sich den Array der Produkte und speichert diesen
	 * erstellt den Graph als zweidimensionales Feld 
	 * @param datenbank
	 */
	public Beliebtheitsgraph(Datenbank datenbank) {
		this.datenbank = datenbank;
		produkte = datenbank.arrayAusgeben();
		graph = new int[produkte.length][produkte.length];
		}
	
	/**
	 * fügt den neuen Warenkorb in den Beliebtsheitsgraph ein
	 * @param warenkorb		Warenkorb eines Kunden
	 */
	public void warenkorbHinzufuegen(LinkedList<Produkt> w) {
	    this.warenkorb = w;
		
	    for (int i = 0; i < warenkorb.size(); i++) {
	        Produkt temp1 = warenkorb.get(i);
	        int index1 = indexFinden(temp1.getName());
	        
	        for (int j = i + 1; j < warenkorb.size(); j++) {
	            Produkt temp2 = warenkorb.get(j);
	            int index2 = indexFinden(temp2.getName());
	            
	            graph[index1][index2]++;
	            graph[index2][index1]++;
	        }
	    }
	}


	/**
	 * gibt für ein Produkt eine Liste von Produkten zurück die mit dem Produkt oft zusammen gekauft wurden
	 * @param name		Name des Produkts HILFE
	 * @param anzahl	Anzahl,wie viele Produkte ausgegeben werden sollen
	 * @return 	Liste der Produkte
	 * 			null, wenn Name nicht vorhanden
	 */
	public LinkedList<String> beliebtheitAusgeben(String name, int anzahl) {
		
		if(datenbank.produktSuchen(name) == null) {
			
			return null;
			
		}
		
		int indexProdukt = indexFinden(name);
		    
		LinkedList<String> ergebnis = new LinkedList<>();
		// Liste die [index, wert] des Produkts speichern
		ArrayList<int[]> wertIndexListe = new ArrayList<>();

		//initiert 
		for(int j = 0; j < graph.length; j++) {
			if(j != indexProdukt) {
				int wert = graph[indexProdukt][j];
				int[] wertIndex = new int[2];
				wertIndex[0] = j;
				wertIndex[1] = wert;
		        wertIndexListe.add(wertIndex);
			}
		}

		// Sortieren nach Wert des Graphens (absteigend)
		wertIndexListe.sort((a, b) -> Integer.compare(b[1], a[1]));

		//gibt die gewünschte Anzahl an Produkten mit der Beliebtheit aus
		for (int i = 0; i < anzahl; i++) {
			int produktIndex = wertIndexListe.get(i)[0];
		    String produktName = produkte[produktIndex].getName();
		    ergebnis.addLast(produktName);
		    System.out.println(/*(i + 1) + ": " +*/ produktName + " (Beliebtheit: " + wertIndexListe.get(i)[1] + ")");
		}
		
		return ergebnis;
		   
	}
	
	/**
	 * findet den Index eines bestimmten Produkts
	 * @param name
	 * @return Index, wenn es das Produkt gibt 
	 * wenn -1 dann gibt es dieses Produkt nicht
	 */
	private int indexFinden(String name) {
	    for (int i = 0; i < produkte.length; i++) {
	        if (produkte[i].getName().equals(name)) {
	            return i;
	        }
	    }
	    return -1;
	}
	
}