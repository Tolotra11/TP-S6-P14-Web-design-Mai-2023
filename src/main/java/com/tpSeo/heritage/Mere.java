/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpSeo.heritage;

import com.tpSeo.DAO.ObjectBDD;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *
 * @author Tolotra
 */
abstract public class Mere extends ObjectBDD{
    private Fille objectFille;
    private Fille[] listeFille;
    abstract public void setDBInformation();
    abstract public void setObjectFille();
    public Fille getObjectFille() {
        return objectFille;
    }
    public void setObjectFille(Fille objectFille) {
        this.objectFille = objectFille;
    }
//Prendre tout les filles d'une mere
    public Fille[] getListeFille() throws Exception {
        Fille[] result = null;
        if(this.listeFille != null){
            result = this.listeFille;
        }
        else{
            
            Method valeurOfLiaison = this.oneMethod("get"+objectFille.getLiaison());
            Object valeur = valeurOfLiaison.invoke(this);
            Method setter = objectFille.oneMethod("set"+this.getNomDeTable()+objectFille.getLiaison());
            setter.invoke(objectFille, valeur);
            Object [] lF = objectFille.find(null);
            result = new Fille[lF.length];
            result = Arrays.copyOf(lF, lF.length, result.getClass());
            this.listeFille = result;
        }
        return result;
    }

    public void setListeFille(Fille[] listeFille) {
        this.listeFille = listeFille;
    }
    
}
