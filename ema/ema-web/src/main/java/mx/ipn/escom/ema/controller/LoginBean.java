
package mx.ipn.escom.ema.controller;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="login")
@RequestScoped

public class LoginBean {

    public void callServletIngreso(){
        String url= "/SevletLogin";
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
