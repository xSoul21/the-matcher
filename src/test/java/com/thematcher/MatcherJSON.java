package com.thematcher;

import java.util.*;
public class MatcherJSON {

    static class LeftRightElements {
        List<IndexElement> leftElements;
        List<IndexElement> rightElements;

        LeftRightElements(){
            this.leftElements = new LinkedList<>();
            this.rightElements = new LinkedList<>();
        }
    }
}
