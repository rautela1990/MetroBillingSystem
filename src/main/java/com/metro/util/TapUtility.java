package com.metro.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.metro.config.TapConstants;
import com.metro.dto.TapRequest;
import com.metro.dto.TapResult;

/**
 * 
 * @author anilra
 *
 */
@Component
public class TapUtility {

	@Autowired
	private FareCalculateUtility fareCalculateUtility;

	/**
	 * Create the object of TapRequest which is having all the information of
	 * passenger TapIn/TapOff.
	 * 
	 * @param id
	 * @param dateTimeUTC
	 * @param tapType
	 * @param stopId
	 * @param companyId
	 * @param busId
	 * @param pan
	 * @return
	 */
	public TapRequest convertLineToTapRequest(String id, String dateTimeUTC, String tapType, String stopId,
			String companyId, String busId, String pan) {
		TapRequest tapRequest = new TapRequest(Integer.parseInt(id), convertStringToTimestamp(dateTimeUTC),
				tapType.toUpperCase().trim(), stopId.toUpperCase().trim(), companyId, busId, pan);
		return tapRequest;
	}

	/**
	 * 
	 * @param timestamp
	 * @return Timestamp object from the time which we got from csv file of
	 *         passenger.
	 */
	public Timestamp convertStringToTimestamp(String timestamp) {
		DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.from(formatDateTime.parse(timestamp.trim()));
		return Timestamp.valueOf(localDateTime);
	}

	/**
	 * Create the object of TapResult for incomplete trip of passenger.
	 * 
	 * @param fromStop where the passenger started the trip.
	 * @return Object of Tap Result which contain the data of the user trip.
	 */
	public TapResult getInCompletedTripTapResult(TapRequest fromStop) {
		return new TapResult(fromStop.getId(), fromStop.getDateTimeUTC(), fromStop.getDateTimeUTC(),
				TimeUnit.MILLISECONDS
						.toSeconds(fromStop.getDateTimeUTC().getTime() - fromStop.getDateTimeUTC().getTime()),
				fromStop.getStopId().trim(), "N.A", fareCalculateUtility.getHighestFareForIncompetedTaps(),
				fromStop.getCompanyId(), fromStop.getBusId(), fromStop.getPan(), TapConstants.INCOMPLETE_TRIP);
	}

	/**
	 * Create the object of TapResult according to the stops information of the
	 * passenger
	 * 
	 * @param fromStop where the passenger started the trip.
	 * @param toStop   where the passenger ends the trip.
	 * @return Object of Tap Result which contain the data of the user trip.
	 */
	public TapResult getCompletedTripTapResult(TapRequest fromStop, TapRequest toStop) {
		String chargeAmount = fareCalculateUtility.getChargeAmountByTrip(fromStop.getStopId(), toStop.getStopId());
		if (null == chargeAmount) {
			return new TapResult(fromStop.getId(), fromStop.getDateTimeUTC(), toStop.getDateTimeUTC(),
					TimeUnit.MILLISECONDS
							.toSeconds(toStop.getDateTimeUTC().getTime() - fromStop.getDateTimeUTC().getTime()),
					fromStop.getStopId().trim(), toStop.getStopId().trim(), fareCalculateUtility.getFareOfCancelledTrip(), fromStop.getCompanyId(),
					fromStop.getBusId(), fromStop.getPan(), TapConstants.CANCELLED_TRIP);
		} else {
			return new TapResult(fromStop.getId(), fromStop.getDateTimeUTC(), toStop.getDateTimeUTC(),
					TimeUnit.MILLISECONDS
							.toSeconds(toStop.getDateTimeUTC().getTime() - fromStop.getDateTimeUTC().getTime()),
					fromStop.getStopId().trim(), toStop.getStopId().trim(), chargeAmount, fromStop.getCompanyId(),
					fromStop.getBusId(), fromStop.getPan(), TapConstants.COMPLETED_TRIP);
		}
	}

	/**
	 * Generate string representation of the exception.
	 * 
	 * @param exception Exception object from which stack trace is to be taken
	 * @return String representation of the stack trace
	 */
	public String getStackTraceAsString(Exception exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		return sw.toString();
	}

	public String[] getCsvLineFromTapResult(TapResult tapResult) {
		return new String[] { tapResult.getJourneyStarted().toString(), tapResult.getJourneyFinished().toString(),
				String.valueOf(tapResult.getDurationInSeconds()), tapResult.getFromStopId().toString(),
				tapResult.getToStopId(), tapResult.getChargeAmount(), tapResult.getCompanyId(), tapResult.getBusId(),
				tapResult.getPan(), tapResult.getStatus() };

	}

	public String getResultHeader() {
		return "Started" + "," + "Finished" + "," + " DurationSecs" + "," + " FromStopId" + "," + " ToStopId" + ","
				+ " ChargeAmount" + "," + " CompanyId" + "," + " BusID" + "," + " PAN" + "," + "Status";
	}
}
