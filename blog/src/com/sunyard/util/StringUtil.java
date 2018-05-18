package com.sunyard.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;


/** 
* @author  作者 yanl.fu: 
* @date    时间：2018年1月30日 上午10:38:00 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class StringUtil {
	public static final String arrTest[] = {"[br]", "[/b]", "[/i]", "[/u]", "[/size]", "[/color]", "[/align]", "[/url]", "[/email]", "[/img]"};
    public static final String arrParam[] = {"\\[br\\]", "\\[b\\](.+?)\\[/b\\]",
            "\\[i\\](.+?)\\[/i\\]",
            "\\[u\\](.+?)\\[/u\\]",
            "\\[size=(.+?)\\](.+?)\\[/size\\]",
            "\\[color=(.+?)\\](.+?)\\[/color\\]",
            "\\[align=(.+?)\\](.+?)\\[/align\\]",
            "\\[url=(.+?)\\](.+?)\\[/url\\]",
            "\\[email=(.+?)\\](.+?)\\[/email\\]," +
                    "\\[img=(.+?)\\](.+?)\\[/img\\]"};
    public static final String arrCode[] = {"<br>", "<b>$1</b>", "<i>$1</i>", "<u>$1</u>",
            "<font size=\"$1\">$2</font>",
            "<font color=\"$1\">$2</font>",
            "<div align=\"$1\">$2</div>",
            "<a href=\"$1\" target=\"_blank\">$2</a>",
            "<a href=\"email:$1\">$2</a>",
            "<img src=\"$1\" border=0>$2</img>"};


    public static int getInt(String content) {
        int intContent;
        try {
            intContent = Integer.parseInt(content);
        } catch (Exception e) {
            intContent = 0;
        }
        return intContent;
    }

    public static long getLong(String content) {
        long lngContent;
        try {
            lngContent = Long.parseLong(content);
        } catch (Exception e) {
            lngContent = 0L;
        }
        return lngContent;
    }

    /** 1
     * @param str    原字符串
     * @param length 字符串达到多长才截取
     * @return
     */
    @SuppressWarnings("static-access")
	public static String subStringToPoint(String str, int length, String more) {

        String reStr = "";

        if (str.length() * 2 - 1 > length) {

            int reInt = 0;

            if (str == null)

                return "";

            char[] tempChar = str.toCharArray();

            for (int kk = 0; (kk < tempChar.length && length > reInt); kk++) {

                String s1 = str.valueOf(tempChar[kk]);

                byte[] b = s1.getBytes();

                reInt += b.length;

                reStr += tempChar[kk];

            }

            if (length == reInt || (length == reInt - 1)) {

                if (!reStr.equals(str)) {
                    reStr += more;
                }
            }

        } else {
            reStr = str;
        }
        return reStr;

    }


    /** 2
     * 将指定的对象转换为String类型
     *
     * @param curObject 传入对象参数
     * @return String
     */
    public static String getString(Object curObject) {
        if (null == curObject) {
            throw new NullPointerException("The input object is null.");
        } else {
            return curObject.toString();
        }
    }

    /** 3
     * 转换字符,用于替换提交的数据中存在非法数据:"'"
     *
     * @param Content
     * @return
     */
    public static String replaceChar(String content) {
        String newstr = "";
        newstr = content.replaceAll("\'", "''");
        return newstr;
    }

    /**4
     * 对标题""转换为中文“”采用对应转换
     *
     * @param Content
     * @return
     */
    public static String replaceSymbol(String content) {
        int intPlaceNum = 0;
        int Num = 0;
        String strContent = content;
        while (true) {
            //判断是否还存在"
            intPlaceNum = strContent.indexOf("\"");
            if (intPlaceNum < 0) {
                break;
            } else {
                if (Num % 2 == 0) {
                    strContent = strContent.replaceFirst("\"", "“");
                } else {
                    strContent = strContent.replaceFirst("\"", "”");
                }
                Num = Num + 1;
            }
        }
        return strContent;
    }

    /**5
     * 替换HTML标记
     *
     * @param Content
     * @return
     */
    public static String replaceCharToHtml(String content) {
        String strContent = content;
        strContent = strContent.replaceAll("<", "<");
        strContent = strContent.replaceAll(">", ">");
        strContent = strContent.replaceAll("\"", "\"");
        return strContent;
    }

    public static String replaceHtmlToChar(String content) {
        String strContent = content;
        strContent = strContent.replaceAll("<", "<");
        strContent = strContent.replaceAll(">", ">");
        strContent = strContent.replaceAll("\"", "\"");
        return strContent;
    }

    //数据库替换
    public static String replaceCharToSql(String content) {
        String strContent = content;
        strContent = strContent.replaceAll("%", "\\\\%");
        return strContent;
    }

    public static String toHtmlValue(String value) {
        if (null == value) {
            return null;
        }
        char a = 0;
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            a = value.charAt(i);
            switch (a) {
                // 双引号
                case 34:
                    buf.append("\"");
                    break;
                // &号
                case 38:
                    buf.append("&");
                    break;
                // 单引号
                case 39:
                    buf.append("'");
                    break;
                // 小于号
                case 60:
                    buf.append("<");
                    break;
                // 大于号
                case 62:
                    buf.append(">");
                    break;
                default:
                    buf.append(a);
                    break;
            }
        }
        return buf.toString();
    }


    /**6
     * 标题中含有特殊字符替换 如:●▲@◎※ 主要在标题中使用
     *
     * @param Content
     * @return
     */
    public static String replaceSign(String content) {
        String strContent = "";
        strContent = content.replaceAll("\\*", "");
        strContent = strContent.replaceAll("\\$", "");
        strContent = strContent.replaceAll("\\+", "");
        String arrStr[] = {":", "：", "●", "▲", "■", "@", "＠",
                "◎", "★", "※", "＃", "〓", "＼", "§", "☆",
                "○", "◇", "◆", "□", "△", "＆", "＾", "￣",
                "＿", "♂", "♀", "Ю", "┭", "①", "「", "」", "≮", "§",
                "￡", "∑", "『", "』", "⊙", "∷", "Θ", "の", "↓", "↑",
                "Ф", "~", "Ⅱ", "∈", "┣", "┫", "╋", "┇", "┋", "→",
                "←", "!", "Ж", "#"};
        for (int i = 0; i < arrStr.length; i++) {
            if ((strContent.indexOf(arrStr[i])) >= 0) {
                strContent = strContent.replaceAll(arrStr[i], "");
            }
        }

        return strContent;
    }

    /**7
     * 替换所有英文字母
     *
     * @param Content
     * @return
     */
    public static String replaceLetter(String content) {
        String strMark = "[^[A-Za-z]+$]";
        String strContent = "";
        strContent = content.replaceAll(strMark, "");
        return strContent;
    }

    /**8
     * 替换所有数字
     *
     * @param Content
     * @return
     */
    public static String replaceNumber(String content) {
        String strMark = "[^[0-9]+$]";
        String strContent = "";
        strContent = content.replaceAll(strMark, "");
        return strContent;
    }

    /**9
     * 将/n转换成为回车<br> ,空格转为 
     *
     * @param Content
     * @return
     */
    public static String replaceBr(String content) {
        if (content == null) {
            return "";
        }
        String strContent = "";

        // String strMark ="[/\n\r\t]";

        //strContent = content.replaceAll(strMark,"<br>");

        strContent = content.replaceAll("\n\r\t", "<br>");
        strContent = strContent.replaceAll("\n\r", "<br>");
        strContent = strContent.replaceAll("\r\n", "<br>");
        strContent = strContent.replaceAll("\n", "<br>");
        strContent = strContent.replaceAll("\r", "<br>");
        strContent = strContent.replaceAll(" ", " ");
        return strContent;
    }

    /**10
     * 清除所有<>标记符号 主要在搜索中显示文字内容 而不显示样式
     *
     * @param Content
     * @return
     */
    public static String replaceMark(String content) {
        String strContent = "";
        String strMark = "<\\s*[^>]*>";
        strContent = content.trim();
        strContent = strContent.replaceAll("\"", "");
        strContent = strContent.replaceAll("\'", "");
        //删除所有<>标记
        strContent = strContent.replaceAll(strMark, "");
        strContent = strContent.replaceAll(" ", "");
        strContent = strContent.replaceAll(" ", "");
        strContent = strContent.replaceAll("　", "");
        strContent = strContent.replaceAll("\r", "");
        strContent = strContent.replaceAll("\n", "");
        strContent = strContent.replaceAll("\r\n", "");
        return strContent;
    }

    /**11
     * 清楚WOrd垃圾代码
     *
     * @param Content
     * @return
     */
    public static String clearWord(String content) {
        String strContent = "";
        strContent = content.trim();
        strContent = strContent.replaceAll("x:str", "");
        //Remove Style attributes
        strContent = strContent.replaceAll("<(\\w[^>]*) style=\"([^\"]*)\"", "<$1");
        //Remove all SPAN  tags
        strContent = strContent.replaceAll("<\\/?SPAN[^>]*>", "");
        //Remove Lang attributes
        strContent = strContent.replaceAll("<(\\w[^>]*) lang=([^ |>]*)([^>]*)", "<$1$3");
        //Remove Class attributes
        strContent = strContent.replaceAll("<(\\w[^>]*) class=([^ |>]*)([^>]*)", "<$1$3");
        //Remove XML elements and declarations
        strContent = strContent.replaceAll("<\\\\?\\?xml[^>]*>", "");
        //Remove Tags with XML namespace declarations: <o:p></o:p>
        strContent = strContent.replaceAll("<\\/?\\w+:[^>]*>", "");
        return strContent;
    }

    /**
     * 对组ID信息进行处理 转换为标准ID组 并过滤重复的信息
     *
     * @param teamId
     * @return
     */
    public static String checkTeamId(String teamId) {
        String strTeamId = "";
        String strTempId = "";
        String strTemp = "";
        String[] arrTeamId = teamId.split(",");
        for (int num = 0; num < arrTeamId.length; num++) {
            strTemp = arrTeamId[num].trim();
            if ((!strTemp.equals("")) && (!strTemp.equals("0"))) {
                if ((strTempId.indexOf("," + strTemp + ",")) >= 0) { //表示已经保存过了
                } else {
                    if (strTeamId.equals("")) {
                        strTeamId = strTemp;
                        strTempId = strTempId + "," + strTemp + ",";
                        ;
                    } else {
                        strTeamId = strTeamId + "," + strTemp;
                        strTempId = strTempId + strTemp + ",";
                    }
                }
            }
        }
        return strTeamId;
    }


    public static String replaceUbb(String content) {
        String strContent = content;
        try {
            for (int num = 0; num < arrTest.length; num++) {
                if ((strContent.indexOf(arrTest[num])) >= 0) {
                    try {
                        strContent = strContent.replaceAll(arrParam[num], arrCode[num]);
                    } catch (Exception ex) {
                    }
                }
            }
        } catch (Exception e) {
            //System.out.println("UBB CODE 错误"+e);
        }
        return strContent;
    }


    /**12
     * 判断传入的字符串如果为null则返回"",否则返回其本身
     *
     * @param string
     * @param instant
     * @return String
     */
    public static String convertNull(String string, String instant) {
        return isNull(string) ? instant : string;
    }

    /**
     * {@link #convertNull(String, String)}
     *
     * @param string
     * @return String
     */
    public static String convertNull(String string) {
        return convertNull(string, "");
    }

    /**
     * 判断对象是否为空
     *
     * @param obj Object
     * @return boolean 空返回true,非空返回false
     */
    public static boolean isNull(Object obj) {
        return (null == obj) ? true : false;
    }

    /**
     * Description:判断字段空null <br>
     *
     * @param s
     * @return boolean
     */
    public static boolean isNull(String s) {
        if (s == null || "".equals(s.trim())) {
            return true;
        }

        return false;
    }

    /**13
     * 获取百分比
     *
     * @param p1
     * @param p2
     * @return
     */
    public static String percent(double p1, double p2) {
        if (p2 == 0) {
            return "0.00%";
        }
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        str = nf.format(p3);
        return str;
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param oldCharset 原编码
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String changeCharset(String str, String oldCharset, String newCharset) {
        try {
            if (str != null) {
                //用旧的字符编码解码字符串。解码可能会出现异常。
                byte[] bs = str.getBytes(oldCharset);
                //用新的字符编码生成字符串
                return new String(bs, newCharset);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str        待转换编码的字符串
     * @param newCharset 目标编码
     * @return
     * @throws UnsupportedEncodingException
     */
    public String changeCharset(String str, String newCharset) {
        try {
            if (str != null) {
                //用默认字符编码解码字符串。
                byte[] bs = str.getBytes();
                //用新的字符编码生成字符串
                return new String(bs, newCharset);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解析html中的参数信息
     *
     * @param elementStr
     * @return
     */
    public static Map<String, String> getConfigValue(String elementStr) {
        try {
            elementStr = java.net.URLDecoder.decode(elementStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int start = elementStr.indexOf("configvalue");
        Map<String, String> map = null; //参数的键值对
        if (start != -1) {
            map = new HashMap<String, String>();
            start = elementStr.indexOf("\"", start);
            int end = elementStr.lastIndexOf("||");
            if (start < 0 || end < 0) {
                return null;
            }
            String configValue = elementStr.substring(start + 1, end);
            String[] values = configValue.split("\\|\\|");

            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (value != null && value.trim().length() > 1) {
                    int de = value.indexOf("=");
                    if (de > 0) {
                        String name = value.substring(0, de);
                        String v = value.substring(de + 1);
                        map.put(name, v);
                    }
                }
            }
        }
        return map;
    }

    /**
     * 转换空值为0
     *
     * @param str
     * @return
     */
    public static String conventString(String str) {
        return null == str || "".equals(str) ? "" + "0" : str;
    }


    
    
    /**
     * 方法描述：判断传入的字符串是否非空，即：字符串是否等于null、""或" "。
     * 创建日期：2013-12-7下午11:39:34
     * 修改日期：
     * 作者：zhanglu
     * @param:
     * @return:boolean
     */
    public static boolean isNotEmpty(String str)
	{
		if ((null == str) || ("".equals(str.trim())))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
    
    /**
     * 方法描述：将传入的字符串转换成整型数据，如果转换过程中发生异常，则返回默认值：defaultValue。
     * 创建日期：2013-12-7下午11:40:20
     * 修改日期：
     * 作者：zhanglu
     * @param:
     * @return:int
     */
    public static int convertIntoInt(String str, int defaultValue)
	{
		// 定义一个返回值，假如转型过程中发生异常，则返回此默认值
		int retData = defaultValue;
		try
		{
			retData = Integer.parseInt(str.trim());
		}
		catch(NumberFormatException e)
		{
		}
		catch(Exception ex)
		{
		}

		return retData;
	}
    
    /**
	 * 四舍五入求值
	 * 
	 * @param v     原始数值
	 * @param scale 小数点位数
	 * @return 如果scale参数小于0则返回v
	 */
	public static double round(double v,int scale) {
		if(scale<0){
			return v;
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal oneValue = new BigDecimal("1");
		return b.divide(oneValue,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
