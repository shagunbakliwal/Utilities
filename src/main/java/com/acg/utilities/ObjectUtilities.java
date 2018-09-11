package com.acg.utilities;

import java.lang.reflect.Field;

public class ObjectUtilities {

	public static boolean isNull(Object object, Object... exclusions) {
		Field fields[] = object.getClass().getDeclaredFields();

		for (Field field : fields) {
			try {
				if (field.get(field) == null) {
					return true;
				}
			} catch (IllegalArgumentException e) {
				return false;
			} catch (IllegalAccessException e) {
				return false;
			}
		}
		return false;
	}
}