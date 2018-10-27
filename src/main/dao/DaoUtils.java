package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DaoUtils {
	public static EntityManagerFactory factory = Persistence.createEntityManagerFactory("ScoreOverFlowPU");
}