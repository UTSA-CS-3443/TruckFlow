package edu.utsa.cs3443.truckflow;

/**
 * FuelInput holds the small helper methods the Add, Update and Search Fuel
 * screens share for turning what the user typed into numbers. Keeping them in
 * one place means every screen accepts and rejects the same input.
 *
 * @author Fuel/Fuel Controller team
 */
final class FuelInput {

    /** Not meant to be created; this class only has static helper methods. */
    private FuelInput() {
    }

    /**
     * Turns typed text into a number. A leading dollar sign and spaces are
     * ignored, so "$3.45" and " 3.45 " both work.
     *
     * @param text      what the user typed
     * @param fieldName the field's name, used in the error message
     * @return the number
     * @throws IllegalArgumentException with a user-friendly message if the text
     *                                  is empty or is not a number
     */
    static double parseNumber(String text, String fieldName) {
        String cleaned = text == null ? "" : text.trim().replace("$", "").trim();
        if (cleaned.isEmpty()) {
            throw new IllegalArgumentException("Please enter the " + fieldName + ".");
        }
        double value;
        try {
            value = Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The " + fieldName + " must be a number (for example 3.45).");
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) { // "NaN" and "Infinity" parse, but are not usable amounts
            throw new IllegalArgumentException("The " + fieldName + " must be a number (for example 3.45).");
        }
        return value;
    }

    /**
     * Works out the total cost for a purchase. If the user left the total cost
     * empty it is calculated as price per gallon times gallons (rounded to
     * cents); otherwise the number the user typed is used (so fees or
     * discounts can be recorded).
     *
     * @param totalText the text in the total cost field (may be empty)
     * @param price     the price per gallon
     * @param gallons   the number of gallons
     * @return the total cost
     * @throws IllegalArgumentException if the total cost field contains something that is not a number
     */
    static double totalCostOrCalculated(String totalText, double price, double gallons) {
        if (totalText == null || totalText.trim().isEmpty()) {
            return Math.round(price * gallons * 100.0) / 100.0;
        }
        return parseNumber(totalText, "total cost");
    }
}
