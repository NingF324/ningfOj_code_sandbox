/**
 * @description: 程序运行阻塞
 * @author: Lenovo
 * @time: 2024/11/14 上午10:20
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        long ONE_HOUR = 60*60*1000;
        Thread.sleep(ONE_HOUR);
        System.out.println("睡醒了");
    }
}