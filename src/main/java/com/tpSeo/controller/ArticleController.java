package com.tpSeo.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tpSeo.DAO.Pageable;
import com.tpSeo.DAO.Sort;
import com.tpSeo.Util.Util;
import com.tpSeo.model.Article;
import com.tpSeo.model.Categorie;
import com.tpSeo.model.V_article;

@Controller
public class ArticleController {
//--------------------------BACK OFFICE----------------------------------------------------------------------
    @PostMapping("/articles")
    public String createArticle(HttpSession session,@RequestParam(value = "titre") String titre,@RequestParam(value = "contenu") String contenu,@RequestParam(value = "categorie") int categorie,@RequestParam(value = "resume") String resume,@RequestParam MultipartFile image,Model model) throws Exception{
        if(Util.isLog(session, "idAdmin")){
            Article article = new Article();
            article.setTitre(titre);
            article.setResume(resume);
            article.setContenu(contenu);
            article.setCategorieId(categorie);
            int idArticle = 0;
            try{
             idArticle = article.create(session, image);
            }
            catch(Exception e){
                e.printStackTrace();
                return "redirect:/articleForm?error="+e.getMessage();
            }
            return "redirect:/previsualisation?idArticle="+idArticle;
        }
        else{
            return "redirect:/login";
        }
        
    }
    @GetMapping("/articleForm")
    public String articleForm(HttpSession session,@RequestParam(defaultValue = "") String success,@RequestParam(defaultValue = "") String error,Model model) throws Exception{
        if(Util.isLog(session, "idAdmin")){
            System.out.println(session.getAttribute("idAdmin"));
            model.addAttribute("sess", session.getAttribute("idAdmin"));
            model.addAttribute("categorie", new Categorie().find(null));
            model.addAttribute("success", success);
            model.addAttribute("error", error);
        }
        else{
            return "redirect:/login";
        }
        return "articleForm";
    }
    @GetMapping("/articlesAdmin")
    public String myArticle(HttpSession session,Model model) throws Exception{
        if(Util.isLog(session, "idAdmin")){
            V_article article = new V_article();
            article.setAdminId((Integer)session.getAttribute("idAdmin"));
            model.addAttribute("article",new V_article().find(null));
        }
        else{
            return "redirect:/login";
        }
        return "mesArticles";
    }
    @GetMapping("/articles/*-{idArticle}")
    public String detailArticle(@PathVariable("idArticle") int idArticle,Model model) throws Exception{
        V_article article = new V_article();
        article.setId(idArticle);
        V_article val = (V_article)article.find(null)[0];
        model.addAttribute("resume", val.getResume());
        model.addAttribute("titre", val.getTitre());
        model.addAttribute("article", val);
        return "articleDetails";
    }
    @GetMapping("/")
    public String index(){
        return "redirect:/articleForm";
    }
    @GetMapping("/previsualisation")
    public String previsualisation(HttpSession session,@RequestParam int idArticle,Model model) throws Exception{
        if(Util.isLog(session, "idAdmin")){
            Article article = new Article().previsualisation(idArticle);
            model.addAttribute("article", article);
        }
        else{
            return "redirect:/login";
        }
        return "previsualisation";
    }
    @GetMapping("/modif")
    public String modificationForm(HttpSession session,@RequestParam int idArticle,@RequestParam(defaultValue = "") String error,Model model) throws Exception{
        if(Util.isLog(session, "idAdmin")){
            HashMap<String,Object> informations = new V_article().forModification(idArticle);
            model.addAttribute("article", informations.get("article"));
            model.addAttribute("categorie", informations.get("categorie"));
            model.addAttribute("error", error);        }
        else{
            return "redirect:/login";
        }
        return "modificationArticle";
    }
    @PostMapping("/modif")
    public String modif(HttpSession session,@RequestParam(value = "titre") String titre,@RequestParam(value = "contenu") String contenu,@RequestParam(value = "categorie") int categorie,@RequestParam(value = "resume") String resume,@RequestParam MultipartFile image,@RequestParam int idArticle,Model model) throws Exception{
        if(Util.isLog(session, "idAdmin")){
            Article article = new Article();
            article.setTitre(titre);
            article.setResume(resume);
            article.setContenu(contenu);
            article.setCategorieId(categorie);
            article.setId(idArticle);
            try{
                article.modify(image);
            }
            catch(Exception e){
                e.printStackTrace();
                return "redirect:/modif?error="+e.getMessage();
            }
            return "redirect:/previsualisation?idArticle="+idArticle;
        }
        else{
            return "redirect:/login";
        }
        
    }
    @GetMapping("/confirm")
    public String confirm(@RequestParam int idArticle,HttpSession session) throws Exception{
        if(Util.isLog(session, "idAdmin")){
            Article art = new Article();
            art.setId(idArticle);
            art.setDatePublication(Timestamp.valueOf(LocalDateTime.now()));
            art.setEtat(1);
            art.update("id", null);
            return "redirect:/previsualisation?idArticle="+idArticle;
        }
        else{
            return "redirect:/login";
        }
    }
//---------------------------Front Office--------------------------------------
    @GetMapping("/articles")
    public String listeArticle(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "datePublication") String sort,@RequestParam(defaultValue = "DESC") String direction,Model model) throws Exception{
        Sort order = new Sort(sort,direction);
        Pageable p = new Pageable(page, size, order);
        model.addAttribute("resume", "Le actualités sur l'intelligence artificielle sur ce site");
        model.addAttribute("titre", "Les actualités sur l'IA");
        V_article art = new V_article();
        art.setEtat(1);
        HashMap<String,Object> hash = art.getArticleFront(size, p);
        model.addAttribute("article",hash.get("article"));
        model.addAttribute("pageCount", hash.get("nbPage"));
        model.addAttribute("page", page);
        return "articles";
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String motCle, @RequestParam(defaultValue = "") String debut, @RequestParam(defaultValue = "") String fin, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "datePublication") String sort,@RequestParam(defaultValue = "DESC") String direction,Model model) throws Exception{
        model.addAttribute("page", page);
        model.addAttribute("resume", "Recherchez un article sur l'intelligence artificielle");
        model.addAttribute("titre", "Les actualités sur l'IA");
        model.addAttribute("motCle", motCle);
        model.addAttribute("debut", debut);
        model.addAttribute("fin",fin);
        if(motCle.equals("") && debut.equals("") && fin.equals("")){
            model.addAttribute("article", new ArrayList<>());
            model.addAttribute("pageCount", 1);
        }
        else{
            Sort order = new Sort(sort,direction);
            Pageable p = new Pageable(page, size, order);
            V_article art = new V_article();
            art.setEtat(1);    
            HashMap<String,Object> hash = art.search(motCle, debut, fin, size, p);
            model.addAttribute("article",hash.get("article"));
            model.addAttribute("pageCount", hash.get("pageCount"));
        }
        return"search";
    }
}
