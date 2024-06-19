package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {
	private double round(double num) {
		return new BigDecimal(num).setScale(16, RoundingMode.HALF_UP).doubleValue();
	}
	
	double add(double a, double b) {
		return round(a + b);
	}
	
	double multiply(double a, double b) {
		return a * b;
	}
	
	double divide(double a, double b) {
		if(b == 0) throw new ArithmeticException("/ by zero");
		return a / b;
	}
	
	int div(int a, int b) {
		return a / b;
	}
}
