import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @description: 占用系统资源（内存占用过多）
 * @author: Lenovo
 * @time: 2024/11/14 上午10:20
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        String userDir =System.getProperty("user.dir");
        String filePath = userDir + File.separator + "src/main/resources/木马.bat";
        Process process = Runtime.getRuntime().exec(filePath);
        process.waitFor();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String compileOutputLine;
        while ((compileOutputLine = bufferedReader.readLine())!= null){
            System.out.println(compileOutputLine);
        }
        System.out.println("程序执行成功");
    }
}
