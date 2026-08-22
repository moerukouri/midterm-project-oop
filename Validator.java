public class Validator {
    private final String[] VALID_CATEGORIES = {"Electronics", "Clothing", "Entertainment"};

    public boolean isValidPositiveInteger(String input) {
        try {
            int value = Integer.parseInt(input);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidNonNegativeInteger(String input) {
        try {
            int value = Integer.parseInt(input);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidPositiveDouble(String input) {
        if(!input.matches("^[0-9]+(\\.[0-9]{1,2})?$")){
            return false;
        }
        return Double.parseDouble(input) > 0;
    }

    public boolean isValidId(String input) {
        return input.matches("^[A-Z0-9]{6}$");
    }

    public boolean isValidName(String input){
        return input.matches("^[a-zA-Z0-9 ]+$");
    }

    public String normalizeCategory(String category) {
        for (String validCategory : VALID_CATEGORIES) {
            if (validCategory.equalsIgnoreCase(category)) {
                return validCategory;
            }
        }
        return null;
    }

    public boolean isValidCategory(String category) {
        return normalizeCategory(category) != null;
    }
}