/***********************************************************************
 * Module:  CN2PY.java
 * Author:  Winston
 * Purpose: Defines the Class CN2PY
 ***********************************************************************/

package com.upd.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 中文转换为拼音实现类
 * 
 * @pdOid a1420139-30ac-4011-ba2a-5a1224f11b5f
 */
public class CN2PY {

	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * 根据中文字符串得到拼音字符串，使用pinying4j转换。
	 * 
	 * @param cnstr
	 *            中文字符串
	 * @return 转换后的pinying字符串
	 * @pdOid f71cd487-04e2-4ab4-8467-554ced97ee9a
	 */
	public static String getPinYingStr(String cnstr) {

		String zhongWenPinYin = "";
		char[] chars = cnstr.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			String[] pinYin = null;

			try {
				pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i],
						getDefaultOutputFormat());
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
			// 当转换不是中文字符时,返回null
				if (pinYin != null) {
					zhongWenPinYin += capitalize(pinYin[0]);
				} else {
					zhongWenPinYin += chars[i];
				}
			
		}

		return zhongWenPinYin;
	}

	public static HanyuPinyinOutputFormat getDefaultOutputFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 没有音调数字
		format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// u显示

		return format;
	}

	public static String capitalize(String s) {
		char ch[];
		ch = s.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		String newString = new String(ch);
		return newString;
	}
	/**
	 * 提取每个汉字的首字母
	 *
	 * @param str
	 * @return String
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}
}
