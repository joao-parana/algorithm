package br.com.joaoparana.algorithm;

import java.util.Random;

// tentativa de resolver este problema https://www.damninteresting.com/the-birthday-paradox/

public class App {
	static int samplesQty = 20000;
	static int personsQty = 49;

	public static void main(String[] args) {
		System.out.println("Calculando a probabilidade com " + samplesQty + " pessoas");
		float total = 0;
		for (int i = 0; i < samplesQty; i++) {
			total += doIt();
		}

		System.out.println("com " + personsQty + " pessoas temos probabilidade -> " + (total / samplesQty));
	}

	public static float doIt() {

		float qtdMatch = 0;
		for (int i = 0; i < personsQty; i++) {
			if (checkMatch(i)) {
				qtdMatch++;
			}
		}
		return qtdMatch / personsQty;
	}

	static boolean checkMatch(int n) {
		Boolean[] match = new Boolean[365];
		// Inicializa o vetor
		for (int i = 0; i < match.length; i++) {
			match[i] = false;
		}

		for (int i = 0; i < n; i++) {
			int wd = new Random().nextInt(365);
			if (match[wd]) {
				return true;
			} else {
				match[wd] = true;
			}
		}

		return false;
	}
}
