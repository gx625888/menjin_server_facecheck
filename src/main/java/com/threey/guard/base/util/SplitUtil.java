package com.threey.guard.base.util;

import java.util.ArrayList;
import java.util.List;

public class SplitUtil {

    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {

        List<List<T>> listArray = new ArrayList<List<T>>();

        List<T> subList = null;

        for (int i = 0; i < list.size(); i++) {

            if (i % pageSize == 0) {//每次到达页大小的边界就重新申请一个subList

                subList = new ArrayList<T>();

                listArray.add(subList);

            }

            subList.add(list.get(i));

        }

        return listArray;

    }
}
