package dao.abitante;

import model.Persona;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class PersonaDAOImpl implements PersonaDAO {

	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Persona> list() throws Exception {
		return entityManager.createQuery("from Persona", Persona.class).getResultList();
	}

	@Override
	public Optional<Persona> findOne(Long id) throws Exception {
		return Optional.ofNullable(entityManager.find(Persona.class, id));
	}

	@Override
	public void update(Persona personaInstance) throws Exception {
		if (personaInstance == null) {
			throw new Exception("Problema valore in input");
		}

		personaInstance = entityManager.merge(personaInstance);
	}

	@Override
	public void insert(Persona personaInstance) throws Exception {
		if (personaInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(personaInstance);
	}

	@Override
	public void delete(Persona personaInstance) throws Exception {
		if (personaInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.remove(entityManager.merge(personaInstance));
	}

	@Override
	public List<Persona> findByExample(Persona example) throws Exception {

		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select p from Persona p where p.id = p.id ");

		if (StringUtils.isNotEmpty(example.getNome())) {
			whereClauses.add(" p.nome  like :nome ");
			paramaterMap.put("nome", "%" + example.getNome() + "%");
		}
		if (StringUtils.isNotEmpty(example.getCognome())) {
			whereClauses.add(" p.cognome like :cognome ");
			paramaterMap.put("cognome", "%" + example.getCognome() + "%");
		}
		if (example.getDataNascita() != null) {
			whereClauses.add(" p.dataNascita >= :dataNascita ");
			paramaterMap.put("dataNascita", example.getDataNascita());
		}

		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Persona> typedQuery = entityManager.createQuery(queryBuilder.toString(), Persona.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();

	}

}
