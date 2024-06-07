package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Jogo;
import util.JPAUtil;

public class JogoDAO {
	public static void save(Jogo jogo) {
		EntityManager em = JPAUtil.criarEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(jogo);
		
		em.getTransaction().commit();
		
		em.close();
		
	}
	
	
	public static void edit(Jogo jogo) {
		EntityManager em = JPAUtil.criarEntityManager();
		
		
		em.getTransaction().begin();
		
		em.merge(jogo);
		
		em.getTransaction().commit();
		
		em.close();
	}
	
	
	public static void delete(int id) {
		EntityManager em = JPAUtil.criarEntityManager();
		
		em.getTransaction().begin();
		
		Jogo jogoDelete = em.find(Jogo.class, id);
		
		if(jogoDelete != null) {
			em.remove(jogoDelete);
		}
		
		em.getTransaction().commit();
		
		em.close();
		
	}
	
	
	public static List<Jogo> listar() {
		EntityManager em = JPAUtil.criarEntityManager();
		
		Query q = em.createQuery("FROM Jogo", Jogo.class);
		List<Jogo> lista = q.getResultList();
		em.clear();
		return lista;
	}
}
