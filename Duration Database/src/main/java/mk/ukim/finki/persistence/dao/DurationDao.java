package mk.ukim.finki.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mk.ukim.finki.persistence.model.Duration;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DurationDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void save(Duration duration) {
		entityManager.persist(duration);
  }

}
