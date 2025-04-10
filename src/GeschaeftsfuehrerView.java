import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GUI-Oberfläche für den Geschäftsführer zur Verwaltung von Produkten.
 * Produkte können hinzugefügt, entfernt, angezeigt und bearbeitet werden.
 */
public class GeschaeftsfuehrerView {

    /** Hauptfenster der Anwendung */
    private JFrame frame;
    /** Eingabefelder für Produktinformationen */
    private JTextField nameField, ortField, lageranzahlField, regalanzahlField, preisField, einkaufspreisField, verkaufszahlenField, einkaufszahlenField;
    /** Buttons zum Hinzufügen, Entfernen und Speichern */
    private JButton einfuegenButton, entfernenButton, speichernButton;
    /** Liste zur Anzeige der vorhandenen Produkte */
    private JList<String> produktListe;
    /** Model für die Produktliste */
    private DefaultListModel<String> produktListModel;
    /** Instanz des Geschäftsführers */
    private Geschaeftsfuehrer geschaeftsfuehrer;
    /** Verweis auf die zentrale Datenbank */
    private Datenbank datenbank;

    /**
     * Hilfsmethode: Parsen eines Strings in eine Ganzzahl, oder Default-Wert zurückgeben.
     * @param text Text, der geparst werden soll
     * @param defaultValue Standardwert, falls Parsing fehlschlägt
     * @return Integer-Wert oder defaultValue
     */
//    private int parseOrDefault(String text, int defaultValue) {
//        try {
//            return text.trim().isEmpty() ? defaultValue : Integer.parseInt(text.trim());
//        } catch (NumberFormatException e) {
//            return defaultValue;
//        }
//    }

