public class Validator {
    private final String[] VALID_CATEGORIES = {"Electronics", "Clothing", "Entertainment"};
    private final double MAX_PRICE = 9_007_199_254_740_991.0;

    public boolean isValidPositiveInteger(String input) {
        if(!input.matches("^[1-9][0-9]*$")){
            return false;
        }

        int value = Integer.parseInt(input);

        if(value > Integer.MAX_VALUE) {
            System.out.println("Input cannot exceed max integer limit of " + Integer.MAX_VALUE + ". Please enter a smaller value.");
            return false;
        }
        return value >= 0;
    }

    public boolean isValidNonNegativeInteger(String input) {
        if(!input.matches("^[0-9]+$")){
            return false;
        }

        int value = Integer.parseInt(input);

        if(value > Integer.MAX_VALUE) {
            System.out.println("Input cannot exceed max integer limit of " + Integer.MAX_VALUE + ". Please enter a smaller value.");
            return false;
        }
        return value >= 0;
    }

    public boolean isValidPositiveDouble(String input) {
        if(!input.matches("^(0|[1-9][0-9]*)(\\.[0-9]{1,2})?$")){
            return false;
        }

        double value = Double.parseDouble(input);

        if(value > MAX_PRICE) {
            System.out.println("Input cannot exceed max double limit of " + MAX_PRICE + ". Please enter a smaller value.");
            return false;
        }

        return value > 0;
    }

    public boolean isValidId(String input) {
        return input.matches("^[a-zA-Z0-9]{6}$");
    }

    public boolean isValidName(String input){
        return !input.trim().isEmpty();
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