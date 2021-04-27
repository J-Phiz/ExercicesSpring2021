package org.jps.jpsave.entity;

public class GouvGeo {
    private String nom;
    private String code;
    private int codeDepartement;
    private int codeRegion;
    private int population;
    private int[] codesPostaux;

    public GouvGeo() {

    }

    public GouvGeo(String nom, String code, int codeDepartement, int codeRegion, int population) {
        this.nom = nom;
        this.code = code;
        this.codeDepartement = codeDepartement;
        this.codeRegion = codeRegion;
        this.population = population;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCodeDepartement() {
        return codeDepartement;
    }

    public void setCodeDepartement(int codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public int getCodeRegion() {
        return codeRegion;
    }

    public void setCodeRegion(int codeRegion) {
        this.codeRegion = codeRegion;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int[] getCodesPostaux() {
        return codesPostaux;
    }

    public void setCodesPostaux(int[] codesPostaux) {
        this.codesPostaux = codesPostaux;
    }

}
