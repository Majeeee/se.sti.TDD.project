# TDD ATM Projekt

Detta är ett ATM-simuleringssystem utvecklat i Java med TDD och JUnit 5.

## Funktioner
- Visa saldo
- Sätta in och ta ut pengar
- Visa kvitto och historik
- Spara transaktioner i SQLite
- Återskapa databastabell

## Struktur
- `Main.java` – programstart
- `MyATM.java` – logik för användarinteraktion
- `DBconnection/` – hantering av databas
- `tables/` – SQL-skript
- `test/` – JUnit-tester

## Bygga och köra
```bash
mvn clean compile
mvn test
