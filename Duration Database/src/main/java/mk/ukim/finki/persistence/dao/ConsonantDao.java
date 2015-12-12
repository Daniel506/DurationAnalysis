package mk.ukim.finki.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import mk.ukim.finki.persistence.model.Consonant;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ConsonantDao {

		@PersistenceContext(type=PersistenceContextType.EXTENDED)
		private EntityManager entityManager;

		@Transactional
		public void save(Consonant consonant) {
			entityManager.persist(consonant);
    }
		
}
