package llcweb.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 20:24
 */
public class RandomSomething {



    public static void main(String[] args){
        System.out.println(RondomVacancyStr(4));
        System.out.println(RondomVacancyStr(10));

        int arr[]={1,2,3};
        String str = idsIntToString(arr);
        System.out.println(str);

        int brr[]= idsStringToInt(str);
        for (int i:brr)System.out.println(i);
        Integer integer = 4;
        int i =integer;
    }
    /**
    * 根据给定的length，随机length长度的01字符串，如"11001"、"01101"
    * */
    public static String RondomVacancyStr(int lenth){
        String str="";
        for (int i=0;i<lenth;i++){
            int number = (int)(Math.random()*10);
            if(number%2 == 1)str+="1";
            else str+="0";

        }
        return  str;
    }

    /**
     * module类的整形数组转为String，以","符号分隔，*/
    public static String idsIntToString(int arr[]){

        String str="";
        for (int i = 0; i < arr.length; i++) {
            str += arr[i];
            if(i<arr.length-1)str += ",";
        }
        return str;
    }
    /**
     * module类的String转为 int[]，以","符号分隔，*/
    public static int[] idsStringToInt(String str){

        String strArr[] = str.split(",");
        List<Integer> list=new ArrayList<Integer>();
        for (String itor: strArr) {
            list.add(Integer.valueOf(itor));
        }
        int intArr[] = new int[list.size()];
        for (int i=0;i<list.size();i++) {
            intArr[i] = list.get(i);
        }
        return intArr;
    }
}
