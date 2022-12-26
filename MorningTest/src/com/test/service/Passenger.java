package com.test.service;

public class Passenger {
	// id는 필요 없을 수도 있다. 이 자체를 배열로 만들거고 그 배열의 번호가 부여될테니까
//	int id; // 승객아이디
	private int trainIndex; // 탑승한차번호
	private int destination; // 목적지 1~5
	
	public Passenger() {
		this(0, 0);
	}

	public Passenger(int trainIndex, int destination) {
		this.trainIndex = trainIndex;
		this.destination = destination;
	}

	public int getTrainIndex() {
		return trainIndex;
	}

	public void setTrainIndex(int trainIndex) {
		this.trainIndex = trainIndex;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}
	
}
