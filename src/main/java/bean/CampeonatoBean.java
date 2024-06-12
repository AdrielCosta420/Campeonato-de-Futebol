package bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.CampeonatoDAO;
import entities.Campeonato;

@ManagedBean
@ViewScoped
public class CampeonatoBean {
	private Campeonato campeonato = new Campeonato();
	private List<Campeonato> campeonatos;

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
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

	
	public String save() {
		if (campeonato != null) {
			CampeonatoDAO.save(campeonato);
			campeonato = new Campeonato();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Campeonato criado!"));
		//	campeonatos = CampeonatoDAO.listar();
			return null;
		}
		return null;

	}

	public String edit(Campeonato campeonatoEdit) {
		if (campeonatoEdit != null) {
			CampeonatoDAO.edit(campeonatoEdit);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Campeonato atualizado!"));
			campeonatos = CampeonatoDAO.listar();
			return null;

		}
		return null;

	}

	public void delete(Campeonato campeonato) {
		CampeonatoDAO.delete(campeonato.getId());
		campeonatos = CampeonatoDAO.listar();
	}
}
