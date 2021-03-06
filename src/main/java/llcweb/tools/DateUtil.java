package llcweb.tools;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	 * @return
	 */
	public static Date StringTodate(String dateStr){

		//注意：SimpleDateFormat构造函数的样式与strDate的样式必须相符
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
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

}
