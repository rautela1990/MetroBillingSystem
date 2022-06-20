package com.metro.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author anilra
 *
 */
@Component
public class FareCalculateUtility {

	private static final Logger LOG = LoggerFactory.getLogger(FareCalculateUtility.class);

	private Map<String, Set<String>> fareMap = new HashMap<>();

	@Autowired
	private TapUtility tapUtility;

	private String maxFare = null;

	private Map<String, String> stopsAndFareMap = new HashMap<>();

	/**
	 * Loads all fares and store it into a Hashmap.
	 */
	@PostConstruct
	private void loadFaresForAllStops() {
		LOG.info("Loading properties file of fare.");
		Properties prop = null;
		try (InputStream input = FareCalculateUtility.class.getClassLoader()
				.getResourceAsStream("Tapfare.properties")) {
			prop = new Properties();
			prop.load(input);

		} catch (IOException ex) {
			LOG.error("Exception in csvParseAndCalculateFare : " + tapUtility.getStackTraceAsString(ex));
		}
		LOG.info("Properties file Loaded sucesfully.");

		prop.forEach(
				(key, value) -> fareMap.put(value.toString(), new HashSet<>(Arrays.asList(key.toString().toUpperCase().split("-")))));

		LOG.info("All Fares are as : " + fareMap);
	}

	/**
	 * Calculate the fare of the passenger according to the source and
	 * destination stops of any passenger
	 * 
	 * @param stop1 where the passenger started the trip.
	 * @param stop2 where the passenger ends the trip.
	 * @return chargeAmount of the passenger.
	 */
	public String getChargeAmountByTrip(String stop1, String stop2) {
		if (stop1.equals(stop2)) {
			return null;
		}
		if (null != stopsAndFareMap.get(stop1 + stop2))
			return stopsAndFareMap.get(stop1 + stop2);
		for (Map.Entry<String, Set<String>> entry : fareMap.entrySet()) {
			if (validateStopsFromSet(entry.getValue(), stop1, stop2)) {
				stopsAndFareMap.put(stop1 + stop2, entry.getKey());// Storing the fare value against stops for not
																	// iterating map again for same combination.
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param stopsSet set of two stops against any fare
	 * @param stop1    where the passenger started the trip.
	 * @param stop2    where the passenger ends the trip.
	 * @return true if stops both stops are there in set else false.
	 */
	private Boolean validateStopsFromSet(Set<String> stopsSet, String stop1, String stop2) {
		if (stopsSet.contains(stop1) && stopsSet.contains(stop2)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * This method will only run once a parsing as we are storing the value of max
	 * fare in a instance variable for not iterating the map of fares again.
	 * 
	 * @return max fare from of all fares in a fare csv file
	 */
	public String getHighestFareForIncompetedTaps() {
		if (null == maxFare) {
			float fare = 0;
			for (Map.Entry<String, Set<String>> entry : fareMap.entrySet()) {
				if (fare == 0)
					fare = Float.valueOf(entry.getKey().replaceAll("\\$", ""));
				if (fare != 0 && fare < Float.valueOf(entry.getKey().replaceAll("\\$", "")))
					fare = Float.valueOf(entry.getKey().replaceAll("\\$", ""));
			}
			maxFare = "$" + fare;
			LOG.info("Highest fare : " + maxFare);
		}
		return maxFare;
	}
	
	/**
	 * 
	 * @return fare for cancelled trip.
	 */
	public String getFareOfCancelledTrip() {
		return "$0.00";
	}
}
