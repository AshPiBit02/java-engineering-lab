public class UserService {
    private ace logger = AuditLogger.getInstance();

    void createUser(String name) {
        logger.log("User create: " + name);
    }
}