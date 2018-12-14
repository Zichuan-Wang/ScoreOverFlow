package batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

import server.constraint.SearchRoomConstraint;
import utils.FacilityActionTestUtils;

public class CSVReaderTest {
	
	private static final String CORRECT = "\n\n12/22/2018,15:00,22:10,250,Lerner,projector";
	private static final String INCORRECT = "\n\n12/222018,15:00,22:10,250,,";

	@Test
	public void readCorrectCSV() throws CSVParseException, IOException {
		BufferedReader reader = new BufferedReader(new StringReader(CORRECT));
		CSVReader csvReader = new CSVReader(FacilityActionTestUtils.getFacilityAction());
		List<SearchRoomConstraint> constraints = csvReader.readCSV(reader);
		assertEquals(1, constraints.size());
	}
	
	@Test
	public void readIncorrectCSV() {
		BufferedReader reader = new BufferedReader(new StringReader(INCORRECT));
		CSVReader csvReader = new CSVReader(FacilityActionTestUtils.getFacilityAction());
		assertThrows(CSVParseException.class, () -> csvReader.readCSV(reader));
	}
	
}
