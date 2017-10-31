package com.upd.common.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by zhangshao on 2015/6/9
 * 读取指定配置文件
 */
public class PropertyUtil extends Properties {
    public PropertyUtil() {
        String basePath = this.getClass().getResource("/").getPath();
        String filePath = basePath + "/file.properties";

        try {
            File file = new File(filePath);
            if (file.exists()) {
                InputStream in = new BufferedInputStream(new FileInputStream(
                        file));
                this.load(in);
            } else {
                System.out.println(filePath+" not exists");
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
