package dao.abitante;

import dao.IBaseDAO;
import model.Persona;

import java.util.List;

public interface PersonaDAO extends IBaseDAO<Persona> {

	public List<Persona> findByExample(Persona example) throws Exception;
	
}
