public class Main {
    public static void main(String[] args) {
        LibraryApp app = new LibraryApp();
        Book book = new Book("B01", "Clean Code", 1);

        app.borrowBook("Andi", "andi@ui.ac.id", book);    // succeeds
        app.borrowBook("Citra", "citra@ui.ac.id", book);  // rejected: out of stock

        // Andi returns the book: stock is changed directly from outside the Book class
        book.stock = book.stock + 1;
        app.borrowBook("Citra", "citra@ui.ac.id", book);  // succeeds
    }
}
