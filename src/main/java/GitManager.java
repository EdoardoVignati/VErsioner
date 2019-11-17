import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Git Manager
 * Thanks to https://www.codeaffine.com/2015/05/06/jgit-initialize-repository/
 *
 * @author @EdoardoVignati
 */

public class GitManager {

    private static Logger logger = Logger.getLogger(Main.class);

    private static Git git;
    public static ArrayList<String> paths = new ArrayList<>();

    public static void registerPath(String filePath) {
        paths.add(filePath);
        logger.info("[" + LocalDateTime.now() + "] " + filePath);
    }

    public static ArrayList<String> getPaths() {
        return paths;
    }

    public static void manage(String message) {
        logger.info("[" + LocalDateTime.now() + "] Git manager started");

        if (paths.size() > 0) {
            try {
                File file = new File(paths.get(0));
                file.createNewFile();
                git = Git.init().setDirectory(file.getParentFile()).call();
                logger.info("[" + LocalDateTime.now() + "] git add");
                git.add().addFilepattern(paths.get(0)).call();
                logger.info("[" + LocalDateTime.now() + "] git commit");
                git.commit().setMessage(message).call();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public static HashMap<Integer, String> getCommits() {
        HashMap<Integer, String> commits = new HashMap();
        try {
            if (!new File(new File(paths.get(0)).getParent() + "/.git").exists())
                throw new FileNotFoundException();
            Repository repository = new FileRepository(new File(paths.get(0)).getParent() + "/.git");
            String treeName = "refs/heads/master";

            for (RevCommit commit : git.log().add(repository.resolve(treeName)).call())
                commits.put(commit.getCommitTime(), commit.getShortMessage());

        } catch (Exception e) {
            if (e instanceof FileNotFoundException)
                logger.info("[" + LocalDateTime.now() + "] No repo found");
        }
        return commits;
    }
}
