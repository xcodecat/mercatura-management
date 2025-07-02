# Supermarkt Produktverwaltung

Dieses Projekt dient zur Verwaltung und Bearbeitung von Supermarktprodukten. Die Produkte werden aus einer CSV-Datei eingelesen, im Lager verwaltet und können über eine grafische Benutzeroberfläche bearbeitet werden.

## Features

- **Produktverwaltung**  
  Übersichtliche Speicherung von Produkten mit Lagerort, Regalplatz, Lagerbestand, Verkaufspreis und Einkaufspreis.

- **Verkaufsverwaltung**  
  Anpassung der Lagerbestände bei Verkäufen, Übersicht über Verkaufszahlen.

- **Regal- und Lagerverwaltung**  
  Produkte können explizit Regalen oder Lagerorten zugewiesen werden.

- **Benutzeroberfläche**  
  Einfache GUI zur Bedienung für verschiedene Benutzerrollen (z. B. Lagermitarbeiter, Geschäftsführer).

- **CSV-Unterstützung**  
  Produkte werden bequem aus einer CSV-Datei eingelesen und gespeichert.

- **JAR-kompatibel**  
  Die Anwendung kann als ausführbare `.jar` gestartet werden. Alle Daten werden dabei im Unterordner `daten/` gespeichert – relativ zum Speicherort der JAR-Datei. So bleiben Produkt- und Finanzdaten auch nach dem Schließen erhalten.

## CSV-Datenstruktur

Die CSV-Datei erwartet folgende Spalten (jeweils durch Semikolon `;` getrennt):

```
Name;Ort;Lageranzahl;Regalanzahl;Preis;Verkaufszahlen;Einkaufspreis;Einkaufszahlen
```

Beispieldatensatz:

```
Apfel;A1;45;14;0.99;20;0.5;70
Milch;C1;28;10;1.09;8;0.6;40
```

## Installation & Ausführung

### IDE

1. Repository klonen:
   ```bash
   git clone <repository-url>
   cd supermarkt-alpha
   ```

2. Projekt in eine Java-IDE (z. B. IntelliJ oder Eclipse) importieren.

3. `SupermarktStarter.java` starten.

### JAR

1. Projekt als ausführbare `.jar` exportieren:
   z. B. `supermarkt.jar`

2. JAR-Datei starten:
   ```bash
   java -jar supermarkt.jar
   ```

3. Die Dateien werden automatisch neben der JAR erstellt:
   - `produkte.csv`
   - `finanzen.csv`
   - `tagstatus.txt`  

## Projektstruktur
- `produkte.csv`, `finanzen.csv`, `tagstatus.txt`
- `src/` – Quellcode der Anwendung
    - Klassen wie `Produkt`, `Datenbank`
    - Benutzeroberflächen für Lagermitarbeiter und Geschäftsführer
    - Logik zur Verarbeitung von Benutzerinteraktionen
- `README.md` – diese Dokumentation

## Rollen und Benutzeroberflächen

- **Lagermitarbeiter**  
  → Übersicht über Lagerbestände, Möglichkeit zur Einlagerung und Lieferung.

- **Geschäftsführer**  
  → Verwaltung der Verkaufsstatistiken, Preisänderungen, Gesamtauswertung.
