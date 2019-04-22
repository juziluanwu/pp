package app.pp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderCodeUtils {

    public static String generateCode() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = simpleDateFormat.format(new Date());
        StringBuffer sb = new StringBuffer(timeStr);
        Random random = new Random();
        sb.append(random.nextInt(10)).append(random.nextInt(10));
        return sb.toString();
    }
}
