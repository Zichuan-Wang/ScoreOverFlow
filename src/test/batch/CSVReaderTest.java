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
	
	private static final String CORRECT1 = "\n\n12/22/2018,15:00,22:10,250,Lerner,projector";
	private static final String CORRECT2 = "\n\n12/22/2018,15:00,22:10,250,,";
	private static final String INCORRECT = "\n\n12/222018,15:00,22:10,250,,";

	@Test
	public void readCorrectCSV() throws CSVParseException, IOException {
		BufferedReader reader1 = new BufferedReader(new StringReader(CORRECT1));
		BufferedReader reader2 = new BufferedReader(new StringReader(CORRECT2));
		CSVReader csvReader = new CSVReader(FacilityActionTestUtils.getFacilityAction());
		List<SearchRoomConstraint> constraints1 = csvReader.readCSV(reader1);
		List<SearchRoomConstraint> constraints2 = csvReader.readCSV(reader2);
		assertEquals(1, constraints1.size());
		assertEquals(1, constraints2.size());
	}
	
	@Test
	public void readIncorrectCSV() {
		BufferedReader reader = new BufferedReader(new StringReader(INCORRECT));
		CSVReader csvReader = new CSVReader(FacilityActionTestUtils.getFacilityAction());
		assertThrows(CSVParseException.class, () -> csvReader.readCSV(reader));
	}
	
}
