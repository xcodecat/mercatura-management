import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Dashboard {
    public static void main(String[] args) {
        // Erstellen einer Instanz der Datenbank
        Datenbank datenbank = new Datenbank();

        // Erstellen einer Instanz des Geschäftsführers mit der Datenbank
        Geschaeftsfuehrer geschaeftsfuehrer = new Geschaeftsfuehrer(datenbank);

        // Erstellen des Hauptfensters
        JFrame frame = new JFrame("Rollenwahl – Supermarkt-System");

        // Setzen der Standard-Schließoperation (Schließen des Fensters beendet das Programm)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Festlegen der Größe des Fensters
        frame.setSize(500, 300);

        // Setzen des Layouts des Fensters (2 Zeilen, 3 Spalten, 10 Pixel Abstand)
        frame.setLayout(new GridLayout(2, 3, 10, 10));

        // Erstellen der Buttons für die verschiedenen Rollen und Funktionen
        JButton btnKassierer = new JButton("Kassierer");
        JButton btnLager = new JButton("Lagermitarbeiter");
        JButton btnKunde = new JButton("Kunde");
        JButton btnVerwaltung = new JButton("Produktverwaltung");
        JButton btnFinanzen = new JButton("Finanzen");

        // Hinzufügen von ActionListenern zu den Buttons, um die entsprechenden Views zu öffnen
        btnKassierer.addActionListener(e -> new KassiererView(datenbank));
        btnLager.addActionListener(e -> new LagermitarbeiterView(datenbank));
        btnKunde.addActionListener(e -> new KundeView(datenbank));
        btnVerwaltung.addActionListener(e -> new ProduktverwaltungsView(datenbank));
        btnFinanzen.addActionListener(e -> new FinanzenView(geschaeftsfuehrer));

        // Hinzufügen der Buttons zum Fenster
        frame.add(btnKassierer);
        frame.add(btnLager);
        frame.add(btnKunde);
        frame.add(btnVerwaltung);
        frame.add(btnFinanzen);

        // Fenster sichtbar machen
        frame.setVisible(true);
    }
}
