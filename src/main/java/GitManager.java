import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    private static Git git;
    public static ArrayList<String> paths = new ArrayList<>();

    public static void registerPath(String filePath) {
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
                git = Git.init().setDirectory(file.getParentFile()).call();
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

    public static ArrayList<Integer> getCommits() {
        ArrayList<Integer> commits = new ArrayList<>();
        try {
            if (!new File(new File(paths.get(0)).getParent() + "/.git").exists())
                throw new FileNotFoundException();
            Repository repository = new FileRepository(new File(paths.get(0)).getParent() + "/.git");
            String treeName = "refs/heads/master";

            for (RevCommit commit : git.log().add(repository.resolve(treeName)).call())
                commits.add(commit.getCommitTime());

        } catch (Exception e) {
            if (e instanceof FileNotFoundException)
                logger.info("[" + LocalDateTime.now() + "] No repo found");
        }
        return commits;
    }
}
