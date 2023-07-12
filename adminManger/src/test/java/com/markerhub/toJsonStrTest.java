package com.markerhub;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.markerhub.common.lang.Result;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class toJsonStrTest {
    public static void main(String[] args) throws IOException {
        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
        outputStream.write(JSONUtil.toJsonStr(Result.fail("前登录")).getBytes(StandardCharsets.UTF_8));
        String s = JSONUtil.toJsonStr(Result.fail("前登录"));
        System.out.println(s);
        System.out.println(outputStream.toString());
        outputStream.flush();
        outputStream.close();
    }
}
