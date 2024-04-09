package ch.hslu.prg.gruppenarbeit;

import java.util.Random;
import java.util.Scanner;

import ch.hslu.prg.ledboard.proxy.BoardService;
import ch.hslu.prg.ledboard.proxy.Led;
import ch.hslu.prg.ledboard.proxy.LedColor;

public class ClientApp {

	public static int readIntFromUser(int min, int max) {
		Scanner sc = new Scanner(System.in);
		int zahl;
		do {
			System.out.print("Bitte eine Ganzzahl zwischen " + min + " und " + max + " eingeben: ");
			// ob die nächste Eingabe ein Int ist
			while (!sc.hasNextInt()) {
				System.out.println("\nDas ist keine Ganzzahl. Bitte nochmal versuchen! ");
				System.out.print("Bitte eine Ganzzahl zwischen " + min + " und " + max + " eingeben: ");
				// Leert den aktuellen Eingabewert, wenn er kein Int ist
				sc.next();
			}
			zahl = sc.nextInt();
		} while (zahl < min || zahl > max);
		return zahl;
	}

	public static void ledsOnOff(BoardService board) {
		
		// Scanner erstellen
		Scanner sc = new Scanner(System.in);
		// 1.1.1
		int reihen = readIntFromUser(1, board.MAX_ROWS);
		
		// 1.2
		// Farbauswahl
		LedColor colorLed = null;
		System.out
				.println("LED Farbe waehlen: \n 1: Rot \n 2: Gruen \n 3: Gelb \n 4: Blau \n 5: Random \n Default: Rot");
		int wahl = sc.nextInt();

		switch (wahl) {
		case 1:
			colorLed = LedColor.RED;
			break;
		case 2:
			colorLed = LedColor.GREEN;
			break;
		case 3:
			colorLed = LedColor.YELLOW;
			break;
		case 4:
			colorLed = LedColor.BLUE;
			break;
		case 5:
			colorLed = LedColor.RANDOM;
			break;
		default:
			colorLed = LedColor.RED;
		}

		// 1.1.2
		// LED Reihen dem Board hinzufuegen
		board.add(reihen, colorLed);

		// 1.1.3
		// Zwei Sekunden anhlten
		board.pauseExecution(2000);

		// 1.1.8
		// Schritte 4 bis 7 drei mal wiederholen
		for (int x = 0; x < 3; x++) {
			// 1.1.4
			// Alle LEDs von rechts nach Links einschalten
			Led leds[][] = board.getAllLeds();
			for (int i = board.MAX_ROWS - 1; i >= 0; i--) {
				for (int y = (reihen - 1); y >= 0; y--) {
					leds[y][i].turnOn();
				}
			}

			// 1.1.5
			// 250 Millisekunden anhlten
			board.pauseExecution(250);

			// 1.1.6
			// Alle LEDs von links nach rechts ausschalten
			for (int i = 0; i < board.MAX_ROWS; i++) {
				for (int y = 0; y < reihen; y++) {
					leds[y][i].turnOff();
				}
			}
			// 1.1.7
			// 250 Millisekunden anhlten
			board.pauseExecution(250);

		}

		// 1.1.9
		// Zwei Sekunden anhlten
		board.pauseExecution(2000);

		// 1.1.10
		// Board zurücksetzen
		board.removeAllLeds();

	}

