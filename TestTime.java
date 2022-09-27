package autotrade;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestTime {
	
	
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String formatted = form.format(cal.getTime());
		
		Calendar cal2 = Calendar.getInstance();
		cal.set(2022, 05, 19, 12, 12, 00);
		System.out.println(cal2.compareTo(cal));
		System.out.println(formatted);
	}
}
