package it.ralucamb.laboratoriomobile.italiancities.models;

public class City {

    private long id;
    private String name;
    private String foreingName;
    private String cadastralCode;
    private String cap;
    private String prefix;
    private String province;
    private String email;
    private String pec;
    private String phoneNumber;
    private String fax;
    private GeoLocation location;

    //constructor with all fields
    public City(String name, String cadastralCode, String cap, String prefix, String province, String email, String pec, String phoneNumber, String fax) {
        this.name = name;
        this.cadastralCode =cadastralCode;
        this.cap = cap;
        this.prefix = prefix;
        this.province = province;
        this.email = email;
        this.pec = pec;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
    }

    //constructor without fields
    public City() {
    }

    //getters and setter for all fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForeingName() {
        return foreingName;
    }

    public void setForeingName(String foreingName) {
        this.foreingName = foreingName;
    }

    public String getCadastralCode() {
        return cadastralCode;
    }

    public void setCadastralCode(String cadastralCode) {
        this.cadastralCode = cadastralCode;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPec() {
        return pec;
    }

    public void setPec(String pec) {
        this.pec = pec;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
