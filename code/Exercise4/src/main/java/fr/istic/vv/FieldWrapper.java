package fr.istic.vv;

public class FieldWrapper {
    private String className;
    private String packageName;
    private String fieldName;

    FieldWrapper(String className, String packageName, String fieldName) {
        this.className = className;
        this.packageName = packageName;
        this.fieldName = fieldName;
    }

    public String getClassName() {
        return className;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
