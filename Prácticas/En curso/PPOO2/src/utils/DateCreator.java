package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCreator {
	
	public DateCreator() {}
	
	public Date createDate(String strDate) {

		SimpleDateFormat dateformat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		strDate = "06-12-2000 11:35:42";
		Date newDate = new Date();
		
		try {
			newDate = dateformat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}
}
