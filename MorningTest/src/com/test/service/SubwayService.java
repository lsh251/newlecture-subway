package com.test.service;

import java.util.Scanner;

/*
 * Newlec SubwayService이다.
 * 이동할 수 있는 역이 고정되어 있다.
 * 현재는 합정 -> 홍대입구 방향으로만 운행한다. 
 * 최대 16명이 이용할 수 있고, 하차한 승객이 있더라도 이용 불가하다.
 * 
 * Train과 Composition 관계이다.
 * 지하철 운행을 위해서 SubwayService 객체가 생성될 때 바로 준비되어야 하기 때문이다.
 * 
 * Passenger와 Aggregation 관계이다.
 * 지하철 서비스 준비 여부와 관계 없이 승객이 원할 때 이용하기 때문이다.
 * join() 할 때마다 Passenger 객체가 생성된다.
 */

public class SubwayService {
	private String[] stations;
	private int stationIndex;
	private int destinationIndex;
	
	private Passenger[] passengers; 
	private int passengerIndex;
	
	private Train[] trains;
	private int trainIndex;
	
	public SubwayService() {
		// SubwayService 객체가 생성되자마자 station 배열은 5개의 역으로 확정한다.
		// 가장 첫 역은 항상 합정이니 stationIndex를 0으로 초기화한다.
		stations = new String[]{"합정", "홍대입구", "신촌", "이대", "아현"}; 
		stationIndex = 0;
		destinationIndex = -1;
		
		trains = new Train[4];
		for(int i=0; i<4; i++)
			trains[i] = new Train();
		trainIndex = 0;
		
		passengers = new Passenger[16]; // 최대 4*4 탑승이 가능하니까
		passengerIndex = -1; // 첫 승객의 인덱스가 0이 될 수 있도록
	}

	public int inputMenu() {
		int menu;
		String station = stations[stationIndex];
		
		Scanner scan = new Scanner(System.in);

		//현재역안내(); 따로 메소드 만들 수도 있음
		System.out.println("=================================");
		// 가장 처음에만 합정이고, 그 이후에는 현재 역으로 바뀌어야한다.
		System.out.println("현재역은 " + station + "입니다.");
		System.out.println("=================================");
		System.out.println("메뉴를 선택하세요.");
		System.out.println("1.탑승 2.상세보기 3.이동 9.종료");
		System.out.print(">");
		return menu = scan.nextInt();
	}

	public void join() {
		Scanner scan = new Scanner(System.in);
		boolean boardingPossibility;
		
		System.out.println("---- 탑승가능 현황 ----");
		// 만약 trains[i].getCapacity가 4 미만이면 가능
		String yes = ""; 
		for(int i=0; i<4; i++) {
			boardingPossibility = trains[i].isBoardingPossibility();
			if(boardingPossibility)
				yes = "탑승 가능";
			else
				yes = "탑승 불가능";
			System.out.printf("%d호차 : %s\n", i+1, yes);
		}
		
		//ss.차량호수선택();
		do {
			System.out.println("어느 열차에 탑승하시겠습니까?");
			System.out.println("1호차 2호차 3호차 4호차");
			System.out.print(">");
			trainIndex = scan.nextInt()-1;
			
			// 만약 해당 차량이 만석이라면 '탑승이 불가능합니다.' 안내 후 재선택
			boardingPossibility = trains[trainIndex].isBoardingPossibility();
			if(!boardingPossibility)
				System.out.println("탑승이 불가능합니다.");
			
		} while(!boardingPossibility);
		
		trains[trainIndex].addCount();
		
		//ss.목적지선택();
		//목적지를 선택하면 해당 목적지가 '[합정]' 형태로 저장되어야한다. 
		System.out.println("목적지를 선택해 주세요.");
		// 현재의 상황에 따라 목적지 선택지가 달라져야함
		// 일단 최대 4번 반복한다.
		for(int i=0; i<4; i++) {
			if(i<stationIndex)
				continue;
			
			System.out.printf("%d.", i+2);
			String station = stations[i+1];
			System.out.printf("%s ", station);
			if(i==3)
				System.out.println();
				
		}
		System.out.print(">");
		destinationIndex = scan.nextInt()-1;
		
		// 탑승이 가능할 때 Passenger 객체가 생성돼야해
		// passenger생성()
		passengerIndex++;
		passengers[passengerIndex] = new Passenger(trainIndex, destinationIndex);
	}

	public void status() {
		System.out.println("---- 열차 현황 ----");
		
		for(int j=0; j<4; j++) {
			System.out.printf("%d호차 : ", j+1);
			
			for(int i=0; i<passengerIndex+1; i++) {
				
				if(passengers[i]==null)
					continue;
				else if(passengers[i].getTrainIndex() == j) { // 승객의 탑승차량이 j와 일치하다면 목적지를 보여준다.
					String station = stations[passengers[i].getDestination()];
					System.out.printf("[%s]", station);
				}
			}
			
			System.out.println();
		}

	}

	public void move() {
		// 1. 다음역으로 이동한다. stationIndex++;
		// 현재 마지막 역인 경우에는 return한다.
		if(stationIndex==4) {
			System.out.println("종점입니다.");
			return;
		}
		stationIndex++;
		
		// 2. ss.자동하차안내();
		// passengerIndex+1 만큽 반복한다.
		int count = 0;
		for(int i=0; i<passengerIndex+1; i++) {
			
			if(passengers[i]==null)
				continue;
			else if(passengers[i].getDestination() == stationIndex) {
				passengers[i] = null; // 승객이 하차하면 null을 저장한다.
				count++;
			}
		}
		
		// 하차한 승객이 있다면 안내한다.
		if(count>0)
			System.out.printf("%d명 하차함\n", count);
		
	}
}

 




















