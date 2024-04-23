# Opis
Jest to prosta aplikacja konsolowa, która na podstawie przekazanych kalendarzy dwóch osób oraz oczekiwanej długości spotkania wyznacza propozycję możliwych terminów spotkań.
* Danymi wejściowymi są:
  - pliki kalendarzy, które zawierają w formacie JSON informacje na temat godzin pracy danej osoby oraz godziny zaplanowanych już spotkań.
Przykładowy plik wejściowy:
  ```json
    {
      "working_hours": {
          "start": "09:00",
          "end": "19:55"
      },
      "planned_meeting": [
          {
              "start": "09:00",
              "end": "10:30"
          },
          {
              "start": "12:00",
              "end": "13:00"
          },
          {
              "start": "16:00",
              "end": "18:00"
          }
      ]
  }
  ```
  - oczekiwana długość spotkania w formacie HH:MM np. `00:40` 
* Dane wyjściowe:
  - tablica z przedziałami czasowymi w których można zaplanować spotkanie. 
  Przykładowe dane wyjściowe: 
 ```
  [[11:30,12:00], [15:00, 16:00], [18:00, 18:30]]
```
# Jak uruchomić projekt
* sklonuj repozytorium: `git clone https://github.com/Ikar17/zadanie`
* otwórz projekt w wybranym IDE (np. Intellij) i uruchom. Testy znajdują się w klasie `AppTest` w folderze `test`.

