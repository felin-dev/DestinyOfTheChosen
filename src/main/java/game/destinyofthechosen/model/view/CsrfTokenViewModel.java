package game.destinyofthechosen.model.view;

public class CsrfTokenViewModel {

    private final String headerName;
    private final String csrf;

    public CsrfTokenViewModel(String headerName, String csrf) {
        this.headerName = headerName;
        this.csrf = csrf;
    }

    public String getHeaderName() {
        return headerName;
    }

    public String getCsrf() {
        return csrf;
    }
}
