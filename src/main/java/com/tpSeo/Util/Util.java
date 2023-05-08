/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tpSeo.Util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.InputStreamContent;
import com.tpSeo.storage.DriveServiceImpl;



/**
 *
 * @author Tolotra
 */
public class Util {
    public static Connection getConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
       Class.forName("org.postgresql.Driver");
        Connection connectionSql = DriverManager.getConnection("jdbc:postgresql://postgresql-tolotra11.alwaysdata.net/tolotra11_mini_projet_s6", "tolotra11", "P14A_90_Tolotra");   
        // Connection connectionSql = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mini_projet_s6", "postgres", "1234");   
        return connectionSql;
        
    }
    public static String getMd5(String input)
    {
        try {
 
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
 
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
 
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
 
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
 
    public static String[] vectorToArray(Vector<String> v){
        String [] array = new String[v.size()];
        array = v.toArray(array);
        return array;
    }
    ///Check si la connection est null
    public static boolean connectionNull(Connection con){
        boolean check = true;
        if(con != null){
            check = false;
        }
        return check;
    }
    
    public static double differenceHourTwoDate(Date date1,Date date2){
        double val = 0.0;
        double d1 = date1.getTime();
        double d2 = date2.getTime();
        double difference = d1 - d2;
        val = difference/3600000.0;
        return val;
    }
    public static double differenceDaysTwoDate(Date date1,Date date2){
        return Util.differenceHourTwoDate(date1, date2)/24.0;
    }
    public static double differenceHourTwoDate(Timestamp date1,Timestamp date2){
        double val = 0.0;
        double d1 = date1.getTime();
        double d2 = date2.getTime();
        double difference = d1 - d2;
        val = difference/3600000.0;
        return val;
    }
    public static double differenceDaysTwoDate(Timestamp date1,Timestamp date2){
        return Util.differenceHourTwoDate(date1, date2)/24.0;
    }
    public static int jourOuvrable(Date date1,Date date2){
        int intervalle = 0;
        Calendar first = Calendar.getInstance();
        first.setTime(date1);
        Calendar fin = Calendar.getInstance();
        fin.setTime(date2);
        while(first.before(fin)){
            if(first.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY || first.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
                first.add(Calendar.DATE, 1);
            }
            else{
                first.add(Calendar.DATE, 1);                
                intervalle++;
            }
        }
        return intervalle;
    }
    public static int jourOuvrable1(Date date1,Date date2){
        int intervalle = 1;
        Calendar first = Calendar.getInstance();
        first.setTime(date1);
        Calendar fin = Calendar.getInstance();
        fin.setTime(date2);
        while(first.before(fin)){
            if(first.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY || first.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
                first.add(Calendar.DATE, 1);
            }
            else{
                first.add(Calendar.DATE, 1);                
                intervalle++;
            }
        }
        return intervalle;
    }
    public static String slugify(String area){
        String val = area.trim().replaceAll("[^a-zA-Z0-9\\-\\s\\.]", "");
        val = val.replaceAll("[\\-| |\\.]+", "-");
        return val;
    }
    public static boolean isLog(HttpSession session, String sessionName){
        boolean log = true;
        if(session.getAttribute(sessionName) == null){
            log = false;
        }
        return log;
    }
    public static boolean isImage(File file){{
        try{
            BufferedImage image = ImageIO.read(file);
            return (image != null);
        }
        catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }
        
    }
    public static void createImage(MultipartFile file,String dirName) throws Exception {
        String name = "";
        try {
            if (file != null && !file.getOriginalFilename().equalsIgnoreCase("")) {
                Path dir = Paths.get("src/main/resources/static/"+dirName);
                if(!Files.exists(dir)){
                    Files.createDirectories(dir);
                }
                name = StringUtils.cleanPath(file.getOriginalFilename());
                Path path = Paths.get("src/main/resources/static/"+dirName+"/"+name);
                Files.copy(file.getInputStream(),  path, StandardCopyOption.REPLACE_EXISTING);
            }
            
        } catch (Exception e) {
            throw e;
        }
    }
    public static String createImage(MultipartFile file) throws Exception {
        String name = "";
        String val = "";
        try {
            if (file.isEmpty()) {
                throw new Exception("Photos is empty");
            }
            if (file != null && !file.getOriginalFilename().equalsIgnoreCase("")) {
                name = file.getOriginalFilename();
                if (!name.endsWith(".jpeg") && !name.endsWith(".JPEG")
                        && !name.endsWith(".jpg") && !name.endsWith(".JPG")
                        && !name.endsWith(".png") && !name.endsWith(".PNG")
                        && !name.endsWith(".gif") && !name.endsWith(".GIF")
                        && !name.endsWith(".BMP") && !name.endsWith(".bmp")
                        && !name.endsWith(".tiff") && !name.endsWith(".TIFF")
                        && !name.endsWith(".WEBP") && !name.endsWith(".webp")
                        && !name.endsWith(".SVG") && !name.endsWith(".svg")) {
                    throw new Exception("Veuillez saisir une image");
                }
    
                // Upload le fichier dans Google Drive
                DriveServiceImpl imp = new DriveServiceImpl();
                com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
                fileMetadata.setName(file.getOriginalFilename());
                fileMetadata.setParents(Collections.singletonList("1ep0h_jUJzRL30Vh_p1LmgF6vvfrdVM6Q"));
                InputStreamContent mediaContent = new InputStreamContent(file.getContentType(),
                        new BufferedInputStream(file.getInputStream()));
                mediaContent.setLength(file.getSize());
    
                com.google.api.services.drive.model.File uploadedFile = imp.getDriveService().files().create(fileMetadata, mediaContent)
                        .setFields("id, webContentLink, webViewLink").execute();
                val = uploadedFile.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return val;
    }
}

