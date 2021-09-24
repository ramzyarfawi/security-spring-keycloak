package com.arfaoui.samples.security.cloudgateway;

public class Calculatrice {

	public static void main(String[] args) {
		
		int a = 20;
		int b = 13;

		System.out.println("La somme de " + a + " et " + b + " est égale à : " + add(a, b));

		System.out.println("La soustraction de " + a + " et " + b + " est égale à : " + diff(a, b));

		
	}

	public static int add(int x, int y) {
		return x+y;
	}

	public static int diff(int x, int y) {
		return x-y;
	}
	
}
