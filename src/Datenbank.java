import java.util.LinkedList;

public class Datenbank {
	
	private LinkedList<Produkt> produkte;
	private double umsatz;
	
	public Datenbank() {
		
		produkte = ProduktSpeicher.laden();
		
	}

	public LinkedList<Produkt> produkteAusgeben() {
		
		return produkte;
		
	}
	
	public Produkt[] arrayAusgeben() {
		
		Produkt[] ptemp = new Produkt[produkte.size()];
		for(int i = 0; i < produkte.size(); i++) {
			
			ptemp[i] = produkte.get(i);
			
		}
		return ptemp;
		
	}
	
	public int produktortVeraendern(String ort, String name) {
		
		if(ortChecken(ort) == true) {
			
			if(produktSuchen(name) == null) {
				
				return 2;
				
			} else {
				
				produktSuchen(name).setOrt(ort);
				ProduktSpeicher.speichern(produkte);
				return 0;
				
			}
			
		} 
		
		else {
			
			return 1;
			
		}
		
	}
	
	public boolean ortChecken(String ort) {	
		
		int z = 0;
		for(int i = 0; i < produkte.size(); i++) {
			
			if(produkte.get(i).getOrt() == ort) {
				
				z++;
				
			}
			
		}
		
		if(z > 3) {
			
			return false;
			
		} else {
			
			return true;
			
		}
		
	}
	
	public void produktanzahlVeraendernK(int anzahl, String name) {
		
		if(produktSuchen(name) != null) {
			
			Produkt ptemp = produktSuchen(name);
			ptemp.setRegalanzahl(ptemp.getRegalanzahl() - anzahl);
			ProduktSpeicher.speichern(produkte);
			umsatz = umsatz + ptemp.getPreis();
			
		}
				
	}
	
	public int produktanzahlVeraendern(int anzahl, boolean ort, String name) {
		
		if(ort == true) {
			
			if(produktSuchen(name) != null) {
				
				Produkt ptemp = produktSuchen(name);
				if(ptemp.getLageranzahl() + anzahl < 0) {
					
					return 2;
					
				}
				ptemp.setLageranzahl(ptemp.getLageranzahl() + anzahl);
				ProduktSpeicher.speichern(produkte);
				return 0;
				
			} else {
				
				return 1;
				
			}
			
		}
		
		if(ort == false) {
			
			if(produktSuchen(name) != null) {
				
				Produkt ptemp = produktSuchen(name);
				if(ptemp.getOrt().equals("Lager")) {
					
					return 4;
					
				}				
				if(anzahl > 0) {
					if(ptemp.getLageranzahl() - anzahl < 0) {
						
						return 5;
						
					}
					ptemp.setLageranzahl(ptemp.getLageranzahl() - anzahl);
					
				}
				if(ptemp.getRegalanzahl() + anzahl < 0) {
					
					return 3;
					
				}
				ptemp.setRegalanzahl(ptemp.getRegalanzahl() + anzahl);
				ProduktSpeicher.speichern(produkte);
				return 0;
				
			} else {
				
				return 1;
				
			}
			
		}
		
		return 6;
		
	}
	
	public void produktEinfuegen(Produkt produkt) {
		
		produkte.add(produkt);
		ProduktSpeicher.speichern(produkte);
		
	}
	
	public boolean produktEntfernen(String name) {
		
		Produkt ptemp = produktSuchen(name);
		
		if(ptemp != null) {
			
			produkte.remove(ptemp);
			ProduktSpeicher.speichern(produkte);
			return true;
		
		}
		
		return false;
		
	}

	public double getUmsatz() {
		
		return umsatz;
		
	}
	
	public Produkt produktSuchen(String name) {
		
		for(int i = 0; i < produkte.size(); i++) {
			
			if(produkte.get(i).getName().equals(name)) {
				
				return produkte.get(i);
				
			}
			
		}
		
		return null;
		
	}
	
	public void einkaufszahlenErhoehen (int anzahl, String name) {
		
		Produkt ptemp = produktSuchen(name);
		if(ptemp != null) {
			
			ptemp.setEinkaufszahlen(ptemp.getEinkaufszahlen() + anzahl);
			
		}
		ProduktSpeicher.speichern(produkte);
		
	}

}