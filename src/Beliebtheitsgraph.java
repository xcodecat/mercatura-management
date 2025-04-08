import java.util.LinkedList;

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
		Produkt temp = warenkorb.removeFirst();
		
		for(int k = 0; k < warenkorb.size(); k++) {
			for (int i = 0; i < graph.length; i++) {
				if(produkte[i].getName() == temp.getName()) {
					for(int j = 0; j < graph.length; j++) {
						Produkt temp2 = warenkorb.removeFirst();
						if(produkte[j].getName() == temp2.getName()) {
							if(i != j) {
								graph[i][j]++;
								graph[j][i]++;
							}
						}
						warenkorb.add(temp2);
					}
				}
			}
		}
		
		if(!warenkorb.isEmpty()) {
			warenkorbHinzufuegen(warenkorb);
		} else if (warenkorb.isEmpty()) {
			ausgeben();
		}
		
	}
	
	public void ausgeben() {
		for (int i = 0; i < graph.length; i++) {
			for(int j = 0; j < graph.length; j++) {
				System.out.print(graph[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
}