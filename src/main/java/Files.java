import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Files {
    public static void main(String[] args) {
//        File[] files = File.listRoots();
//        for (File f : files) {
//            System.out.println(f.getPath());
//        }
//        System.out.println("1");
        File file = new File("src\\main\\resources\\manager\\file_manager_view.fxml");
        System.out.println(file.exists());
        System.out.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneOffset.ofHours(0)));
    }
}
