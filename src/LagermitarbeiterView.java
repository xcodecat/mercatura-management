import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LagermitarbeiterView {
	
	/**
	 * Frames der Grafikoberfläche des LagermitarbeitersViews
	 */
	private JFrame frame_main, frame_ort, frame_lager, frame_regal;
	/**
	 * Buttons der Grafikoberfläche des LagermitarbeitersViews
	 */
	private JButton ort, regal, lager, ort_ver, regal_hinzu, regal_ent, lager_hinzu, lager_ent;
	/**
	 * Textfelder der Grafikoberfläche des LagermitarbeitersViews
	 */
	private JTextField ort_ort, ort_name, regal_name, regal_anzahl, lager_name, lager_anzahl;
	/**
	 * Lagermitarbeiter(Controller), an den Inputs weitergegeben werden
	 */
	private Lagermitarbeiter lma;
	
	/**
	 * erstellt einen neue LagermitarbeiterView
	 * @param datenbank Datenbank, mit der Lagermitarbeiter arbeitet
	 */
	public LagermitarbeiterView(Datenbank datenbank, Beliebtheitsgraph beliebtheitsgraph) {
		
		lma = new Lagermitarbeiter(datenbank, beliebtheitsgraph);
		
		frame_main = new JFrame("Lagermitarbeiter - Menü");
        frame_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_main.setSize(300, 200);
        frame_main.setLayout(new GridLayout(3, 1));
        frame_main.setResizable(false);
        frame_main.setLocation(700, 500);
        
        ort = new JButton("Ort verändern");
        regal = new JButton("Regalanzahl verändern");
        lager = new JButton("Lageranzahl verändern");
        
        frame_main.add(ort);
        frame_main.add(regal);
        frame_main.add(lager);
        
        ort.addActionListener(new ActionListener() {
        	
        	/**
        	 * ruft Frame des Ortes auf
        	 * @param e
        	 */
			public void actionPerformed(ActionEvent e) {
				
				frame_ort.setVisible(true);
				
			}
        	
        });
        
        regal.addActionListener(new ActionListener() {
        	
        	/**
        	 * ruft Frame des Regals auf
        	 * @param e
        	 */
			public void actionPerformed(ActionEvent e) {
				
				frame_regal.setVisible(true);
				
			}
        	
        });
        
        lager.addActionListener(new ActionListener() {
        	
        	/**
        	 * ruft Frame des Lagers auf
        	 * @param e
        	 */
			public void actionPerformed(ActionEvent e) {
				
				frame_lager.setVisible(true);
				
			}
        	
        });
        
        frame_main.setVisible(true);
        
        
        
		
        frame_ort = new JFrame("Lagermitarbeiter - OrtVerändern");
        frame_ort.setSize(400, 200);
        frame_ort.setLayout(new GridLayout(3, 2));
        frame_ort.setResizable(false);
        frame_ort.setLocation(700, 500);
        
        frame_ort.add(new JLabel("Produktname:"));
        ort_name = new JTextField();
        frame_ort.add(ort_name);
        frame_ort.add(new JLabel("Neuer Produktort:"));
        ort_ort = new JTextField();
        frame_ort.add(ort_ort);
        ort_ver = new JButton("Ort verändern");
        frame_ort.add(new JLabel(""));
        frame_ort.add(ort_ver);
        
        ort_ver.addActionListener(new ActionListener() {
        	
        	/**
        	 * verändert den Ort durch Weitergabe der Inputs name und ort an den Lagermitarbeiter
        	 * gibt Fehlermeldungen aus
        	 * @param e
        	 */
        	public void actionPerformed(ActionEvent e) {
        		
        		String name = ort_name.getText().trim();
        		String ort = ort_ort.getText().trim();
        		int fehler = lma.produktortVeraendern(ort, name);
        		if(fehler == 0) {
        			
        			JOptionPane.showMessageDialog(frame_ort, "Ort erfolgreich geändert!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
        			
        		}
        		if(fehler == 1) {
        			
        			JOptionPane.showMessageDialog(frame_ort, "Ort nicht verfügbar", "Fehler", JOptionPane.ERROR_MESSAGE);
        			
        		}
        		if(fehler == 2) {
        			
        			JOptionPane.showMessageDialog(frame_ort, "Name nicht vorhanden", "Fehler", JOptionPane.ERROR_MESSAGE);
        			
        		}
        		ort_name.setText("");
        		ort_ort.setText("");
        		
        	}
        	
        });
        
        frame_ort.setVisible(false);
        
        
        
        frame_regal = new JFrame("Lagermitarbeiter - RegalanzahlVerändern");
        frame_regal.setSize(400, 200);
        frame_regal.setLayout(new GridLayout(3, 2));
        frame_regal.setResizable(false);
        frame_regal.setLocation(700, 500);
        
        frame_regal.add(new JLabel("Produktname:"));
        regal_name = new JTextField();
        frame_regal.add(regal_name);
        frame_regal.add(new JLabel("Anzahl:"));
        regal_anzahl = new JTextField();
        frame_regal.add(regal_anzahl);
        regal_hinzu = new JButton("zu Regal hinzufügen");
        regal_ent = new JButton("aus Regal entfernen");
        frame_regal.add(regal_hinzu);
        frame_regal.add(regal_ent);
        
        regal_hinzu.addActionListener(new ActionListener(){
        	
        	/**
        	 * fügt Produkte zum Regal durch Weitergabe der Inputs name und anzahl an den Lagermitarbeiter hinzu
        	 * gibt Fehlermeldungen aus
        	 * @param e
        	 */
			public void actionPerformed(ActionEvent e) {
				
				try {
				
					String name = regal_name.getText().trim();
					int anzahl = Integer.parseInt(regal_anzahl.getText().trim());
					int fehler = lma.zuRegalHinzufuegen(anzahl, name);
					if(fehler == 0) {
	        			
	        			JOptionPane.showMessageDialog(frame_regal, "Anzahl erfolgreich geändert!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
	        			
	        		}
	        		if(fehler == 1) {
	        			
	        			JOptionPane.showMessageDialog(frame_regal, "Name nicht verhanden", "Fehler", JOptionPane.ERROR_MESSAGE);
	        			
	        		}
	        		if(fehler == 4) {
	        			
	        			JOptionPane.showMessageDialog(frame_regal, "Keine Regalzuweisung", "Fehler", JOptionPane.ERROR_MESSAGE);
	        			
	        		}
	        		if(fehler == 5) {
	
	        			JOptionPane.showMessageDialog(frame_regal, "Zu viel zu Regal hinzugefügt", "Fehler", JOptionPane.ERROR_MESSAGE);
	
	        		}
	        		regal_name.setText("");
	        		regal_anzahl.setText("");
				
				}
				
				catch (NumberFormatException ex) {
					
                JOptionPane.showMessageDialog(frame_regal, "Bitte gültige Zahlen eingeben. Dezimalzahlen bitte mit Punkt statt Komma einfügen!", "Fehler", JOptionPane.ERROR_MESSAGE);
				
				}
			
			}
			
        });
        
        regal_ent.addActionListener(new ActionListener(){
        	
        	/**
        	 * entfernt Produkte aus dem Regal durch Weitergabe der Inputs name und anzahl an den Lagermitarbeiter
        	 * gibt Fehlermeldungen aus
        	 * @param e
        	 */
			public void actionPerformed(ActionEvent e) {
				
				try {
				
					String name = regal_name.getText().trim();
					int anzahl = Integer.parseInt(regal_anzahl.getText().trim());
					int fehler = lma.ausRegalEntfernen(anzahl, name);
					if(fehler == 0) {
	        			
	        			JOptionPane.showMessageDialog(frame_regal, "Anzahl erfolgreich geändert!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
	        			
	        		}
	        		if(fehler == 1) {
	        			
	        			JOptionPane.showMessageDialog(frame_regal, "Name nicht verhanden", "Fehler", JOptionPane.ERROR_MESSAGE);
	        			
	        		}
	        		if(fehler == 3) {
	        			
	        			JOptionPane.showMessageDialog(frame_regal, "Zu viel aus Regal entfernt", "Fehler", JOptionPane.ERROR_MESSAGE);
	        			
	        		}
	        		if(fehler == 4) {
	        			
	        			JOptionPane.showMessageDialog(frame_regal, "Keine Regalzuweisung", "Fehler", JOptionPane.ERROR_MESSAGE);
	        			
	        		}
	        		regal_name.setText("");
	        		regal_anzahl.setText("");
				
				}
				
				catch (NumberFormatException ex) {
					
                JOptionPane.showMessageDialog(frame_regal, "Bitte gültige Zahlen eingeben. Dezimalzahlen bitte mit Punkt statt Komma einfügen!", "Fehler", JOptionPane.ERROR_MESSAGE);
				
				}
			
			}
			
        });
        
        frame_regal.setVisible(false);
        
        
        
        frame_lager = new JFrame("Lagermitarbeiter - LageranzahlVerändern");
        frame_lager.setSize(400, 200);
        frame_lager.setLayout(new GridLayout(3, 2));
        frame_lager.setResizable(false);
        frame_lager.setLocation(700, 500);
        
        frame_lager.add(new JLabel("Produktname:"));
        lager_name = new JTextField();
        frame_lager.add(lager_name);
        frame_lager.add(new JLabel("Anzahl:"));
        lager_anzahl = new JTextField();
        frame_lager.add(lager_anzahl);
        lager_hinzu = new JButton("zu Lager hinzufügen");
        lager_ent = new JButton("aus Lager entfernen");
        frame_lager.add(lager_hinzu);
        frame_lager.add(lager_ent);
        
        lager_hinzu.addActionListener(new ActionListener(){
        	
        	/**
        	 * fügt Produkt zum Lager durch Weitergabe der Inputs name und anzahl an den Lagermitarbeiter hinzu
        	 * @param e
        	 */
			public void actionPerformed(ActionEvent e) {
				
				try {
				
					String name = lager_name.getText().trim();
					int anzahl = Integer.parseInt(lager_anzahl.getText().trim());
					int fehler = lma.zuLagerHinzufuegen(anzahl, name);
					if(fehler == 0) {
	        			
	        			JOptionPane.showMessageDialog(frame_lager, "Anzahl erfolgreich geändert!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
	        			
	        		}
	        		if(fehler == 1) {
	        			
	        			JOptionPane.showMessageDialog(frame_lager, "Name nicht verhanden", "Fehler", JOptionPane.ERROR_MESSAGE);
	        			
	        		}
	        		lager_name.setText("");
	        		lager_anzahl.setText("");
				
				}
				
				catch (NumberFormatException ex) {
					
                JOptionPane.showMessageDialog(frame_lager, "Bitte gültige Zahlen eingeben. Dezimalzahlen bitte mit Punkt statt Komma einfügen!", "Fehler", JOptionPane.ERROR_MESSAGE);
				
				}
			
			}
			
        });
        
        lager_ent.addActionListener(new ActionListener(){
        	
        	/**
        	 * entfernt Produkte aus dem Lager durch Weitergabe der Inputs name und anzahl an den Lagermitarbeiter
        	 * @param e
        	 */
			public void actionPerformed(ActionEvent e) {
				
				try {
				
					String name = lager_name.getText().trim();
					int anzahl = Integer.parseInt(lager_anzahl.getText().trim());
					int fehler = lma.ausLagerEntfernen(anzahl, name);
					if(fehler == 0) {
	        			
	        			JOptionPane.showMessageDialog(frame_lager, "Anzahl erfolgreich geändert!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
	        			
	        		}
	        		if(fehler == 1) {
	        			
	        			JOptionPane.showMessageDialog(frame_lager, "Name nicht verhanden", "Fehler", JOptionPane.ERROR_MESSAGE);
	        			
	        		}
	        		if(fehler == 2) {
	        			
	        			JOptionPane.showMessageDialog(frame_regal, "Zu viel aus Lager entfernt", "Fehler", JOptionPane.ERROR_MESSAGE);
	        			
	        		}
	        		regal_name.setText("");
	        		regal_anzahl.setText("");
				
				}
				
				catch (NumberFormatException ex) {
					
                JOptionPane.showMessageDialog(frame_lager, "Bitte gültige Zahlen eingeben. Dezimalzahlen bitte mit Punkt statt Komma einfügen!", "Fehler", JOptionPane.ERROR_MESSAGE);
				
				}
			
			}
			
        });        
        
        frame_lager.setVisible(false);
        
        
	}

}