package com.test.service;

public class SubwayController {

	public static void main(String[] args) {
//		SubwayService ss = new SubwayService();
//		
//		System.out.println("----- Welcome Subway -----");
//		
//		EXIT:
//		while(true) {
//			// 현재역 안내 + 메뉴 선택
//			int menu = ss.inputMenu();
//			
//			switch(menu) {
//			// 탑승 -> 탑승가능현황 안내 + 차량호수 선택 + 목적지 선택
//			case 1: 
//				ss.join();
//				break;
//			
//			// 상세보기 -> 열차현황 안내(탑승객의 목적지 표시) 
//			case 2:
//				ss.status();
//				break;
//			
//			// 이동 -> 차량이동 + 자동하차 안내 
//			case 3:
//				ss.move();
//				break;
//			
//			// 종료
//			case 9:
//				System.out.println("열차운행을 종료합니다.");
//				break EXIT;
//			default:
//				System.out.println("잘못 클릭함?");
//			}
//		}
		
		SubwayService2 ss2 = new SubwayService2();
		EXIT:
			while(true) {
				// 현재역 안내 + 메뉴 선택
				int menu = ss2.inputMenu();
				
				switch(menu) {
				// 탑승 -> 탑승가능현황 안내 + 차량호수 선택 + 목적지 선택
				case 1: 
					ss2.join();
					break;
				
				// 상세보기 -> 열차현황 안내(탑승객의 목적지 표시) 
				case 2:
					ss2.status();
					break;
				
				// 이동 -> 차량이동 + 자동하차 안내 
				case 3:
					ss2.move();
					break;
				
				// 종료
				case 9:
					System.out.println("열차운행을 종료합니다.");
					break EXIT;
				default:
					System.out.println("잘못 클릭함?");
				}
			}
	}

}
