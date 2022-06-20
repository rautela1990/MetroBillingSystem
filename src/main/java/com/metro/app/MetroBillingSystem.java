package com.metro.app;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.metro.dto.TapResult;
import com.metro.service.CsvParserService;

/**
 * 
 * @author anilra
 *
 */
public class MetroBillingSystem {
	private static final Logger LOG = LoggerFactory.getLogger(MetroBillingSystem.class);

	/** The Constant applicationContext. */
	private static AnnotationConfigApplicationContext applicationContext;

	static {
		LOG.info("Spring context Initialization Start.");
		applicationContext = new AnnotationConfigApplicationContext("com.metro");
		LOG.info("Spring context initialization end.");
	}

	private static CsvParserService csvParserService = (CsvParserService) applicationContext
			.getBean(CsvParserService.class);

	public static void main(String[] args) {
		if (args.length > 0) {
			LOG.info("Csv parsing of Taps started..");
			Map<Integer, TapResult> fareResult = csvParserService.csvParseAndCalculateFare(args[0]);
			LOG.info("Csv parsing of Taps Completed..");
			if (MapUtils.isNotEmpty(fareResult)) {
				LOG.info("Creating trips.csv file from results..");
				csvParserService.createCsvfromResults(fareResult);
				LOG.info("File created sucessfully");
			} else {
				LOG.info("No/False data is there in request csv file.");
			}
		} else {
			LOG.info("Please pass the filename in command line.");
		}
	}
}
