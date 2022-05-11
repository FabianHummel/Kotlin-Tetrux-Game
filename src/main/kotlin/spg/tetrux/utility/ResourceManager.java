package spg.tetrux.utility;

import spg.tetrux.Main;
import java.net.URL;

public class ResourceManager {

    private static final Logger logger = Logger.Companion.getLogger("Resource-Manager");

    /**
     * Loads a resource from the given path.
     * @param input The path to the resource. Can start with / or not.
     */
    public static URL getRes(String input) {
        var path = input;
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        System.out.println("Loading resource: " + path);
        var resource = Main.class.getResource(path);

        if (resource == null) {
            logger.fatal("Resource not found, exiting: " + path, true, 1);
            return null; // Won't be executed, because the program will be terminated
        } else {
            logger.log("Resource loaded: " + path, true);
            return resource;
        }
    }
}