public class UserService {
    private Logger logger = AuditLogger.getInstance();

    void createUser(String name) {
        logger.log("User create: " + name);
    }
}