	public static void switchEvenOdd(BoardService board) {
		
		// 2.1
		int reihen = readIntFromUser(1, board.MAX_ROWS);

		// LED Reihen dem Board hinzufuegen
		board.add(reihen);

		// 2.2
		// Zwei Sekunden anhlten
		board.pauseExecution(2000);

		// zwei Dimensionales LED Array
		Led leds[][] = board.getAllLeds();

		// 2.7
		// Schritte 3 bis 6 drei mal wiederholen
		for (int x = 0; x < 3; x++) {
			/*
			 * // Optimal koennte man bei jedem Durchlauf alle LEDs ausschalten for (int i =
			 * 0; i < board.MAX_ROWS; i++) { for (int y = 0; y < reihen; y++) {
			 * leds[y][i].turnOff(); } }
			 */

			// 2.3
			// Alle geraden LEDs einschalten

			// Alle LEDs von links nach rechts
			for (int i = 0; i < board.MAX_ROWS; i++) {
				for (int y = 0; y < reihen; y++) {
					// wenn gerade (restlos durch 2 teilbar)
					if (leds[y][i].getLedId() % 2 == 0) {
						leds[y][i].turnOn();
					}
				}
			}

			// 2.4
			// Eine Sekunden anhlten
			board.pauseExecution(1000);

			// 2.5
			for (int i = 0; i < board.MAX_ROWS; i++) {
				for (int y = 0; y < reihen; y++) {
					// wenn angeschaltet dann ausschalten sonst anschalten
					if (leds[y][i].isOn()) {
						leds[y][i].turnOff();
					} else {
						leds[y][i].turnOn();
					}
				}
			}

			// 2.6
			// Eine Sekunden anhlten
			board.pauseExecution(1000);
		}

		// 2.8
		// Alle leds aussschalten
		for (int i = 0; i < board.MAX_ROWS; i++) {
			for (int y = 0; y < reihen; y++) {
				leds[y][i].turnOff();
			}
		}

		// 2.9
		// Zwei Sekunden anhlten
		board.pauseExecution(2000);

		// 2.10
		// Board zurücksetzen
		board.removeAllLeds();

	}

	public static void switchRandom(BoardService board) {
		
		// 3.1
		int reihen = readIntFromUser(1, board.MAX_ROWS);

		// 3.2
		// zufällige Farbauswahl
		LedColor colorLed = null;
		Random r = new Random();

		// generiert Int zwischen 1 und 5
		int wahl = r.nextInt(5) + 1;

		switch (wahl) {
		case 1:
			colorLed = LedColor.RED;
			break;
		case 2:
			colorLed = LedColor.GREEN;
			break;
		case 3:
			colorLed = LedColor.YELLOW;
			break;
		case 4:
			colorLed = LedColor.BLUE;
			break;
		case 5:
			colorLed = LedColor.RANDOM;
			break;
		default:
			colorLed = LedColor.RED;
		}

		// 1.1.2
		// LED Reihen dem Board hinzufuegen
		board.add(reihen, colorLed);

		// 3.3
		// Zwei Sekunden anhlten
		board.pauseExecution(2000);

		// 3.4
		// zufällige Hälfte der LEDs einschalten

		// zwei Dimensionales LED Array
		Led leds[][] = board.getAllLeds();

		int sumLeds = board.MAX_ROWS * reihen;
		int countDown = sumLeds / 2;

		do {
			int y = r.nextInt(reihen);
			// i ist von 0 bis aussschliesslich 32 also 31
			int i = r.nextInt(board.MAX_ROWS);
			if (!leds[y][i].isOn()) {
				countDown--;
				leds[y][i].turnOn();
			}
		} while (countDown != 0);

		// 3.5
		// Zwei Sekunden anhlten
		board.pauseExecution(2000);

		// 3.8
		// Fünf mal wiederholen
		for (int t = 0; t < 5; t++) {

			// 3.6
			// Alle eingeschalteten LEDs ausschalten alle ausgeschalteten einschalten
			for (int i = 0; i < board.MAX_ROWS; i++) {
				for (int y = 0; y < reihen; y++) {
					if (leds[y][i].isOn()) {
						leds[y][i].turnOff();
					} else {
						leds[y][i].turnOn();
					}
				}
			}
			// 3.7
			// Halbe Sekunde anhlaten
			board.pauseExecution(500);

		}
		// 3.9
		// Zwei Sekunden anhlaten
		board.pauseExecution(2000);

		// 3.10
		// Board zurücksetzen
		board.removeAllLeds();

	}

