package alihamuh.quotes.motivational.inspirational.quotesapplication;

public class CompleteQuote {

    private String quoteText;
    private String quoteAuthor;
    private String quoteBook;
    private int ID;

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public void setQuoteBook(String quoteBook) {
        this.quoteBook = quoteBook;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public String getQuoteBook() {
        return quoteBook;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public int getID() {
        return ID;
    }
}
