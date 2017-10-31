package com.upd.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
	//默认除法运算精度   
  private static final int DEF_DIV_SCALE = 10;

	/**
	 * 获得一个UUID
	 * @return String UUID
	 */
	public static String getUUID(){
		String s = UUID.randomUUID().toString();
		//去掉“-”符号
		return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
	}
	public static String replaceNR(String Str) {
    	if(Str!=null){
    	    Str =Str.replaceAll("\n","<br/>");
    	    Str =Str.replaceAll(" ","&nbsp;");
    	}
    	return Str;
	}
	
    
    public static String getRandom(int count) {
        Random r = new Random();
        Double d = r.nextDouble();
        String s = d + "";
        s=s.substring(3,3+count);
        return s;
    }
    
    public static String getDecode(String encode) {
    	String a="";
    	try {
    		a=java.net.URLDecoder.decode(encode, "UTF-8");
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return a;
    }
    public static String getDecode2(String encode) {
    	String a="";
    	try {

			a=java.net.URLDecoder.decode(encode.replaceAll("%", "%25"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return a;
    }
    
    public static String Encode(String code) {
    	String a="";
    	try {
    		a=URLEncoder.encode(code, "UTF-8") ;			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return a;
    }
    public static String formatUtf8(String encode) {
    	String a="";
        try {
            if(encode!=null)
            a = new String(encode.getBytes("ISO8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return a;
    }
    public static String formatGBK(String encode) {
    	String a="";
    	try {
    		if(encode!=null)
    			a = new String(encode.getBytes("ISO8859_1"), "GBK");
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	}
    	return a;
    }
    
    //String 转 Date
    public static Date StringToDate(String t, String kind)
    {
    	 Date dat = null;
    	 try { 
	        SimpleDateFormat format = new SimpleDateFormat(kind);
	        dat = format.parse(t);
 		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return dat;
    }
  //String 转 Date
    public static String DateToString(Date date, String kind)
    {
    	String dateString="";
    	 try { 
	        SimpleDateFormat format = new SimpleDateFormat(kind);
	        dateString=format.format(date);
 		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return dateString;
    }
    
    //区域码转换
    public static String NewAreaType(String areaType) throws Exception
    {
    	String a=areaType;
	 
		    	 while(a.endsWith("00")){
		    		 a=a.substring(0,a.length()-2);
		    	 }
	
        return a;
    }
    //是否是数字
    public static boolean isNumeric(String str){ 
        Pattern pattern = Pattern.compile("[0-9]*"); 
        return pattern.matcher(str).matches();    
     } 
    //是否是数字
    public static String trimall(String str){ 
    	String pattern = str.replaceAll("\\s*", "");; 
    	return pattern;    
    } 
     

	//近似二位显示数值
	public static String FloatData( Float i) {
	
		DecimalFormat dec = new DecimalFormat("0.00");
		String d=dec.format(i);
		if(d.indexOf(".00")>-1){
			d=d.substring(0,d.length()-3);
		}else if(d.endsWith("0")&&d.substring(d.length()-3,d.length()-2).equals(".")){
			d=d.substring(0,d.length()-1);
		}
		return d;
	}
	//如果为0则返回空
	public static String changeToString(Object value)
	{
		String returnvalue="";
		if(value!=null){
			if(value  instanceof Float  )
			{
				if((Float)value!=0)
				{
					returnvalue=String.valueOf(value);
				}
			}
			else if(value  instanceof Double  )
			{
				if((Double)value!=0)
				{
					returnvalue=String.valueOf(value);
				}
			}
			else if(value  instanceof Integer  )
			{
				if((Integer)value!=0)
				{
					returnvalue=String.valueOf(value);
				}
			}
		}
		return returnvalue;

	}


    public static Object byteStreamToObject(byte[] bytes) {

        if (null != bytes && 0 < bytes.length) {

            ByteArrayInputStream bis = null;

            ObjectInputStream ois = null;

            try {

                bis = new ByteArrayInputStream(bytes);

                ois = new ObjectInputStream(bis);

                return ois.readObject();

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            } catch (ClassNotFoundException e) {

                e.printStackTrace();

            } finally {

                if (null != bis) {

                    try {

                        bis.close();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }



                if (null != ois) {

                    try {

                        ois.close();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }

            }

        }

        return null;

    }



    public static byte[] objectToByteStream(Object obj) {

        if (null != obj) {

            ObjectOutputStream oos = null;

            ByteArrayOutputStream bos = null;

            try {

                bos = new ByteArrayOutputStream();

                oos = new ObjectOutputStream(bos);

                oos.writeObject(obj);

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                if (null != bos) {

                    try {

                        bos.close();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }

                if (null != oos)

                    try {

                        oos.close();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

            }



            if (null != bos) {

                try {

                    return (bos.toByteArray());

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        }

        return null;

    }
    public static String getDistance(Float scroe){
		String distance = scroe * 111.1951+"";
		return distance;
	}
    public static Integer getPersistenceTime(int num,int unit){
    	Integer time = 0;
    	if(1==unit){
    		time = num*365;
    	}else if(2==unit){
    		time = num*30;
    	}else if(3==unit){
    		time = num;
    	}
    	return time;
    }
    public static String getPersistenceTimeDisplay(int num,int unit,String ex){
    	String time = "";
    	if(1==unit){
    		time = num+"年";
    	}else if(2==unit){
    		time = num+"月";
    	}else if(3==unit){
    		time = num+"天";
    	}
    	if(ex!=null&&!"".equals(ex)){
    		time = time+"+";
    		if(1==unit){
    			time=time+ex+"年";
        	}else if(2==unit){
        		time=time+ex+"月";
        	}else if(3==unit){
        		time=time+ex+"天";
        	}
    	}
    	return time;
    }



    public static String TextEncode(String Str){
    	if(Str!=null){
    	    Str =Str.replaceAll("\n","<br/>");
    	    Str =Str.replaceAll(" ","&nbsp;");


    	}
    	return Str;
    }

    public static boolean isNumber(String str)
    {
    	if(str!=null){
	        Pattern pattern= Pattern.compile("[0-9]*");
	        Matcher match=pattern.matcher(str);
	        if(match.matches()==false)
	        {
	             return false;
	        }
	        else
	        {
	             return true;
	        }
    	}else{
    		return false;
    	}
    }
    public static String getNum(String str) {

    	str=str.trim();
    	String str2="";
    	if(str != null && !"".equals(str)){
	    	for(int i=0;i<str.length();i++){
		    	if(str.charAt(i)>=48 && str.charAt(i)<=57){
		    	 str2+=str.charAt(i);
		    	}
	    	}
    	}
    	return str2;
    }
    /**
     * 判断是否是数字，包括小数
     * @param str
     * @return
     */
public static boolean getIsNum(String str) {

    	str=str.trim();
    	if(str != null && !"".equals(str)){
    		String reg = "\\d+(\\.\\d+)?";
    		return 	str.matches(reg);
    	}
    	else
    	{
    		return true;
    	}
    }
    public static String BigDecimal2String(BigDecimal b) {
		DecimalFormat df =new DecimalFormat("##0.00");
		return df.format(b);
    }
    //小数点后两位一律进 没数字也进
    public static String BigDecimal2StringBig(BigDecimal b) {
    	b=b.setScale(2, BigDecimal.ROUND_UP);
		DecimalFormat df =new DecimalFormat("##0.00");
		return df.format(b);
    }
    //小数点后两位一律退
    public static String BigDecimal2StringSmall(BigDecimal b) {
    	b=b.setScale(2, BigDecimal.ROUND_DOWN);
		DecimalFormat df =new DecimalFormat("##0.00");
		return df.format(b);
    }
    public static String BigDecimal2StringSmall(BigDecimal b,Integer length) {
    	b=b.setScale(length, BigDecimal.ROUND_DOWN);
    	String format = "##0.";
    	for(int i = 0 ;i<length;i++){
    		format+="0";
    	}
    	DecimalFormat df =new DecimalFormat(format);
    	return df.format(b);
    }

    public static String DoubletoDecimal(double b) { 
    	DecimalFormat df = new DecimalFormat("###,##0.00");
		return df.format(b);
    }
   //处理除法 除不尽的情况
    public static BigDecimal div(BigDecimal v1,BigDecimal v2){   
        return div(v1,v2,DEF_DIV_SCALE);   
    }   

       public static BigDecimal div(BigDecimal v1,BigDecimal v2,int scale){   
            if(scale<0){   
               throw new IllegalArgumentException(   
                    "The scale must be a positive integer or zero");   
           }   
            return v1.divide(v2,scale,BigDecimal.ROUND_HALF_UP);   
      }   

    /**
     * 将一种时间格式 转化成另一种时间格式输出
     */
   
    public static String TimetoTime(String srcTimeString,String srckind,String dirkind) { 
    	try {
		String  dateString=	StringUtil.DateToString(StringUtil.StringToDate(
                        srcTimeString, srckind),
                dirkind);
		return dateString;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
    }
    //转换成万为单位
    public static String FormatToTenThousand(String money){
    	if(money==null||money.equals("")){
    		return "0";
    	}
    	Double dm=new Double(money);
    	Double  tsm=dm/10000;
    	return tsm.toString();
    }
    //获取一个日期往后几天的日期 如果前几天 dateInterval 为负数
    public  static Date getIntervalDate(Date dNow,int dateInterval){
		Date dafter = new Date();
		Calendar calendar = Calendar.getInstance();  //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, dateInterval);  //设置为前多少天
		dafter = calendar.getTime();   //得到前一天的时间
		return  dafter;
    }
	public static boolean filterSql(String sSql)
	{
		int srcLen, decLen = 0;
		sSql = sSql.toLowerCase().trim();
		srcLen = sSql.length();
		sSql = sSql.replaceAll("exec", "");
		sSql = sSql.replaceAll("delete", "");
		sSql = sSql.replaceAll("master", "");
		sSql = sSql.replaceAll("truncate", "");
		sSql = sSql.replaceAll("declare", "");
		sSql = sSql.replaceAll("create", "");
		sSql = sSql.replaceAll("xp_", "no");
		decLen = sSql.length();
		if (srcLen == decLen) return true; else return false;
	}
	//将float字符串转化成 float数组
	public static float[] String2Float(String floatstrs){
	   	String[] floatstr=floatstrs.split(",");
		float[] floats=new float[floatstr.length];
		for(int i=0; i<floatstr.length-1; i++){
			float fstr=Float.parseFloat(floatstr[i]);
			floats[i]=fstr;
		}
		return floats;
	}
	// md5加密
	public static String md5(String str) {
		String s = str;
		if (s == null) {
			return "";
		} else {
			String value = null;
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
			}
			sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
			try {
				value = baseEncoder.encode(md5.digest(s.getBytes("utf-8")));
			} catch (Exception ex) {
			}
			return value;
		}
	}

	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);


		return m.matches();
	}

	//返回yyMMddHHmmssmm格式的时间信息
	public static String getTime1(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		return simpleDateFormat.format(date);
	}
	public static String arrayToString(String[] arry){
		if(arry==null){
			return "";
		}
		String str="";
		for(String stat: arry){
			str+=stat+",";
		}
		if(!str.equals("")){
		return  str.substring(0,str.length()-1);
		}
		return  str;
	}
}
