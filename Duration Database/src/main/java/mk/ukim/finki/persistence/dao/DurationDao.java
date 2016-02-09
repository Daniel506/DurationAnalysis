package mk.ukim.finki.persistence.dao;

import java.util.List;

import javax.naming.directory.InvalidAttributeIdentifierException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mk.ukim.finki.persistence.model.Duration;

import org.springframework.core.annotation.OrderUtils;
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

	@Transactional
	public void update(Duration duration) {
		entityManager.merge(duration);
  }
	
//	@Transactional
//	public Duration findPhoneme(String sentenceid, int phonemePosition) throws InvalidAttributeIdentifierException {
//		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//		CriteriaQuery<Duration> criteria = builder.createQuery(Duration.class);
//		Root<Duration> durationRoot = criteria.from(Duration.class);
//		
//		criteria.select(durationRoot);
//		Predicate sentenceEqual = builder.equal(durationRoot.get("sentenceId"), sentenceid);
//		Predicate phonemePositionEqual = builder.equal(durationRoot.get("phonemePosition"), phonemePosition);
//		criteria.where(builder.and(sentenceEqual, phonemePositionEqual));
//		
//		List<Duration> durations = entityManager.createQuery(criteria).getResultList();
//		if (durations.size() > 1) {
//			throw new InvalidAttributeIdentifierException("sentenceid: " + sentenceid + " phoneme_position: " + phonemePosition);
//		}
//		System.out.println("sentenceid: " + sentenceid + " phoneme_position: " + phonemePosition);
//		return durations.get(0);
//  }
	@Transactional
	public List<Duration> findDurations(String sentenceid) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Duration> criteria = builder.createQuery(Duration.class);
		Root<Duration> durationRoot = criteria.from(Duration.class);
		
		criteria.select(durationRoot);
		Predicate sentenceEqual = builder.equal(durationRoot.get("sentenceId"), sentenceid);
		criteria.where(sentenceEqual);
		criteria.orderBy(builder.asc(durationRoot.get("phonemePosition")));
		
		List<Duration> durations = entityManager.createQuery(criteria).getResultList();
		return durations;
  }

	@Transactional
	public List<Duration> findDurationByPhoneme(String phoneme) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Duration> criteria = builder.createQuery(Duration.class);
		Root<Duration> durationRoot = criteria.from(Duration.class);
		
		criteria.select(durationRoot);
		Predicate sentenceEqual = builder.equal(durationRoot.get("phonemeId"), phoneme);
		criteria.where(sentenceEqual);
		
		List<Duration> durations = entityManager.createQuery(criteria).getResultList();
		return durations;
  }
}
