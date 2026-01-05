MonitorWydatkow
📱 Opis aplikacji

MonitorWydatkow to aplikacja mobilna na system Android, służąca do zarządzania wydatkami osobistymi. Umożliwia użytkownikowi kontrolę finansów, analizę kosztów oraz generowanie raportów w czytelnej formie.

🧩 Funkcjonalności aplikacji
🔹 Podział wydatków na kategorie

Użytkownik może przypisywać wydatki do różnych kategorii, takich jak:

jedzenie

transport

rozrywka

rachunki

Każda kategoria może posiadać własną ikonę oraz kolor, co ułatwia wizualne rozróżnianie danych.

📊 Wyświetlanie statystyk

Aplikacja automatycznie analizuje wydatki i prezentuje je w formie:

statystyk dziennych

tygodniowych

miesięcznych

Dane prezentowane są za pomocą wykresów kołowych oraz słupkowych, umożliwiając analizę historii i trendów wydatków.

📄 Generowanie raportów

Użytkownik może wygenerować raport wydatków za wybrany okres w formatach:

PDF

DOCX

Raport zawiera:

sumy wydatków

podział na kategorie

wykresy statystyczne

📝 Notatki do wydatków

Każdy wydatek może posiadać krótką notatkę, np.:

„obiad z przyjaciółmi”

„prezent urodzinowy”

💱 Obsługa wielu walut

Aplikacja obsługuje wiele walut i automatycznie:

pobiera aktualne kursy walut z zewnętrznego API (np. NBP lub ExchangeRatesAPI)

przelicza wartości pomiędzy walutami

⏰ Przypomnienia o płatnościach

Użytkownik może ustawić przypomnienia o płatnościach cyklicznych, takich jak:

abonament

czynsz

rata kredytu

Powiadomienia realizowane są przez system Android.

🗄️ Projekt bazy danych

Baza danych aplikacji MonitorWydatkow została zaprojektowana w oparciu o relacyjny model danych i zaimplementowana przy użyciu biblioteki Room.

Główne tabele:

Expense – przechowuje informacje o wydatkach (kwota, data, kategoria, waluta)

Note – opcjonalne notatki przypisane do wydatków

Category – kategorie wydatków

Currency – waluty oraz ich kursy

User – ustawienia użytkownika (domyślna waluta, tryb interfejsu)

Reminder – przypomnienia o wydatkach cyklicznych

Report – informacje o wygenerowanych raportach

Struktura bazy danych zapewnia spójność danych, wydajność oraz możliwość dalszej rozbudowy aplikacji.

🧠 Wybrane zagadnienia programistyczne

Aplikacja została stworzona w środowisku Android Studio z wykorzystaniem języka Kotlin.

🏗️ Architektura

MVVM (Model–View–ViewModel) – wyraźny podział logiki biznesowej, danych i interfejsu użytkownika

⚙️ Technologie i biblioteki

Room – lokalna baza danych

Kotlin Coroutines – asynchroniczne operacje na danych

RecyclerView – dynamiczne listy wydatków

Material Design – spójny i intuicyjny interfejs

Dodatkowo zaimplementowano:

nawigację pomiędzy ekranami

obsługę dialogów

walidację danych wprowadzanych przez użytkownika

Zastosowane rozwiązania zwiększają czytelność kodu, łatwość utrzymania oraz skalowalność projektu.

▶️ Opis działania aplikacji

Po uruchomieniu aplikacji użytkownik trafia na ekran główny, gdzie wyświetlana jest lista zapisanych wydatków.

Dostępne operacje:

dodawanie nowych wydatków

edycja i usuwanie istniejących wpisów

zarządzanie kategoriami

zmiana ustawień użytkownika (waluta, tryb interfejsu)

tworzenie przypomnień o wydatkach cyklicznych

generowanie raportów finansowych (PDF / DOCX)

Obsługa aplikacji jest intuicyjna i nie wymaga specjalistycznej wiedzy technicznej.
