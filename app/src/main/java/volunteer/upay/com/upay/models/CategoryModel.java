package volunteer.upay.com.upay.models;

/**
 * Created by amanbansal on 11/05/18.
 */

public class CategoryModel {
    private String category;
    private String backgroundUrl;
    private String iconUrl;

    public CategoryModel(String category, String backgroundUrl, String iconUrl) {
        this.category = category;
        this.backgroundUrl = backgroundUrl;
        this.iconUrl = iconUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
