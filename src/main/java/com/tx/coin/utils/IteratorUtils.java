package com.tx.coin.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.utils
 * @Description 迭代器转集合
 * @date 2018-1-13 23:47
 */
public class IteratorUtils {
    public static <T> List<T> copyIterator(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext()) {
            copy.add(iter.next());
        }
        return copy;
    }
}