    /**
     * Konstruktor: Erstellt die grafische Oberfläche und initialisiert alle Komponenten.
     * @param datenbank Gemeinsame Produkt-Datenbank
     */
    public GeschaeftsfuehrerView(Datenbank datenbank) {
        this.datenbank = datenbank;
        this.geschaeftsfuehrer = new Geschaeftsfuehrer(datenbank);

        frame = new JFrame("Produktverwaltung");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel eingabePanel = new JPanel(new GridLayout(8, 2, 5, 5));
        eingabePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();

        ortField = new JTextField("Lager");
        ortField.setEditable(false);
        ortField.setBackground(new Color(230, 230, 230));
        ortField.setForeground(Color.GRAY);

        lageranzahlField = new JTextField("0");
        lageranzahlField.setEditable(false);
        lageranzahlField.setBackground(new Color(230, 230, 230));
        lageranzahlField.setForeground(Color.GRAY);

        regalanzahlField = new JTextField("0");
        regalanzahlField.setEditable(false);
        regalanzahlField.setBackground(new Color(230, 230, 230));
        regalanzahlField.setForeground(Color.GRAY);

        preisField = new JTextField();
        einkaufspreisField = new JTextField();

        verkaufszahlenField = new JTextField("0");
        verkaufszahlenField.setEditable(false);
        verkaufszahlenField.setBackground(new Color(230, 230, 230));
        verkaufszahlenField.setForeground(Color.GRAY);

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

        produktListe.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                int index = produktListe.locationToIndex(evt.getPoint());
                if (index >= 0) {
                    produktListe.setSelectedIndex(index);
                    String name = produktListe.getSelectedValue();
                    Produkt p = datenbank.produktSuchen(name);
                    if (p == null) return;

                    if (SwingUtilities.isLeftMouseButton(evt)) {
                        nameField.setText(p.getName());
                        ortField.setText(p.getOrt());
                        preisField.setText(String.valueOf(p.getPreis()));
                        einkaufspreisField.setText(String.valueOf(p.getEinkaufspreis()));
                        verkaufszahlenField.setText(String.valueOf(p.getVerkaufszahlen()));
                        einkaufszahlenField.setText(String.valueOf(p.getEinkaufszahlen()));
                        // Lager- und Regalanzahl nur setzen, wenn Felder nicht leer
                        if (!lageranzahlField.getText().trim().isEmpty()) {
                            lageranzahlField.setText(String.valueOf(p.getLageranzahl()));
                        }
                        if (!regalanzahlField.getText().trim().isEmpty()) {
                            regalanzahlField.setText(String.valueOf(p.getRegalanzahl()));
                        }
                    } else if (SwingUtilities.isRightMouseButton(evt)) {
                        nameField.setText("");
                        ortField.setText("Lager");
                        lageranzahlField.setText("0");
                        regalanzahlField.setText("0");
                        preisField.setText("");
                        einkaufspreisField.setText("");
                        verkaufszahlenField.setText("0");
                        einkaufszahlenField.setText("");
                        produktListe.clearSelection();
                    }
                }
            }
        });

        einfuegenButton = new JButton("Produkt hinzufügen");
        entfernenButton = new JButton("Produkt entfernen");
        speichernButton = new JButton("Speichern");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(einfuegenButton);
        buttonPanel.add(entfernenButton);
        buttonPanel.add(speichernButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        einfuegenButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) throw new IllegalArgumentException("Produktname darf nicht leer sein.");

                double preis = Double.parseDouble(preisField.getText().trim());
                double einkaufspreis = Double.parseDouble(einkaufspreisField.getText().trim());
                int einkaufszahlen = Integer.parseInt(einkaufszahlenField.getText().trim());
                String ort = ortField.getText().trim();

                int lageranzahl = 0;
                int regalanzahl = 0;
                int verkaufszahlen = 0;

                geschaeftsfuehrer.produktEinfuegen(lageranzahl, regalanzahl, preis, einkaufspreis, name, ort, verkaufszahlen, einkaufszahlen);
                produktListModel.addElement(name);
                ProduktSpeicher.speichern(datenbank.produkteAusgeben());

                nameField.setText("");
                preisField.setText("");
                einkaufspreisField.setText("");
                einkaufszahlenField.setText("");
                lageranzahlField.setText("0");

                JOptionPane.showMessageDialog(frame, "Produkt erfolgreich hinzugefügt.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Bitte gültige Zahlen eingeben.", "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        entfernenButton.addActionListener(e -> {
            String selected = produktListe.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(frame, "Bitte ein Produkt auswählen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            geschaeftsfuehrer.produktEntfernen(selected);
            produktListModel.removeElement(selected);
            ProduktSpeicher.speichern(datenbank.produkteAusgeben());
            JOptionPane.showMessageDialog(frame, "Produkt entfernt.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
        });

        speichernButton.addActionListener(e -> {
            String selectedName = produktListe.getSelectedValue();
            if (selectedName == null) {
                JOptionPane.showMessageDialog(frame, "Bitte ein Produkt auswählen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Produkt p = datenbank.produktSuchen(selectedName);
            if (p == null) return;

            try {
                p.setName(nameField.getText().trim());
                p.setOrt(ortField.getText().trim());
                p.setPreis(Double.parseDouble(preisField.getText().trim()));
                p.setEinkaufspreis(Double.parseDouble(einkaufspreisField.getText().trim()));
                p.setVerkaufszahlen(Integer.parseInt(verkaufszahlenField.getText().trim()));
                p.setEinkaufszahlen(Integer.parseInt(einkaufszahlenField.getText().trim()));

                if (!lageranzahlField.getText().trim().isEmpty()) {
                    p.setLageranzahl(Integer.parseInt(lageranzahlField.getText().trim()));
                }
                if (!regalanzahlField.getText().trim().isEmpty()) {
                    p.setRegalanzahl(Integer.parseInt(regalanzahlField.getText().trim()));
                }

                produktListModel.setElementAt(p.getName(), produktListe.getSelectedIndex());
                ProduktSpeicher.speichern(datenbank.produkteAusgeben());

                JOptionPane.showMessageDialog(frame, "Produkt aktualisiert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Ungültige Eingabe. Bitte Werte prüfen.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }
}
