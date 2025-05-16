import javax.swing.*;
import java.awt.*;

//GeschäftsführerView
public class GeschaeftsfuehrerView {
    public GeschaeftsfuehrerView() {
        Datenbank datenbank = new Datenbank();
        Geschaeftsfuehrer geschaeftsfuehrer = new Geschaeftsfuehrer(datenbank);

        JFrame frame = new JFrame("Geschaeftsfuehrer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);
        frame.setLayout(new GridLayout(1, 2));

        JButton btnProduktverwaltung = new JButton("Produktverwaltung");
        JButton btnFinanzen = new JButton("Finanzen");

        btnProduktverwaltung.addActionListener(e -> new ProduktverwaltungsView(datenbank));
        btnFinanzen.addActionListener(e -> new FinanzenView(geschaeftsfuehrer));

        frame.add(btnProduktverwaltung);
        frame.add(btnFinanzen);
        frame.setVisible(true);
    }
}
