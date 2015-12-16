package mk.ukim.finki.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mk.ukim.finki.persistence.model.Sentence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SentenceDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void save(Sentence sentence) {
		entityManager.persist(sentence);
  }
	
}
