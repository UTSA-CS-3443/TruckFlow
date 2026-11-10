package edu.utsa.cs3443.truckflow.model;

import java.time.LocalDate;

/**
 * Fuel represents a single fuel purchase made for the user's truck while
 * running the business. It stores the date of the purchase, the price paid
 * per gallon, the name of the fuel station, the number of gallons bought and
 * the total cost of the purchase. Like {@link Expense}, every record also gets
 * an ID so it can be selected, edited, deleted and searched for.
 * <p>
 * The setters validate their input and throw an {@link IllegalArgumentException}
 * with a user-friendly message, so a Fuel object can never hold bad data.
 *
 * @author Fuel/Fuel Controller team
 */
public class Fuel {

    /** The ID shown in the dashboard table. Assigned automatically. */
    private int fuelId;

    /** The date the fuel was purchased. */
    private LocalDate date;

    /** The price paid per gallon. Always greater than 0. */
    private double pricePerGallon;

    /** The name of the fuel station. Never blank and never contains a comma. */
    private String fuelStationName;

    /** The number of gallons purchased. Always greater than 0. */
    private double totalGallons;

    /** The total cost of the purchase. Always greater than 0. */
    private double totalCost;

    /** The ID the next new Fuel record will receive. */
    private static int nextID = 1;

    /**
     * Creates a fuel purchase record and gives it the next available ID.
     * The values are validated first, so a rejected record does not use up an ID.
     *
     * @param date            the date the fuel was purchased
     * @param pricePerGallon  the price paid per gallon
     * @param fuelStationName the name of the fuel station
     * @param totalGallons    the number of gallons purchased
     * @param totalCost       the total cost of the purchase
     * @throws IllegalArgumentException if any value fails validation
     */
    public Fuel(LocalDate date, double pricePerGallon, String fuelStationName,
                double totalGallons, double totalCost) {
        setDate(date);
        setPricePerGallon(pricePerGallon);
        setFuelStationName(fuelStationName);
        setTotalGallons(totalGallons);
        setTotalCost(totalCost);
        this.fuelId = nextID++;
    }

    /**
     * Gets the record's ID.
     *
     * @return the fuel ID
     */
    public int getFuelId() {
        return fuelId;
    }

    /**
     * Sets the record's ID.
     *
     * @param fuelId the new fuel ID
     */
    public void setFuelId(int fuelId) {
        this.fuelId = fuelId;
    }

    /**
     * Gets the date the fuel was purchased.
     *
     * @return the purchase date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date the fuel was purchased.
     *
     * @param date the new date; must not be null
     * @throws IllegalArgumentException if the date is null
     */
    public void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Please enter a valid date.");
        }
        this.date = date;
    }

    /**
     * Gets the price paid per gallon.
     *
     * @return the price per gallon
     */
    public double getPricePerGallon() {
        return pricePerGallon;
    }

    /**
     * Sets the price paid per gallon.
     *
     * @param pricePerGallon the new price; must be greater than 0
     * @throws IllegalArgumentException if the price is not a positive number
     */
    public void setPricePerGallon(double pricePerGallon) {
        if (Double.isNaN(pricePerGallon) || Double.isInfinite(pricePerGallon) || pricePerGallon <= 0) {
            throw new IllegalArgumentException("Price per gallon must be greater than 0.");
        }
        this.pricePerGallon = pricePerGallon;
    }

    /**
     * Gets the name of the fuel station.
     *
     * @return the fuel station name
     */
    public String getFuelStationName() {
        return fuelStationName;
    }

    /**
     * Sets the name of the fuel station. Surrounding spaces are removed.
     *
     * @param fuelStationName the new name; must not be blank or contain a comma
     *                        (commas would break the CSV data file)
     * @throws IllegalArgumentException if the name is blank or contains a comma
     */
    public void setFuelStationName(String fuelStationName) {
        if (fuelStationName == null || fuelStationName.isBlank()) {
            throw new IllegalArgumentException("Please enter the fuel station name.");
        }
        if (fuelStationName.contains(",")) {
            throw new IllegalArgumentException("The fuel station name cannot contain a comma.");
        }
        this.fuelStationName = fuelStationName.trim();
    }

    /**
     * Gets the number of gallons purchased.
     *
     * @return the total gallons
     */
    public double getTotalGallons() {
        return totalGallons;
    }

    /**
     * Sets the number of gallons purchased.
     *
     * @param totalGallons the new gallons; must be greater than 0
     * @throws IllegalArgumentException if the gallons are not a positive number
     */
    public void setTotalGallons(double totalGallons) {
        if (Double.isNaN(totalGallons) || Double.isInfinite(totalGallons) || totalGallons <= 0) {
            throw new IllegalArgumentException("Total gallons must be greater than 0.");
        }
        this.totalGallons = totalGallons;
    }

    /**
     * Gets the total cost of the purchase.
     *
     * @return the total cost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the total cost of the purchase.
     *
     * @param totalCost the new total cost; must be greater than 0
     * @throws IllegalArgumentException if the cost is not a positive number
     */
    public void setTotalCost(double totalCost) {
        if (Double.isNaN(totalCost) || Double.isInfinite(totalCost) || totalCost <= 0) {
            throw new IllegalArgumentException("Total cost must be greater than 0.");
        }
        this.totalCost = totalCost;
    }

    /**
     * Gets the ID the next new Fuel record will receive.
     *
     * @return the next ID
     */
    public static int getNextID() {
        return nextID;
    }

    /**
     * Sets the ID the next new Fuel record will receive. The data manager
     * resets this to 1 each time the file is loaded, so IDs always match the
     * record's position in the file.
     *
     * @param nextID the next ID
     */
    public static void setNextID(int nextID) {
        Fuel.nextID = nextID;
    }

    /**
     * Returns the record as one CSV line, in the same column order as the
     * header of {@code Data/Fuel.csv}: date, price per gallon, station,
     * gallons, total cost. This is what the data manager writes to the file.
     *
     * @return the record as a CSV line
     */
    @Override
    public String toString() {
        return getDate() + "," + getPricePerGallon() + "," + getFuelStationName() + ","
                + getTotalGallons() + "," + getTotalCost();
    }
}
