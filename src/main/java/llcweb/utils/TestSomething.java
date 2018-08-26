package llcweb.utils;

import com.sun.tools.corba.se.idl.StringGen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/2/1
 * Time: 20:24
 */
public class TestSomething {



    public static void main(String[] args){
        String order ="弯管,校管,表处,焊接";
        int first = order.indexOf(",");
        int last =order.lastIndexOf(",");
        System.out.println(order.substring(first,last));
    }

}
