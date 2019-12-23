package com.eresh.spring.util;

import com.eresh.spring.mongo.Address;
import com.eresh.spring.mongo.PersonalInformation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.util.List;

/**
 * Created By Gorantla, Eresh on 23/Dec/2019
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MongoQueryUtil {

	public static <T> void appendMongoDocument(Document document, String columnName, Object value, Class<T> clazz) {
		T type  = (T) clazz.getTypeName();
		String typeData = type.toString();
		if ("java.lang.String".equals(typeData)) {
			String columnData = (String) value;
			if (StringUtils.isNotBlank(columnData)) {
				document.append(columnName, columnData);
			}
		}
		if ("java.lang.Integer".equals(typeData)) {
			Integer columnData = (Integer) value;
			if (columnData != null) {
				document.append(columnName, columnData);
			}
		}
		if ("java.lang.Float".equals(typeData)) {
			Float columnData = (Float) value;
			if (columnData != null) {
				document.append(columnName, columnData);
			}
		}
		if ("java.lang.Long".equals(typeData)) {
			Long columnData = (Long) value;
			if (columnData != null) {
				document.append(columnName, columnData);
			}
		}
		if ("java.lang.Boolean".equals(typeData)) {
			Boolean columnData = (Boolean) value;
			if (columnData != null) {
				document.append(columnName, columnData);
			}
		}
		if ("java.lang.Double".equals(typeData)) {
			Double columnData = (Double) value;
			if (columnData != null) {
				document.append(columnName, columnData);
			}
		}
		if ("com.eresh.spring.mongo.PersonalInformation".equals(typeData)) {
			PersonalInformation columnData = (PersonalInformation) value;
			if (columnData != null) {
				document.append(columnName, columnData);
			}
		}

		if ("com.eresh.spring.mongo.Address[]".equals(typeData)) {
			Address[] columnData = (Address[]) value;
			if (columnData != null) {
				document.append(columnName, columnData);
			}
		}
	}

	public static <T> void appendMongoDocument(Document document, String columnName, List<Address> value) {
		document.append(columnName, value);
	}
}
