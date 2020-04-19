package it.edoardovignati.versioner;

import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    static Logger logger = Logger.getLogger(Main.class);

    public static Git git;
    public static String path;

    public static void registerPath(String filePath) {
        path = filePath;
        logger.info("[" + LocalDateTime.now() + "] " + filePath);
    }

    public static String getPath() {
        return path;
    }

    public static void addAndCommit(String message) {

        logger.info("[" + LocalDateTime.now() + "] Git manager started");

        if (path != null || !path.equals("")) {
            File file = new File(path);

            try {
                git = Git.open(new File(file.getParent() + "/.git"));
            } catch (Exception e) {
                //e.printStackTrace();
                logger.error("[" + LocalDateTime.now() + "] Repo not found");
                git = null;
            }

            //First commit
            if (git == null || GitManager.getCommits().size() == 0) {
                logger.info("[" + LocalDateTime.now() + "] Creating new git repo");
                try {
                    git = Git.init().setDirectory(file.getParentFile()).call();
                    git.add().addFilepattern(file.getName()).call();
                    git.commit().setMessage(message).call();
                } catch (GitAPIException e) {
                    e.printStackTrace();
                }
            } else {
                //Not first commit
                try {
                    logger.info("[" + LocalDateTime.now() + "] Creating temp branch");
                    git.branchCreate().setName("temp").call();
                    logger.info("[" + LocalDateTime.now() + "] Checking out temp");
                    git.checkout().setName("temp").call();

                    logger.info("[" + LocalDateTime.now() + "] git add " + path);
                    git.add().addFilepattern(file.getName()).call();
                    logger.info("[" + LocalDateTime.now() + "] git commit " + path);
                    git.commit().setMessage(message).call();

                    logger.info("[" + LocalDateTime.now() + "] Merging ours master");
                    git.merge().setStrategy(MergeStrategy.OURS).include(git.getRepository().resolve("master")).call();

                    restore();

                    logger.info("[" + LocalDateTime.now() + "] Merging into master");
                    git.merge().setStrategy(MergeStrategy.OURS).include(git.getRepository().resolve("temp")).call();

                    logger.info("[" + LocalDateTime.now() + "] Deleting branch temp");
                    git.branchDelete().setBranchNames("temp").call();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("[" + LocalDateTime.now() + "] Git error");
                }
            }
        }


    }

    public static ArrayList<RevCommit> getCommits() {
        ArrayList<RevCommit> commits = new ArrayList();
        if (git == null)
            try {
                git = Git.open(new File(new File(path).getParent() + "/.git"));
            } catch (Exception e) {
                //e.printStackTrace();
                logger.error("[" + LocalDateTime.now() + "] Repo not found");
                git = null;
            }

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

    public static RevCommit getHead() {
        try {
            String head = git.getRepository().resolve("HEAD").getName();
            for (RevCommit r : getCommits())
                if (r.getName().equals(head))
                    return r;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
