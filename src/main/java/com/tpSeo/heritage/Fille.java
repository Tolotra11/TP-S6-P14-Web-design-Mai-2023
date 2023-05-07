/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpSeo.heritage;

import com.tpSeo.DAO.ObjectBDD;

/**
 *
 * @author Tolotra
 */
abstract public class Fille extends ObjectBDD{
    private String liaison;
    abstract public void setDBInformation();
    abstract public void setLiaison();
    public String getLiaison() {
        return liaison;
    }
    public void setLiaison(String liaison) {
        this.liaison = liaison;
    }
    
}
