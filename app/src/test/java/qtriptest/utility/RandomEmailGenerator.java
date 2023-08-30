package qtriptest.utility;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomEmailGenerator {
    public String generateRandomEmail() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = dateFormat.format(new Date());

        //return dateString + "@testexample.comm";
        return dateString;
    }
}
