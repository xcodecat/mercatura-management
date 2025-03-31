import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GeschaeftsfuehrerView {
    private JFrame frame;
    private JTextField nameField, ortField, lageranzahlField, regalanzahlField, preisField, einkaufspreisField, verkaufszahlenField, einkaufszahlenField;
    private JButton einfuegenButton, entfernenButton, bearbeitenButton;
    private JList<String> produktListe;
    private DefaultListModel<String> produktListModel;
    private Geschaeftsfuehrer geschaeftsfuehrer;
    private Datenbank datenbank;

    private int parseOrDefault(String text, int defaultValue) {
        try {
            return text.trim().isEmpty() ? defaultValue : Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public GeschaeftsfuehrerView(Datenbank datenbank) {
        this.datenbank = datenbank;
        this.geschaeftsfuehrer = new Geschaeftsfuehrer(datenbank);

        frame = new JFrame("Geschäftsführer - Produktverwaltung");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel eingabePanel = new JPanel(new GridLayout(8, 2, 5, 5));
        eingabePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        //Nicht bearbeitbar, da Ort zuerst Lager
        ortField = new JTextField("Lager"); ortField.setEditable(false);
        //Nicht bearbeitbar, da Ort zuerst Lager (ohne spezifiziertes Regal)
        lageranzahlField = new JTextField("0"); lageranzahlField.setEditable(false);
        //Nicht bearbeitbar, da Ort zuerst Lager (ohne spezifiziertes Regal)
        regalanzahlField = new JTextField("0"); regalanzahlField.setEditable(false);
        preisField = new JTextField();
        einkaufspreisField = new JTextField();
        //Nicht bearbeitbar, da neu bestellt und noch nicht verkauft.
        verkaufszahlenField = new JTextField("0"); verkaufszahlenField.setEditable(false);
        einkaufszahlenField = new JTextField();

        eingabePanel.add(new JLabel("Produktname:")); eingabePanel.add(nameField);
        eingabePanel.add(new JLabel("Ort:")); eingabePanel.add(ortField);
        eingabePanel.add(new JLabel("Lageranzahl:")); eingabePanel.add(lageranzahlField);
        eingabePanel.add(new JLabel("Regalanzahl:")); eingabePanel.add(regalanzahlField);
        eingabePanel.add(new JLabel("Preis:")); eingabePanel.add(preisField);
        eingabePanel.add(new JLabel("Einkaufspreis:")); eingabePanel.add(einkaufspreisField);
        eingabePanel.add(new JLabel("Verkaufszahlen:")); eingabePanel.add(verkaufszahlenField);
        eingabePanel.add(new JLabel("Einkaufszahlen:")); eingabePanel.add(einkaufszahlenField);

        frame.add(eingabePanel, BorderLayout.NORTH);

        produktListModel = new DefaultListModel<>();
        produktListe = new JList<>(produktListModel);
        JScrollPane scrollPane = new JScrollPane(produktListe);
        scrollPane.setPreferredSize(new Dimension(500, 250));
        frame.add(scrollPane, BorderLayout.CENTER);

        for (Produkt p : datenbank.produkteAusgeben()) {
            produktListModel.addElement(p.getName());
        }

        // Rechtsklick auf Produktliste: Popup anzeigen
        produktListe.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                int index = produktListe.locationToIndex(evt.getPoint());
                if (index >= 0) {
                    produktListe.setSelectedIndex(index);
                    String name = produktListe.getSelectedValue();
                    Produkt p = datenbank.produktSuchen(name);
                    if (p == null) return;

                    // Popup nur bei Rechtsklick
                    if (SwingUtilities.isLeftMouseButton(evt)) {
                    	// Felder bei Rechtsklick aktualisieren (für Bearbeiten)
                        nameField.setText(p.getName());
                        ortField.setText(p.getOrt());
                        lageranzahlField.setText(String.valueOf(p.getLageranzahl()));
                        regalanzahlField.setText(String.valueOf(p.getRegalanzahl()));
                        preisField.setText(String.valueOf(p.getPreis()));
                        einkaufspreisField.setText(String.valueOf(p.getEinkaufspreis()));
                        verkaufszahlenField.setText(String.valueOf(p.getVerkaufszahlen()));
                        einkaufszahlenField.setText(String.valueOf(p.getEinkaufszahlen()));
                    }
                    
                    if(SwingUtilities.isRightMouseButton(evt)) {
                    	nameField.setText("");
                    	ortField.setText("Lager");
                    	lageranzahlField.setText("0");
                    	regalanzahlField.setText("0");
                    	preisField.setText("");
                    	einkaufspreisField.setText("");
                    	verkaufszahlenField.setText("0");
                    	einkaufszahlenField.setText("");
                    }
                }
            }
        });
        einfuegenButton = new JButton("Produkt hinzufügen");
        entfernenButton = new JButton("Produkt entfernen");
        bearbeitenButton = new JButton("Bearbeiten");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(einfuegenButton);
        buttonPanel.add(entfernenButton);
        buttonPanel.add(bearbeitenButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        einfuegenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    if (name.isEmpty()) throw new IllegalArgumentException("Produktname darf nicht leer sein.");

                    double preis = Double.parseDouble(preisField.getText().trim());
                    double einkaufspreis = Double.parseDouble(einkaufspreisField.getText().trim());
                    int einkaufszahlen = Integer.parseInt(einkaufszahlenField.getText().trim());

                    String ort = ortField.getText().trim();
                    int regalanzahl = parseOrDefault(regalanzahlField.getText(), 0);
                    int verkaufszahlen = parseOrDefault(verkaufszahlenField.getText(), 0);
                    int lageranzahl = einkaufszahlen;

                    Produkt vorhanden = datenbank.produktSuchen(name);
                    if (vorhanden != null) {
                        vorhanden.setOrt(ort);
                        vorhanden.setLageranzahl(lageranzahl);
                        vorhanden.setRegalanzahl(regalanzahl);
                        vorhanden.setPreis(preis);
                        vorhanden.setEinkaufspreis(einkaufspreis);
                        vorhanden.setVerkaufszahlen(verkaufszahlen);
                        vorhanden.setEinkaufszahlen(einkaufszahlen);
                        JOptionPane.showMessageDialog(frame, "Produkt aktualisiert.", "Update", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        geschaeftsfuehrer.produktEinfuegen(lageranzahl, regalanzahl, preis, einkaufspreis, name, ort, verkaufszahlen, einkaufszahlen);
                        produktListModel.addElement(name);
                        JOptionPane.showMessageDialog(frame, "Neues Produkt hinzugefügt!", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                    }

                    nameField.setText("");
                    preisField.setText("");
                    einkaufspreisField.setText("");
                    einkaufszahlenField.setText("");
                    lageranzahlField.setText("0");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Bitte gültige Zahlen eingeben. '.' anstatt ','", "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Ein unerwarteter Fehler ist aufgetreten: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        entfernenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedProduct = produktListe.getSelectedValue();
                    if (selectedProduct == null) {
                        JOptionPane.showMessageDialog(frame, "Bitte ein Produkt aus der Liste auswählen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    geschaeftsfuehrer.produktEntfernen(selectedProduct);
                    produktListModel.removeElement(selectedProduct);
                    JOptionPane.showMessageDialog(frame, "Produkt entfernt.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Ein unerwarteter Fehler ist aufgetreten: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        bearbeitenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedName = produktListe.getSelectedValue();
                if (selectedName == null) {
                    JOptionPane.showMessageDialog(frame, "Bitte ein Produkt auswählen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Produkt p = datenbank.produktSuchen(selectedName);
                if (p == null) return;

                try {
                    p.setOrt(ortField.getText().trim());
                    p.setPreis(Double.parseDouble(preisField.getText().trim()));
                    p.setEinkaufspreis(Double.parseDouble(einkaufspreisField.getText().trim()));
                    p.setEinkaufszahlen(Integer.parseInt(einkaufszahlenField.getText().trim()));
                    p.setVerkaufszahlen(Integer.parseInt(verkaufszahlenField.getText().trim()));
                    p.setLageranzahl(Integer.parseInt(lageranzahlField.getText().trim()));
                    p.setRegalanzahl(Integer.parseInt(regalanzahlField.getText().trim()));

                    JOptionPane.showMessageDialog(frame, "Produkt aktualisiert.", "Bearbeiten", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Ungültige Eingaben – bitte Zahlen prüfen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }
}