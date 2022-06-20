package com.metro.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metro.config.TapConstants;
import com.metro.dto.TapRequest;
import com.metro.dto.TapResult;
import com.metro.util.TapUtility;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

/**
 * 
 * @author anilra
 *
 */
@Component
public class CsvParserService {
	private static final Logger LOG = LoggerFactory.getLogger(CsvParserService.class);

	@Autowired
	private TapUtility tapUtility;

	public Map<Integer, TapResult> csvParseAndCalculateFare(String filename) {
		LOG.info("Csv file parsing started with file : " + filename);
		Map<String, TapRequest> tapOnAndOffData = new HashMap<>();
		Map<Integer, TapResult> fareData = new HashMap<>();
		try (CSVReader reader = new CSVReader(
				new InputStreamReader(CsvParserService.class.getClassLoader().getResourceAsStream(filename)))) {
			String[] nextLine;
			reader.readNext(); // For excluding first line

			while ((nextLine = reader.readNext()) != null) { // Read one line at a time
				// Creating TapRequest dto object from line
				TapRequest tapRequest = tapUtility.convertLineToTapRequest(nextLine[0], nextLine[1], nextLine[2],
						nextLine[3], nextLine[4], nextLine[5], nextLine[6]);
				if (TapConstants.TAP_ON.equals(tapRequest.getTapType())) {
					tapOnAndOffData.put(tapRequest.getPan(), tapRequest);
				} else {
					if (null == tapOnAndOffData.get(tapRequest.getPan())) {
						LOG.info("Line Cannot be treated as direct TAP off found : " + Arrays.toString(nextLine));
					} else {
						fareData.put(tapRequest.getId(), tapUtility
								.getCompletedTripTapResult(tapOnAndOffData.get(tapRequest.getPan()), tapRequest));
						LOG.debug("Tap off request of PAN : " + tapRequest.getPan());
						tapOnAndOffData.remove(tapRequest.getPan()); // After got tap off event for any PAN remove that from results Map.
					}
				}
				// LOG.info("Next Line : " + Arrays.toString(nextLine));
				nextLine = null;
			}
			for (Map.Entry<String, TapRequest> entry : tapOnAndOffData.entrySet()) { // Calculating the fare of Incomplete trips.
				fareData.put(entry.getValue().getId(), tapUtility.getInCompletedTripTapResult(entry.getValue()));
			}
			LOG.info("Size of the results : " + fareData.size());
			return fareData;
		} catch (IOException | CsvValidationException e) {
			LOG.error("Exception in csvParseAndCalculateFare : " + tapUtility.getStackTraceAsString(e));
			return null;
		}
	}

	public void createCsvfromResults(Map<Integer, TapResult> fareData) {
		File file = new File("trips.csv");
		try {
			FileWriter outputfile = new FileWriter(file);

			LOG.info("create CSVWriter object filewriter object as parameter");
			CSVWriter writer = new CSVWriter(outputfile);

			LOG.info("adding header to csv");
			String[] header = { "Started", "Finished", "DurationSecs", "FromStopId", "ToStopId", "ChargeAmount",
					"CompanyId", "BusID", "PAN", "Status" };
			writer.writeNext(header);

			// add trips fare data to csv
			for (Entry<Integer, TapResult> entry : fareData.entrySet()) {
				writer.writeNext(tapUtility.getCsvLineFromTapResult(entry.getValue()));
			}
			LOG.info("Closing writer connection");
			writer.close();

		} catch (IOException e) {
			LOG.error("Exception in createCsvfromResults : " + tapUtility.getStackTraceAsString(e));
		}
	}
}