	public static void showBinary(BoardService board) {
		
		int zahl = readIntFromUser(-2147483648, 2147483647);

		// 4.2
		// Eingelesene ganze Zahl als Binaerzahl umwandeln
		String binaryString = Integer.toBinaryString(zahl);

		// LED Reihen und Farbe definieren
		int reihen = 1;
		LedColor colorLed = LedColor.RED;

		// 4.3
		// LED Reihe dem Board hinzufügen
		board.add(reihen, colorLed);

		// 4.4
		Led leds[][] = board.getAllLeds();
		// LED Postionsvariable
		int p = board.MAX_ROWS - 1;
		// Einschalten der LED's
		for (int i = binaryString.length() - 1; i >= 0; i--) {
			if (binaryString.charAt(i) == '1') {
				leds[0][p].turnOn();
			}
			p--;
		}

	}

	// 5
	public static void showBorder(BoardService board) {

		// Maximale Anzahl von LED Reihen
		Led[][] leds = board.add(BoardService.MAX_ROWS);

		// Board für 2 Sekunden anhalten
		board.pauseExecution(2000);

		// Alle LED's am Rande einschalten
		for (int row = 0; row < BoardService.MAX_ROWS; row++) {
			for (int col = 0; col < BoardService.LEDS_PER_ROW; col++) {
				if (row == 0 || row == BoardService.MAX_ROWS - 1 || col == 0 || col == BoardService.LEDS_PER_ROW - 1) {
					leds[row][col].turnOn();
				}
			}
		}

		// Board für 2 Sekunden anhalten
		board.pauseExecution(2000);

		// LED's am Rande ausschalten
		for (int row = 0; row < BoardService.MAX_ROWS; row++) {
			for (int col = 0; col < BoardService.LEDS_PER_ROW; col++) {
				if (row == 0 || row == BoardService.MAX_ROWS - 1 || col == 0 || col == BoardService.LEDS_PER_ROW - 1) {
					leds[row][col].turnOff();
				}
			}
		}
		// Board für 2 Sekunden anhalten
		board.pauseExecution(2000);

		// Board zurücksetzen
		board.removeAllLeds();
	}

	// 6
	public static void showSquare(BoardService board) {

		// Scanner erstellen
		Scanner sc = new Scanner(System.in);

		// 6.1
		// Maximale Anzahl von LED Reihen
		Led[][] leds = board.add(BoardService.MAX_ROWS);

		// Koordinaten in dezimaler Form einlesen
		int reihe = 0;
		int spalte = 0;
		System.out.println("Bitte eine Ganzzahl fuer die Reihe eingeben");
		reihe = readIntFromUser(0, BoardService.MAX_ROWS-1);

		System.out.println("Bitte eine Ganzzahl fuer die Spalte eingeben");
		spalte = readIntFromUser(0, BoardService.MAX_ROWS-1);

		// Darstellen des topleft Punkts
		leds[reihe][spalte].turnOn();

		// Seitenlaenge des Quadrates einlesen unter der Bedingung, dass es ins Feld
		// passt
		int seitenlaenge = 0;
		do {
			System.out.println("Bitte eine Ganzzahl fuer die Seitenlaenge eingeben: ");
			seitenlaenge = sc.nextInt();
			if (seitenlaenge + reihe > BoardService.MAX_ROWS || seitenlaenge + spalte > BoardService.LEDS_PER_ROW) {
				System.out.println("Bitte die maximale Hoehe und Breite des Quadreates beachten!");
			}
		} while (seitenlaenge + reihe > BoardService.MAX_ROWS || seitenlaenge + spalte > BoardService.LEDS_PER_ROW);
		seitenlaenge--;

		// Alle LED's am Quadratrande einschalten
		for (int row = reihe; row <= seitenlaenge + reihe; row++) {
			for (int col = spalte; col <= seitenlaenge + spalte; col++) {
				if (row == reihe || row == seitenlaenge + reihe || col == spalte || col == seitenlaenge + spalte) {
					leds[row][col].turnOn();
				}
			}
		}

		// 6.2
		// Abfrage, ob Diagonale gezeichnet werden soll

		System.out.print(
				"Sollen die Diagonalen des Quadrates von links nach rechts auch gezeichnet werden? (true/false): ");
		int diagonaleLinksNachRechtsAktiv = readIntFromUser(0, 1);
		System.out.print(
				"Sollen die Diagonalen des Quadrates von rechts nach links auch gezeichnet werden? (true/false): ");
		int diagonaleRechtsNachLinksAktiv = readIntFromUser(0, 1);

		// Aufruf der Methode zum Zeichnen der Diagonalen
		if (diagonaleLinksNachRechtsAktiv == 1 || diagonaleRechtsNachLinksAktiv == 1) {
			drawDiagonalOfSquare(diagonaleLinksNachRechtsAktiv == 1, diagonaleRechtsNachLinksAktiv == 1, reihe, spalte,
					seitenlaenge, leds);
		}

	}

