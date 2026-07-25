public enum SessionManager implements Logger {
    INSTANCE;

    private String currentUser;

    public void login(String username) {
        currentUser = username;
        System.out.println(username + " logged in.");
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        System.out.println(currentUser + " logged out.");
        currentUser = null;
    }

    @Override
    public void log(String action) {
        System.out.println("[SESSION LOG] " + action);
    }

}
