/*
package Diary2.diary2;

import Diary2.diary2.Entry.DiaryEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiaryManager {
    private final List<DiaryEntry> entries;
    private final Scanner scanner;

    public DiaryManager() {
        entries = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addEntry() {
        System.out.print("날짜를 입력하세요 (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("제목을 입력하세요: ");
        String title = scanner.nextLine();
        System.out.print("내용을 입력하세요: ");
        String content = scanner.nextLine();
        entries.add(new DiaryEntry(date, title, content));
        System.out.println("일기가 추가되었습니다.");
    }

    public void viewEntries() {
        for (DiaryEntry entry : entries) {
            System.out.println(entry);
            System.out.println("-----------");
        }
    }

    public void deleteEntry() {
        System.out.print("삭제할 일기의 날짜를 입력하세요 (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        entries.removeIf(entry -> entry.getDate().equals(date));
        System.out.println("일기가 삭제되었습니다.");
    }

    public void editEntry() {
        System.out.print("수정할 일기의 날짜를 입력하세요 (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        for (DiaryEntry entry : entries) {
            if (entry.getDate().equals(date)) {
                System.out.print("새로운 제목을 입력하세요: ");
                entry.setTitle(scanner.nextLine());
                System.out.print("새로운 내용을 입력하세요: ");
                entry.setContent(scanner.nextLine());
                System.out.println("일기가 수정되었습니다.");
                return;
            }
        }
        System.out.println("일기를 찾을 수 없습니다.");
    }

    public void menu() {
        while (true) {
            System.out.println("1. 일기 추가");
            System.out.println("2. 일기 보기");
            System.out.println("3. 일기 수정");
            System.out.println("4. 일기 삭제");
            System.out.println("5. 종료");
            System.out.print("옵션을 선택하세요: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addEntry();
                    break;
                case 2:
                    viewEntries();
                    break;
                case 3:
                    editEntry();
                    break;
                case 4:
                    deleteEntry();
                    break;
                case 5:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }
}
*/
