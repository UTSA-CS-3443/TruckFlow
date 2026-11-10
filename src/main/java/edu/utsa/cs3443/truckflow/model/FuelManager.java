package edu.utsa.cs3443.truckflow.model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * FuelManager manages the list of {@link Fuel} purchases: it adds, retrieves,
 * updates and deletes fuel records, saves every change through the
 * {@link dataManager}, and reports the totals and averages the user needs
 * to see how much they spend on fuel.
 * <p>
 * It follows the same pattern as {@link ExpenseManager}: it shares the list
 * that the dataManager loaded, and every change is written to
 * {@code Data/Fuel.csv}. If the file cannot be written, the change is undone
 * so the screen and the file never disagree, and the IOException is passed
 * to the controller so it can tell the user.
 *
 * @author Fuel/Fuel Controller team
 */
public class FuelManager {

    /** The list of fuel records (shared with the dataManager). */
    private ArrayList<Fuel> fuel;

    /** The object that loads and saves the data files. */
    private dataManager dataManager;

    /**
     * Creates a manager for the given list of fuel records.
     *
     * @param dataManager the object that loads and saves the data files
     * @param fuel        the list of fuel records to manage, normally
     *                    {@code dataManager.getFuel()}
     */
    public FuelManager(dataManager dataManager, ArrayList<Fuel> fuel) {
        this.dataManager = dataManager;
        this.fuel = fuel;
    }

    /**
     * Adds a fuel purchase to the list and saves the list to the data file.
     * If saving fails the purchase is removed again.
     *
     * @param newFuel the fuel purchase to add
     * @throws IOException              if the data file cannot be written
     * @throws IllegalArgumentException if the record is null
     */
    public void addFuelExpense(Fuel newFuel) throws IOException {
        if (newFuel == null) {
            throw new IllegalArgumentException("Cannot add an empty fuel record.");
        }
        fuel.add(newFuel);
        try {
            dataManager.saveFuelData();
        } catch (IOException e) {
            fuel.remove(newFuel);
            throw e;
        }
    }

    /**
     * Finds the fuel record with the given ID.
     *
     * @param fuelId the ID to search for
     * @return the matching record, or null if there is none
     */
    public Fuel findFuelId(int fuelId) {
        for (Fuel record : fuel) {
            if (record.getFuelId() == fuelId) {
                return record;
            }
        }
        return null;
    }

    /**
     * Retrieves the fuel purchases that match the given search. Every filter
     * is optional: a null date or a blank station name is ignored, and the
     * filters that are given must all match.
     *
     * @param startDate       earliest purchase date to include, or null
     * @param endDate         latest purchase date to include, or null (use the
     *                        same date for both to find one particular day)
     * @param stationContains part of the station name (ignores upper/lower
     *                        case), or null/blank to ignore the station
     * @return the matching records in their current order (empty if none)
     */
    public ArrayList<Fuel> retrieveFuelExpense(LocalDate startDate, LocalDate endDate,
                                               String stationContains) {
        ArrayList<Fuel> matches = new ArrayList<>();
        String station = stationContains == null ? "" : stationContains.trim().toLowerCase();
        for (Fuel record : fuel) {
            if (startDate != null && record.getDate().isBefore(startDate)) {
                continue;
            }
            if (endDate != null && record.getDate().isAfter(endDate)) {
                continue;
            }
            if (!station.isEmpty() && !record.getFuelStationName().toLowerCase().contains(station)) {
                continue;
            }
            matches.add(record);
        }
        return matches;
    }

