package com.tpSeo.model;

import java.sql.Connection;

import com.tpSeo.DAO.ObjectBDD;
import com.tpSeo.Util.Util;

public class Admin extends ObjectBDD{
    private Integer id;
    private String nom;
    private String prenom;
    private String identifiant;
    private String mdp;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getIdentifiant() {
        return identifiant;
    }
    public void setIdentifiant(String identifiant) throws Exception {
        if(identifiant.equals("")){
            throw new Exception("Veuillez mettre un identifiant");
        }
        this.identifiant = identifiant;
    }
    public String getMdp() {
        return mdp;
    }
    public void setMdp(String mdp) throws Exception {
        if(mdp.equals("")){
            throw new Exception("Veuillez mettre un mot de passe");
        }
        this.mdp = mdp;
    }
    public void init(){
        this.setNomDeTable("admin");
        this.setPkey("id");;
    }
    public Admin() {
        this.init();
    }
    public Admin login(String id, String password) throws Exception{
        Connection con = null;
        Admin myAdmin = null;
        this.setIdentifiant(id);
        this.setMdp(Util.getMd5(password));
        try{
            con = Util.getConnection();
            try{
                myAdmin = (Admin)this.find(con)[0];
            }
            catch(Exception ex){
                throw new Exception("Mot de passe ou identifiant incorrect");
            }
        }
        catch(Exception e){
            throw e;
        }
        return myAdmin;
    }
    
    
}