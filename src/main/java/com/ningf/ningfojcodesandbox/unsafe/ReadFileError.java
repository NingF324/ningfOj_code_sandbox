package com.ningf.ningfojcodesandbox.unsafe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 占用系统资源（内存占用过多）
 * @author: Lenovo
 * @time: 2024/11/14 上午10:20
 */
public class ReadFileError {
    public static void main(String[] args) throws InterruptedException, IOException {
        String userDir =System.getProperty("user.dir");
        String filePath = userDir + File.separator + "src/main/resources/application.yml";
        List<String> allLines = Files.readAllLines(Paths.get(filePath));
        System.out.println(String.join("\n",allLines));
    }
}