    /**
     * Updates an existing fuel record with new values and saves the list.
     * The new values are validated by the Fuel setters; if any value is
     * invalid, or if the file cannot be written, the record is put back
     * exactly as it was.
     *
     * @param record          the record to update (must be in this manager's list)
     * @param date            the new date
     * @param pricePerGallon  the new price per gallon
     * @param fuelStationName the new station name
     * @param totalGallons    the new gallons
     * @param totalCost       the new total cost
     * @return true if the record was updated and saved, false if it is not in the list
     * @throws IllegalArgumentException if a value is invalid (record unchanged)
     * @throws IOException              if the data file cannot be written (record unchanged)
     */
    public boolean updateFuelRecord(Fuel record, LocalDate date, double pricePerGallon,
                                    String fuelStationName, double totalGallons,
                                    double totalCost) throws IOException {
        if (record == null || !fuel.contains(record)) {
            return false;
        }
        LocalDate oldDate = record.getDate();
        double oldPrice = record.getPricePerGallon();
        String oldStation = record.getFuelStationName();
        double oldGallons = record.getTotalGallons();
        double oldCost = record.getTotalCost();
        try {
            record.setDate(date);
            record.setPricePerGallon(pricePerGallon);
            record.setFuelStationName(fuelStationName);
            record.setTotalGallons(totalGallons);
            record.setTotalCost(totalCost);
            dataManager.saveFuelData();
            return true;
        } catch (IllegalArgumentException | IOException e) {
            record.setDate(oldDate);
            record.setPricePerGallon(oldPrice);
            record.setFuelStationName(oldStation);
            record.setTotalGallons(oldGallons);
            record.setTotalCost(oldCost);
            throw e;
        }
    }

    /**
     * Deletes the fuel record with the given ID and saves the list. If saving
     * fails the record is put back in its old position.
     *
     * @param fuelId the ID of the record to delete
     * @return true if the record was found and deleted, false if there is no such ID
     * @throws IOException if the data file cannot be written (record is restored)
     */
    public boolean deleteFuelRecord(int fuelId) throws IOException {
        for (int i = 0; i < fuel.size(); i++) {
            if (fuel.get(i).getFuelId() == fuelId) {
                Fuel removed = fuel.remove(i);
                try {
                    dataManager.saveFuelData();
                } catch (IOException e) {
                    fuel.add(i, removed);
                    throw e;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Adds up the total cost of every fuel purchase.
     *
     * @return the total amount spent on fuel
     */
    public double calculateTotalFuelCost() {
        return calculateTotalFuelCost(fuel);
    }

    /**
     * Adds up the total cost of the given fuel purchases (for example, the
     * results of a search).
     *
     * @param records the purchases to add up
     * @return the total amount spent on those purchases
     */
    public double calculateTotalFuelCost(List<Fuel> records) {
        double total = 0;
        for (Fuel record : records) {
            total += record.getTotalCost();
        }
        return total;
    }

    /**
     * Adds up the gallons of every fuel purchase.
     *
     * @return the total gallons bought
     */
    public double calculateTotalGallons() {
        return calculateTotalGallons(fuel);
    }

    /**
     * Adds up the gallons of the given fuel purchases.
     *
     * @param records the purchases to add up
     * @return the total gallons in those purchases
     */
    public double calculateTotalGallons(List<Fuel> records) {
        double total = 0;
        for (Fuel record : records) {
            total += record.getTotalGallons();
        }
        return total;
    }

    /**
     * Calculates the average cost of one fuel purchase (fill-up).
     *
     * @return total cost divided by the number of purchases, or 0 if there are none
     */
    public double calculateAverageFuelCost() {
        return calculateAverageFuelCost(fuel);
    }

    /**
     * Calculates the average cost of one purchase among the given purchases.
     *
     * @param records the purchases to average
     * @return total cost divided by the number of purchases, or 0 if the list is empty
     */
    public double calculateAverageFuelCost(List<Fuel> records) {
        if (records.isEmpty()) {
            return 0;
        }
        return calculateTotalFuelCost(records) / records.size();
    }

    /**
     * Calculates the average price paid per gallon across all purchases
     * (total cost divided by total gallons, so big fill-ups count more).
     *
     * @return the average price per gallon, or 0 if there are no purchases
     */
    public double calculateAveragePricePerGallon() {
        return calculateAveragePricePerGallon(fuel);
    }

    /**
     * Calculates the average price paid per gallon among the given purchases.
     *
     * @param records the purchases to average
     * @return total cost divided by total gallons, or 0 if there are no gallons
     */
    public double calculateAveragePricePerGallon(List<Fuel> records) {
        double gallons = calculateTotalGallons(records);
        if (gallons <= 0) {
            return 0;
        }
        return calculateTotalFuelCost(records) / gallons;
    }

    /**
     * Gets the list of fuel records.
     *
     * @return the fuel list
     */
    public ArrayList<Fuel> getFuel() {
        return fuel;
    }

    /**
     * Replaces the list of fuel records.
     *
     * @param fuel the new list
     */
    public void setFuel(ArrayList<Fuel> fuel) {
        this.fuel = fuel;
    }
}
