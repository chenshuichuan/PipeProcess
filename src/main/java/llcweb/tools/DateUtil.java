package llcweb.tools;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目名称：账号安全中心(all)   
 * 类名称：DateUtil   
 * 类描述：   时间操作工具
 * 创建人：linwu   
 * 创建时间：2014-12-17 上午10:43:08       
 * @version
 */
public class DateUtil {

	/**
	 * 生成ISO-8601 规范的时间格式
	 * @param date
	 * @return
	 */
	public static String formatISO8601DateString(Date date){
		String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		return  DateFormatUtils.format(date, pattern);
	}
	
	
	/**
	 * 获取反时间戳
	 * @return
	 */
	public static Long getCurrentReverseTime(){
		long longTime = System.currentTimeMillis()*1000000 + CalculateUtil.getNext(999999);
		return Long.MAX_VALUE - longTime;
	}
	
	/**
	 * 获取原时间戳
	 * @param reverseTime
	 * @return
	 */
	public static Long recoverReverseTime(Long reverseTime){
		long longTime = Long.MAX_VALUE - reverseTime;
		return longTime/1000000;
	}
	/**
	 * 生成页面普通展示时间
	 * @param date
	 * @return
	 */
	public static String formatDateTimeString(Date date){
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return DateFormatUtils.format(date, pattern);
	}
	/**
	 * 生成页面普通展示日期
	 * @param date
	 * @return
	 */
	public static String formatDateString(Date date){
		String pattern = "yyyy-MM-dd";
		return DateFormatUtils.format(date, pattern);
	}
	/**
	 * 根据日期生成date对象，日期必须为2012-08-02这种格式
	 * @param dateStr
	 * @param pattern "yyyy/MM/dd","yyyy-MM-dd"等
	 * @return
	 */
	public static Date StringTodate(String dateStr,String pattern){

		//注意：SimpleDateFormat构造函数的样式与strDate的样式必须相符
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
		//必须捕获异常
		try {
			Date date=simpleDateFormat.parse(dateStr);
			System.out.println(date);
			return date;
		} catch(ParseException px) {
			px.printStackTrace();
			return  null;
		}
	}

	public static List<String> monthListOfYear(int year){
		List list = new ArrayList();
		for (int month = 1; month <= 12; month++) {
			String aDate=null;
			if(month<10)
				aDate = String.valueOf(year)+"-0"+month;
			else aDate = String.valueOf(year)+"-"+month;
			list.add(aDate);
		}
		return list;
	}
	//根据月份得到当月的日期列表
	//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static List<String> getDayByMonth(int yearParam,int monthParam){
		List list = new ArrayList();
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		aCalendar.set(yearParam,monthParam,1);
		int year = aCalendar.get(Calendar.YEAR);//年份
		int month = aCalendar.get(Calendar.MONTH)+1;//！！！//从0算起的1月
		int day = aCalendar.getActualMaximum(Calendar.DATE);
		for (int i = 1; i <= day; i++) {
			String aDate=null;
			if(month<10&&i<10){
				aDate = String.valueOf(year)+"-0"+month+"-0"+i;
			}
			if(month<10&&i>=10){
				aDate = String.valueOf(year)+"-0"+month+"-"+i;
			}
			if(month>=10&&i<10){
				aDate = String.valueOf(year)+"-"+month+"-0"+i;
			}
			if(month>=10&&i>=10){
				aDate = String.valueOf(year)+"-"+month+"-"+i;
			}
			list.add(aDate);
		}
		return list;
	}
}
