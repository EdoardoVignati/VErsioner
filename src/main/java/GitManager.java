import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Git Manager
 * Thanks to https://www.codeaffine.com/2015/05/06/jgit-initialize-repository/
 *
 * @author @EdoardoVignati
 */

public class GitManager {

    private static Logger logger = Logger.getLogger(Main.class);

    public static ArrayList<String> paths = new ArrayList<>();

    public static void add(String filePath) {
        paths.add(filePath);
        logger.info("[" + LocalDateTime.now() + "] " + filePath);
    }

    public static ArrayList<String> getPaths() {
        return paths;
    }

    public static void manage() {
        logger.info("[" + LocalDateTime.now() + "] Git manager started");

        try {
            File file = new File(paths.get(0));
            try {
                file.createNewFile();
                Git git = Git.init().setDirectory(file.getParentFile()).call();
                logger.info("[" + LocalDateTime.now() + "] git add");
                git.add().addFilepattern(".").call();
                logger.info("[" + LocalDateTime.now() + "] git commit");
                git.commit().setMessage(LocalDateTime.now().toString()).call();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
