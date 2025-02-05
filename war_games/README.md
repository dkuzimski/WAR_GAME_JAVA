# Zadanie: Gry wojenne

| Termin oddania   | Punkty     |
|------------------|:-----------|
| 11.01.2025 10:30 |    20      |

--- 
Przekroczenie terminu o **n** zajęć wiąże się z karą:
- punkty uzyskania za realizację zadania są dzielone przez **2<sup>n</sup>**.

--- 

W grze biorą udział dwaj generałowie ze swoimi armiami. Każdy generał posiada armię żołnierzy oraz worek ze złotymi monetami.

Żołnierze posiadają:
- stopień wojskowy: szeregowy 
    (wartość: 1), kapral (wartość: 2), kapitan (wartość: 3) i major (wartość: 4)
- doświadczenie
- siła żołnierza jest obliczana jako iloczyn jego stopnia i doświadczenia
- żołnierz ginie, gdy jego doświadczenie = 0
- jeżeli doświadczenie osiągnie pięciokrotność wartości stopnia, 
awansuje na kolejny stopień oraz jego doświadczenie = 1.

Generałowie posiadają początkową (ograniczoną) liczbę złotych monet.
Celem generała jest posiadanie największej i najlepiej wyszkolonej armii. 

Generał może:
- zarządzić manewry swojej armii (lub jej części), które powiększają doświadczenie uczestniczących w nich żołnierzy o 1; manewry kosztują: za każdego żołnierza biorącego udział w manewrach generał płaci wartość (liczbę monet) przypisaną do stopnia wojskowego
- zaatakować drugiego generała; wygrywa generał, który posiada armię o większej łącznej sile; przegrany przekazuje 10% swojego złota wygrywającemu; każdy żołnierz z armii przegrywającej traci 1 punkt doświadczenia, a z wygrywającej zyskuje jeden; w przypadku remisu każdy generał musi rozstrzelać jednego swojego losowo wybranego żołnierza
- kupić żołnierzy; koszt żołnierza = 10 *(jego stopień); zakupieni żołnierze posiadają doświadczenie = 1

Walczącym generałom przygląda się sekretarz prezydenta. Pisze on raporty dotyczące obu armii. Opisuje wszelkie akcje podjęte przez generałów oraz zmiany poszczególnych żołnierzy.
Generał wraz ze swoimi zasobami powinien mieć możliwość zapisu i odczytu swojego stanu na / z dysku.

System Zarządzania Stanem Gry:
- Centralny system kontrolujący przejścia między etapami gry,
- Umożliwia zarządzanie kolejnością akcji oraz monitorowanie postępu rozgrywki.

Automatyczne Zapisywanie i Ładowanie Stanu Gry:
- System automatycznie zapisuje stan gry po każdej wykonanej akcji,
- Umożliwia przywrócenie gry do dowolnego wcześniejszego punktu.

Moduł Logowania Akcji i Raportowania:
- Szczegółowe logi wszystkich działań generałów,
- Automatyczne generowanie raportów po każdej turze gry.

System Weryfikacji i Walidacji Danych:
- Mechanizmy sprawdzające poprawność danych wprowadzanych przez użytkownika,
- Zapewnienie integralności stanu gry podczas zapisu i odczytu.

---

W zadaniu oceniane będą:
- Tworzenie klas: 	10%
- Kompozycja: 		10%
- Dziedziczenie: 	20%
- Implementacje odpowiednich wzorców projektowych: 30%
- Implementacja opisanych algorytmów:		 20%
- Polimorfizm: 		10%

### Uwaga
Projekt powinien również zawierać odpowiednie testy jednostkowe do implementowanej funkcjonalności.