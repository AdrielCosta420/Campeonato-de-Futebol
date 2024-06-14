package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Campeonato;
import util.JPAUtil;

public class CampeonatoDAO {
	public static void save(Campeonato campeonato) {
		EntityManager em = JPAUtil.criarEntityManager();

		em.getTransaction().begin();

		em.persist(campeonato);

		em.getTransaction().commit();

		em.close();

	}

	public static void edit(Campeonato campeonato) {
		EntityManager em = JPAUtil.criarEntityManager();

		em.getTransaction().begin();

		em.merge(campeonato);

		em.getTransaction().commit();

		em.close();
	}

	public static void delete(int id) {
		EntityManager em = JPAUtil.criarEntityManager();

		em.getTransaction().begin();

		Campeonato campeonatoDelete = em.find(Campeonato.class, id);

		if (campeonatoDelete != null) {
			em.remove(campeonatoDelete);
		}

		em.getTransaction().commit();

		em.close();

	}

	public static List<Campeonato> listar() {
		EntityManager em = JPAUtil.criarEntityManager();

		Query q = em.createQuery("SELECT c FROM Campeonato c", Campeonato.class);
		List<Campeonato> lista = q.getResultList();
		em.clear();
		return lista;
	}

	public static Campeonato findOneCampeonato(Integer id) {
		EntityManager em = JPAUtil.criarEntityManager();
		Campeonato campeonato = em.find(Campeonato.class, id);
		em.close();
		return campeonato;
	}
}
