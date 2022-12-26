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
	
	private Train[] trains;
	private int trainIndex;
	
	private Passenger[] passengers; 
	private int passengerIndex; // 0~15
	// private 탑승객[] 탑승객들;
	// SubwayService와 Aggregation 관계일 것 같다.
	// 필요할 때마다 추가될거야
	
	public SubwayService() {
		// SubwayService 객체가 생성되자마자 station 배열은 5개의 역으로 확정한다.
		// 가장 첫 역은 항상 합정이니 stationIndex를 0으로 초기화한다.
		stations = new String[]{"합정", "홍대입구", "신촌", "이대", "아현"}; 
		stationIndex = 0;
		destinationIndex = -1;
		
		passengers = new Passenger[16]; // 최대 4*4 탑승이 가능하니까
		passengerIndex = -1; // 첫 승객의 인덱스가 0이 될 수 있도록
		
		trains = new Train[4];
		for(int i=0; i<4; i++)
			trains[i] = new Train();
		trainIndex = 0;
	}

	public int inputMenu() {
		int menu;
		
		Scanner scan = new Scanner(System.in);

		//현재역안내(); 따로 메소드 만들 수도 있음
		System.out.println("=================================");
		// 가장 처음에만 합정이고, 그 이후에는 현재 역으로 바뀌어야한다.
		System.out.println("현재역은 " + stations[stationIndex] + "입니다.");
		System.out.println("=================================");
		System.out.println("메뉴를 선택하세요.");
		System.out.println("1.탑승 2.상세보기 3.이동 9.종료");
		System.out.print(">");
		return menu = scan.nextInt();
	}

	public void join() {
		Scanner scan = new Scanner(System.in);
		System.out.println("---- 탑승가능 현황 ----");
		// 만약 trains[i].getCapacity가 4 미만이면 가능
		String yes = ""; 
		for(int i=0; i<4; i++) {
			if(trains[i].isPossibility())
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
			// 만석이 아니라면 capacity를 올려줘야겠다.
			if(!trains[trainIndex].isPossibility())
				System.out.println("탑승이 불가능합니다.");
		} while(!trains[trainIndex].isPossibility());
		
		trains[trainIndex].addCapacity();
		
		//ss.목적지선택();
		//목적지를 선택하면 해당 목적지가 '[합정]' 형태로 저장되어야한다. 
		System.out.println("목적지를 선택해 주세요.");
		// 현재의 상황에 따라 목적지 선택지가 달라져야함
		// 일단 최대 4번 반복한다.
		for(int i=0; i<4; i++) {
			// 0, 1, 2, 3
			// 4번 반복할건데, i에 스테이션을 집어 넣으면 안 되나 ㅋㅋ
			// 근데 저기는 건들이면 안 되니까 보정하는 방식으로 하자
			// stationIndex보다 ~하면 continue 한다. // 0, 1, 2, 3, 4
			// stationIndex가 0이라면 1부터 4번 해야해
			// stationIndex가 1이라면 2부터 3번 해야해
			// 지금이 신촌이면 이대랑 아현만 보여줘야한단 말이야.
			// 
			if(i<stationIndex)
				continue;
			// 합정이 1이야. 1,2,3,4,5 // 홍대는 2란 말이지
			System.out.printf("%d.", i+2);
			System.out.printf("%s ", stations[i+1]);
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
		// n호차에 몇 명 탔고, 그 사람의 목적지는 어디인지 보여주는 메소드
		System.out.println("---- 열차 현황 ----");
		/*
		 * 많이 까다로운 메소드라 생각한다.
		 * 승객은 최대 16명인데, passengerIndex와 상관없이 각기 다른 차량에 탑승한다.
		 * 그렇다면, 차량별로 16번을 검사해야 하는 것으로..?
		 * 이번에도 각 차량마다 passengerIndex+1 만큼 반복하고 continue 해야겠다.
		 * 이중for문으로 간다.
		 */
		
		for(int j=0; j<4; j++) {
			System.out.printf("%d호차 : ", j+1);
			
			for(int i=0; i<passengerIndex+1; i++)
				if(passengers[i]==null)
					continue;
				else if(passengers[i].getTrainIndex() == j) // 승객의 탑승차량이 j와 일치하다면 목적지를 보여준다.
					System.out.printf("[%s]", stations[passengers[i].getDestination()]);
			
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

 




















