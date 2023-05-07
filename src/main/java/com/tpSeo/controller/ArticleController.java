package com.tpSeo.controller;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tpSeo.Util.Util;
import com.tpSeo.model.Article;
import com.tpSeo.model.Categorie;
import com.tpSeo.model.V_article;

@Controller
public class ArticleController {
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
        V_article article = new V_article();
        article.setAdminId((Integer)session.getAttribute("idAdmin"));
        model.addAttribute("article",new V_article().find(null));
        return "mesArticles";
    }
    @GetMapping("/articles")
    public String listeArticle(Model model) throws Exception{
        model.addAttribute("article",new V_article().find(null));
        return "articles";
    }
    @GetMapping("/articles/*-{idArticle}")
    public String detailArticle(@PathVariable("idArticle") int idArticle,Model model) throws Exception{
        V_article article = new V_article();
        article.setId(idArticle);
        model.addAttribute("article", article.find(null)[0]);
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
}
