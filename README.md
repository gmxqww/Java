Java

Feladat
A feladat egy repülőtér leegyszerűsített működése, ahol indulásra készülő utasszállító gépek pilótái és a légiirányítás kommunikál egymással. Ebben a Pilot osztály írja le a pilóták viselkedését, a Delivery az útvonalengedélyeket, a Tower pedig a felszállási engedélyeket kiadó légiirányítókat írja le, míg az Airport osztály a program fő osztálya, az egész szimuláció vezérléséért felelős. Ezen kívül van egy segédosztály Constants néven, amely a program működéséhez szükséges konstansokat tartalmazza, valamint a Clearance osztály az útvonalengedély adatait írja le. Ez utóbbiakat nem kell módosítani.

A feladat három, egymásra épülő lépésből áll, a TODO után zárójelben álló szám azt a lépést jelöli, amelyben azt meg kell valósítani. A programkód kitöltendő váza innen tölthető leLinkek egy külső oldalra.

Útvonalengedély kérése 
Minden pilóta tudja a járata hívójelét (callsign), valamint a kapu számát, ahol a gépe áll (gate). Míg előbbit a Pilot osztály konstruktora paramétereként kapja, utóbbit úgy kell a konstruktornak beállítani, hogy minden gép másik kapuról induljon. Ehhez szükség lesz egy nextGate nevű, osztályszintű adattagra is. A rendszer akkor is működjön jól, ha esetleg két szál is egyszerre hívna konstruktort.

Az Airport osztály feladata a szimuláció futtatása. Ehhez először is létre kell hoznia az összes pilótát, valamint az egyetlen légiirányítót, ami útvonalengedélyeket ad ki. Ezek mind egy-egy szálként kerüljenek megvalósításra, melyeket egy ExecutorService futtat. A pilótákat ne egyszerre indítsd, hanem minden két pilóta között MIN_SLOT_DIFF és MAX_SLOT_DIFF közötti ezredmásodperc teljen el. Ehhez használd a megfelelő sleepMsec() metódust. Az útvonalengedélyek (egyszerű karakterlánc) továbbításához használj adatcsatornát requests néven. A végén állítsd le az ExecutorService-t, ha pedig eltelik TIMEOUT idő, akkor szakítsd meg a működését.

Szintén az Airport osztály tartalmazza a pilóták által hívott requestClearance() és az irányítás által hívott receive() metódusokat. Ezek megvalósítása is a te feladatod: az előbbi helyezze be az engedélyt a megfelelő adatcsatornába, az utóbbi meg vegye ki úgy, hogy a paraméterében megadott ezredmásodpercig vár rá, különben pedig null-t ad vissza.

Útvonalengedély fogadása 
Ehhez a részfeladathoz vedd el a kommenteket a Pilot osztály első kikommentezett blokkja körül.

A légiirányítás megadja az útvonalengedélyt az Airport osztály clearRoute() metódusa segítségével. Ennek a metódusnak az engedélyt, amely a Clearance osztály egy példánya, be kell tennie egy clearances nevű Map-be, melynek kulcsa a hívójel. A readClearance() metódus, amelyet minden pilóta TIME_BETWEEN_POLLS ezredmásodpercenként próbál hívni ugyanebből az adatszerkezetből, olvassa ki az eredményt. Fontos, hogy a program akkor is működjön helyesen, ha egyszerre több pilóta hívja ezt a metódust, miközben a légiirányítás is éppen meghívja a clearRoute()-ot.

Felszállási engedély 
Ehhez a részfeladathoz vedd el a kommenteket a Pilot osztály második kikommentezett blokkja körül.

Az útvonalengedélyben a pilóta megkapta a felszálláshoz szükséges futópálya azonosítóját is. Minden futópályához külön-külön tartozik egy irányító (Tower), amely az adott pályán történő felszállásokért felel. Hozd létre és indítsd el ezeket az irányítókat is. A pilóták az Airport osztály readyForDeparture() metódusának meghívásával jelzik az adott torony felé, hogy készen állnak a felszállásra. Ennek a metódusnak a feladata, hogy kommunikálja a pilóta azonosítóját a torony felé, és ébressze azt fel a notify() segítségével. A torony legfeljebb AIRPORT_TIMEOUT ideig várjon (wait) arra, hogy felébresszék, utána termináljon. Miután egy gép felszállt, a pilóta az Airport osztály airborne() metódusát hívja meg ennek jelzésére. Ez a metódus a readyForDeparture()-höz hasonlóan újból felébreszti a tornyot, ami azonban a felszállási engedély kiadása után határozatlan ideig vár, mielőtt a következő engedélyt kiadná.
