# whackABeer Idee

Bei Whack a Beer geht es darum schlechte Biersorten anzuklicken um sie zu zerstören, damit bekommt man Punkte. Wenn man "gute" Biersorten anklickt verliert man Punkte. Das Projekt ist also ähnlich wie whack a mole. Für  den Singleplayer-Mode könnten wir mehrere Level/Schwierigkeitsstufen implementieren. Optional könnten wir hier Multiplayer features implementieren. Zum beispiel high scores oder LAN multiplayer wo jeder Spieler eine Biersorte bekommt die er anklicken soll.

# Aufbau

* Startscreen mit jeweils einen Button für Single und Multiplayer.
* Bei multi player kann man entweder eine Lobby erstellen oder im LAN nach lobbies suchen
* Bei single player wird im Hintergrund einfach die multiplayer implementation verwendet (mit einem Spieler)

# Implementation

* Lokaler multiplayer mit Network discovery library von Google, ein Teilnehmer (Host) agiert als Server (multi-device). Es wird mit einem custom message protocol kommuniziert.
* Gameplay loop besteht aus anklickbaren Bildern welche zufällig erscheinen und dem Spieler Punkte geben oder abziehen
* Es soll bei jedem Spieler der aktuelle Spielstand angezeigt werden
* Nach X viele Sekunden hört das Spiel auf (vom Host einstellbar, eventuell andere Parameter auch)
* Highscores am server speichern (Data centricity)
* Chat im multiplayer
* Special gestures (Swipes, doppel-click statt einfach-click) bei bierdosen damit der Spieler mehr Punkte erzielen kann

# Topics

* Data centricity
* Multi-device
* special gestures

# Arbeitsaufteilung

* Gameplay: Hüttner, Kornfeld, Mrsic
* Server: Hüttner
* GUI: Kornfeld, Mrsic
