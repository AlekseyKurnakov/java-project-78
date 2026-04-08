package hexlet.code.schemas;


public class StringSchema {

    private boolean required;
    private Integer minLength;
    private String contains;

    public StringSchema() {
        required = false;
        minLength = null;
        contains = null;
    }

    public void required() {
        required = true;
    }
    public void minLength(int length) {
        minLength = length;
    }
    public void contains(String substring) {
        contains = substring;
    }

    public boolean isValid(String text) {
        if (required) {
            if(text == null || text.isEmpty()) {
                return false;
            }
        }
        if (minLength != null && text.length() < minLength) {
                return false;
        }
        if (contains != null && !text.contains(contains)) {
                return false;
        }
        return true;
    }
}
