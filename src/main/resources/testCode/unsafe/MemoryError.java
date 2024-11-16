import java.util.ArrayList;
import java.util.List;

/**
 * @description: 占用系统资源（内存占用过多）
 * @author: Lenovo
 * @time: 2024/11/14 上午10:20
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<byte[]> bytes = new ArrayList<>();
        while (true) {
            bytes.add(new byte[10000]);
        }
    }
}