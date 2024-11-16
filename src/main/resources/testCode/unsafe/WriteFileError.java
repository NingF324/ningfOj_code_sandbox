import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @description: 占用系统资源（内存占用过多）
 * @author: Lenovo
 * @time: 2024/11/14 上午10:20
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        String userDir =System.getProperty("user.dir");
        String filePath = userDir + File.separator + "src/main/resources/木马.bat";
        String errorProgram = "java -version 2>&1";
        Files.write(Paths.get(filePath), Arrays.asList(errorProgram));
        System.out.println("木马写入成功");
    }
}
