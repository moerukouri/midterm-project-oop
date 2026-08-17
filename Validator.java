public class Validator {
    private final String[] VALID_CATEGORIES = {"Clothing", "Electronics", "Entertainment"};
    
    public boolean isValidCategory(String category) {
        return normalizeCategory(category) != null;
    }

    public String normalizeCategory(String category) {
        if (category == null) {
            return null;
        }
        for (String validCategory : VALID_CATEGORIES) {
            if (validCategory.equalsIgnoreCase(category)) {
                return validCategory;
            }
        }
        return null;
    }

    public void validateNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }

    public void validatePositiveInt(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be a positive integer.");
        }
    }

    public void validatePositiveDouble(double value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be a positive number.");
        }
    }
    
    public int parseInt(String value, String fieldName) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid integer.");
        }
    }

    public double parseDouble(String value, String fieldName) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid number.");
        }
    }

    public int parseMenuChoice(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
