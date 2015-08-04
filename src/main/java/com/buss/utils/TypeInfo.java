package com.buss.utils;

import java.util.List;

/**
 * Created by shilin on 2015/8/4.
 */
public class TypeInfo {
    private String className;
    private List<Field> listField;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Field> getListField() {
        return listField;
    }

    public void setListField(List<Field> listField) {
        this.listField = listField;
    }

    public static class Field{
        private String typeName;
        private String name;

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
