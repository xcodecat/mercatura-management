import java.util.LinkedList;

public class Datenbank {
	/**
	 * Liste aller Produkte des Supermarkts
	 */
	private LinkedList<Produkt> produkte;
	/**
	 * gesamter Umsatz
	 */
	private double umsatz;
	
	/**
	 * erzeugt eine neue Datenbank, die die Produkte aus einer permanenten Datei läd
	 */
	public Datenbank() {
		
		produkte = ProduktSpeicher.laden();
		
	}
	
	public LinkedList<Produkt> produkteAusgeben() {
		
		return produkte;
		
	}
	
	/**
	 * gibt die Liste aller Produkte als Array aus
	 * @return Produkt[]
	 */
	public Produkt[] arrayAusgeben() {
		
		Produkt[] ptemp = new Produkt[produkte.size()];
		for(int i = 0; i < produkte.size(); i++) {
			
			ptemp[i] = produkte.get(i);
			
		}
		return ptemp;
		
	}
	
	/**
	 * verändert den Ort eines Produkts
	 * @param ort  Regalort des Produkts im Format BuchstabeZahl
	 * @param name  Name des veränderten Produkts
	 * @return int, der Art eines möglichen Fehlers angibt
	 * 0: kein Fehler
	 * 1: Ort nicht vorhanden oder schon 4 Produkte an diesem Ort (--> voll)
	 * 2: Produkt nicht vorhanden (oder z.B. Name falsch geschrieben)
	 */
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
	
	/**
	 * überprüft, ob ein Regalort bereits besetzt ist (4 Produkte --> besetzt)
	 * @param ort Ort, der gecheckt werden soll (Format BuchstabeZahl)
	 * @return true, wenn frei; false, wenn besetzt
	 */
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
	
	/**
	 * Spezielle Methode für Verkäufer
	 * entfernt das gekaufte Produkt aus dem Regal und rechnet den Verkaufspreis auf den Umsatz auf
	 * @param anzahl des verkauften Produkts
	 * @param name des verkauften Produkts
	 * @return mögliche Fehlermeldungen
	 * 0: kein Fehler
	 * 1: Name nicht vorhanden (oder falsch geschrieben)
	 */
	public int produktanzahlVeraendernK(int anzahl, String name) {
		
		if(produktSuchen(name) != null) {
			
			Produkt ptemp = produktSuchen(name);
			ptemp.setRegalanzahl(ptemp.getRegalanzahl() - anzahl);
			ProduktSpeicher.speichern(produkte);
			umsatz = umsatz + ptemp.getPreis();
			return 0;
			
		}
		
		return 1;
				
	}
	
	/**
	 * verändert die Anzahl der Produkte in Lager oder Regal(gesteuert durch boolean)
	 * entfernen durch eine negative Anzahl
	 * bei Hinzufügen zu Regal wird dieselbe Anzahl aus Lager entfernt
	 * @param anzahl, gibt Anzahl der hinzugefügten (anzahl > 0) oder der entfernten (anzahl < 0) Produkte an
	 * @param ort als boolean: true, wenn in Lager; false, wenn in Regal
	 * @param name des betreffenden Produkts
	 * @return int, der einen möglichen Fehler angibt
	 * 0: kein Fehler
	 * 1: Name nicht vorhanden (oder falsch geschrieben)
	 * 2: mehr aus Lager entfernt als vorhanden ist
	 * 3: mehr aus Regal entfernt als vorhanden ist
	 * 4: kein Regalort zugewiesen
	 * 5: mehr zu Regal hinzugefügt, als im Lager vorhanden ist
	 */
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
	
	/**
	 * fügt neues Produkt zu Liste hinzu, schreibt es in die permantenten Datei
	 * @param produkt, das eingefügt werden soll
	 */
	public void produktEinfuegen(Produkt produkt) {
		
		produkte.add(produkt);
		ProduktSpeicher.speichern(produkte);
		
	}
	
	/**
	 * entfernt ein Produkt aus der Liste, speichert diese Änderung in der permanenten Datei
	 * @param name des zu entfernenden Produktes
	 * @return true, wenn erfolgreich entfernt; false, wenn Produkt nicht vorhanden
	 */
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
	
	/**
	 * sucht ein Produkt anhand des Namen und gibt dies aus
	 * @param name des gesuchten Produkts
	 * @return gesuchtes Element der Klasse Produkt oder null, wenn kein Produkt gefunden wurde
	 */
	public Produkt produktSuchen(String name) {
		
		for(int i = 0; i < produkte.size(); i++) {
			
			if(produkte.get(i).getName().equals(name)) {
				
				return produkte.get(i);
				
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * erhöht die im Produkt gespeicherten Einkaufszahlen
	 * @param anzahl, der verkauften Stücke
	 * @param name, des betreffenden Produkts
	 */
	public void einkaufszahlenErhoehen (int anzahl, String name) {
		
		Produkt ptemp = produktSuchen(name);
		if(ptemp != null) {
			
			ptemp.setEinkaufszahlen(ptemp.getEinkaufszahlen() + anzahl);
			ProduktSpeicher.speichern(produkte);
			
		}
		
	}

}
