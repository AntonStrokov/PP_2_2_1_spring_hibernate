package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void add(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public List<User> listUsers() {
		Query<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
		return query.getResultList();
	}

	@Override
	public User getUserByCarModelAndSeries(String model, int series) {
		String hql = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";
		Query<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
		query.setParameter("model", model);
		query.setParameter("series", series);

		List<User> users = query.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public List<User> getAllUsersByCarModelAndSeries(String model, int series) {
		String hql = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";
		Query<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
		query.setParameter("model", model);
		query.setParameter("series", series);

		return query.getResultList();
	}
}