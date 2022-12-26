package com.test.service;

import java.util.Scanner;

public class SubwayService {
	private String[] stations;
	private int stationIndex;
	private int destination;
	// SubwayService가 지하철이라면, 사실상 역을 돌아다니는 또 하나의 승객느낌인데
	// 그리고 역은 유일하게 상수 아님?
	
	private Train[] trains;
	private int trainIndex;
	// Composition 관계라 생각한다.
	
	private Passenger[] passengers; 
	private int passengerIndex; // 0~15
	// private 탑승객[] 탑승객들;
	// SubwayService와 Aggregation 관계일 것 같다.
	// 필요할 때마다 추가될거야
	
	
	public SubwayService() {
		// SubwayService 객체가 생성되자마자 station 배열은 5개의 역으로 확정한다.
		// 그리고 가장 첫 역은 항상 합정이니 stationIndex를 0으로 초기화한다.
		stations = new String[]{"합정", "홍대입구", "신촌", "이대", "아현"}; 
		stationIndex = 0;
		destination = -1;
		// 스테이션은 인덱스별로 역이 달라지지 curStation를 int 인덱스로 이용할 수 있겠다.
		// 이동메소드에서 curStation이 바뀌겠군.
		
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

	public void 탑승() {
		Scanner scan = new Scanner(System.in);
		System.out.println("---- 탑승가능 현황 ----");
		// 이것도 for문 4회로 가능할 것 같다.
		// 만약 trains[i].getCapacity가 4 미만이면 가능
		for(int i=0; i<4; i++) 
			System.out.printf("%d호차 : %b\n", i+1, trains[i].isPossibility());
		
		//ss.차량호수선택();
		do {
			System.out.println("어느 열차에 탑승하시겠습니까?");
			System.out.println("1호차 2호차 3호차 4호차");
			System.out.print(">");
			trainIndex = scan.nextInt();
			
			// 만약 해당 차량이 만석이라면 '탑승이 불가능합니다.' 안내 후 재선택
			// 만석이 아니라면 capacity를 올려줘야겠다.
			if(!trains[trainIndex-1].isPossibility())
				System.out.println("탑승이 불가능합니다.");
		} while(!trains[trainIndex-1].isPossibility());
		
		// 탑승이 가능해서 do-while을 빠져나왔다면, 정원을 올려줘야하는데
		// 아.. 이건 do-while에서 처리해야하는구나
		// 이 메소드에서 정원이 찾을 때 flase로 닫고 나오기 때문에 
		// do-while에서 빠져나올 수 없어
		trains[trainIndex-1].addCapacity();
		
		
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
			System.out.printf("%s", stations[i+1]);
			if(i==3)
				System.out.println();
				
		}
		System.out.print(">");
		// 
		destination = scan.nextInt();
		// 탑승이 가능할 때 Passenger 객체가 생성돼야해
		// passenger생성()
		passengerIndex++;
		passengers[passengerIndex] = new Passenger(trainIndex, destination-1);
	}

	public void 열차현황안내() {
		// n호차에 몇 명 탔고, 그 사람의 목적지는 어디인지 보여주는 메소드
		
		System.out.println("---- 열차 현황 ----");
		
		// 일단 승객이 있다면 그 승객의 목적지를 보여주자
//		for(int i=0; i<passengerIndex+1; i++) {
//			System.out.printf("%d번승객 : %d호차, [%s]\n", i, passengers[i].getTrainIndex(), stations[passengers[i].getDestination()]);
//		}
		
		// 만약에.. 차량별로 목적지 보려주려면 아래처럼 시작하면돼
		// 그리고 16번 도는데 null이 아닐 경우 출력하면 되잖아
		for(int j=0; j<4; j++) { // 차량 호수만큼 4번 복한다.
			System.out.printf("%d호차 : ", j+1);
			if(trains[j].getCapacity()>0) // 차량에 탑승한 승객이 있다면 해당 승객의 목적지를 보여준다.
				for(int i=0; i<16; i++) { // 최대 16번 반복한다.
					if(passengers[i] == null) // 승객 객체가 없을 경우 break 한다
						break;
					else if(passengers[i].getTrainIndex()==j+1)
						System.out.printf("[%s]", stations[passengers[i].getDestination()]);
				}
			System.out.println();
		}
		/*
		for(int j=0; j<4; j++) { // 차량 호수만큼 4번 복한다.
			System.out.printf("%d호차 : ", j+1);
			if(trains[j].getCapacity()>0) // 차량에 탑승한 승객이 있다면 해당 승객의 목적지를 보여준다.
				for(int i=0; i<trains[j].getCapacity(); i++) { // trains[j].getCapacity() 만큼 반복한다.
//					System.out.printf("[%s]", stations[passengers[i] destination]);
					// 근데 여기에 passengerIndex 0, 15번 승객이 타고있을 수도 있잖아.
					// 어떻게 하려고? ㅋㅋㅋㅋ 설마 승객이 한 명이라도 있으면 16번 검사함? 인덱스범위오류남 ㅡㅡ
				}
		}
		*/
		
	}

	public void move() {
			
		// 다음역으로 이동한다.
		// index가 ++ 된다는뜻이지
		// 물론 역방향일때는 수정해야한다.
		if(stationIndex==4) {
			System.out.println("다음역은 없어");
			return;
		}
		stationIndex++;
		
		//ss.자동하차안내();
		// 자동하차 안     내가 필요한 경우 먼저 안내한다.
		// 자동하차하는건 승객이야. 그럼 승객을 검사해서 내리게 해야돼
		// stationIndex가 2면, destination-1이 2인 승객은 내려야해
		// 승각은 최대 16명이니 16번 검사를 해야겠지만 continue하거나 break 해야할 수도 있겠다
		// passengerIndex+1 만큽 반복한다.
		int count = 0;
		for(int i=0; i<passengerIndex+1; i++) {
			if(passengers[i].getDestination() == stationIndex)
				count++;
				
//			if(passengers[i].getDestination()-1 == stationIndex);
		}
		
		if(count>0)
			System.out.printf("%d명 하차함\n", count);
		
		// 하차할 때 승객을 null로 만들어주자
		
//		System.out.println(stationIndex);
//		System.out.println(passengers[0].getDestination());
	}
	
}
 




















