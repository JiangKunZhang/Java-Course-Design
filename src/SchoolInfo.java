/**
 * @author PineappleSnow
 * @version 1.0
 * @date 2019/12/19 23:10
 */

public class SchoolInfo {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPrincipalname() {
        return principalname;
    }

    public void setPrincipalname(String principalname) {
        this.principalname = principalname;
    }

    private int id;
    private String name;
    private String address;
    private String phone;
    private String postcode;
    private String principalname;

    public SchoolInfo(int id, String name, String address, String phone, String postcode,String principalname) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone=phone;
        this.postcode = postcode;
        this.principalname = principalname;
    }
}

