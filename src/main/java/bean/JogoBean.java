package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.CampeonatoDAO;
import dao.JogoDAO;
import entities.Campeonato;
import entities.Jogo;
import entities.Resume;

@ManagedBean
@SessionScoped
public class JogoBean {
	private Jogo jogo = new Jogo();
	private List<Jogo> lista;
	private List<Campeonato> campeonatos;
	private List<Resume> resumos;
	private String selectedTeam;
	private List<Jogo> filteredGames;
	private Date dataAtual = new Date();

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

	public String getSelectedTeam() {
		return selectedTeam;
	}

	public void setSelectedTeam(String selectedTeam) {
		this.selectedTeam = selectedTeam;
	}

	public List<Jogo> getFilteredGames() {
		return filteredGames;
	}

	public void setFilteredGames(List<Jogo> filteredGames) {
		this.filteredGames = filteredGames;
	}

	public List<Resume> getResumos() {
		if (resumos == null) {
			calcularResumos();
		}
		return resumos;
	}

	public void setResumos(List<Resume> resumos) {
		this.resumos = resumos;
	}

	public List<Campeonato> getCampeonatos() {
		if (campeonatos == null) {
			campeonatos = CampeonatoDAO.listar();
		}
		return campeonatos;
	}

	public void setCampeonatos(List<Campeonato> campeonatos) {
		this.campeonatos = campeonatos;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public List<Jogo> getLista() {
		if (lista == null) {
			lista = JogoDAO.listar();
		}
		return lista;
	}

	public void setLista(List<Jogo> lista) {
		this.lista = lista;
	}

	public void init() {
		campeonatos = CampeonatoDAO.listar();
		lista = JogoDAO.listar();

	}

	public String save() {
		if (jogo != null) {
			if (jogo.getTime1().equals(jogo.getTime2())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Times não podem ser iguais."));
				return null;
			}
			jogo.setDataCadastro(new Date());
			JogoDAO.save(jogo);
			jogo = new Jogo();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Jogo criado!"));
			lista = JogoDAO.listar();
			return "listagem.xhtml";

		}
		return null;
	}

	public void edit(Jogo jogoEdit) {
		if (jogoEdit != null) {
			if (jogoEdit.getTime1().equals(jogoEdit.getTime2())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Times não podem ser iguais."));

			}
			JogoDAO.edit(jogoEdit);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Jogo atualizado!"));
			//lista = JogoDAO.listar();

		}

	}

	public void delete(Integer id) {
		
		JogoDAO.delete(id);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Jogo excluído!"));
	}

	public String filterGames() {
		if (selectedTeam != null && !selectedTeam.isEmpty()) {
			filteredGames = JogoDAO.findByTeam(selectedTeam);
		} else {
			filteredGames = new ArrayList<>();
		}
		return null;
	}
	

	private void calcularResumos() {
		Map<String, Resume> mapResumos = new HashMap<>();

		for (Jogo jogo : lista) {
			String time1 = jogo.getTime1();
			String time2 = jogo.getTime2();
			int golsTime1 = jogo.getGolsTime1();
			int golsTime2 = jogo.getGolsTime2();

			mapResumos.putIfAbsent(time1, new Resume(time1));
			mapResumos.putIfAbsent(time2, new Resume(time2));

			Resume resumo1 = mapResumos.get(time1);
			Resume resumo2 = mapResumos.get(time2);

			resumo1.setGolsMarcados(resumo1.getGolsMarcados() + golsTime1);
			resumo1.setGolsSofridos(resumo1.getGolsSofridos() + golsTime2);

			resumo2.setGolsMarcados(resumo2.getGolsMarcados() + golsTime2);
			resumo2.setGolsSofridos(resumo2.getGolsSofridos() + golsTime1);

			if (golsTime1 > golsTime2) {
				resumo1.setVitorias(resumo1.getVitorias() + 1);
				resumo1.setPontos(resumo1.getPontos() + 3);
				resumo2.setDerrotas(resumo2.getDerrotas() + 1);
			} else if (golsTime1 < golsTime2) {
				resumo2.setVitorias(resumo2.getVitorias() + 1);
				resumo2.setPontos(resumo2.getPontos() + 3);
				resumo1.setDerrotas(resumo1.getDerrotas() + 1);
			} else {
				resumo1.setEmpates(resumo1.getEmpates() + 1);
				resumo1.setPontos(resumo1.getPontos() + 1);
				resumo2.setEmpates(resumo2.getEmpates() + 1);
				resumo2.setPontos(resumo2.getPontos() + 1);
			}
		}

		resumos = new ArrayList<>(mapResumos.values());

		for (Resume resumo : resumos) {
			resumo.setSaldoGols(resumo.getGolsMarcados() - resumo.getGolsSofridos());
		}
	}
}
