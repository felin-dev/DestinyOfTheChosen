package game.destinyofthechosen.model.service;

public class CloudinaryImage {

    private String Url;
    private String publicId;

    public String getUrl() {
        return Url;
    }

    public CloudinaryImage setUrl(String url) {
        Url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public CloudinaryImage setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }
}
