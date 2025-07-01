import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class KassiererView extends View{
    private DefaultListModel<String> produktListModel;
    private DefaultListModel<String> warenkorbListModel;
    private JList<String> produktListe;
    private JList<String> warenkorbListe;
    private JTextField anzahlField;
    private JLabel gesamtpreisLabel;
    private Kassierer kassierer;

    private LinkedList<WarenkorbEintrag> warenkorb;

    public KassiererView(Datenbank datenbank, Beliebtheitsgraph beliebtheitsgraph) {
        this.kassierer = new Kassierer(datenbank, beliebtheitsgraph);
        this.warenkorb = new LinkedList<>();
        datenbank.viewEinfuegen(this);

        frame = new JFrame("Kassierer - Verkauf");
        frame.setSize(800, 550);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Produktliste
        produktListModel = new DefaultListModel<>();
        produktListe = new JList<>(produktListModel);
        for (Produkt p : kassierer.produkteAusgeben()) {
            produktListModel.addElement(p.getName());
        }
        produktListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane produktScroll = new JScrollPane(produktListe);

        produktListe.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                int index = produktListe.locationToIndex(evt.getPoint());
                if (index >= 0) {
                    produktListe.setSelectedIndex(index);
                    Produkt p = kassierer.produktSuchen(produktListe.getSelectedValue());
                    if (SwingUtilities.isRightMouseButton(evt) && p != null) {
                        JOptionPane.showMessageDialog(frame,
                                "Preis: " + p.getPreis() + " €",
                                "Produktpreis",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // Warenkorb
        warenkorbListModel = new DefaultListModel<>();
        warenkorbListe = new JList<>(warenkorbListModel);
        JScrollPane warenkorbScroll = new JScrollPane(warenkorbListe);

        // Eingabefeld + Buttons
        JPanel eingabePanel = new JPanel();
        eingabePanel.setLayout(new BoxLayout(eingabePanel, BoxLayout.Y_AXIS));

        anzahlField = new JTextField("1");
        anzahlField.setMaximumSize(new Dimension(150, 30));
        anzahlField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton verkaufenButton = new JButton("In Warenkorb");
        JButton entfernenButton = new JButton("Entfernen");
        JButton kassierenButton = new JButton("Kassieren");

        verkaufenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        entfernenButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        kassierenButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        gesamtpreisLabel = new JLabel("Gesamtpreis: 0.00 €");
        gesamtpreisLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        eingabePanel.add(new JLabel("Anzahl:"));
        eingabePanel.add(anzahlField);
        eingabePanel.add(Box.createVerticalStrut(10));
        eingabePanel.add(verkaufenButton);
        eingabePanel.add(entfernenButton);
        eingabePanel.add(Box.createVerticalStrut(10));
        eingabePanel.add(kassierenButton);
        eingabePanel.add(Box.createVerticalStrut(10));
        eingabePanel.add(gesamtpreisLabel);

        // Hauptpanel mit drei Spalten
        JPanel hauptPanel = new JPanel(new BorderLayout(10, 10));
        hauptPanel.add(produktScroll, BorderLayout.WEST);
        hauptPanel.add(eingabePanel, BorderLayout.CENTER);
        hauptPanel.add(warenkorbScroll, BorderLayout.EAST);

        produktScroll.setPreferredSize(new Dimension(250, 400));
        warenkorbScroll.setPreferredSize(new Dimension(300, 400));

        frame.add(hauptPanel, BorderLayout.CENTER);

        // Aktionen
        verkaufenButton.addActionListener(e -> {
            String name = produktListe.getSelectedValue();
            if (name == null) {
                JOptionPane.showMessageDialog(frame, "Bitte ein Produkt auswählen.");
                return;
            }

            int anzahl = parseOrDefault(anzahlField.getText(), 1);
            if (anzahl <= 0) {
                JOptionPane.showMessageDialog(frame, "Anzahl muss größer als 0 sein.");
                return;
            }

            Produkt produktGefunden = kassierer.produktSuchen(name);
            if (produktGefunden == null) return;

            if (produktGefunden.getRegalanzahl() < anzahl) {
                JOptionPane.showMessageDialog(frame, "Nicht genug im Regal vorhanden!");
                return;
            }

            kassierer.kassieren(name, anzahl);
            warenkorb.add(new WarenkorbEintrag(name, anzahl));
            warenkorbListModel.addElement(name + " × " + anzahl);
            updateGesamtpreis();
            anzahlField.setText("1");
        });

        kassierenButton.addActionListener(e -> {
            if (warenkorb.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Warenkorb ist leer.");
                return;
            }

            kassierer.warenkorbBeenden();
            warenkorb.clear();
            warenkorbListModel.clear();
            updateGesamtpreis();
            JOptionPane.showMessageDialog(frame, "Warenkorb abgeschlossen.");
        });

        entfernenButton.addActionListener(e -> {
            int index = warenkorbListe.getSelectedIndex();
            if (index >= 0 && index < warenkorb.size()) {
                warenkorb.remove(index);
                warenkorbListModel.remove(index);
                updateGesamtpreis();
            } else {
                JOptionPane.showMessageDialog(frame, "Bitte ein Produkt im Warenkorb auswählen.");
            }
        });

        frame.setVisible(true);
    }

    private int parseOrDefault(String text, int defaultValue) {
        try {
            return text.trim().isEmpty() ? defaultValue : Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void updateGesamtpreis() {
        double summe = 0.0;
        for (WarenkorbEintrag eintrag : warenkorb) {
            Produkt p = kassierer.produktSuchen(eintrag.name);
            if (p != null) {
                summe += p.getPreis() * eintrag.anzahl;
            }
        }
        gesamtpreisLabel.setText(String.format("Gesamtpreis: %.2f €", summe));
    }

    private static class WarenkorbEintrag {
        String name;
        int anzahl;

        public WarenkorbEintrag(String name, int anzahl) {
            this.name = name;
            this.anzahl = anzahl;
        }
    }
    
    public void update() {
    	
    	produktListModel.clear();
    	for (Produkt p : kassierer.produkteAusgeben()) {
            produktListModel.addElement(p.getName());
        }
    	
    }
}