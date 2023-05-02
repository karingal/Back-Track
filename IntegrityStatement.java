
public final class IntegrityStatement {
    public static String signature() {
        String names = "Karin Gal and Einav Tougendhaft"; // <- Fill in your names here!
        if (names.length() == 0) {
            throw new UnsupportedOperationException("You should sign here");
        }
        return names;
    }
}