	// 6.2
	public static void drawDiagonalOfSquare(boolean linksRechts, boolean rechtsLinks, int topLeftReihe,
			int topLeftSpalte, int seitenlaenge, Led[][] leds) {
		// Diagonale des Quadrates von links nach rechts zeichnen
		if (linksRechts) {
			int versatzCounter = 0;
			for (int row = topLeftReihe; row < seitenlaenge + topLeftReihe; row++) {
				for (int col = topLeftSpalte; col < 1 + topLeftSpalte; col++) {
					leds[row][col + versatzCounter].turnOn();
				}
				versatzCounter++;
			}
		}

		// Diagonale des Quadrates von rechts nach links zeichnen
		if (rechtsLinks) {
			int versatzCounter = seitenlaenge;
			for (int row = topLeftReihe; row < seitenlaenge + topLeftReihe; row++) {
				for (int col = topLeftSpalte; col < 1 + topLeftSpalte; col++) {
					leds[row][col + versatzCounter].turnOn();
				}
				versatzCounter--;
			}
		}

	}

	// 7
	public static void showRectangle(BoardService board) {
		// Scanner erstellen
		Scanner sc = new Scanner(System.in);

		// 7.1 Maximale Anzahl Reihen hinzufügen
		board.add(board.MAX_ROWS);

		int topleftX = 0;
		int topleftY = 0;
		int bottomRightX = 0;
		int bottomRightY = 0;

		do {
			System.out.println("Das Rechtecks muss mindestens 3 mal 3 LEDs gross sein");
			System.out.print("Top Left X Koordinate des Rechtecks eingeben: \n X: ");
			topleftX = sc.nextInt();
			System.out.print("Top Left Y Koordinate des Rechtecks eingeben: \n Y: ");
			topleftY = sc.nextInt();

			System.out.print("Bottom Right X Koordinate des Rechtecks eingeben: \n X: ");
			bottomRightX = sc.nextInt();
			System.out.print("Bottom Right Y Koordinate des Rechtecks eingeben: \n Y: ");
			bottomRightY = sc.nextInt();

		} while ((bottomRightX - topleftX) < 2 || (bottomRightY - topleftY) < 2);

		// zwei Dimensionales LED Array
		Led leds[][] = board.getAllLeds();

		// leds[topleftX][topleftY].turnOn();
		// leds[bottomRightX][bottomRightY].turnOn();

		// Alle LEDs von links nach rechts
		for (int x = topleftX; x <= bottomRightX; x++) {
			for (int y = topleftY; y <= bottomRightY; y++) {
				// LED einschalten
				leds[x][y].turnOn();

			}
		}

		// Drei Sekunden anhlten
		board.pauseExecution(3000);

		// LEDs im inneren des Rechtecks aussschalten
		for (int x = (topleftX + 1); x <= (bottomRightX - 1); x++) {
			for (int y = (topleftY + 1); y <= (bottomRightY - 1); y++) {
				// LED einschalten
				leds[x][y].turnOff();

			}
		}

		// Drei Sekunden anhlten
		board.pauseExecution(3000);

		// LEDS die angeschaltet sind zu Blauen LEDs machen
		// Alle LEDs von links nach rechts
		for (int x = topleftX; x <= bottomRightX; x++) {
			for (int y = topleftY; y <= bottomRightY; y++) {
				// Alle eingeschalteten und somit nur der Rahmen wird durch blaue LEDs ersetzt
				if (leds[x][y].isOn()) {
					LedColor colorLed = LedColor.BLUE;
					board.replace(leds[x][y], colorLed);
					leds[x][y].turnOn();
				}
			}
		}

		// Drei Sekunden anhlten
		board.pauseExecution(3000);

		// Board zurücksetzen
		board.removeAllLeds();

	}

