package service.abitante;

import dao.abitante.PersonaDAO;
import model.Persona;

import java.util.List;

public interface PersonaService {

	public List<Persona> listAllElements() throws Exception;

	public Persona caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Persona personaInstance) throws Exception;

	public void inserisciNuovo(Persona personaInstance) throws Exception;

	public void rimuovi(Persona personaInstance) throws Exception;

	public List<Persona> findByExample (Persona persona) throws Exception;

	// per injection
	public void setPersonaDAO(PersonaDAO personaDAO);

}
