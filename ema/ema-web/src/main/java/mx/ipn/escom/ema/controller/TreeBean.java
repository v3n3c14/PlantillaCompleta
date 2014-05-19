package mx.ipn.escom.ema.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.ipn.escom.ema.model.entities.CSSResources;
import mx.ipn.escom.ema.model.entities.Users;
import mx.ipn.escom.ema.model.resources.DAO.ResourceDAOcss;
import mx.ipn.escom.ema.model.resources.DAO.impl.CSSResourcesDAOimpl;
import mx.ipn.escom.ema.services.CSSResourceService;
import mx.ipn.escom.ema.services.HTMLResourceService;
import mx.ipn.escom.ema.services.ProjectsService;
import mx.ipn.escom.ema.services.impl.CSSResourceServiceimpl;
import mx.ipn.escom.ema.services.impl.HTMLResourceServiceimpl;
import mx.ipn.escom.ema.services.impl.ProjectServiceimpl;
import mx.ipn.escom.ema.to.CSSResourceTO;
import mx.ipn.escom.ema.to.HTMLResourceTO;
import mx.ipn.escom.ema.to.ProjectsTO;
import mx.ipn.escom.ema.to.UsersTO;

import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@ManagedBean
@ViewScoped


public class TreeBean implements Serializable{

    private static final long serialVersionUID = 2417620239014385855L;
    private TreeNode root;
    private TreeNode selectedNode;
    private ProjectsService projectService = new ProjectServiceimpl();
    private HTMLResourceService htmlService = new HTMLResourceServiceimpl();
    private CSSResourceService cssService = new CSSResourceServiceimpl();
    private UsersTO userTO = new UsersTO();
    private List<ProjectsTO> listProjects = new ArrayList<ProjectsTO>();
    private List<HTMLResourceTO> listHtml = new ArrayList<HTMLResourceTO>();
    private List<CSSResourceTO> listCss = new ArrayList<CSSResourceTO>();

    public TreeBean(){
    	root = new DefaultTreeNode("node", "Root", null);
    	
        //UserService userService = UserServiceFactory.getUserService(); //no comentar para subir
        //User user = userService.getCurrentUser();// Obtiene la sesion de google. descomentar para subir
       //userTO.setUser(user.getEmail());  //no comentar para subir
    	userTO.setUser("andrea@example.com");
		listProjects = projectService.showProjects(userTO);
		for (int i = 0; i < listProjects.size(); i++) {
			ProjectsTO project = listProjects.get(i);
			TreeNode node1 = new DefaultTreeNode("node", project.getName(), root);
			listHtml = htmlService.showHTMLResources(project, userTO);
			for (int j = 0; j < listHtml.size(); j++) {
				HTMLResourceTO html = listHtml.get(j);
				TreeNode node11 = new DefaultTreeNode("leaf", html.getName(), node1);
			}
			listCss = cssService.showCSSResources(project, userTO);
			for (int k = 0; k < listCss.size(); k++) {
				CSSResourceTO css = listCss.get(k);
				TreeNode node12 = new DefaultTreeNode("leaf", css.getName(), node1);
			}
		}
    }
    

    public TreeNode getRoot() {
		return root;
	}
    public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}
	public void displaySelectedSingle(){
	    if(selectedNode == null) {
	    		return;
	    }
	    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
	    FacesContext.getCurrentInstance().addMessage(null, message);    
	}
	
	  public void onNodeSelect(NodeSelectEvent event) {
	        System.out.println("NodeSelectEvent Fired");
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().getData().toString());
	        FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
	    }
    
    
    
    
    
    
    
    
    

/*	
    public TreeBean() {
        root = new DefaultTreeNode("Root", null);
/*        TreeNode node0 = new TreeNodeImplement("Segment 0", root);
        TreeNode node1 = new TreeNodeImplement("Segment 1", root);
        TreeNode node2 = new TreeNodeImplement("Segment 2", root);
        TreeNode node00 = new TreeNodeImplement("Segment 0.0", node0);
        TreeNode node01 = new TreeNodeImplement("Segment 0.1", node0);
        TreeNode node10 = new TreeNodeImplement("Segment 1.0", node1);
        TreeNode node11 = new TreeNodeImplement("Segment 1.1", node1);
        TreeNode node000 = new TreeNodeImplement("Segment 0.0.0", node00);
        TreeNode node001 = new TreeNodeImplement("Segment 0.0.1", node00);
        TreeNode node010 = new TreeNodeImplement("Segment 0.1.0", node01);
        TreeNode node100 = new TreeNodeImplement("Segment 1.0.0", node10);*/
