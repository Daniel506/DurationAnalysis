package mk.ukim.finki.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mk.ukim.finki.persistence.model.Word;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class WordDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void save(Word word) {
		entityManager.persist(word);
  }
	
	@Transactional
	public Word find(String id) {
		return entityManager.find(Word.class, id);
  }
}
