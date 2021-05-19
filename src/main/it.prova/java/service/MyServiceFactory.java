package service;

import dao.MyDaoFactory;
import service.abitante.PersonaService;
import service.abitante.PersonaServiceImpl;

public class MyServiceFactory {

	// rendiamo le istanze restituite SINGLETON
	private static PersonaService personaServiceInstance = null;

	public static PersonaService getPersonaServiceInstance() {
		if (personaServiceInstance == null) {
			personaServiceInstance = new PersonaServiceImpl();
			personaServiceInstance.setPersonaDAO(MyDaoFactory.getPersonaDAOInstance());
		}
		return personaServiceInstance;
	}

}
