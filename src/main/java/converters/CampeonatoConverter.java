package converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dao.CampeonatoDAO;
import entities.Campeonato;

@FacesConverter(forClass = Campeonato.class)
public class CampeonatoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null || value.isEmpty()) { 
			return null;
		}
		Integer id = Integer.valueOf(value);
		return CampeonatoDAO.findOneCampeonato(id);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
	    if (value == null) {
            return "";
        }
        if (value instanceof Campeonato) {
            Campeonato campeonato = (Campeonato) value;
            return campeonato.getId().toString() != null ? campeonato.getId().toString() : "";
        } else {
            throw new IllegalArgumentException("Objeto não é um Campeonato: " + value.getClass().getName());
        }
    }
	
}
