package moldel;

import java.sql.Date;

public class Lending {
    private final int id;
    private final Date date;
    private final int borrowedFilmID;
    private final String borrowingPerson;
    private final Date backDate;

    public Lending(int id, Date date, int borrowedFilmID, String borrowingPerson, Date backDate) {
        this.id = id;
        this.date = date;
        this.borrowedFilmID = borrowedFilmID;
        this.borrowingPerson = borrowingPerson;
        this.backDate = backDate;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getBorrowedFilmID() {
        return borrowedFilmID;
    }

    public String getBorrowingPerson() {
        return borrowingPerson;
    }

    public Date getBackDate() {
        return backDate;
    }
    
}
