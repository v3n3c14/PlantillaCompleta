package mx.ipn.escom.ema.model.resources.DAO.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import mx.ipn.escom.ema.model.entities.CSSResources;
import mx.ipn.escom.ema.model.entities.HTMLResources;
import mx.ipn.escom.ema.model.entities.Projects;
import mx.ipn.escom.ema.model.entities.Resources;
import mx.ipn.escom.ema.model.entities.Users;
import mx.ipn.escom.ema.model.persistence.EMFService;
import mx.ipn.escom.ema.model.projects.DAO.impl.ProjectsDAOimpl;
import mx.ipn.escom.ema.model.resources.DAO.ResourceDAOcss;

public class CSSResourcesDAOimpl implements ResourceDAOcss{

	public void addResourceCSS(CSSResources css) {
		EntityManager em = EMFService.get().createEntityManager();
		try{
			em.persist(css);
		}finally{
			em.close();
		}
	}

	public void updateResourceCSS(CSSResources css, Projects project, Users user, String code) {
		Date date = new Date();
		ProjectsDAOimpl pdi = new ProjectsDAOimpl();
		Projects projectResult = pdi.findProject(project, user);
		CSSResources cssResult = findCSSofProjectofUser(css, projectResult, user);
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select e from CSSResources e where name= :name and id= :id");
		q.setParameter("name", cssResult.getName());
		q.setParameter("id", cssResult.getId());
		CSSResources nameOfResource = (CSSResources) q.getSingleResult();
		nameOfResource.setCode(code);
		nameOfResource.setDate(date);
		try{
			em.persist(nameOfResource);
		}finally{
			em.close();
		}
	}

