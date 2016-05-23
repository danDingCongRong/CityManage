package cn.edu.bupt.citymanageapp.model;

/**
 * Created by chenjun14 on 16/5/17.
 */
public class Report {

    private int imageId;

    private String url;

    private String name;

    public Report() {
        this.imageId = 0;
        this.url = "";
        this.name = "";
    }

    public Report(int imageId, String name, String url) {
        this.imageId = imageId;
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Report{" +
                "imageId=" + imageId +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
