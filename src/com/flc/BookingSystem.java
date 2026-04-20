package com.flc;

import com.flc.model.*;
import com.flc.timetable.Timetable;

import java.util.*;

public class BookingSystem {

    private Timetable timetable = new Timetable();
    private List<Member> members = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        initializeData();
        int choice;

        do {
            System.out.println("\n===== FLC BOOKING SYSTEM =====");
            System.out.println("1. Add Member");
            System.out.println("2. View All Members");
            System.out.println("3. Add Lesson (Manual)");
            System.out.println("4. Show Timetable");
            System.out.println("5. Book Lesson");
            System.out.println("6. Change Booking");
            System.out.println("7. Cancel Booking");
            System.out.println("8. Attend Lesson");
            System.out.println("9. Write Review");
            System.out.println("10. Lesson Report");
            System.out.println("11. Income Report");
            System.out.println("0. Exit");

            choice = readInt("Choice: ");

            switch (choice) {
                case 1 -> addMember();
                case 2 -> viewAllMembers();
                case 3 -> addLesson();
                case 4 -> showTimetableMenu();
                case 5 -> bookLesson();
                case 6 -> changeBooking();
                case 7 -> cancelBooking();
                case 8 -> attendLesson();
                case 9 -> writeReview();
                case 10 -> generateLessonReport();
                case 11 -> generateIncomeReport();
            }
        } while (choice != 0);
    }

    private void initializeData() {
        // Members will be added manually via the "Add Member" menu function as permitted by requirements
        
        double yogaPrice = 10.0;
        double zumbaPrice = 12.0;
        double aquacisePrice = 15.0;
        double boxFitPrice = 11.0;
        double bodyBlitzPrice = 13.0;
        
        for (int w = 1; w <= 8; w++) {
            int month = (w <= 4) ? 5 : 6;
            timetable.addLesson(new Lesson("Yoga", w, month, "Saturday", "Morning", yogaPrice));
            timetable.addLesson(new Lesson("Zumba", w, month, "Saturday", "Afternoon", zumbaPrice));
            timetable.addLesson(new Lesson("Aquacise", w, month, "Saturday", "Evening", aquacisePrice));
            
            timetable.addLesson(new Lesson("Box Fit", w, month, "Sunday", "Morning", boxFitPrice));
            timetable.addLesson(new Lesson("Body Blitz", w, month, "Sunday", "Afternoon", bodyBlitzPrice));
            timetable.addLesson(new Lesson("Yoga", w, month, "Sunday", "Evening", yogaPrice));
        }
    }

    private void addMember() {
        boolean continueAdding = true;
        while (continueAdding) {
            int id = readInt("Member ID: ");
            System.out.print("Member name: ");
            String name = scanner.nextLine();
            members.add(new Member(id, name));
            System.out.println("Member added.");
            
            System.out.print("Do you want to add another member? (y/n): ");
            String res = scanner.nextLine();
            if (!res.trim().equalsIgnoreCase("y")) {
                continueAdding = false;
            }
        }
    }

    private void addLesson() {
        System.out.print("Exercise name: ");
        String exercise = scanner.nextLine();
        int weekend = readInt("Weekend number (1-8): ");
        int month = readInt("Month number (e.g. 5): ");
        System.out.print("Day (Saturday/Sunday): ");
        String day = scanner.nextLine();
        System.out.print("Time (Morning/Afternoon/Evening): ");
        String time = scanner.nextLine();
        double price = readDouble("Price: ");

        timetable.addLesson(new Lesson(exercise, weekend, month, day, time, price));
        System.out.println("Lesson added.");
    }

    private void showTimetableMenu() {
        System.out.println("1. View All");
        System.out.println("2. View by Day");
        System.out.println("3. View by Exercise");
        int choice = readInt("Choice: ");
        
        List<Lesson> list = new ArrayList<>();
        if (choice == 1) list = timetable.getLessons();
        else if (choice == 2) {
            System.out.print("Enter Day (Saturday/Sunday): ");
            list = timetable.getLessonsByDay(scanner.nextLine());
        } else if (choice == 3) {
            System.out.print("Enter Exercise: ");
            list = timetable.getLessonsByExercise(scanner.nextLine());
        }
        
        System.out.println("\n+-------+----------------+-------+-------+------------+-----------+-------+--------+");
        System.out.printf("| %-5s | %-14s | %-5s | %-5s | %-10s | %-9s | %-5s | %-6s |\n", "Index", "Exercise", "Month", "Wknd", "Day", "Time", "Price", "Booked");
        System.out.println("+-------+----------------+-------+-------+------------+-----------+-------+--------+");
        for (Lesson lesson : list) {
            int index = timetable.getLessons().indexOf(lesson);
            System.out.printf("| %-5d | %-14s | %-5d | %-5d | %-10s | %-9s | £%-4.2f | %-6d |\n",
                    index, lesson.getExerciseName(), lesson.getMonth(), lesson.getWeekend(),
                    lesson.getDay(), lesson.getTime(), lesson.getPrice(), lesson.getMemberCount());
        }
        System.out.println("+-------+----------------+-------+-------+------------+-----------+-------+--------+\n");
    }

    private Member findMember() {
        int id = readInt("Member ID: ");
        for (Member m : members) {
            if (m.getId() == id) return m;
        }
        System.out.println("Member not found.");
        return null;
    }

    private void bookLesson() {
        Member member = findMember();
        if (member == null) return;
        
        boolean continueBooking = true;
        while (continueBooking) {
            showTimetableMenu();
            int index = readInt("Lesson index to book: ");
            if (index < 0 || index >= timetable.getLessons().size()) {
                System.out.println("Invalid index.");
            } else {
                Lesson lesson = timetable.getLessons().get(index);
                if (lesson.getMemberCount() >= 4) {
                    System.out.println("Lesson is full.");
                } else if (lesson.getBooking(member) != null) {
                    System.out.println("Member already booked this lesson.");
                } else {
                    if (lesson.addMember(member)) {
                        Booking booking = lesson.getBooking(member);
                        System.out.println("Booking successful! Your specific Booking ID is: " + booking.getBookingId());
                    }
                }
            }
            System.out.print("Do you want to book another lesson for this member? (y/n): ");
            String res = scanner.nextLine();
            if (!res.trim().equalsIgnoreCase("y")) {
                continueBooking = false;
            }
        }
    }

    private void changeBooking() {
        Member member = findMember();
        if (member == null) return;
        int oldIndex = readInt("Current Lesson index to cancel: ");
        if (oldIndex < 0 || oldIndex >= timetable.getLessons().size()) return;
        
        Lesson oldLesson = timetable.getLessons().get(oldIndex);
        Booking oldBooking = oldLesson.getBooking(member);
        
        if (oldBooking == null || oldBooking.isCancelled()) {
            System.out.println("No active booking found for this lesson.");
            return;
        }
        
        showTimetableMenu();
        int newIndex = readInt("New Lesson index to book: ");
        if (newIndex < 0 || newIndex >= timetable.getLessons().size() || oldIndex == newIndex) return;
        
        Lesson newLesson = timetable.getLessons().get(newIndex);
        
        if (newLesson.addExistingBooking(oldBooking)) {
            oldLesson.removeBooking(oldBooking);
            oldBooking.setLesson(newLesson);
            oldBooking.markChanged();
            System.out.println("Booking changed successfully. Booking ID " + oldBooking.getBookingId() + " has been preserved.");
        }
    }

    private void cancelBooking() {
        Member member = findMember();
        if (member == null) return;
        int index = readInt("Lesson index to cancel: ");
        if (index < 0 || index >= timetable.getLessons().size()) return;
        
        Lesson lesson = timetable.getLessons().get(index);
        Booking booking = lesson.getBooking(member);
        
        if (booking != null && !booking.isCancelled()) {
            booking.cancel();
            System.out.println("Booking cancelled.");
        } else {
            System.out.println("No active booking found.");
        }
    }

    private void attendLesson() {
        Member member = findMember();
        if (member == null) return;
        int index = readInt("Lesson index to attend: ");
        if (index < 0 || index >= timetable.getLessons().size()) return;
        
        Lesson lesson = timetable.getLessons().get(index);
        Booking booking = lesson.getBooking(member);
        
        if (booking != null && !booking.isCancelled()) {
            booking.markAttended();
            System.out.println("Lesson marked as attended.");
        } else {
            System.out.println("No active booking found to attend.");
        }
    }

    private void writeReview() {
        Member member = findMember();
        if (member == null) return;
        int index = readInt("Lesson index to review: ");
        if (index < 0 || index >= timetable.getLessons().size()) return;
        
        Lesson lesson = timetable.getLessons().get(index);
        Booking booking = lesson.getBooking(member);
        
        if (booking == null || !booking.isAttended()) {
            System.out.println("You can only review lessons you have attended.");
            return;
        }

        int rating = readInt("Rating (1-5): ");
        System.out.print("Comment: ");
        String comment = scanner.nextLine();

        lesson.addReview(new Review(member, rating, comment));
        System.out.println("Review added.");
    }

    private void generateLessonReport() {
        int month = readInt("Enter month number (e.g., 5 for May): ");

        System.out.println("--- LESSON REPORT FOR MONTH " + month + " (Grouped by Day) ---");
        for (int w = 1; w <= 8; w++) {
            boolean hasLessonsInWeekend = false;
            for (Lesson l : timetable.getLessons()) {
                if (l.getMonth() == month && l.getWeekend() == w) hasLessonsInWeekend = true;
            }
            if (!hasLessonsInWeekend) continue;

            System.out.println("\n== WEEKEND " + w + " ==");
            for (String d : new String[]{"Saturday", "Sunday"}) {
                System.out.println(" -> " + d);
                for (Lesson l : timetable.getLessons()) {
                    if (l.getMonth() == month && l.getWeekend() == w && l.getDay().equalsIgnoreCase(d)) {
                        System.out.println("    " + l.getExerciseName() + " (" + l.getTime() + ") | Attended: " + l.getAttendedCount() + " | Avg rating: " + l.getAverageRating());
                    }
                }
            }
        }
    }

    private void generateIncomeReport() {
        int month = readInt("Enter month number (e.g., 5 for May): ");

        Map<String, Double> income = new HashMap<>();
        for (Lesson lesson : timetable.getLessons()) {
            if (lesson.getMonth() == month) {
                double value = lesson.getPrice() * lesson.getMemberCount();
                income.put(
                        lesson.getExerciseName(),
                        income.getOrDefault(lesson.getExerciseName(), 0.0) + value
                );
            }
        }
        
        System.out.println("--- INCOME REPORT FOR MONTH " + month + " ---");
        String bestExercise = "";
        double max = -1;
        for (String ex : income.keySet()) {
            System.out.println(ex + " generated £" + income.get(ex));
            if (income.get(ex) > max) {
                max = income.get(ex);
                bestExercise = ex;
            }
        }
        if (max >= 0) {
            System.out.println("Highest income exercise: " + bestExercise + " (£" + max + ")");
        } else {
            System.out.println("No income generated yet for this month.");
        }
    }

    private void viewAllMembers() {
        System.out.println("\n+------------+-----------------------------------+");
        System.out.printf("| %-10s | %-33s |\n", "Member ID", "Member Name");
        System.out.println("+------------+-----------------------------------+");
        if (members.isEmpty()) {
            System.out.printf("| %-45s |\n", "No members registered yet.");
        } else {
            for (Member m : members) {
                System.out.printf("| %-10d | %-33s |\n", m.getId(), m.getName());
            }
        }
        System.out.println("+------------+-----------------------------------+\n");
    }

    // --- HELPER METHODS FOR ROBUST INPUT ---

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Discard the invalid input
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid decimal number.");
                scanner.nextLine(); // Discard the invalid input
            }
        }
    }
}