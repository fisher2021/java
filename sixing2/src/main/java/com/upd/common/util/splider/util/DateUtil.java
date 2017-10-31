package com.upd.common.util.splider.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * @(#)DateUtil.java 
 *       
 * 系统名称：     
 * 版本号：      1.0
 *  
 * Copyright (c)     All rights reserved 
 * 
 * 作者: 	  xiaotao
 * 创建日期:    2015年12月1日
 * 
 * 包名：
 * 功能描述：
 * 公用方法描述：
 * 
 * 修改人：
 * 修改日期：
 * 修改原因：
 * 
 **/
public class DateUtil {

private final static String Month_FORMAT = "yyyyMM";
	

    public static String objToStr(String str){
    	if(str==null || str.isEmpty() || str.trim().length()==0){
    		return "";
    	}
    	return str.trim();
    }

	/**
	 * @description:将时间转为字符串，并且格式为yyyy-MM-dd
	 * @param: 
	 * @return: String 
	 */
	public static String parseDate(Date date){
		if(date != null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}else{
			return null;
		}
	}
	/**
	 * @description:将时间转为前两年字符串，并且格式为yyyy-MM-dd
	 * @param: 
	 * @return: String 
	 */
	public static String parseDateYearAgo(Date date){
		if(date != null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.YEAR, -2);
			return sdf.format(cal.getTime());
		}else{
			return null;
		}
	}
	
	/**
	 * @description:将时间转为字符串，并且格式为yyyy-MM-dd HH:mm
	 * @param: 
	 * @return: String 
	 */
	public static String parseDateTimeToMin(Date date){
		if(date != null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return sdf.format(date);
		}else{
			return null;
		}
	}
	
	/**
	 * @description:将时间转为字符串，并且格式为yyyy-MM-dd HH:mm:ss
	 * @param: 
	 * @return: String 
	 */
	public static String parseDateTimeToSec(Date date){
		if(date != null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		}else{
			return null;
		}
	}
	
	/**
	 * @description：通过同步的方式给上传的文件重新获取名称。以时间解析得到.用于文件名的前缀名
	 * @param
	 * @return：String
	 * @方法编号：
	 */
	public synchronized  static String getTimeForFileName(){
		try {
			Thread.sleep(1L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssS");
		return sdf.format(date);
			
	}
	
	/**
	 * @description：生成年月
	 * @param
	 * @return：String 
	 */
	public static String getCurrYearMonth(int imonth) { 
		Calendar c= new GregorianCalendar();
		c.setTime(new Date());
		if(imonth!=0)
        c.add(Calendar.MONTH, imonth);
		SimpleDateFormat formatter = new SimpleDateFormat(Month_FORMAT);
		return formatter.format((c.getTime()));
		 
	}
	/**
	 * @description：生成年月
	 * @param
	 * @return：String 
	 */
	public static String getCurrYearMonth() { 
		Calendar c= new GregorianCalendar();
		c.setTime(new Date()); 
		SimpleDateFormat formatter = new SimpleDateFormat(Month_FORMAT);
		return formatter.format((c.getTime()));
		 
	}
	
	
	/**
	 *description:生成年
	 *@return  String 
	 */
	public static String getCurrYear() { 
		Calendar c= new GregorianCalendar();
		c.setTime(new Date()); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format((c.getTime()));
		 
	}
	
	/**
	 *description:生成月
	 *@return  String 
	 */
	public static String getCurrMoonth() { 
		Calendar c= new GregorianCalendar();
		c.setTime(new Date()); 
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		return formatter.format((c.getTime()));
		 
	}
	/**
	 *description:根据date  得到月
	 *@return  String 
	 */
	public static String getDateMonth(Date date) { 
		Calendar c= new GregorianCalendar();
		c.setTime(date); 
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		return formatter.format((c.getTime()));
		 
	}
	/**
	 *description:根据date  得到年
	 *@return  String 
	 */
	public static String getDateYear(Date date) { 
		Calendar c= new GregorianCalendar();
		c.setTime(date); 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format((c.getTime()));
	}
	
	/**
	 * @description:字符串转为时间 
	 */
	public static Date parseDateTimeToMin(String str){
		if(str !=null && !"".equals(str)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date d=null;
			try {
				d=sdf.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return d;
		}else{
			return null;
		}
	}
	
	/**
	 * @description:字符串转为时间 
	 */
	public static Date parseDate(String str){
		if(str !=null && !"".equals(str)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d=null;
			try {
				d=sdf.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return d;
		}else{
			return null;
		}
	}
	 
	/**
	 * @description:日期加天数得到新的日期 
	 */
    public static Date addDate(Date d,Integer day){
    	if(d != null){
    		Calendar cal=Calendar.getInstance();
    		cal.setTime(d);
    		cal.add(Calendar.DATE, day);
    		Date date=cal.getTime();
    		return date;
    	}else{
    		return null;
    	}
		
	}
    
    /**
     * @description: 日期加分钟得到新的日期 
     */
    public static Date addMinutes(Date d, Integer minu) {
    	if(d != null) {
    		Calendar cal=Calendar.getInstance();
    		cal.setTime(d);
    		cal.add(Calendar.MINUTE, minu);
    		Date date=cal.getTime();
    		return date;
    	}else{
    		return null;
    	}
    }
    
    /**
	 * @description:将时间转为字符串，并且格式为yyyy/MM/dd HH:mm:ss 
	 */
	public static String parseDateTimeToStr(Date date){
		if(date != null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			return sdf.format(date);
		}else{
			return null;
		}
	}
	
	 /**
	 * @description:将时间转为字符串，并且格式为dateFormat
	 * @param: sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	 * @return: String 
	 */
	public static String parseDateTimeToStrByDateFormat(Date date,String dateFormat){
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		if(date != null){			
			return sdf.format(date);
		}else{
			return sdf.format(new Date());
		}
	} 

	/**
	 * @description:字符串转为时间 
	 */
	public static Date parseDateTimeToDate(String str,String dateFormat){
		if(str !=null && !"".equals(str)){
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Date d=null;
			try {
				d=sdf.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return d;
		}else{
			return null;
		}
	}
	
	
	
	 /**
	 * @description:获取前一天是周几
	 * @param: b 
	 * @return:  
	 */ 
	public static Integer getLastDayOfWeek(){
		Calendar cal=Calendar.getInstance();
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		Integer weekDay = null;
		switch (dayOfWeek){
		     case 1:
			   weekDay=6;
			   break;
			  case 2:
		       weekDay=7;
			   break;
			  case 3:
			   weekDay=1;
			   break;
			  case 4:
			   weekDay=2;
			   break;
			  case 5:
			   weekDay=3;
			   break;
			  case 6:
			   weekDay=4;
			   break;
			  case 7:
			   weekDay=5;
			   break; 
			  }  
		return weekDay; 
	} 
	/**
	 * @description:获取当前日期是周几
	 * @param:   
	 * @return:  
	 */ 
	public static Integer getCurrentDayOfWeek(){
		Calendar cal=Calendar.getInstance();
		int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
		Integer weekDay = null;
		switch (dayOfWeek){
		     case 1:
			   weekDay=7;
			   break;
			  case 2:
		       weekDay=1;
			   break;
			  case 3:
			   weekDay=2;
			   break;
			  case 4:
			   weekDay=3;
			   break;
			  case 5:
			   weekDay=4;
			   break;
			  case 6:
			   weekDay=5;
			   break;
			  case 7:
			   weekDay=6;
			   break; 
			  }  
		return weekDay; 
	} 
	
	/**
	 * @description:获取昨天的日期 
	 */ 
	  public static String getYesterday(){
		  Calendar   cal   =   Calendar.getInstance();
		  cal.add(Calendar.DATE,   -1);
		  String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
		  return yesterday; 
	  }
	 
	
	  /**
	   * 得到某年某月的第一天
	   * 
	   * @param year
	   * @param month
	   * @return
	   */
	  public static String getFirstDayOfMonth(int year, int month) { 
	      Calendar cal = Calendar.getInstance(); 
	      cal.set(Calendar.YEAR, year); 
	      cal.set(Calendar.MONTH, month-1); 
	      cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE)); 
	      return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	  }
	   
	  /**
	   * 得到某年某月的最后一天
	   * 
	   * @param year
	   * @param month
	   * @return
	   */
	  public static String getLastDayOfMonth(int year, int month) { 
	      Calendar cal = Calendar.getInstance(); 
	      cal.set(Calendar.YEAR, year); 
	      cal.set(Calendar.MONTH, month-1); 
	      cal.set(Calendar.DAY_OF_MONTH, 1);
	      int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	      cal.set(Calendar.DAY_OF_MONTH, value); 
	      return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()); 
	  } 
	
	/**
	 * @description:获取当前是几点
	 * @param:   
	 * @return:  
	 */ 
	public static Integer getCurrentHourOfDay(){
		Calendar cal=Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY); 
		return hour; 
	}
	
	/**
	 * @description:获取当前是几点 
	 */ 
	public static Date findNextDay(String str,Integer dayNum){  
		String[] strArr = str.split(",");
		Integer addNum=null;
		for(String s:strArr){ 
			addNum=new Integer(s);
			if(dayNum<addNum){ 
				return addDate(new Date(), addNum-dayNum); 
			}
		}
		addNum=new Integer(strArr[0]); 
		return addDate(new Date(), 7-dayNum+addNum); 
	}
	/**
	 * @description:获取当前是几点 
	 */ 
	public static String showWeekDay(String str){  
		String[] strArr = str.split(",");
		Integer addNum=null;
		String dayStr="";
		String charStr = "";
		for(String s:strArr){ 
			 addNum=new Integer(s);  
			 if(addNum==1){
				 dayStr+=charStr+"周一";
			 }else if(addNum==2){
				 dayStr+=charStr+"周二";
			 }else if(addNum==3){
				 dayStr+=charStr+"周三";
			 }else if(addNum==4){
				 dayStr+=charStr+"周四";
			 }else if(addNum==5){
				 dayStr+=charStr+"周五";
			 }else if(addNum==6){
				 dayStr+=charStr+"周六";
			 }else if(addNum==7){
				 dayStr+=charStr+"周日";
			 }
			 charStr = "、";
		} 
		return dayStr;
	}
	/**
	 * @description:月份转化为字母
	 */ 
	public static String monthToLetter(Date date){ 
		String dateMonth = getDateMonth(date);
		if ("01".equals(dateMonth)) {
			return "A";
		}else if("02".equals(dateMonth)){
			return "B";
		}else if("03".equals(dateMonth)){
			return "C";
		}else if("04".equals(dateMonth)){
			return "D";
		}else if("05".equals(dateMonth)){
			return "M";
		}else if("06".equals(dateMonth)){
			return "N";
		}else if("07".equals(dateMonth)){
			return "L";
		}else if("08".equals(dateMonth)){
			return "R";
		}else if("09".equals(dateMonth)){
			return "S";
		}else if("10".equals(dateMonth)){
			return "X";
		}else if("11".equals(dateMonth)){
			return "Y";
		}else{
			return "Z";
		}
	}
	
	/**
	 * @description:获取链两个日期间的更新数量
	 */ 
	public static int findWeekDayNum(Date beginDate,Date endDate,String str){ 
        int n = 0;		
      	Calendar cal = Calendar.getInstance(); 
        cal.setTime(beginDate);
        while(cal.getTime().compareTo(endDate)<=0){
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if(w==0) w=7;
			if(str.indexOf(w+"")>=0){ 
				n++; 
			}
			cal.add(cal.DAY_OF_MONTH, 1);
        }        
        return n;	
	}

	//判断两个时间点是否间隔time分  false表示间隔未超过time分钟
	public static boolean dateInterval(String start, String end, int time) throws ParseException{
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(start==null || end==null ){
			return false;
		}else{
			Date startDate = format1.parse(start);
			Date endDate = format1.parse(end);
			return (endDate.getTime() - startDate.getTime()) / (1000 * 60) >= time;
		}
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(dateInterval("2016-06-23 16:04:42","2016-06-24 16:04:41",60*24*10));
	}
}

