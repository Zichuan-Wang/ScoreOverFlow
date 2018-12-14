package batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import entity.Facility;
import server.action.FacilityAction;
import server.constraint.SearchRoomConstraint;

public class CSVReader {
	
	private FacilityAction facilityAction;
	
	public CSVReader(FacilityAction facilityAction) {
		this.facilityAction = facilityAction;
	}

	public List<SearchRoomConstraint> readCSV(BufferedReader br) throws CSVParseException, IOException {
		String line = "";
		String cvsSplitBy = ",";
		int lineNum = 0;

		List<SearchRoomConstraint> srcs = new ArrayList<>();
		List<Facility> facilities = facilityAction.findAllFacilities();
		Map<String, Facility> facilityNames = new HashMap<>();
		for (Facility facility : facilities) {
			facilityNames.put(facility.getName(), facility);
		}
		while ((line = br.readLine()) != null) {
			lineNum++;
			if (lineNum < 3) {
				continue;
			}
			String[] groups = line.split(cvsSplitBy, -1);
			try {
				SearchRoomConstraint src = new SearchRoomConstraint();
				src.setEventDate(new SimpleDateFormat("MM/dd/yyyy").parse(groups[0].trim()));
				src.setStartTime(new SimpleDateFormat("hh:mm").parse(groups[1].trim()));
				src.setEndTime(new SimpleDateFormat("hh:mm").parse(groups[2].trim()));
				if (groups[3].trim().length() != 0) {
					src.setCapacity(Integer.parseInt(groups[3].trim()));
				}
				if (groups[4].trim().length() != 0) {
					src.setRoomName(groups[4].trim());
				}
				if (groups[5].trim().length() != 0) {
					String[] requestedfacilityNames = groups[5].trim().split(";");
					Set<Facility> requestedfacilities = new HashSet<>();
					for (String name : requestedfacilityNames) {
						if (facilityNames.containsKey(name.trim())) {
							requestedfacilities.add(facilityNames.get(name.trim()));
						}
					}
					src.setFacilities(requestedfacilities);
				}
				srcs.add(src);
			} catch (Exception exception) {
				throw new CSVParseException(lineNum);
			}
		}
		return srcs;
	}
}
