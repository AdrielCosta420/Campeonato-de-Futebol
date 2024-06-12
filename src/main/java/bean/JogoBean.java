package bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dao.JogoDAO;
import entities.Jogo;

@ManagedBean
public class JogoBean {
	private Jogo jogo = new Jogo();
	private List<Jogo> lista;
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
	
	
	
	public String save() {
		if (jogo != null) {
		
	
			JogoDAO.save(jogo);
			jogo = new Jogo();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Jogo criado!"));
			lista = JogoDAO.listar();
			return null;

		}
		return null;
	}

	public String edit(Jogo jogoEdit) {
		if (jogoEdit != null) {
			JogoDAO.edit(jogoEdit);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Jogo atualizado!"));
			lista = JogoDAO.listar();
			return "listagem.xthml";
		}
		return null;
	}

	public String delete(int id) {

		JogoDAO.delete(id);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Jogo excluído!"));
		lista = JogoDAO.listar();
		return "listagem.xthml";

	}
}
