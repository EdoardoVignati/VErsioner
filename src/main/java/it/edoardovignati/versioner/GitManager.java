package it.edoardovignati.versioner;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.BranchConfig;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public static String path;

    public static void registerPath(String filePath) {
        path = filePath;
        logger.info("[" + LocalDateTime.now() + "] " + filePath);
    }

    public static String getPath() {
        return path;
    }

    public static void addAndCommit(String message) {
        stash();

        logger.info("[" + LocalDateTime.now() + "] Git manager started");

        if (!path.equals("") || path == null) {
            try {
                File file = new File(path);
                git = Git.init().setDirectory(file.getParentFile()).call();
                logger.info("[" + LocalDateTime.now() + "] git add " + path);
                git.add().addFilepattern(file.getName()).call();
                logger.info("[" + LocalDateTime.now() + "] git commit " + path);
                git.commit().setMessage(message).call();

            } catch (Exception e) {
                logger.error("[" + LocalDateTime.now() + "] No file added");
            }
        }


    }

    public static ArrayList<RevCommit> getCommits() {
        ArrayList<RevCommit> commits = new ArrayList();
        try {
            if (!new File(new File(path).getParent() + "/.git").exists())
                throw new FileNotFoundException();
            Repository repository = new FileRepository(new File(path).getParent() + "/.git");
            String treeName = "refs/heads/master";

            for (RevCommit commit : git.log().add(repository.resolve(treeName)).call())
                commits.add(commit);

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

    public static void checkout(RevCommit version) {

        logger.info("[" + LocalDateTime.now() + "] Checking out for " + version.getId());
        try {

            logger.info("[" + LocalDateTime.now() + "] Checking out " +
                    " " + version.getName()
                    + version.getShortMessage());
            git.checkout().setName(version.getName()).call();
        } catch (Exception e) {
            logger.error("[" + LocalDateTime.now() + "] No repo to update");
        }
    }

    public static void restore() {
        try {
            logger.error("[" + LocalDateTime.now() + "] Restoring to master");

            git.checkout().setName("master").call();
        } catch (Exception e) {
            logger.error("[" + LocalDateTime.now() + "] No repo to update");
        }
    }


    public static void stash() {
        try {
            logger.error("[" + LocalDateTime.now() + "] Stashing");
            git.stashCreate().call();
            logger.error("[" + LocalDateTime.now() + "] Cheking out on stash");
            git.checkout().setName("master").call();
            git.stashApply().call();
        } catch (Exception e) {
            logger.error("[" + LocalDateTime.now() + "] No repo to update");
        }
    }

}