	// 8.1
	public static void showTriangle(BoardService board, int wiederholungen) {
		// Scanner erstellen
		Scanner sc = new Scanner(System.in);

		int triangleHight = 0;

		do {
			System.out.println("Hoehe des Dreiecks eingegen mindestens 2, maximal " + (board.MAX_ROWS / 2));
			System.out.print(" Hoehe: ");
			triangleHight = sc.nextInt();

		} while (triangleHight < 2 || triangleHight > board.MAX_ROWS / 2);

		// Anzahl Reihen gemaess Hoehe hinzufügen
		board.add(triangleHight);

		// Gleichschenkliges Dreieck zeichnen
		// Alle LEDs von rechts nach Links einschalten
		Led leds[][] = board.getAllLeds();

		// Alle LEDs von von oben nach unten
		int halfbaseLine = (triangleHight - 1);
		for (int y = 0; y < triangleHight; y++) {
			for (int x = 0; x < board.MAX_ROWS; x++) {
				if (x >= (halfbaseLine - y) && x <= (halfbaseLine + y)) {
					leds[y][x].turnOn();
				}

			}
		}
		moveTriangle(board, triangleHight, wiederholungen);

	}

	// 8.2
	public static void moveTriangle(BoardService board, int reihen, int wiederholungen) {

		int baseLine = (reihen - 1) * 2 + 1;
		Led leds[][] = board.getAllLeds();

		int maxVerschiebung = board.LEDS_PER_ROW - baseLine + 1;

		for (int w = 0; w < wiederholungen; w++) {
			int versch = 0;
			boolean moveToRight = true; // Startet mit Bewegung nach rechts

			do {
				// Alle LEDs ausschalten
				for (int y = 0; y < reihen; y++) {
					for (int x = 0; x < board.LEDS_PER_ROW; x++) {
						leds[y][x].turnOff();
					}
				}

				// Dreieck zeichnen
				for (int y = 0; y < reihen; y++) {
					for (int x = 0; x < 2 * y + 1; x++) {
						// Berechne die X-Position für das LED
						int ledX = x + versch + (reihen - y - 1);
						// Ueberprüfe ob die Grenze des Boards ueberschritten ist
						if (ledX < board.LEDS_PER_ROW) {
							leds[y][ledX].turnOn();
						}
					}
				}

				// Bewegungsrichtung wechseln
				if (moveToRight) {
					// Erreiche die max Verschiebung nicht, um Platz für das letzte LED zu lassen
					if (versch < maxVerschiebung - 1) {
						versch++;
					} else {
						// Wechsle die Richtung
						moveToRight = false;
					}
				} else {
					if (versch >= 0) {
						versch--;
					} else {
						// Wechsle die Richtung am Anfang
						moveToRight = true;
					}
				}

				// Fortsetzen, solange das Dreieck nicht wieder am Anfang
			} while (versch >= 0 || moveToRight);
		}

	}