	public List<CSSResources> listResourcesCss(Resources resource) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select e from CSSResources e where resource =: resource");
		q.setParameter("resource", resource);
		return q.getResultList();
	}

	public CSSResources findOneResourceCSS(Resources resource, String name) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select e from CSSResources e where resource =: resource AND name =: name");
		q.setParameter("resource", resource);
		q.setParameter("name", name);
		CSSResources css = (CSSResources) q.getSingleResult();
		return css;
	}

	public CSSResources findResourcebyId(Long id) {
		EntityManager em = EMFService.get().createEntityManager();
		/*	Query q = em.createQuery("select e from CSSResources e where id= :id");
			q.setParameter("id", id);
			CSSResources cssr = (CSSResources)q.getSingleResult();
			return cssr;*/
			CSSResources cssr = em.find(CSSResources.class, id);
			return cssr;
	}

	public CSSResources findResourcebyName(String name) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select e from CSSResources e where e.name= :name");
		q.setParameter("name", name);
		CSSResources cssr = (CSSResources)q.getSingleResult();
		return cssr;
	}


	public void addReferencetoResource(Resources resource, CSSResources idcss) {
		EntityManager em = EMFService.get().createEntityManager();
		CSSResources cssResult = new CSSResources();
		Resources resourceResult = em.find(Resources.class, resource.getId());
		Key keyCSS = KeyFactory.createKey(CSSResources.class.getSimpleName(), idcss.getId());
		cssResult = em.find(CSSResources.class, keyCSS);
		cssResult.setResource(resourceResult.getId());
		try{
			em.persist(cssResult);
		}finally{
			em.close();
		}
		
	}

	public CSSResources ResourcesCssFromProject(Projects project) {
		ResourcesDAOimpl rdi = new ResourcesDAOimpl();
		Resources resource = rdi.findResourceFromProject(project);
		System.out.println(resource);
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select e from CSSResources e where e.resource= :resource");
		q.setParameter("resource", resource.getId());
		CSSResources css = (CSSResources) q.getSingleResult();
		System.out.println(css.getName());
		return css;
	}

	public CSSResources findCSSofProject(CSSResources css, Projects project) {
		ResourcesDAOimpl rdi = new ResourcesDAOimpl();
		List<Key> resource = rdi.findResourceListFromProject(project);
		CSSResources cssResult= new CSSResources();
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select e from CSSResources e where name = :name");
		q.setParameter("name", css.getName());
		cssResult = (CSSResources) q.getSingleResult();
		Key cssResource = cssResult.getResource();
		for(int i =0; i<resource.size(); i++){
			if(resource.get(i) == cssResource){
				Query qR = em.createQuery("select e from CSSResources e where resource = :idResource");
				qR.setParameter("idResource", cssResource);
				cssResult = (CSSResources) q.getSingleResult();
			}
			
		}
		return cssResult;
	}

	public void modifyName(CSSResources css, Projects project, Users user, String name) {
		Date date = new Date();
		ProjectsDAOimpl pdi = new ProjectsDAOimpl();
		Projects projectResult = pdi.findProject(project, user);
		CSSResources cssResult = findCSSofProjectofUser(css, projectResult, user);
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select e from CSSResources e where name= :name and id= :id");
		q.setParameter("name", cssResult.getName());
		q.setParameter("id", cssResult.getId());
		CSSResources nameOfResource = (CSSResources) q.getSingleResult();
		nameOfResource.setName(name);
		nameOfResource.setDate(date);
		try{
			em.persist(nameOfResource);
		}finally{
			em.close();
		}
	}

	public void addResourceCSStoProject(CSSResources css, Projects project,Users user) {
		ProjectsDAOimpl pdi = new ProjectsDAOimpl();
		Projects projectResult = pdi.findProject(project, user);
		ResourcesDAOimpl rdi = new ResourcesDAOimpl();
		Resources resource = new Resources();
		resource.setProject(projectResult.getId());
		rdi.addResource(resource);
		pdi.addResourcetoProjectTest(resource, projectResult);
		EntityManager em = EMFService.get().createEntityManager();
		css.setResource(resource.getId());
		try{
			em.persist(css);
		}finally{
			em.close();
		}
		rdi.addReferenceOfCSS(css, resource);
	}

	public void deleteResourceCSS(CSSResources css, Projects project, Users user) {
		ProjectsDAOimpl pdi = new ProjectsDAOimpl();
		ResourcesDAOimpl rdi = new ResourcesDAOimpl();
		Projects projectResult = pdi.findProject(project, user);
		CSSResources cssResult = findCSSofProjectofUser(css, projectResult, user);
		Key resourceKey = cssResult.getResource();
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select e from Resources e where id= :id");
		q.setParameter("id", resourceKey);
		Resources resource = (Resources) q.getSingleResult();
		CSSResources id = em.find(CSSResources.class, cssResult.getId());
		rdi.deleteResourceCSS(id, projectResult, user);
		pdi.deleteCSSfromProject(projectResult, resource.getId(), user);
		try{
			em.remove(id);
			
		}finally{
			em.close();
		}
	}

	public List<CSSResources> showCSSResourcesFromProject(Projects project,Users user) {
		ResourcesDAOimpl resources = new ResourcesDAOimpl();
		List<Key> resourcesList = resources.findResourceListFromUserProject(project, user);
		List<CSSResources> cssList = new ArrayList<CSSResources>();
		CSSResources css = new CSSResources();
		EntityManager em = EMFService.get().createEntityManager();
		for(int i =0; i<resourcesList.size(); i++){
			System.out.println(resourcesList.get(i));
			Key resourceKey = resourcesList.get(i);
			Resources resource = em.find(Resources.class, resourceKey);
			if(resource.getCssrec() != null){
				Query q = em.createQuery("select e from CSSResources e where resource = :resource");
				q.setParameter("resource", resource.getId());
				css = (CSSResources) q.getSingleResult();
				cssList.add(css);
				System.out.println(cssList);
			}
		}
		return cssList;
	}

	public CSSResources findCSSofProjectofUser(CSSResources css,Projects project, Users user) {
		ResourcesDAOimpl rdi = new ResourcesDAOimpl();
		List<Key> resource = rdi.findResourceListFromUserProject(project, user);
		CSSResources cssResult= new CSSResources();
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select e from CSSResources e where name = :name");
		q.setParameter("name", css.getName());
		cssResult = (CSSResources) q.getSingleResult();
		Key cssResource = cssResult.getResource();
		for(int i =0; i<resource.size(); i++){
			if(resource.get(i) == cssResource){
				Query qR = em.createQuery("select e from CSSResources e where resource = :idResource");
				qR.setParameter("idResource", cssResource);
				cssResult = (CSSResources) q.getSingleResult();
			}
			
		}
		return cssResult;
	}

	public void deleteAllCSS(Resources resource) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select e from CSSResources e where resource = :resource");
		q.setParameter("resource", resource.getId());
		CSSResources css = (CSSResources) q.getSingleResult();
		if(css == null){
			System.out.println("No hay css");
		}else{
			try{
				em.remove(css);
			}finally{
				em.close();
			}
		}
	}

	public CSSResources existingCSSinProject(CSSResources css,
			Projects project, Users user) {
		ResourcesDAOimpl rdi = new ResourcesDAOimpl();
		List<Key> resource = rdi.findResourceListFromUserProject(project, user);
		CSSResources cssResult= new CSSResources();
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select e from CSSResources e where name = :name");
		q.setParameter("name", css.getName());
		try{
			cssResult = (CSSResources) q.getSingleResult();
			if(cssResult !=null){
				System.out.println("Existe CSS");
				Key cssResource = cssResult.getResource();
				for(int i =0; i<resource.size(); i++){
					if(resource.get(i) == cssResource){
						Query qR = em.createQuery("select e from CSSResources e where resource = :idResource");
						qR.setParameter("idResource", cssResource);
						cssResult = (CSSResources) q.getSingleResult();
					}
			}
		}
		}catch(Exception e){
			addResourceCSStoProject(css, project, user);
			e.printStackTrace();
		}
		return cssResult;
	}
}