/*		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();// Obtiene la sesion de google
		userTO.setUser(user.getEmail());
		// userTO.setUser("andrea@example.com");
		listProjects = projectService.showProjects(userTO);
		for (int i = 0; i < listProjects.size(); i++) {
			ProjectsTO project = listProjects.get(i);
			// TreeNode node1 = new DefaultTreeNode(project.getName(), root);
			node1 = new DefaultTreeNode(project.getName(), root);
			listHtml = htmlService.showHTMLResources(project, userTO);
			for (int j = 0; j < listHtml.size(); j++) {
				HTMLResourceTO html = listHtml.get(j);
				TreeNode node11 = new DefaultTreeNode(html.getName(), node1);
			}
			listCss = cssService.showCSSResources(project, userTO);
			for (int k = 0; k < listCss.size(); k++) {
				// System.out.println(listCss.get(k));
				CSSResourceTO css = listCss.get(k);
				TreeNode node12 = new DefaultTreeNode(css.getName(), node1);
			}
		}
       
    }
     

	public TreeNode getRoot() {
		return root;
	}

    public TreeNode getSelectedNode() {
        return selectedNode;
    }
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    public void onNodeSelect(NodeSelectEvent event) {
        System.out.println("NodeSelectEvent Fired");
        changeName();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().getData().toString());
        FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
    }
    public void onNodeExpand(NodeExpandEvent event) {
    	
        System.out.println("NodeExpandEvent Fired");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().getData().toString());
        FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
    }
    public void onNodeCollapse(NodeCollapseEvent event) {
        System.out.println("NodeCollapseEvent Fired");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", event.getTreeNode().getData().toString());
        FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
    }
    
        public void displaySelectedSingle() {
        if(selectedNode != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
 
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        selectedNode = null;
    }
        
        @Override
        public String toString() {
            return selectedNode.getData().toString();
        }
    
    public String changeName(){
    	String treeNode = null;
    	FacesContext context = FacesContext.getCurrentInstance();
     	UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();// Obtiene la sesion de google
	    userTO.setUser(user.getEmail());
    	System.out.println("njdnfjf");
    	if(selectedNode != null){
    		System.out.println(selectedNode);
    		treeNode = selectedNode.getData().toString();
    		ProjectsTO project = new ProjectsTO();
    		project.setName(selectedNode.getData().toString());
    		System.out.println(project.getName());
    		newName = context.getExternalContext().getRequestParameterMap().get("text");
        	System.out.println(newName);
    	}
    	return treeNode;
    	
    }
	
	
/*	private TreeNode root;
	private TreeNode node1;
	private TreeNode node11;
	private TreeNode node12;
	private List<Name> listProject;
	 private ProjectsService projectService = new ProjectServiceimpl();
	    private HTMLResourceService htmlService = new HTMLResourceServiceimpl();
	    private CSSResourceService cssService = new CSSResourceServiceimpl();
	    private UsersTO userTO = new UsersTO();
	    private List<ProjectsTO> listProjects = new ArrayList<ProjectsTO>();
	    private List<HTMLResourceTO> listHtml = new ArrayList<HTMLResourceTO>();
	    private List<CSSResourceTO> listCss = new ArrayList<CSSResourceTO>();
	private String name;
	private TreeNode selectedNode;
	
	   /**
     * Gets the selected node in the tree.
     *
     * @return selected node in tree.
     */
/*	
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     * Sets the selected node in the tree.
     *
     * @param selectedNode node to be set as selected.
     */
	/*
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
	
	public TreeBean(){
		listProject = new ArrayList<Name>();
	}
	

	public TreeNode getRoot(){
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();// Obtiene la sesion de google
	    userTO.setUser(user.getEmail());
		if(root == null){
			root = new DefaultTreeNode();
			//  userTO.setUser("andrea@example.com");
		        listProjects = projectService.showProjects(userTO);
		       for(int i=0; i<listProjects.size(); i++){
		        	ProjectsTO project = listProjects.get(i);
		        	//TreeNode node1 = new DefaultTreeNode(project.getName(), root);
		        	node1 = new DefaultTreeNode("node",new Name("1",project.getName()), root);
		        	listHtml = htmlService.showHTMLResources(project, userTO);
		        	for(int j=0; j<listHtml.size(); j++){
		        		HTMLResourceTO html = listHtml.get(j);
		        		node11 = new DefaultTreeNode("leaf",new Name("2",html.getName()), node1);
		        	}
		        	listCss = cssService.showCSSResources(project, userTO);
		        	for(int k=0; k<listCss.size();k++){
		        		//System.out.println(listCss.get(k));
		        		CSSResourceTO css = listCss.get(k);
		        		node12 = new DefaultTreeNode("leaf",new Name("2",css.getName()), node1);
		        	}
		        }
		}
		
	        return root;
	}
	
	public void saveData(){
		iterateNodes(root);
	}
	
	public void iterateNodes(TreeNode node){
		for(TreeNode iterateNode: node.getChildren()){
			listProject.add((Name)node.getData());
			if(!iterateNode.isLeaf()){
				iterateNodes(iterateNode);
			}
		}
	}
	
	
	public String getNameofProject(){
		String project= null; 
		String resource;
		TreeNode node = selectedNode;
		if(node.getParent()!= null){
			System.out.println(getNameofResource());
		}else{
			System.out.println(project = node.getData().toString());
		}
		return project;
	}
	
	public String getNameofResource(){ 
		String resource = null;
		TreeNode node = selectedNode;
		if(node.getParent()!=null){
			resource = node.getData().toString();
		}
		return resource;
	}
	
	  
    public void displaySelectedSingle() {
        if(selectedNode != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
 
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
	
	  public void onNodeSelect(NodeSelectEvent event) {
	        System.out.println("NodeSelectEvent Fired");
	        //saveNewName();
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().getData().toString());
	        FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), msg);
	    }
	
	public void handleSave(){
		UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();// Obtiene la sesion de google
	    userTO.setUser(user.getEmail());
		if(selectedNode != null){
			String nodeResult = selectedNode.getData().toString();
			ProjectsTO project = new ProjectsTO();
    		project.setName(nodeResult);
    		FacesContext context = FacesContext.getCurrentInstance();
    		name = context.getExternalContext().getRequestParameterMap().get("global:inputText");
    		projectService.updateProject(project, userTO, nodeResult);
		}
	}
	
	public void deleteProject(){
		
	}

	
	public static class Name{
		private String id;
		private String resourceName;

		public String getResourceName() {
			return resourceName;
		}

		public void setResourceName(String resourceName) {
			this.resourceName = resourceName;
		}
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Name(String id, String name){
			this.id = id;
			this.resourceName = name;
		}
		
		 @Override
	        public String toString() {
	            return resourceName;
	        }
		
	}*/
    
    
}
