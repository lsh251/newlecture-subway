package com.test.service;

public class Train {
	private int capacity; // 정원
	private boolean possibility; // 탑승가능여부
	
	public Train() {
		capacity = 0;
		possibility = true;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isPossibility() {
		return possibility;
	}

	public void setPossibility(boolean possibility) {
		this.possibility = possibility;
	}

	public void addCapacity() {
		// 이 메소드는 호출되면 capacity를 1 증가시켜줘야해
		// 그런데 만약 capacity의 값이 4라면 다시 리턴되어야하지
		// 그러기 위해서는 capacity 값이 4가 되는 순간 possibility를 flase로 바꿔줄 수 있지 않을까?
		// 제어는 제품에서 하고
		capacity++;
		
		// capacity가 4까지 증가했다면, possibility에 flase를 저장한다. 
		if(capacity==4)
			possibility = false;
	}
	
	
	
	
}
