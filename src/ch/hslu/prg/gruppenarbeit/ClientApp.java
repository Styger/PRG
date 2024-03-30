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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BoardService board = new BoardService();

		// ledsOnOff(board);
		// switchEvenOdd(board);
		switchRandom(board);

	}

}
