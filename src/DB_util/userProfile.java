package DB_util;
import java.util.Date;

public class userProfile {
    public String country;
    public int age;
    public Date registerDate;
    public int postScore;
    public userProfile(String _country, int _age, Date _registerDate, int _postScore)
    {
        country = _country;
        age = _age;
        registerDate = _registerDate;
        postScore = _postScore;
    }
    public userProfile()
    {

    }
}
