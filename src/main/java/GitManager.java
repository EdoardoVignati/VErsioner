import org.apache.log4j.BasicConfigurator;
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

    public static ArrayList<String> paths = new ArrayList<>();

    public static void add(String filePath) {
        paths.add(filePath);
        System.out.println(filePath);
    }

    public static void manage() {
        System.out.println("Managing...");
        Repository repo = null;
        // Create a new repository; the path must exist
        try {
            BasicConfigurator.configure();
            VersionEr ve = new VersionEr();
            ve.build();
            File file = new File(paths.get(0));
            try {
                file.createNewFile();
                Git git = Git.init().setDirectory(file.getParentFile()).call();
                System.out.println(git.status().call().getUntracked().contains(file.getName()));
                git.add().addFilepattern(".").call();
                git.commit().setMessage(LocalDateTime.now().toString()).call();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
