import java.util.*;
import java.io.*;

class Book {
    int id;
    String title, author, category;
    boolean issued;

    Book(int id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.issued = false;
    }

    void print() {
        System.out.println("Book ID: " + id + ", Title: " + title +
                ", Author: " + author + ", Category: " + category +
                ", Issued: " + (issued ? "Yes" : "No"));
    }

    void issue() { issued = true; }
    void ret() { issued = false; }
}

class Member {
    int id;
    String name, email;
    List<Integer> list = new ArrayList<>();

    Member(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    void print() {
        System.out.println("Member ID: " + id + ", Name: " + name + ", Email: " + email);
        System.out.println("Issued Books: " + list);
    }

    void add(int bid) { list.add(bid); }
    void rem(int bid) { list.remove(Integer.valueOf(bid)); }
}

public class LibrarySystem {
    Map<Integer, Book> books = new HashMap<>();
    Map<Integer, Member> members = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Book Title: ");
        String t = sc.nextLine();
        System.out.print("Enter Author: ");
        String a = sc.nextLine();
        System.out.print("Enter Category: ");
        String c = sc.nextLine();

        books.put(id, new Book(id, t, a, c));
        System.out.println("Book added successfully!");
        saveBooks();
    }

    void addMember() {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Member Name: ");
        String n = sc.nextLine();
        System.out.print("Enter Member Email: ");
        String e = sc.nextLine();

        members.put(id, new Member(id, n, e));
        System.out.println("Member added successfully!");
        saveMembers();
    }

    void issueBook() {
        System.out.print("Enter Member ID: ");
        int mid = sc.nextInt();
        System.out.print("Enter Book ID: ");
        int bid = sc.nextInt();

        if (!members.containsKey(mid) || !books.containsKey(bid)) {
            System.out.println("Invalid Member or Book ID!");
            return;
        }

        Book b = books.get(bid);
        Member m = members.get(mid);

        if (b.issued) System.out.println("Book already issued!");
        else {
            b.issue();
            m.add(bid);
            System.out.println("Book issued successfully!");
            saveBooks();
            saveMembers();
        }
    }

    void returnBook() {
        System.out.print("Enter Member ID: ");
        int mid = sc.nextInt();
        System.out.print("Enter Book ID: ");
        int bid = sc.nextInt();

        if (!members.containsKey(mid) || !books.containsKey(bid)) {
            System.out.println("Invalid Member or Book ID!");
            return;
        }

        Book b = books.get(bid);
        Member m = members.get(mid);

        if (b.issued) {
            b.ret();
            m.rem(bid);
            System.out.println("Book returned successfully!");
            saveBooks();
            saveMembers();
        } else System.out.println("Book was not issued!");
    }

    void searchBooks() {
        sc.nextLine();
        System.out.print("Enter keyword: ");
        String key = sc.nextLine().toLowerCase();

        for (Book b : books.values()) {
            if (b.title.toLowerCase().contains(key) ||
                b.author.toLowerCase().contains(key) ||
                b.category.toLowerCase().contains(key)) {
                b.print();
            }
        }
    }

    void sortBooks() {
        List<Book> list = new ArrayList<>(books.values());
        list.sort(Comparator.comparing(b -> b.title.toLowerCase()));
        System.out.println("Books sorted by title:");
        for (Book b : list) b.print();
    }

    void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("books.txt"))) {
            for (Book b : books.values()) {
                bw.write(b.id + "," + b.title + "," + b.author + "," + b.category + "," + b.issued);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving books!");
        }
    }

    void saveMembers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("members.txt"))) {
            for (Member m : members.values()) {
                bw.write(m.id + "," + m.name + "," + m.email + "," + m.list);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving members!");
        }
    }

    public static void main(String[] args) {
        LibrarySystem l = new LibrarySystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to City Library Digital Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int ch = sc.nextInt();
            switch (ch) {
                case 1: l.addBook(); break;
                case 2: l.addMember(); break;
                case 3: l.issueBook(); break;
                case 4: l.returnBook(); break;
                case 5: l.searchBooks(); break;
                case 6: l.sortBooks(); break;
                case 7: System.out.println("Thank you for using Library System!"); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }
}
