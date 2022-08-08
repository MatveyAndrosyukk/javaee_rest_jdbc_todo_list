package api.util;

import java.time.LocalDate;
import java.util.Date;

public class TimeUtils {
    public static LocalDate convertSQLDateToLocalDate(Date sqlDate){
        return new java.sql.Date(sqlDate.getTime()).toLocalDate();
    }
}
