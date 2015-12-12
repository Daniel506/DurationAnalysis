package mk.ukim.finki.persistence.model;

import mk.ukim.finki.persistence.service.PhonemeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Example {

	public static void main(String[] args) {
		ApplicationContext context = 
    	  new ClassPathXmlApplicationContext("applicationContext.xml");

		 initSession(context);
		 PhonemeService phonemeService = context.getBean(PhonemeService.class);
		 phonemeService.insertAllophonesToDB();
//		 session.beginTransaction();
//		Phoneme phonemeDao = (Phoneme) session.get(Phoneme.class, "Ѓ");
//		 
////		 Phoneme phoneme = new Phoneme();
////		 phoneme.setId("Ѓ");
////		 phoneme.setTranscription("gj");
////		 phoneme.setType("consonant");
////		openSession.save(phoneme);
////		openSession.getTransaction().commit();
//		System.out.println(phonemeDao.getTranscription());
	}

	private static void initSession(ApplicationContext context) {
//	  Configuration configuration = new Configuration();
//		 configuration.configure();
//		 ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
//     .applySettings(configuration.getProperties()).buildServiceRegistry();
//		 
//		 ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
//		 SessionFactory sessionFactory = beanFactory.createBean(SessionFactory.class);
//		 sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//		 Session session = sessionFactory.openSession();

  }

}
