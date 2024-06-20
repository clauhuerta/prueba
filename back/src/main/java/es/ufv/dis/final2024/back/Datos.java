package es.ufv.dis.final2024.back;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter @Setter
public class Datos {

    //Atributos para leer el Json, y JsonProperty para que sepa como se llama en el archivo
    @JsonProperty("estimate")
    private Float estimate;
    @JsonProperty("se")
    private Float se;
    @JsonProperty("year")
    private String year;
    @JsonProperty("mscode")
    private String mscode;
    @JsonProperty("lowerCIB")
    private Float lowerCIB;
    @JsonProperty("upperCIB")
    private Float upperCIB;
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("estCode")
    private String estCode;

    // Constructor del DatosChild
    public Datos(Float estimate, Float se, String year, String mscode, Float lowerCIB, Float upperCIB, String _id, String estCode) {
        this.estimate = estimate;
        this.se = se;
        this.year = year;
        this.mscode = mscode;
        this.lowerCIB = lowerCIB;
        this.upperCIB = upperCIB;
        this._id = _id;
        this.estCode = estCode;
    }

    // Getters y Setters
    public Float getEstimate() {
        return estimate;
    }

    public void setEstimate(Float estimate) {
        this.estimate = estimate;
    }

    public Float getSe() {
        return se;
    }

    public void setSe(Float se) {
        this.se = se;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMscode() {
        return mscode;
    }

    public void setMscode(String mscode) {
        this.mscode = mscode;
    }

    public Float getLowerCIB() {
        return lowerCIB;
    }

    public void setLowerCIB(Float lowerCIB) {
        this.lowerCIB = lowerCIB;
    }

    public Float getUpperCIB() {
        return upperCIB;
    }

    public void setUpperCIB(Float upperCIB) {
        this.upperCIB = upperCIB;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEstCode() {
        return estCode;
    }

    public void setEstCode(String estCode) {
        this.estCode = estCode;
    }

    @Override
    public String toString() {
        return "Datos{" +
                "estimate=" + estimate +
                ", se=" + se +
                ", year='" + year + '\'' +
                ", mscode='" + mscode + '\'' +
                ", lowerCIB=" + lowerCIB +
                ", upperCIB=" + upperCIB +
                ", _id='" + _id + '\'' +
                ", estCode='" + estCode + '\'' +
                '}';
    }
}
