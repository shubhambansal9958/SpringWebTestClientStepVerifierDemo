package com.spring5.concept.learning.model;

public class StringHolder {

	private String str;
	
	public StringHolder() {
	}

	public StringHolder(String str) {
		super();
		this.str = str;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	public boolean equals(Object obj) {
		StringHolder holder = (StringHolder) obj;

		return str.equals(holder.getStr());
	}

}
