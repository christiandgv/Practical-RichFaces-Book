/**
 * 
 */
package org.richfaces.book.examples;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.richfaces.book.examples.model.BaseDescriptor;
import org.richfaces.book.examples.model.GameDescriptor;

@ApplicationScoped
@ManagedBean(eager = true)
public class GamesHibernateBean {

	private SessionFactory sessionFactory = new Configuration().configure(
			"hibernate.cfg.xml").buildSessionFactory();
	private Session hibernateSession = sessionFactory.openSession();
	@ManagedProperty(value = "#{gamesParser.gamesList}")
	private List<GameDescriptor> games = new ArrayList<GameDescriptor>();
	private static final String[] GAME_FIELDS = {"country", "city", "continent", "season", "number", "year"};
	@PostConstruct
	public void fillDatabase(){
		for (BaseDescriptor game : games) {
			try {
				hibernateSession.persist(game);
			}catch (HibernateException e){
				e.printStackTrace();
			}
		}
		hibernateSession.flush();
	}

	public String[] getGameFields(){
		return GAME_FIELDS;
	}
	
	public void setGames(List<GameDescriptor> games) {
		this.games = games;
	}

	public List<GameDescriptor> getGames() {
		return games;
	}
	
	public Session getHibernateSession() {
		return hibernateSession;
	}
}