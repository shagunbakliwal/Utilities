package com.acg.utilities;

import java.lang.reflect.Field;

public class ObjectUtilities {

	public static boolean isNull(Object object) {
		Field fields[] = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				if (field.get(object) == null) {
					return false;
				}
			} catch (IllegalArgumentException e) {
				return false;
			} catch (IllegalAccessException e) {
				return false;
			}
			return true;
		}
		return false;
	}
}