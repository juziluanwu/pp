package app.pp.utils;

import java.util.UUID;

/**
 * Created by wcy on 2017/10/25.
 */
public class UUid {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
