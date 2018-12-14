package ui;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;

import entity.Facility;
import entity.Room;
import server.action.FacilityAction;

public class EditPanelTest {
	@BeforeEach
	protected void onSetUp(){
		
	}

	@Test
	protected void setRoomEmptyName() {
		Room room = mock(Room.class);
		when(room.getName()).thenReturn(null);
		EditPanel pane = new EditPanel(null, null, null, null, null);
		pane.setRoom(room);
		Assert.assertEquals(pane.titleLabel.getText(), "Create a Room");
	}
	
	@Test
	protected void setRoomNonEmptyName() {
		Room room = mock(Room.class);
		when(room.getName()).thenReturn("some string");
		EditPanel pane = new EditPanel(null, null, null, null, null);
		pane.setRoom(room);
		Assert.assertEquals(pane.titleLabel.getText(), "Edit Room some string");
	}
	
	@Test
	protected void nonNullRoomPareparePanel() {
		Room room = mock(Room.class);
		when(room.getName()).thenReturn("some string");
		when(room.getFacilities()).thenReturn(new HashSet<Facility>());
		FacilityAction fa = mock(FacilityAction.class);
		when(fa.findAllFacilities()).thenReturn(new ArrayList<Facility>());
		EditPanel pane = new EditPanel(null, null, fa, null, null);
		pane.setRoom(room);
		pane.pareparePanel();
	}
	
	@Test
	protected void nullRoomPareparePanel() {
		EditPanel pane = new EditPanel(null, null, null, null, null);
		Assertions.assertThrows(NullPointerException.class, () -> pane.pareparePanel());
	}
}
