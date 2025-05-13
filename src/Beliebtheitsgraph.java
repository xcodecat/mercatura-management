import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class Beliebtheitsgraph {
	private int[][] graph;
	private Produkt[] produkte;
	//private Datenbank datenbank;
	private LinkedList<Produkt> warenkorb;

	public Beliebtheitsgraph(Datenbank datenbank) {
		//this.datenbank = datenbank;
		produkte = datenbank.arrayAusgeben();
		graph = new int[produkte.length][produkte.length];
		}
	
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


	//linkedList mit beliebten produkten, anzahl produkte variabel, Spalte zweidimensionales Feld durchgehen; höchste vorne niedrigste Hinten
	public LinkedList<String> beliebtheitAusgeben(String name, int anzahl) {
		
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
	
	private int indexFinden(String name) {
	    for (int i = 0; i < produkte.length; i++) {
	        if (produkte[i].getName().equals(name)) {
	            return i;
	        }
	    }
	    return -1;
	}
	
}