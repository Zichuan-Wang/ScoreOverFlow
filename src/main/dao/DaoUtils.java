package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// Factory pattern that enforces DAOs to be singleton
public class DaoUtils {
	public static EntityManagerFactory factory = Persistence.createEntityManagerFactory("ScoreOverFlowPU");
}