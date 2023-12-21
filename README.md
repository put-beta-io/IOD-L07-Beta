
# Opis 

Dla osób pracujących z danymi tekstowymi nasza aplikacja Text Transformer umożliwi transformacje danych tekstowych (np. zmiana wielkości liter, eliminacja duplikatów, itd.). Aplikacja będzie dostępna poprzez GUI a także zdalne API dzięki czemu będzie można ją zintegrować z istniejącymi narzędziami.

# Dokumentacja 

Dokumentacja generowana automatycznie, dotępna online: https://put-beta-io.github.io/IOD-L07-Beta/.

# Testy CI 

![Testy](https://github.com/put-beta-io/IOD-L07-Beta/actions/workflows/maven.yml/badge.svg)

# Product backlog

| Element rejestru produktu	| Ważność	| Pracochłonność	| Business Value (BV) | 
|---------------------------|-----------|-------------------|---------------------|
| Jako twórca aplikacji mogę wywołać transformację zdalnie poprzez REST, aby móc zintegrować narzędzie z moimi innymi aplikacjami	| 15 | 	8	| 1 |
Jako użytkownik mogę zmianać wielkości liter – zestaw trzech transformacji (upper, lower, capitalize) |	12 |	5 |	0,5 |
| Jako użytkownik mogę odwracać ciąg znaków z zachowaniem wielkości liter na konkretnych pozycjach (inverse) | 	10	 | 4 |	0,5 |   
| Jako użytkownik mogę zamieniać liczby na tekst w języku polskim (Wpłać 100 złotych -> wpłać sto złotych) – wsparcie zakresu liczb do negocjacji |	10	| 7	| 1 |
| Jako użytkownik mogę o zamieniać wybrane (predefiniowane) słowa na skróty (Pieczywo to na przykład chleb i bułki -> Pieczywo to np. chleb i bułki) – zakres obsługiwanych skrótów do negocjacji	| 10	| 7 |	0,5 |
| Jako użytkownik mogę rozwijać wybrane skróty do pełnych postaci (Pan Prof. spóźnił się na zajęcia -> Pan Profesor spóźnił się na zajęcia)	| 10	| 5	 | 0,5 |
| Jako użytkownik mogę przekształcać tekst do formatu wspieranego przez Latex (znaki specjalne) -> (John Smith & Sons -> John Smith \& Sons)	| 2 | 	6 |	0,5 |
| Jako użytkownik mogę eliminować powtarzające się wyrazy w bezpośrednim sąsiedztwie (Wyślij do do mnie wiadomość -> Wyślij do mnie wiadomość)	| 1 | 	8 | 	1 |
| Jako użytkownik mogę skorzystać z aplikacji za pomocą interfejsu użytkownika	| 15	| 16 | 2 | 

# Backlog sprintu

## Jako twórca aplikacji mogę wywołać transformację zdalnie poprzez REST, aby móc  zintegrować narzędzie z moimi innymi aplikacjami
| No. | Zadania | Pracochłonność | 
|-----|---------|----------------|
| 1 | utworzeniu struktury |  1|
| 2 | zdefiniowanie listy endpointow | 3 |
| 3 | utworzenie enpointy | 3 |
| 4 | utworzenie dokumentacji dla klas | 1 |

## Jako użytkownik mogę zmianać wielkości liter – zestaw trzech transformacji (upper, lower, capitalize)
| No. | Zadania | Pracochłonność | 
|-----|---------|----------------|
| 1 | wczytanie tekstu i oczekiwanej zmiany | 1 |
| 2 | budowa funkcji transformującej tekst | 2 |
| 3 | testy i napraw ewentualnych błędów | 1 |
| 4 | utworzenie dokumentacji dla klas | 1 |

## Jako użytkownik mogę odwracać ciąg znaków z zachowaniem wielkości liter na konkretnych pozycjach (inverse)
| No. | Zadania | Pracochłonność | 
|-----|---------|----------------|
| 1 | zapamiętanie pozycji wielkich liter | 0.5 |
| 2 | odwrócenie tekstu | 1 |
| 3 | przywrócenie wielkich liter na odpowiednie pozycje | 0.5 |
| 4 | testy i naprawa ewentualnych błędów | 1 |
| 5 | utworzenie dokumentacji dla klas | 1 |

## Jako użytkownik mogę zamieniać liczby na tekst w języku polskim (Wpłać 100 złotych -> wpłać sto złotych) – wsparcie zakresu liczb do negocjacji
| No. | Zadania | Pracochłonność | 
|-----|---------|----------------|
| 1 | określenie zakresu liczb | 0.5 |
| 2 | przygotowanie tablicy liczb | 1.5 |
| 3 | budowa funkcji | 3 |
| 4 | testy i naprawa ewentualnych błędów | 1 |
| 5 | utworzenie dokumentacji dla klas | 1 |

## Jako użytkownik mogę o zamieniać wybrane (predefiniowane) słowa na skróty (Pieczywo to na przykład chleb i bułki -> Pieczywo to np. chleb i bułki) – zakres obsługiwanych skrótów do negocjacji
| No. | Zadania | Pracochłonność | 
|-----|---------|----------------|
| 1 | przygotowanie listy skrótów | 2 |
| 2 | budowa funkcji aplikacji z wykorzystaniem listy skrótów | 3 |
| 3 | testy i naprawa ewentualnych błędów | 1 |
| 4 | utworzenie dokumentacji dla klas | 1 |

## Jako użytkownik mogę rozwijać wybrane skróty do pełnych postaci (Pan Prof. spóźnił się na zajęcia -> Pan Profesor spóźnił się na zajęcia)
| No. | Zadania | Pracochłonność | 
|-----|---------|----------------|
| 1 | budowa funkcji aplikacji z wykorzystaniem listy skrótów | 3 |
| 2 | testy i naprawa ewentualnych błędów | 1 |
| 3 | utworzenie dokumentacji dla klas | 1 |
