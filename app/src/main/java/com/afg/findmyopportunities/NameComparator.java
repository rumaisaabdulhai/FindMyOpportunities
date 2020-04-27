package com.afg.findmyopportunities;

import java.util.Comparator;

public class NameComparator implements Comparator<Opportunity>{

    @Override
    public int compare(Opportunity o1, Opportunity o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }

}
