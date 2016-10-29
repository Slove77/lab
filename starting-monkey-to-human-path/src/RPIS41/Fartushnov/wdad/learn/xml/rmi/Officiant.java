package RPIS41.Fartushnov.wdad.learn.xml.rmi;/**
 * Created by никита on 29.10.2016.
 */
public class Officiant {
    private final String firstName;
    private final String secondName;

    public Officiant(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }
}
