package de.iteratec.schnitzel.server.business;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.iteratec.schnitzel.common.model.Puzzle;
import de.iteratec.schnitzel.server.persistence.IBaseDAO;
import de.iteratec.schnitzel.server.persistence.PuzzleDAO;

@Service
@Transactional
public class PuzzleBA extends BaseBA<Puzzle> {
	
	@Autowired
	private PuzzleDAO dao;

	@Override
	public IBaseDAO<Puzzle> getDao() {
		return dao;
	}

	@Transactional
	public Puzzle findByName(String name, String[] lazyProperties) {
		Query query = dao.createQuery("from " + dao.getType().getSimpleName()
				+ " where " + Puzzle.COL_NAME + " = :name");
		query.setParameter("name", name);
		return findSingleEntityByQuery(query, lazyProperties);
	}

}
