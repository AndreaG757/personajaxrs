package dao;

import dao.abitante.PersonaDAO;
import dao.abitante.PersonaDAOImpl;

public class MyDaoFactory {

	// rendiamo questo factory SINGLETON
	private static PersonaDAO personaDAOInstance = null;

	public static PersonaDAO getPersonaDAOInstance() {
		if (personaDAOInstance == null)
			personaDAOInstance = new PersonaDAOImpl();
		return personaDAOInstance;
	}

}
