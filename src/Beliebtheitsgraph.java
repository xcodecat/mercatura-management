import java.util.LinkedList;

public class Beliebtheitsgraph {
	private int[][] graph;
	private Produkt[] produkte;
	//private Datenbank datenbank;
	//private LinkedList<Produkt> warenkorb;

	public Beliebtheitsgraph(Datenbank datenbank) {
		//this.datenbank = datenbank;
		produkte = datenbank.arrayAusgeben();
		graph = new int[produkte.length][produkte.length];
		}
	
	public void warenkorbHinzufuegen(LinkedList<Produkt> warenkorb) {
		Produkt temp = warenkorb.removeFirst();
		
		for(int i = 0; i < produkte.length; i++) {
			
			if (produkte[i].getName() == temp.getName()) {
				
				for(int j = 0; j < produkte.length; j++) {
					temp = warenkorb.removeFirst();
					
					if(produkte[j].getName() == temp.getName()) {
						graph[i][j]++;
						graph[j][i]++;
					}
					
					warenkorb.addLast(temp);
				}
			}
		}
		
		warenkorbHinzufuegen(warenkorb);
	}
}