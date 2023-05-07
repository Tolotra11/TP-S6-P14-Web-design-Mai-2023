package com.tpSeo.model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;

import com.tpSeo.DAO.ObjectBDD;
import com.tpSeo.Util.Util;

public class V_article extends ObjectBDD{
    private Integer id;
    private String resume;
    private String titre;
    private String contenu;
    private Integer categorieId;
    private Integer adminId;
    private Integer etat;
    private Timestamp datePublication;
    private String nomCat;
    private String nom;
    private String prenom;
    private String titleInUrl;
    private Image image;
    public Image getImage(Connection con) throws Exception {
        if(this.image == null){
            Image nIm = new Image();
            nIm.setArticleId(this.id);
            nIm =(Image)nIm.find(con, " 1=1 ORDER BY dateimage DESC LIMIT 1")[0];
            this.image = nIm;
        }
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    
    public String getTitleInUrl() {
        if(this.titleInUrl == null){
            titleInUrl = Util.slugify(this.titre);
        }
        return titleInUrl;
    }
    public void setTitleInUrl(String titleInUrl) {
        this.titleInUrl = titleInUrl;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getResume() {
        return resume;
    }
    public void setResume(String resume) {
        this.resume = resume;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public Integer getCategorieId() {
        return categorieId;
    }
    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
    }
    public String getNomCat() {
        return nomCat;
    }
    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }
    public void init(){
        this.setNomDeTable("v_article");
        this.setPkey("id");
    }
    public V_article(){
        this.init();
    }
    public String getContenu() {
        return contenu;
    }
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    public Integer getAdminId() {
        return adminId;
    }
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
    public Integer getEtat() {
        return etat;
    }
    public void setEtat(Integer etat) {
        this.etat = etat;
    }
    public Timestamp getDatePublication() {
        return datePublication;
    }
    public void setDatePublication(Timestamp datePublication) {
        this.datePublication = datePublication;
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
    public HashMap<String,Object> forModification(int idArticle) throws Exception{
        this.setId(idArticle);
        Connection con =  null;
        HashMap<String,Object> hash = new HashMap<>();
        try{
            con = Util.getConnection();
            V_article article = (V_article)this.find(con)[0];
            article.getImage(con);
            Object [] categorie =  new Categorie().find(con, " Id!="+article.getCategorieId());
            hash.put("article", article);
            hash.put("categorie",categorie);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            if(con != null){
                con.close();
            }
        }
        return hash;
    }
    public Image getImage() {
        return image;
    }
}