	// 9
	public static void createRunningLight(BoardService board) {

		// LED Reihe ohne Farbe explizit angeben dem Board hinzufügen
		int reihen = 1;
		board.add(reihen);

		// zwei Dimensionales LED Array
		Led leds[][] = board.getAllLeds();

		// Alle LEDs von rechts nach Links einschalten
		for (int i = 31; i >= 0; i--) {
			for (int y = (reihen - 1); y >= 0; y--) {
				leds[y][i].turnOn();
			}
		}

		// LED in 8er Bereiche unterteilen von rechts nach links
		for (int i = board.MAX_ROWS - 1; i >= 0; i--) {
			if (i >= board.MAX_ROWS - 8) {
				leds[0][i] = board.replace(leds[0][i], LedColor.GREEN);
				leds[0][i].turnOn();
			} else if (i >= board.MAX_ROWS - 16) {
				leds[0][i] = board.replace(leds[0][i], LedColor.RED);
				leds[0][i].turnOn();
			} else if (i >= board.MAX_ROWS - 24) {
				leds[0][i] = board.replace(leds[0][i], LedColor.BLUE);
				leds[0][i].turnOn();
			} else {
				leds[0][i] = board.replace(leds[0][i], LedColor.YELLOW);
				leds[0][i].turnOn();
			}
		}

		// Zwei Sekunden anhlten
		board.pauseExecution(2000);

		// 3 Mal durchfuehren
		for (int z = 0; z < 3; z++) {
			// Ein Durchlauf fuer jede LED in der Reihe
			for (int durchlauf = 0; durchlauf < 32; durchlauf++) {
				// Jedes Led wird angefasst
				for (int i = 0; i < 31; i++) {
					// Jedes Achte mal wird der uebertrag vollzogen

					if (i == 7) {
						// Die Farbe der letzten LED speichern, um sie am Anfang zu setzen
						LedColor farbeDerLetztenLED = leds[0][BoardService.LEDS_PER_ROW - 1].getColor();
						// Die gespeicherte Farbe der letzten LED an den Anfang setzen
						leds[0][0] = board.replace(leds[0][0], farbeDerLetztenLED);
					}

					// Wenn die aktuelle LED nicht die gleiche Farbe wie die nachfolgenden LED
					if (leds[0][i].getColor() != leds[0][i + 1].getColor()) {
						// Farbe der aktuellen LED nehmen und nachfolgeden LED dem entsprechend
						// einfaerben
						leds[0][i + 1] = board.replace(leds[0][i + 1], leds[0][i].getColor());
						// naechste LED ueberspringen
						i++;
					}
				}

			}
		}

	}

	// 10
	public static void countColors(BoardService board) {
		// Maximale Anzahl Reihen hinzufuegen
		board.add(board.MAX_ROWS, LedColor.RANDOM);

		// zwei Dimensionales LED Array
		Led leds[][] = board.getAllLeds();

		// Alle Leds einschalten von Links nach Rechts
		for (int x = 0; x < board.MAX_ROWS; x++) {
			for (int y = 0; y < board.MAX_ROWS; y++) {
				leds[y][x].turnOn();
			}
		}

		// Zwei Sekunden anhlten
		board.pauseExecution(2000);

		int blueAnz = 0;
		int greenAnz = 0;
		int yellowAnz = 0;
		int redAnz = 0;

		// zaehlen wie viel von welcher Farbe
		for (int x = 0; x < board.MAX_ROWS; x++) {
			for (int y = 0; y < board.MAX_ROWS; y++) {
				if (leds[y][x].getColor() == LedColor.BLUE) {
					blueAnz++;
				}
				if (leds[y][x].getColor() == LedColor.GREEN) {
					greenAnz++;
				}
				if (leds[y][x].getColor() == LedColor.YELLOW) {
					yellowAnz++;
				}
				if (leds[y][x].getColor() == LedColor.RED) {
					redAnz++;
				}
			}
		}
		System.out.println("Blau: " + blueAnz);
		System.out.println("Grün: " + greenAnz);
		System.out.println("Gelb: " + yellowAnz);
		System.out.println("Rot: " + redAnz);
	}

