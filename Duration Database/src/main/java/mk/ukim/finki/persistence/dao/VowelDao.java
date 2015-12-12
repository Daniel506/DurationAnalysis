package mk.ukim.finki.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mk.ukim.finki.persistence.model.Vowel;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VowelDao {

		@PersistenceContext
		private EntityManager entityManager;

		@Transactional
		public void save(Vowel vowel) {
			entityManager.persist(vowel);
    }
		
}
