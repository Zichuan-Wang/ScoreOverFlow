package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAOUtils {
	public static EntityManagerFactory factory = Persistence.createEntityManagerFactory("ScoreOverFlowPU");
}
