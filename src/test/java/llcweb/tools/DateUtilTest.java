package llcweb.tools;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/9/4
 * Time: 15:31
 */
public class DateUtilTest {
    @Test
    public void formatISO8601DateString() throws Exception {
    }

    @Test
    public void getCurrentReverseTime() throws Exception {
    }

    @Test
    public void recoverReverseTime() throws Exception {
    }

    @Test
    public void formatDateTimeString() throws Exception {
    }

    @Test
    public void formatDateString() throws Exception {
    }

    @Test
    public void stringTodate() throws Exception {
    }

    @Test
    public void monthListOfYear() throws Exception {
        List<String> dayList = DateUtil.monthListOfYear(2018);
        for(String date: dayList)System.out.println(date);
    }

    @Test
    public void getDayByMonth() throws Exception {

        for(int i=0;i<12;i++){
            System.out.println("month = "+(i+1));
            List<String> dayList = DateUtil.getDayByMonth(2018,i);
            for(String date: dayList)System.out.println(date+",");
        }

    }

}