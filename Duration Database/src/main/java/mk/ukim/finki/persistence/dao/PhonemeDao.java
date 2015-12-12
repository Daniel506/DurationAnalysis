package mk.ukim.finki.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mk.ukim.finki.persistence.model.Phoneme;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PhonemeDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void save(Phoneme phoneme) {
		entityManager.persist(phoneme);
  }
}
