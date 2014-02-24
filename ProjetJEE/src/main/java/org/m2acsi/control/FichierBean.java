package org.m2acsi.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.m2acsi.boundary.FichierEJB;
import org.m2acsi.boundary.TacheEJB;
import org.m2acsi.entities.Fichier;
import org.m2acsi.entities.Tache;
import org.m2acsi.entities.Utilisateur;

@Named("fichierBean")
@RequestScoped
public class FichierBean implements Serializable {

    @Inject
    private FichierEJB fichierEJB;

    @Inject
    private TacheEJB tacheEJB;

    private Fichier fichier = new Fichier();

    private Utilisateur utilisateur = (Utilisateur) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("connexionUtilisateur");

    private static final long serialVersionUID = 9040359120893077422L;

    private Part part;
    private String statusMessage;

    private String fichierDL;

    private Long pid;

    /**
     * Constructeur
     */
    public FichierBean() {
    }

    /**
     * Getters and setters
     */
    public FichierEJB getFichierEJB() {
        return fichierEJB;
    }

    public void setFichierEJB(FichierEJB fichierEJB) {
        this.fichierEJB = fichierEJB;
    }

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier(Fichier fichier) {
        this.fichier = fichier;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public TacheEJB getTacheEJB() {
        return tacheEJB;
    }

    public void setTacheEJB(TacheEJB tacheEJB) {
        this.tacheEJB = tacheEJB;
    }

    public String getFichierDL() {
        return fichierDL;
    }

    public void setFichierDL(String fichierDL) {
        this.fichierDL = fichierDL;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    /**
     * Fonction permettant d'uploader un fichier
     */
    public String uploadFile() throws IOException {
        //Récupération du nom de fichier sélectionné par l'écran sur la page html
        String fileName = getFileName(part);
        System.out.println("***** fileName: " + fileName);

        //Récupération du chemin d'accès où copier le fichier
        String basePath = System.getProperties().get("user.home") + "/NetBeansProjects/ProjetJEE_GestionDeTaches/ProjetJEE/src/documents/";
        File outputFilePath = new File(basePath + fileName);
        System.out.println("chemin du fichier" + basePath);

        //Copie du fichier à l'endroit déterminé
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = part.getInputStream();
            outputStream = new FileOutputStream(outputFilePath);

            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            //Ajout dans la base de données
            fichier.setLienFichier(basePath + fileName);
            fichier.setNomFichier(fileName);
            fichier.setProprietaireFichier(utilisateur);
            Tache tache = tacheEJB.findTache(pid);
            fichier.setTache(tache);

            fichier = fichierEJB.ajouterFichier(fichier, tache);
            statusMessage = "File upload successfull !!";
        } catch (IOException e) {
            e.printStackTrace();
            statusMessage = "File upload failed !!";
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return null;    // return to same page
    }
    
    /**
     * Fonction permettant de télécharger un fichier
     * @param nameFile 
     */
    public void downloadFile(String nameFile) {
        try {
            nameFile = "CM.pdf";
            File file = new File(System.getProperties().get("user.home")+"/NetBeansProjects/ProjetJEE_GestionDeTaches/ProjetJEE/src/documents/" + nameFile);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! "+file.getPath());
            InputStream fis = new FileInputStream(file);
            byte[] buf = new byte[Integer.valueOf((int)file.length())];
            int offset = 0;
            int numRead = 0;
            while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) {
                offset += numRead;
            }
            fis.close();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + nameFile + "");
            response.getOutputStream().write(buf);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            System.out.println("Error : " + ex);
        }
    } 


   
    /**
     * // Extract file name from content-disposition header of file part
     *
     * @param part
     * @return
     */
    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("***** partHeader: " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }
}
