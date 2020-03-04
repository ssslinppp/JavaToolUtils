package com.ssslinppp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyConfig {
	private String stringValue;
	private Integer integerValue;
	private Long longValue;
	private HashMap<Integer, Book> booksMap = new HashMap<Integer, Book>();
	private List<Computer> computerList = new ArrayList<Computer>();

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	public Long getLongValue() {
		return longValue;
	}

	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}

	public HashMap<Integer, Book> getBooksMap() {
		return booksMap;
	}

	public void setBooksMap(HashMap<Integer, Book> booksMap) {
		this.booksMap = booksMap;
	}

	public List<Computer> getComputerList() {
		return computerList;
	}

	public void setComputerList(List<Computer> computerList) {
		this.computerList = computerList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((booksMap == null) ? 0 : booksMap.hashCode());
		result = prime * result
				+ ((computerList == null) ? 0 : computerList.hashCode());
		result = prime * result
				+ ((integerValue == null) ? 0 : integerValue.hashCode());
		result = prime * result
				+ ((longValue == null) ? 0 : longValue.hashCode());
		result = prime * result
				+ ((stringValue == null) ? 0 : stringValue.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "MyConfig [stringValue=" + stringValue + ", integerValue="
				+ integerValue + ", longValue=" + longValue + ", booksMap="
				+ booksMap + ", computerList=" + computerList + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyConfig other = (MyConfig) obj;
		if (booksMap == null) {
			if (other.booksMap != null)
				return false;
		} else if (!booksMap.equals(other.booksMap))
			return false;
		if (computerList == null) {
			if (other.computerList != null)
				return false;
		} else if (!computerList.equals(other.computerList))
			return false;
		if (integerValue == null) {
			if (other.integerValue != null)
				return false;
		} else if (!integerValue.equals(other.integerValue))
			return false;
		if (longValue == null) {
			if (other.longValue != null)
				return false;
		} else if (!longValue.equals(other.longValue))
			return false;
		if (stringValue == null) {
			if (other.stringValue != null)
				return false;
		} else if (!stringValue.equals(other.stringValue))
			return false;
		return true;
	}

}
