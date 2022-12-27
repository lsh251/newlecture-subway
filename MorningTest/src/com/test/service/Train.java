package com.test.service;

public class Train {
	private int count; // 정원
	private boolean boardingPossibility; // 탑승가능여부
	
	public Train() {
		count = 0;
		boardingPossibility = true;
	}

	public int getCapacity() {
		return count;
	}

	public void setcount(int count) {
		this.count = count;
	}

	public boolean isBoardingPossibility() {
		return boardingPossibility;
	}

	public void setboardingPossibility(boolean boardingPossibility) {
		this.boardingPossibility = boardingPossibility;
	}

	public void addCount() {
		// 이 메소드는 호출되면 capacity를 1 증가시켜줘야해
		// 그런데 만약 capacity의 값이 4라면 다시 리턴되어야하지
		// 그러기 위해서는 capacity 값이 4가 되는 순간 possibility를 flase로 바꿔줄 수 있지 않을까?
		// 제어는 제품에서 하고
		count++;
		
		// capacity가 4까지 증가했다면, possibility에 flase를 저장한다. 
		if(count==4)
			boardingPossibility = false;
	}
	
	
	
	
}