	// 10.2
	public static void countColorsExt(BoardService board) {

		// Maximale Anzahl Reihen hinzufuegen
		board.add(board.MAX_ROWS, LedColor.RANDOM);

		// zwei Dimensionales LED Array
		Led leds[][] = board.getAllLeds();

		// Alle Leds einschalten von Links nach Rechts
		for (int x = 0; x < board.MAX_ROWS; x++) {
			for (int y = 0; y < board.MAX_ROWS; y++) {
				leds[y][x].turnOn();
			}
		}

		// Zwei Sekunden anhlten
		board.pauseExecution(2000);

		// Zeile mit am meisten gleichen Farbe herausfinden
		int blueAnz = 0;
		int blueZeile = 0;
		int greenAnz = 0;
		int greenZeile = 0;
		int yellowAnz = 0;
		int yellowZeile = 0;
		int redAnz = 0;
		int redZeile = 0;
		for (int y = 0; y < board.MAX_ROWS; y++) {

			int tempBlueAnz = 0;
			int tempGreenAnz = 0;
			int tempYellowAnz = 0;
			int tempRedAnz = 0;

			for (int x = 0; x < board.MAX_ROWS; x++) {
				if (leds[y][x].getColor() == LedColor.BLUE) {
					tempBlueAnz++;
				}
				if (leds[y][x].getColor() == LedColor.GREEN) {
					tempGreenAnz++;
				}
				if (leds[y][x].getColor() == LedColor.YELLOW) {
					tempYellowAnz++;
				}
				if (leds[y][x].getColor() == LedColor.RED) {
					tempRedAnz++;
				}
			}
			if (blueAnz <= tempBlueAnz) {
				blueAnz = tempBlueAnz;
				blueZeile = y;
			}
			if (greenAnz <= tempGreenAnz) {
				greenAnz = tempGreenAnz;
				greenZeile = y;
			}
			if (yellowAnz <= tempYellowAnz) {
				yellowAnz = tempYellowAnz;
				yellowZeile = y;
			}
			if (redAnz <= tempRedAnz) {
				redAnz = tempRedAnz;
				redZeile = y;
			}
		}
		System.out.println("Hinweis für die Nicht Programierer: Die Zeilenzaehlung beginnt bei 0 :-)");
		System.out.println();
		System.out.println(" Led Farbe: Blau \n Zeile mit am meisten blauen Leds: " + blueZeile
				+ "\n Anzahl Leds in dieser Zeile: " + blueAnz);
		System.out.println();
		System.out.println(" Led Farbe: Grün \n Zeile mit am meisten grünen Leds: " + greenZeile
				+ "\n Anzahl Leds in dieser Zeile: " + greenAnz);
		System.out.println();
		System.out.println(" Led Farbe: Gelb \n Zeile mit am meisten gelben Leds: " + yellowZeile
				+ "\n Anzahl Leds in dieser Zeile: " + yellowAnz);
		System.out.println();
		System.out.println(" Led Farbe: Rot \n Zeile mit am meisten roten Leds: " + redZeile
				+ "\n Anzahl Leds in dieser Zeile: " + redAnz);
		System.out.println();

	}

	public static void main(String[] args) {
		BoardService board = new BoardService();
		// 1
		// ledsOnOff(board);
		// 2
		// switchEvenOdd(board);
		// 3
		// switchRandom(board);
		// 4
		// showBinary(board);
		// 5
		// showBorder(board);
		// 6
		// showSquare(board);
		// 7
		 showRectangle(board);
		// 8
		// showTriangle(board, 3); // 3 ist die Anzahl Movement wiederholungen
		// 9
		// createRunningLight(board);
		// 10
		// countColors(board);
		// countColorsExt(board);

	}

}
