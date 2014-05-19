package mx.ipn.escom.ema.controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import mx.ipn.escom.ema.services.CSSResourceService;
import mx.ipn.escom.ema.services.ProjectsService;
import mx.ipn.escom.ema.services.impl.CSSResourceServiceimpl;
import mx.ipn.escom.ema.services.impl.ProjectServiceimpl;
import mx.ipn.escom.ema.to.CSSResourceTO;
import mx.ipn.escom.ema.to.HTMLResourceTO;
import mx.ipn.escom.ema.to.ProjectsTO;
import mx.ipn.escom.ema.to.UsersTO;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;



@ManagedBean(name="cssResource")
@RequestScoped


public class NewResourceCSSBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -3953132193325589822L;
    
    private String nombre_css;
    private String nameProject;
    private CSSResourceTO css = new CSSResourceTO();
    private UsersTO userTO = new UsersTO();
    private ProjectsTO projectTO = new ProjectsTO();
    private CSSResourceService cssService = new CSSResourceServiceimpl();
    private Date date = new Date();
    private List<String> projectList = new ArrayList<String>();
    private ProjectsService projectService = new ProjectServiceimpl();
    
    public String getNombre_css() {
		return nombre_css;
	}

	public void setNombre_css(String nombre_css) {
		this.nombre_css = nombre_css;
	}

	public String getNameProject() {
		return nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

	
	public List<String> complete(String query){
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();// Obtiene la sesion de google
	    userTO.setUser(user.getEmail());
    	//Users user = new Users();
    	System.out.println("antes de set to");
    //	userTO.setUser("andrea@example.com");
    	System.out.println("antes de la lista " + userTO);
    	List<ProjectsTO> listProjects = projectService.showProjects(userTO);
    	System.out.println(listProjects);
    	for(ProjectsTO projectResult: listProjects){
    		if(projectResult.getName().startsWith(query)){
    			projectList.add(projectResult.getName());
    		}
    	}
    	return projectList;
	}
	
	public CSSResourceTO createCSS() throws IOException  {
	    AutocompleteCSSBean autocompleteCSSBean = new AutocompleteCSSBean();
	    String url = "/faces/views/New_Resource_CSS.xhtml";
	    System.out.println("metodo");
		FacesContext context = FacesContext.getCurrentInstance();
   	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();// Obtiene la sesion de google
	    String genericCode = autocompleteCSSBean.getEtiqueta();
	    userTO.setUser(user.getEmail());
	    //    userTO.setUser("andrea@example.com");
	    //NameProject = context.getExternalContext().getRequestParameterMap().get("html:force_proyect");
	    System.out.println(nameProject);
        nombre_css = context.getExternalContext().getRequestParameterMap().get("css:regex");
	    System.out.println("Name resource " + nombre_css );
	    nombre_css = context.getExternalContext().getRequestParameterMap().get("css:regex");
	    System.out.println("name resource " + nombre_css);
	    projectTO.setName(nameProject);
	    css.setName(nombre_css);
	    css.setDate(date);
	    css.setCode(genericCode);
	    css.setProject(projectTO);
	    cssService.addCSS(css, projectTO, userTO);
	//   context.getExternalContext().redirect(url);
	    return css;
		
	}
	
	    public String outcome() throws IOException{
        createCSS();
        return "/faces/views/New_Resource_CSS";
    }
    
	
	
	
}
