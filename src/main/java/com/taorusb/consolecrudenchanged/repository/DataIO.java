package com.taorusb.consolecrudenchanged.repository;

import java.util.List;

public interface DataIO<V> {

    <T> List<T> loadData(String s, Class<T> clazz);

    <T extends V> void saveData(String s, List<T> data);
}
