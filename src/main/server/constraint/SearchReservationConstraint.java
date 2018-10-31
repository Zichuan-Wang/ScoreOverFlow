package server.constraint;

// Java object for specify search constraint for Reservation
public class SearchReservationConstraint {
	int userId;
	
	public SearchReservationConstraint() {
		this.userId = 0;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public SearchReservationConstraint setUserId(int userId) {
		this.userId = userId;
		return this;
	}
}
