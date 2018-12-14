package server.action;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;

import dao.ReservationDao;
import dao.RoomDao;
import dao.factory.ReservationDaoFactory;
import dao.factory.RoomDaoFactory;
import entity.Reservation;
import entity.Room;
import exception.DBConnectionException;
import server.constraint.SearchReservationConstraint;
import utils.EntityTestUtils;

public class ReservationActionTest {

	@Test
	public void testReserveRoom() throws DBConnectionException {
		RoomDao roomDao = RoomDaoFactory.getInstance();
		Room room = roomDao.saveOrUpdate(EntityTestUtils.getDefaultRoom().setId(3));
		
		ReservationDao dao = ReservationDaoFactory.getInstance();
		ReservationAction action = new ReservationAction(dao, RoomDaoFactory.getInstance());

		assertThrows(NullPointerException.class, () -> action.reserveRoom(null));

		boolean suc = action.reserveRoom(EntityTestUtils.getDefaultReservation().setRoomId(3));
		assertTrue(suc);
		Reservation reservation = dao.findById(EntityTestUtils.DEFAULT_RESERVATION_ID);
		dao.remove(reservation);
		roomDao.remove(room);
		assertNotNull(reservation);
	}

	@Test
	public void testCancelReservation() throws DBConnectionException {
		ReservationDao dao = ReservationDaoFactory.getInstance();
		ReservationAction action = new ReservationAction(dao, RoomDaoFactory.getInstance());

		assertThrows(IllegalArgumentException.class, () -> action.cancelReservation(null));
		assertAll(() -> action.cancelReservation(EntityTestUtils.getDefaultReservation()));

		Reservation reservation = dao.saveOrUpdate(EntityTestUtils.getDefaultReservation());
		action.cancelReservation(reservation);
		assertNull(dao.findById(EntityTestUtils.DEFAULT_RESERVATION_ID));
	}

	@Test
	public void testSearchReservations() throws DBConnectionException {
		ReservationDao dao = ReservationDaoFactory.getInstance();
		ReservationAction action = new ReservationAction(dao, RoomDaoFactory.getInstance());
		SearchReservationConstraint constraint = new SearchReservationConstraint()
				.setUserId(EntityTestUtils.DEFAULT_USER_ID);

		// No reservation
		assertEquals(0, action.searchReservations(constraint).size());
		
		Calendar calendar = Calendar.getInstance();
		
		// End of yesterday
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Reservation reservation1 = EntityTestUtils.getDefaultReservation()
				.setEventDate(calendar.getTime()).setEndTime(calendar.getTime()).setId(1);
		
		// End of today
		calendar.add(Calendar.DATE, 1);
		Reservation reservation2 = EntityTestUtils.getDefaultReservation()
				.setEventDate(calendar.getTime()).setEndTime(calendar.getTime()).setId(2);
		
		// Start of today
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Reservation reservation3 = EntityTestUtils.getDefaultReservation()
				.setEventDate(calendar.getTime()).setEndTime(calendar.getTime()).setId(3);

		// Start of tomorrow
		calendar.add(Calendar.DATE, 1);
		Reservation reservation4 = EntityTestUtils.getDefaultReservation()
				.setEventDate(calendar.getTime()).setEndTime(calendar.getTime()).setId(4);
		
		Reservation saved1 = dao.saveOrUpdate(reservation1);
		Reservation saved2 = dao.saveOrUpdate(reservation2);
		Reservation saved3 = dao.saveOrUpdate(reservation3);
		Reservation saved4 = dao.saveOrUpdate(reservation4);
		List<Reservation> reservations = action.searchReservations(constraint);
		dao.remove(saved1);
		dao.remove(saved2);
		dao.remove(saved3);
		dao.remove(saved4);
		
		assertEquals(2, reservations.size());
	}
	
	@Test
	public void testMakeMultipleReservations() throws DBConnectionException {
		ReservationDao dao = ReservationDaoFactory.getInstance();
		RoomDao roomDao = RoomDaoFactory.getInstance();
		ReservationAction action = new ReservationAction(dao, RoomDaoFactory.getInstance());
		SearchReservationConstraint constraint = new SearchReservationConstraint()
				.setUserId(EntityTestUtils.DEFAULT_USER_ID);
		
		Room room = roomDao.saveOrUpdate(EntityTestUtils.getDefaultRoom().setId(3)); // has to be 3, don't know why

		// No reservation
		assertEquals(0, action.searchReservations(constraint).size());
		
		Calendar calendar = Calendar.getInstance();
		
		List<Reservation> reservations = new ArrayList<>();
		
		// End of yesterday
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Reservation reservation1 = EntityTestUtils.getDefaultReservation().setRoomId(3)
				.setEventDate(calendar.getTime()).setEndTime(calendar.getTime());
		
		// End of today
		calendar.add(Calendar.DATE, 1);
		Reservation reservation2 = EntityTestUtils.getDefaultReservation().setRoomId(3)
				.setEventDate(calendar.getTime()).setEndTime(calendar.getTime());
		
		// Start of today
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Reservation reservation3 = EntityTestUtils.getDefaultReservation().setRoomId(3)
				.setEventDate(calendar.getTime()).setEndTime(calendar.getTime());

		// Start of tomorrow
		calendar.add(Calendar.DATE, 1);
		Reservation reservation4 = EntityTestUtils.getDefaultReservation().setRoomId(3)
				.setEventDate(calendar.getTime()).setEndTime(calendar.getTime());
		
		// These should fail
		Reservation reservation5 = EntityTestUtils.getDefaultReservation().setRoomId(1000)
				.setEventDate(calendar.getTime()).setEndTime(calendar.getTime()); // room does not exist
		
		
		// add to the list
		reservations.add(reservation1);
		reservations.add(reservation2);
		reservations.add(reservation3);
		reservations.add(reservation4);
		reservations.add(reservation5);
		
		assertEquals(5, reservations.size());
		
		List<Reservation> failedReservations = action.reserveMultipleRooms(reservations);
		dao.remove(reservation1);
		dao.remove(reservation2);
		dao.remove(reservation3);
		dao.remove(reservation4);
		roomDao.remove(room);
		
		assertEquals(1, failedReservations.size());
	}
	
	@Test
	public void testMakeMultipleReservations_RepeatingRequest() throws DBConnectionException {
		ReservationDao dao = ReservationDaoFactory.getInstance();
		RoomDao roomDao = RoomDaoFactory.getInstance();
		ReservationAction action = new ReservationAction(dao, RoomDaoFactory.getInstance());
		SearchReservationConstraint constraint = new SearchReservationConstraint()
				.setUserId(EntityTestUtils.DEFAULT_USER_ID);
		
		Room room = roomDao.saveOrUpdate(EntityTestUtils.getDefaultRoom().setId(3)); // has to be 3, don't know why

		// No reservation
		assertEquals(0, action.searchReservations(constraint).size());
		
		Calendar calendar = Calendar.getInstance();
		
		List<Reservation> reservations = new ArrayList<>();
		
		// End of yesterday
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Reservation reservation1 = EntityTestUtils.getDefaultReservation().setRoomId(3)
				.setEventDate(calendar.getTime()).setEndTime(calendar.getTime());
		
		
		// These should fail
		Reservation reservation2 = reservation1;
		
		
		// add to the list
		reservations.add(reservation1);
		reservations.add(reservation2);
		
		assertEquals(2, reservations.size());
		
		List<Reservation> failedReservations = action.reserveMultipleRooms(reservations);
		dao.remove(reservation1);
		dao.remove(reservation2);
		roomDao.remove(room);
		
		assertEquals(1, failedReservations.size());
	}
}
