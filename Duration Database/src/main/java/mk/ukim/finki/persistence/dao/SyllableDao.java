package mk.ukim.finki.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mk.ukim.finki.persistence.model.Syllable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SyllableDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void save(Syllable syllable) {
		entityManager.persist(syllable);
  }
	
}
