package qtriptest.utility;
import java.util.UUID;

public class RandomEmailGenerator {
    public String generateRandomEmail() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString() + "@testexample.comm";
    }
}
