package com.tpSeo.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import com.tpSeo.DAO.ObjectBDD;
import com.tpSeo.DAO.Pageable;
import com.tpSeo.Util.Util;

public class Article extends ObjectBDD{
    private Integer id;
    private String resume;
    private String titre;
    private String contenu;
    private Integer categorieId;
    private Integer adminId;
    private Integer etat;
    private Timestamp datePublication;
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
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getResume() {
        return resume;
    }
    public void setResume(String resume) throws Exception {
        if(resume.equals("")){
            throw new Exception("La case resume doit etre remplie");
        }
        this.resume = resume;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) throws Exception {
        if(titre.equals("")){
            throw new Exception("La case titre doit etre remplie");
        }
        this.titre = titre;
    }
    public String getContenu() {
        return contenu;
    }
    public void setContenu(String contenu) throws Exception {
        if(contenu.equals("")){
            throw new Exception("La case contenue doit etre remplie");
        }
        this.contenu = contenu;
    }
    public Integer getCategorieId() {
        return categorieId;
    }
    public void setCategorieId(int categorieId) throws Exception {
        if(categorieId == 0){
            throw new Exception("Veuillez choisir une categorie");
        }
        this.categorieId = categorieId;
    }
       
    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
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
    public void init(){
        this.setNomDeTable("article");
        this.setPkey("id");
    }
    public Article() {
        this.init();
    }   
    public Article previsualisation(int idArticle) throws Exception{
        Article val = null;
        Connection con = null;
        try{
            con = Util.getConnection();
            this.setId(idArticle);
            Image img = this.getImage(con);
            val = (Article)this.find(con)[0];
            val.setImage(img);
        }
        catch(Exception e){
            throw e;
        }
        finally{
            if(con != null){
                con.close();
            }
        }
        return val;
    }
    public Image getImage() {
        return image;
    } 
    public int create(HttpSession session,MultipartFile image) throws Exception{
        Connection con = null;
        int id = 0;
        this.setDatePublication(null);
        this.setEtat(0);
        this.setAdminId((Integer)session.getAttribute("idAdmin"));
        try{
            con = Util.getConnection();
            con.setAutoCommit(false);
            id = this.insertReturningId(con);
            String filename = image.getOriginalFilename();
            System.out.println("image:"+filename);
            System.out.println("idA:"+this.getAdminId());
            Image im = new Image();
            im.setArticleId(id);
        
           // if (Util.isImage(new File(filename))) {
                try {
                    im.setImage(Util.createImage(image));
                    im.setDateImage(Timestamp.valueOf(LocalDateTime.now()));
                    System.out.println("ato");
                    im.insert(con);
                } catch (IOException e) {
                    e.printStackTrace();
                    con.rollback();
                    throw e;
                }
           /*  } else {
                con.rollback();
                throw new Exception("Votre fichier n'est pas une image");
            }*/
            con.commit();
        }
        catch(Exception e){
            con.rollback();
            throw e;
        }
        finally{
            if(con != null){
                con.close();
            }
        }
        return id;
    }
    public void modify(MultipartFile image) throws Exception{
        Connection con = null;
        this.setDatePublication(null);
        this.setEtat(0);
        try{
            con = Util.getConnection();
            con.setAutoCommit(false);
            this.update("id", con);
            if(image != null && !image.getOriginalFilename().equals("")){
                String filename = image.getOriginalFilename();
                System.out.println("image:"+filename);
                System.out.println("idA:"+this.getAdminId());
                Image im = new Image();
                im.setArticleId(this.id);
               // if (Util.isImage(new File(filename))) {
                    try {
                        im.setImage(Util.createImage(image));
                        im.setDateImage(Timestamp.valueOf(LocalDateTime.now()));
                        im.insert(con);
                    } catch (IOException e) {
                        e.printStackTrace();
                        con.rollback();
                        throw e;
                    }
               /*  } else {
                    con.rollback();
                    throw new Exception("Votre fichier n'est pas une image");
                }*/
            }
            con.commit();
        }
        catch(Exception e){
            con.rollback();
            throw e;
        }
        finally{
            if(con != null){
                con.close();
            }
        }
    }
    
}
