package com.xlm.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sujithna
 *
 */
public class AnnotationUtil {
  public static Map<String, Method> getFields(Class claz) throws IntrospectionException {

    Map<String, Method> fieldAnno = new HashMap<String, Method>();

    for (Field f : claz.getDeclaredFields()) {
      XLColumn column = f.getAnnotation(XLColumn.class);
      if (column != null) {
        Method setter = new PropertyDescriptor(f.getName(), claz).getWriteMethod();
        if (setter == null) {
          continue;
        }
        fieldAnno.put(column.name(), setter);
      }
    }
    return fieldAnno;
  }
}
