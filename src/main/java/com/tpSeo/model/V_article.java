package com.tpSeo.model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;

import com.tpSeo.DAO.ObjectBDD;
import com.tpSeo.DAO.Pageable;
import com.tpSeo.Util.Slug;
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
    private String nomImage;
    
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
            titleInUrl = Slug.makeSlug(this.titre);
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
    public String getNomImage() {
        return nomImage;
    }
    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }
    public int numberOfPage(int pageSize,Connection con) throws Exception{
        int nbPage =(int)Math.ceil((double)this.count("etat = 1",con)/(double)pageSize);
        return nbPage;
    }
    public HashMap<String,Object> getArticleFront(int pageSize,Pageable page) throws Exception{
        HashMap<String,Object> hash = new HashMap<>();
        Connection con = null;
        try{
            con = Util.getConnection();
            hash.put("article", this.find(page, con));
            hash.put("nbPage",numberOfPage(pageSize, con));
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
    public HashMap<String,Object> search(String motcle, String debut, String fin,int size,Pageable page) throws Exception{
        HashMap<String,Object> hash = new HashMap();
        Connection con  = null;
        String whereClause = " 1=1";
        V_article article = new V_article();
        int count = 0;
        try{
            con = Util.getConnection();
            if(!motcle.equals("")){
                whereClause += " AND (titre ilike '%"+motcle+"%' OR resume ilike '%"+motcle+"%' OR contenu ilike '%"+motcle+"%')";
            }
            if(!debut.equals("")){
                if(!fin.equals("")){
                    whereClause += " AND datePublication BETWEEN '"+debut+" 24:00:00' AND '"+fin+" 24:00:00'";
                }
                else{
                    whereClause += " AND datePublication >='"+debut+" 24:00:00'";
                }
            }
            else{
                if(!fin.equals("")){
                    whereClause += " AND datePublication <='"+fin+" 24:00:00'";
                }
            }
            count = article.count(whereClause, con);
            int pageCount = (int)Math.ceil((double)count/(double) size);
            Object [] result = article.find(whereClause,page, con);
            hash.put("article", result);
            hash.put("pageCount",pageCount); 
        }
        catch(Exception e){
            throw e;
        }
        return hash;
    }
}
