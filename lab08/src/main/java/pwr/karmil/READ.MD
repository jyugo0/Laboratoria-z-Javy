## Wyniki testów:
Pomiary wykonano 30 razy, na tej samej instancji macierzy. Procesor 4-rdzeniowy.

| Rozmiar macierzy |  Java  | C++     | 
|------------------|:------:|---------|
| 500x500          |  5,17  | 7,67    | 
| 1000x1000        |  7,1   | 12,3    |
| 3000x3000        | 90,37  | 103,67  |
| 5000x5000        | 320,6  | 514,6   |
| 10000x10000      | 670,97 | 1183,27 |

### Wnioski:
Dla mniejszych macierzy zdarza się, że wykonanie z użyciem JNI i c++ jest szybsze, ale
dla większych (10000x10000) implementacja w javie zawsze wykonuje się szybciej. Ma to sens biorąc pod
uwagę zwiększającą się przepaść w czasie wykonania.
### Dlaczego java jest szybsza? 
Prawdopodobnie ma to związek z JTI, albo łączenie się przez JNI z bilbioteką c++ jest bardziej
kosztowne czasowo nawet jeśli same obliczenia mogą być szybsze.