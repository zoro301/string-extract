package com.renqiang.str.extract;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class MessageVerifyUtil {
	/**
     * 判断一个字符串是否是汉字。国际化时使用
     * @param str 字符串内容
     * @return 如果此字符串中包含中文字符，返回true。
     */
    public static boolean isChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            //汉字Unicode编码区间 0x4E00->0x9FA5
            if (ch >= 0x4E00 && ch <= 0x9FA5) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 把一个源代码文件中的字符串文本提取到消息文件中，并把源代码中的字符串修改为到消息文件的引用。
     * @param file 支持java
     * @param encoding
     * @param mf
     * @throws IOException
     */
    public static void fetchStringMessages(String file, String encoding, IMessageFile mf) throws IOException {
        List<String> tokens = JavaTokenizer.parse(new File(file), encoding);
        boolean changed = false;
        for (int i = 0; i < tokens.size(); i++) {
            String tk = tokens.get(i);
            if (tk.startsWith("\"") && tk.endsWith("\"")) {//提取字符串
            	String newStr = mf.input(tk);
                if (newStr != null) {
                    tokens.set(i, newStr);
                    changed = true;
                }
            }
        }
        if (changed) {
            //替换字符串，重写文件
        	JavaTokenizer.save(new File(file), encoding, tokens);
        }
    }
}
