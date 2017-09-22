package com.renqiang.str.extract;

public class MessageUtil {

	public static String translateStringConstant(String str){
		StringBuffer buf = new StringBuffer();
		char[] data = str.toCharArray();
		int dataCount = data.length - 1;
		//str参数包含双引号，去除头尾的双引号
		for (int i = 1; i < dataCount; i++) {
			char ch = data[i];
			if (ch == '\\')
			{
				switch (data[(i + 1)])
				{
				case 'n': 
					buf.append("\n");
					break;
				case 'r': 
					buf.append("\r");
					break;
				case 't': 
					buf.append("\t");
					break;
				case 'o': 
				case 'p': 
				case 'q': 
				case 's': 
				default: 
					buf.append(data[(i + 1)]);
				}
				i++;
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}


	public static String reverseConv(String msg){
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < msg.length(); i++) {
			switch (msg.charAt(i)) {
			case '\n': 
				buf.append("\\n");
				break;
			case '\r': 
				buf.append("\\r");
				break;
			case '\t': 
				buf.append("\\t");
				break;
			case '"': 
				buf.append("\\\"");
				break;
			case '\\': 
				buf.append("\\\\");
				break;
			default: 
				buf.append(msg.charAt(i));
			}
		}
		return buf.toString();
	}
}
