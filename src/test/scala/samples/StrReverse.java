package samples;

import org.glassfish.jersey.message.internal.StringBuilderUtils;
import org.junit.Test;

/**
 * @created by imp ON 2019/3/19
 */
public class StrReverse{

    @Test
    public void StrResverse() {

        String a = "是爱你abcndfg";

        char b[] =a.toCharArray();
        System.out.println(b.length);
        String d="";
        StringBuilder c=new StringBuilder();
       for(int i=b.length-1;i>=0;i--){
         c.append(b[i]);
       }
        System.out.println(c.toString());

       for (int i=b.length-1;i>=0;i--){
          d=d+a.charAt(i);
       }
        System.out.println(d);
       //"异或"
        System.out.println(+1^9);

        System.out.println(19);
        System.out.println( Double.valueOf(Double.valueOf("1550537281.032".trim()) * 1000).longValue());

    }

}
