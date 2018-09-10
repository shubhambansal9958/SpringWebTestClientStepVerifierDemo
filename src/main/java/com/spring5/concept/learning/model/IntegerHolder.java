package com.spring5.concept.learning.model;

public class IntegerHolder {
	int number;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public boolean equals(Object obj) {
		IntegerHolder holder = (IntegerHolder) obj;
		return number == holder.getNumber();
	}
}
