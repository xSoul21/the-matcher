package com.thematcher;

import java.util.Comparator;

public class FlattenComparator  implements Comparator<Object> {
    public int compare(Object obj1, Object obj2) {
        if (obj1.toString() == obj2.toString()) {
            return 0;
        }
        if (obj1 == null) {
            return -1;
        }
        if (obj2 == null) {
            return 1;
        }
        return obj1.toString().compareTo(obj2.toString());
    }
}