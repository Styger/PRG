package ch.hslu.prg.gruppenarbeit;

import java.util.Random;
import java.util.Scanner;

import ch.hslu.prg.ledboard.proxy.BoardService;
import ch.hslu.prg.ledboard.proxy.Led;
import ch.hslu.prg.ledboard.proxy.LedColor;

public class ClientApp {

	public static void ledsOnOff(BoardService board) {
		// Scanner erstellen
		Scanner sc = new Scanner(System.in);

		// 1.1.1
		int reihen = 0;
		do {
			System.out.println("Anzahl LED Reihen eingeben (1 zwischen 32): ");
			reihen = sc.nextInt();
		} while (reihen > board.MAX_ROWS || reihen <= 0);

		// 1.2
		// Farbauswahl
		LedColor colorLed = null;
		System.out.println("LED Farbe waehlen: \n 1: Rot \n 2: Gruen \n 3: Gelb \n 4: Blau \n 5: Random");
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
			for (int i = 31; i >= 0; i--) {
				for (int y = (reihen - 1); y >= 0; y--) {
					leds[y][i].turnOn();
				}
			}

			// 1.1.5
			// 125 Millisekunden anhlten
			board.pauseExecution(125);

			// 1.1.6
			// Alle LEDs von links nach rechts ausschalten
			for (int i = 0; i < 32; i++) {
				for (int y = 0; y < reihen; y++) {
					leds[y][i].turnOff();
				}
			}

			// 1.1.7
			// 125 Millisekunden anhlten
			board.pauseExecution(125);
		}

		// 1.1.9
		// Zwei Sekunden anhlten
		board.pauseExecution(2000);

		// 1.1.10
		// Board zurücksetzen
		board.removeAllLeds();

	}

	public static void switchEvenOdd(BoardService board) {
		// Scanner erstellen
		Scanner sc = new Scanner(System.in);

		// 2.1
		int reihen = 0;
		do {
			System.out.println("Anzahl LED Reihen eingeben (1 zwischen 32): ");
			reihen = sc.nextInt();
		} while (reihen > board.MAX_ROWS || reihen <= 0);

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
			// 2.3
			// Alle geraden LEDs einschalten

			// Alle LEDs von links nach rechts
			for (int i = 0; i < 32; i++) {
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
			for (int i = 0; i < 32; i++) {
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
		for (int i = 0; i < 32; i++) {
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
		// Scanner erstellen
		Scanner sc = new Scanner(System.in);

		// 3.1
		int reihen = 0;
		do {
			System.out.println("Anzahl LED Reihen eingeben (1 zwischen 32): ");
			reihen = sc.nextInt();
		} while (reihen > board.MAX_ROWS || reihen <= 0);

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

		int sumLeds = 32 * reihen;
		int countDown = sumLeds / 2;

		do {
			int y = r.nextInt(reihen);
			int i = r.nextInt(32);
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
			for (int i = 0; i < 32; i++) {
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
		// Scanner erstellen
		Scanner sc = new Scanner(System.in);

		// 4.1
		// int Zahl in dezimaler Form einlesen
		int zahl = 0;
		System.out.println("Bitte eine Ganzzahl eingeben: ");
		zahl = sc.nextInt();

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
		int pl = 31;
		// Einschalten der LED's
		for (int i = binaryString.length() - 1; i >= 0; i--) {
			if (binaryString.charAt(i) == '1') {
				leds[0][pl].turnOn();
			}
			pl--;
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
		System.out.print("Bitte eine Ganzzahl fuer die Reihe eingeben: ");
		reihe = sc.nextInt();
		reihe--;
		System.out.print("Bitte eine Ganzzahl fuer die Spalte eingeben: ");
		spalte = sc.nextInt();
		spalte--;

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
		boolean diagonaleLinksNachRechtsAktiv = false;
		boolean diagonaleRechtsNachLinksAktiv = false;
		System.out.print(
				"Sollen die Diagonalen des Quadrates von links nach rechts auch gezeichnet werden? (true/false): ");
		diagonaleLinksNachRechtsAktiv = sc.nextBoolean();
		System.out.print(
				"Sollen die Diagonalen des Quadrates von rechts nach links auch gezeichnet werden? (true/false): ");
		diagonaleRechtsNachLinksAktiv = sc.nextBoolean();

		// Aufruf der Methode zum Zeichnen der Diagonalen
		if (diagonaleLinksNachRechtsAktiv || diagonaleRechtsNachLinksAktiv) {
			drawDiagonalOfSquare(diagonaleLinksNachRechtsAktiv, diagonaleRechtsNachLinksAktiv, reihe, spalte,
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
			System.out.println("Top Left X Koordinate des Rechtecks eingeben: \n X: ");
			topleftX = sc.nextInt();
			System.out.println("Top Left Y Koordinate des Rechtecks eingeben: \n Y: ");
			topleftY = sc.nextInt();

			System.out.println("Bottom Right X Koordinate des Rechtecks eingeben: \n X: ");
			bottomRightX = sc.nextInt();
			System.out.println("Bottom Right Y Koordinate des Rechtecks eingeben: \n Y: ");
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

	public static void showTriangle(BoardService board) {
		// Scanner erstellen
		Scanner sc = new Scanner(System.in);

		int triangleHight = 0;

		do {
			System.out.println("Hoehe des Dreiecks eingegen mindestens 2, maximal 16");
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
			for (int x = 0; x < 32; x++) {
				// Rand des Dreiecks zeichenen
				// if((baseLine-y) == x || (baseLine+y) == x) {
				// leds[y][x].turnOn();
				// }
				// Das Dreieck zeichnen
				if (x >= (halfbaseLine - y) && x <= (halfbaseLine + y)) {
					leds[y][x].turnOn();
				}

			}
		}
		moveTriangle(board, triangleHight);

	}

	public static void moveTriangle(BoardService board, int rows) {

		int baseLine = (rows - 1) * 2 + 1;
		Led leds[][] = board.getAllLeds();
		boolean leftToRight0 = true;
		boolean leftToRight1 = true;
		
		for (int i = 0; i < board.LEDS_PER_ROW - baseLine; i++) {

			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < board.LEDS_PER_ROW; x++) {
					if (leftToRight0) {
						if (x > 0 && leds[y][x - 1].isOn() && !leds[y][x].isOn()) {
							leds[y][x].turnOn();
							
							
							if(x == board.LEDS_PER_ROW-1 && y == rows-1) {
								leftToRight0 = false;
								System.out.println(leftToRight0);
								x=0;
							}
							// naechstes ueberspringen
							x++;
							
						}
					}

				}
			}

			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < board.LEDS_PER_ROW; x++) {
					if (leftToRight1) {
						if (leds[y][x].isOn() && x == 0) {
							leds[y][x].turnOff();
							// naechstes ueberspringen
							x++;

						} else if (leds[y][x].isOn() && !leds[y][x - 1].isOn()) {
							leds[y][x].turnOff();
							
							if(x == board.LEDS_PER_ROW-baseLine-1 && y == rows-1) {
								leftToRight1 = false;
								System.out.println(leftToRight1);
							}
							// naechstes ueberspringen
							x++;
							
						}
					}
				}
			}
		}

	}


	public static void main(String[] args) {
		BoardService board = new BoardService();

		// ledsOnOff(board);
		// switchEvenOdd(board);
		// switchRandom(board);
		// showBinary(board);
		// showBorder(board);
		// showSquare(board);
		// showRectangle(board);
		showTriangle(board);

	}

}
