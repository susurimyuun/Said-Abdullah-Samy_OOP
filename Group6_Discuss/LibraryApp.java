public class LibraryApp {
    private MySqlDatabase db;

    public LibraryApp() {
        // This class creates the concrete database and its credentials by itself
        db = new MySqlDatabase();
        db.connect("db.perpus.ui.ac.id", "admin", "rahasia123");
    }

    public void borrowBook(String name, String email, Book book) {
        // 1. Borrowing rule
        if (book.stock <= 0) {
            System.out.println("REJECTED: \"" + book.title + "\" is out of stock");
            return;
        }
        book.stock = book.stock - 1;

        // 2. Save to the MySQL database
        db.executeSql("UPDATE books SET stock=" + book.stock
                + " WHERE code='" + book.code + "'");
        db.executeSql("INSERT INTO loans VALUES ('" + name + "','" + book.code + "')");

        // 3. Send a confirmation email through the SMTP server
        System.out.println("[SMTP mail.ui.ac.id] To: " + email);
        System.out.println("[SMTP] Subject: Borrowing " + book.title + " succeeded");

        System.out.println("OK: " + name + " borrowed \"" + book.title + "\"");
    }
}
