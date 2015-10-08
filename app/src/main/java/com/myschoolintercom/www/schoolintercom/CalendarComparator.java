package com.myschoolintercom.www.schoolintercom;

import com.myschoolintercom.www.schoolintercom.types.CalendarData;

import java.util.Comparator;

/**
 * Created by RandyMBP on 7/28/15.
 */
public class CalendarComparator implements  Comparator<CalendarData>{

        @Override
        public int compare(CalendarData left, CalendarData right) {
            return right.getCalStartDate().compareTo(left.getCalStartDate());
        }


}
