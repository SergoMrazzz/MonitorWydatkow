# **MonitorWydatkow**

## **Opis aplikacji**
**MonitorWydatkow** to aplikacja mobilna na system **Android**, przeznaczona do zarządzania wydatkami osobistymi.  
Umożliwia kontrolę finansów, analizę kosztów oraz generowanie raportów.

---

## **Funkcjonalności**

### **Podział wydatków na kategorie**
- Możliwość przypisywania wydatków do kategorii (np. jedzenie, transport, rozrywka, rachunki)
- Każda kategoria posiada **ikonę** oraz **kolor**
- Lepsza czytelność i organizacja danych

---

### **Statystyki wydatków**
- Analiza **dzienna**, **tygodniowa** oraz **miesięczna**
- Prezentacja danych w formie:
  - wykresów kołowych
  - wykresów słupkowych
- Podgląd historii oraz trendów finansowych

---

### **Generowanie raportów**
- Eksport danych do formatów:
  - **PDF**
  - **DOCX**
- Raport zawiera:
  - sumy wydatków
  - podział na kategorie
  - wizualizacje danych

---

### **Notatki do wydatków**
- Możliwość dodania krótkiej notatki do wydatku
- Przykłady:
  - „obiad z przyjaciółmi”
  - „prezent urodzinowy”

---

### **Obsługa wielu walut**
- Automatyczne pobieranie kursów walut z API:
  - **NBP**
  - **ExchangeRatesAPI**
- Przeliczanie wartości pomiędzy walutami
- Obsługa waluty domyślnej użytkownika

---

### **Przypomnienia o płatnościach**
- Tworzenie przypomnień dla płatności cyklicznych:
  - abonamenty
  - czynsz
  - raty kredytowe
- Powiadomienia realizowane przez system **Android**

---

## **Projekt bazy danych**
Baza danych została zaprojektowana w oparciu o **relacyjny model danych**  
i zaimplementowana z użyciem biblioteki **Room**.

### **Główne tabele**
- **Expense** – dane wydatków (kwota, data, kategoria, waluta)
- **Note** – notatki przypisane do wydatków
- **Category** – kategorie wydatków
- **Currency** – waluty i kursy
- **User** – ustawienia użytkownika
- **Reminder** – przypomnienia o płatnościach
- **Report** – dane wygenerowanych raportów

Struktura zapewnia **spójność danych**, **wydajność** oraz **możliwość rozbudowy**.

---

## **Zagadnienia programistyczne**

### **Technologie**
- **Kotlin**
- **Android Studio**
- **Room**
- **Coroutines**
- **RecyclerView**
- **Material Design**

### **Architektura**
- **MVVM (Model–View–ViewModel)**
- Wyraźny podział:
  - logiki biznesowej
  - warstwy danych
  - interfejsu użytkownika

---

## **Działanie aplikacji**
Po uruchomieniu aplikacji użytkownik widzi **ekran główny** z listą wydatków.

### **Możliwości użytkownika**
- Dodawanie, edycja i usuwanie wydatków
- Zarządzanie kategoriami
- Zmiana ustawień aplikacji
- Tworzenie przypomnień
- Generowanie raportów (**PDF / DOCX**)

Aplikacja jest **intuicyjna** i nie wymaga wiedzy technicznej.
