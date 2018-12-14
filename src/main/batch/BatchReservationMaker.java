package batch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import entity.EntityUtils;
import entity.Reservation;
import entity.Room;
import entity.User;
import server.action.ReservationAction;
import server.action.RoomAction;
import server.constraint.SearchRoomConstraint;

public class BatchReservationMaker {

	private RoomAction roomAction;
	private ReservationAction reservationAction;
	private User user;

	public BatchReservationMaker(RoomAction roomAction, ReservationAction reservationAction, User user) {
		this.roomAction = roomAction;
		this.reservationAction = reservationAction;
		this.user = user;
	}

	public List<Integer> makeReservationsGreedy(List<SearchRoomConstraint> constraints) {
		List<Integer> unsuccessful = new ArrayList<>();
		for (int i = 0; i < constraints.size(); i++) {
			SearchRoomConstraint constraint = constraints.get(i);
			List<Room> rooms = roomAction.searchRooms(constraint);
			if (rooms.size() > 0) {
				Room room = Collections.min(rooms, new Comparator<Room>() {
					@Override
					public int compare(Room r1, Room r2) {
						return r1.getCapacity() - r2.getCapacity();
					}

				});
				Reservation reservation = EntityUtils.roomToReservation(room, constraint.getEventDate(),
						constraint.getStartTime(), constraint.getEndTime(), user.getId());

				boolean success = reservationAction.reserveRoom(reservation);
				if (!success) {
					unsuccessful.add(i);
				}
			} else {
				unsuccessful.add(i);
			}
		}
		return unsuccessful;
	}

}
