# whackABeer Idee

whackABeer ist eine Android App bei der es darum geht "schlechte" Bierdosen wenn sie am Bildschirm erscheinen anzuklicken, um sie zu zerstören. Für eine erfolgreich zerstörte Dose erhält man Punkte. Wenn man "gute" Biersorten anklickt verliert man Punkte. Das Projekt ist also eine "bierige" Abwandlung von whack a mole. Es gibt einen Singleplayer und einen Multiplayer Mode. Wer im Multiplayer zuerst auf das Bier drückt bekommt die Punkte. Außerdem gibt es Highscores.

# Aufbau

* Startscreen mit jeweils einen Button für Single und Multiplayer
* About Us Page
* Highscores
* Im Multiplayer kann man entweder eine Lobby erstellen oder Lobbies auswählen
* Im Singleplayer wird im Hintergrund die Multiplayer implementation verwendet (mit einem Spieler)

# Implementation

* Lokaler Multiplayer mit Network Discovery Library von Google (NSD): ein Teilnehmer (Host) agiert als Server (multi-device). Es wird mit einem custom message protocol kommuniziert.
* Der Gameplay loop besteht aus immer wieder neu erscheinenden anklickbaren Bieren an zufälligen Stellen am "Spielfeld". Wenn der Spieler darauf klickt werden Punkte dazu addiert oder subtrahiert.
* Es wird bei jedem Spieler der aktuelle Punktestand angezeigt
* Nach X vielen Sekunden hört das Spiel auf (vom Host einstellbar, andere Parameter auch)
* Highscores am server speichern mit SQLite (Data centricity)
* Special gestures (Doppelklick statt einmaliger Klick) bei Bierdosen damit der Spieler die doppelten Punkte erhält

# Topics

* Data centricity
* Multi-device
* special gestures

# Arbeitsaufteilung

* Gameplay: Kornfeld, Mrsic
* Server: Hüttner
* GUI: Kornfeld, Mrsic
