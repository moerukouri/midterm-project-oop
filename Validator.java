public class Validator {
    private final String[] VALID_CATEGORIES = {"Electronics", "Clothing", "Entertainment"};
    private final double MAX_PRICE = 9_007_199_254_740_991.0;

    public double getMaxPrice() {
        return MAX_PRICE;
    }

    public boolean isValidPositiveInteger(String input) {
        if(!input.matches("^[1-9][0-9]*$")){
            return false;
        }

        try {
            long value = Long.parseLong(input);
            return value <= Integer.MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidNonNegativeInteger(String input) {
        if(!input.matches("^[0-9]+$")){
            return false;
        }

        try {
            long value = Long.parseLong(input);
            return value <= Integer.MAX_VALUE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidPositiveDouble(String input) {
        if(!input.matches("^(0|[1-9][0-9]*)(\\.[0-9]{1,2})?$")){
            return false;
        }

        try {
            double value = Double.parseDouble(input);
            return value > 0 && value <= MAX_PRICE;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidId(String input) {
        return input.matches("^[a-zA-Z0-9]{6}$");
    }

    public boolean isValidName(String input){
        return input.matches("^[a-zA-Z0-9][a-zA-Z0-9 .,'&()#+\\-/]*$");
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