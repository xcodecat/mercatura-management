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

## CSV-Datenstruktur

Die CSV-Datei erwartet folgende Spalten (jeweils durch Semikolon `;` getrennt):

```
Name;Ort;Lageranzahl;Regalanzahl;Preis;Verkaufszahlen;Einkaufspreis;Einkaufszahlen
```

Beispieldatensatz:

```
Apfel;A1;45;14;0.99;20;0.5;70
Milch;C1;28;10;1.09;8;0.6;40
...
```

## Installation

1. Repository klonen:
   ```bash
   git clone <repository-url>
   cd supermarkt-alpha
   ```

2. Abhängigkeiten installieren (falls erforderlich).

3. `SupermarktStarter.java` starten

## Projektstruktur

- `produkte.csv` – enthält die initialen Produktdaten
- `src/` – Quellcode der Anwendung
    -  Klassen wie `Produkt`, `Datenbank`
    -  Benutzeroberflächen für Lagermitarbeiter und Geschäftsführer
    -  Logik zur Verarbeitung von Benutzerinteraktionen
- `README.md` – diese Dokumentation

## Rollen und Benutzeroberflächen

- **Lagermitarbeiter**  
  → Übersicht über Lagerbestände, Möglichkeit zur Einlagerung und Lieferung.

- **Geschäftsführer**  
  → Verwaltung der Verkaufsstatistiken, Preisänderungen, Gesamtauswertung.

- ...
