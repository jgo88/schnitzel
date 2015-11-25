package de.iteratec.schnitzel.common.utils;

import java.util.List;

public class ListUtils {
	
	public static boolean isNullOrEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

}
