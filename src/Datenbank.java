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
		
		Produkt[] ptemp = (Produkt[]) produkte.toArray();
		return ptemp;
		
	}
	
	public boolean produktortVeraendern(String ort, String name) {
		
		if(ortChecken(ort) == true) {
			
			if(produktSuchen(name) == null) {
				
				return false;
				
			} else {
				
				produktSuchen(name).setOrt(ort);
				return true;
				
			}
			
		} 
		
		else {
			
			return false;
			
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
			umsatz = umsatz + ptemp.getPreis();
			
		}
				
	}
	
	public boolean produktanzahlVeraendern(int anzahl, boolean ort, String name) {
		
		if(ort == true) {
			
			if(produktSuchen(name) != null) {
				
				Produkt ptemp = produktSuchen(name);
				ptemp.setLageranzahl(ptemp.getLageranzahl() + anzahl);
				return true;
				
			} else {
				
				return false;
				
			}
			
		}
		
		if(ort == false) {
			
			if(produktSuchen(name) != null) {
						
				Produkt ptemp = produktSuchen(name);
				ptemp.setRegalanzahl(ptemp.getRegalanzahl() + anzahl);
				if(anzahl > 0) {
					
					ptemp.setLageranzahl(ptemp.getLageranzahl() - anzahl);
					
				}
				return true;
				
			} else {
				
				return false;
				
			}
			
		}
		
		return false;
		
	}
	
	public void produktEinfuegen(Produkt produkt) {
		
		produkte.add(produkt);
		ProduktSpeicher.speichern(produkte);
		
	}
	
	public void produktEntfernen(String name) {
		
		Produkt ptemp = produktSuchen(name);
		
		if(ptemp != null) {
			
			produkte.remove(ptemp);
			ProduktSpeicher.speichern(produkte);
		
		}
		
	}

	public double getUmsatz() {
		
		return umsatz;
		
	}
	
	public Produkt produktSuchen(String name) {
		
		for(int i = 0; i < produkte.size(); i++) {
			
			if(produkte.get(i).getName() == name) {
				
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
		
	}

}