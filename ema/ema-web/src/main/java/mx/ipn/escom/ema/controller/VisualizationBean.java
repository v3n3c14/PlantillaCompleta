package mx.ipn.escom.ema.controller;

import javax.faces.context.FacesContext;

public class VisualizationBean {
	
	public void callVisualizationServlet(){
        String url= "/visualizacion";
        FacesContext context = FacesContext.getCurrentInstance();
        try {
        	  System.out.println("try");
            context.getExternalContext().dispatch(url);
            System.out.println("dispatch");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("context bean");
        }finally{
            context.responseComplete();
            System.out.println("finally context");
        }
    }   

}
