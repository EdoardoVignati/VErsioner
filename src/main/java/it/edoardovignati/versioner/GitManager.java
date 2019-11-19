package it.edoardovignati.versioner;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;

/**
 * Git Manager
 * Thanks to https://www.codeaffine.com/2015/05/06/jgit-initialize-repository/
 *
 * @author @EdoardoVignati
 */

public class GitManager {

    private static Logger logger = Logger.getLogger(Main.class);

    private static Git git;
    public static String path = "";

    public static void registerPath(String filePath) {
        path = filePath;
        logger.info("[" + LocalDateTime.now() + "] " + filePath);
    }

    public static String getPath() {
        return path;
    }

    public static void manage(String message) {
        logger.info("[" + LocalDateTime.now() + "] Git manager started");

        if (!path.equals("")) {
            try {
                File file = new File(path);
                git = Git.init().setDirectory(file.getParentFile()).call();
                logger.info("[" + LocalDateTime.now() + "] git add " + path);
                git.add().addFilepattern(file.getName()).call();
                logger.info("[" + LocalDateTime.now() + "] git commit " + path);
                git.commit().setMessage(message).call();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public static HashMap<Integer, String> getCommits() {
        HashMap<Integer, String> commits = new HashMap();
        try {
            if (!new File(new File(path).getParent() + "/.git").exists())
                throw new FileNotFoundException();
            Repository repository = new FileRepository(new File(path).getParent() + "/.git");
            String treeName = "refs/heads/master";

            for (RevCommit commit : git.log().add(repository.resolve(treeName)).call())
                commits.put(commit.getCommitTime(), commit.getShortMessage());

        } catch (Exception e) {
            if (e instanceof FileNotFoundException)
                logger.info("[" + LocalDateTime.now() + "] No repo found");
        }
        return commits;
    }

    public static Set<String> getUntracked() {
        try {
            logger.info("[" + LocalDateTime.now() + "] Getting all untracked files");
            return git.status().call().getUntracked();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return null;
    }
}
