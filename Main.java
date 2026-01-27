//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Book> listBook = new ArrayList<>();
        Scanner x = new Scanner(System.in);

        String msg = """
            Chương trình quản lý sách
            1. Thêm 1 cuốn sách
            2. Xóa 1 cuốn sách
            3. Thay đổi sách
            4. Xuất thông tin
            5. Tìm sách lập trình
            6. Lấy sách tối đa theo giá
            7. Tìm kiếm theo tác giả
            0. Thoát
            Chọn chức năng:""";

        int chon = 0;
        do
        {
            System.out.printf(msg);
            chon = x.nextInt();

            switch (chon)
            {
                case 1 -> {
                    Book newBook = new Book();
                    newBook.input();
                    listBook.add(newBook);
                }
                case 2 -> {
                    System.out.print("Nhập vào mã sách cần xóa:");
                    int bookid = x.nextInt();
                    // kiểm tra mã sách
                    Book find = listBook.stream().filter(p -> p.getId() == bookid).findFirst().orElseThrow();
                    listBook.remove(find);
                    System.out.print("Đã xóa sách thành công");
                }
                case 3 -> {
                    System.out.print("Nhập vào mã sách cần điều chỉnh:");
                    int bookid = x.nextInt();
                    Book find = listBook.stream().filter(p -> p.getId() == bookid).findFirst().orElseThrow();
                }
                case 4 -> {
                    System.out.println("\n Xuất thông tin danh sách ");
                    listBook.forEach(p -> p.output());
                }
                case 5 -> {
                    List<Book> list5 = listBook.stream()
                            .filter(u -> u.getTitle().toLowerCase().contains("lập trình"))
                            .toList();

                    list5.forEach(Book::output);
                }
                case 6 -> {
                    List<Book> list6 = listBook.stream()
                            .sorted((a, b) -> Long.compare(b.getPrice(), a.getPrice()))
                            .limit(1)
                            .toList();

                    list6.forEach(Book::output);
                }
                case 7 -> {
                    System.out.print("Nhập các tác giả cần tìm (cách nhau bởi dấu phẩy): ");
                    x.nextLine();
                    String input = x.nextLine().toLowerCase();

                    Set<String> authorSet = Arrays.stream(input.split(","))
                            .map(String::trim)
                            .collect(Collectors.toSet());

                    Set<Book> result = listBook.stream()
                            .filter(b -> authorSet.contains(b.getAuthor().toLowerCase()))
                            .collect(Collectors.toSet());

                    result.forEach(Book::output);
                }
            }
        } while (chon != 0);
    }
}