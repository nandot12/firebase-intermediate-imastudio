package id.co.imastudio.firebaseappandroid;

/**
 * Created by nandoseptianhusni on 4/11/18.
 */

public class Makanan {



    public Makanan() {
    }

    String name;
    String harga;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getRestoran() {
        return restoran;
    }

    public void setRestoran(String restoran) {
        this.restoran = restoran;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String restoran;
    String key  ;
}
