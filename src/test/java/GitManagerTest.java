import it.edoardovignati.versioner.GitManager;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author @EdoardoVignati
 */

public class GitManagerTest {

    static final String absTestPath = "/tmp/versioner/";
    static File file, untracked;

    @BeforeEach
    public void create() {
        String file1 = "File1.txt";
        String file2 = "Untracked.txt";


        //Test if exists dir and delete it
        File directory = new File(absTestPath);
        if (directory.exists()) {
            try {
                FileUtils.deleteDirectory(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        assertEquals(false, new File(absTestPath).exists());

        //Create a new dir and file
        directory.mkdirs();
        file = new File(absTestPath + file1);
        untracked = new File(absTestPath + file2);


        try {
            file.createNewFile();
            untracked.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void registerPathTest() {
        GitManager.registerPath(file.getPath());
        assertEquals(file.getPath(), GitManager.getPath());
    }

    @Test
    public void getCommitsTest() {
        GitManager.registerPath(file.getPath());
        GitManager.addAndCommit("File1 versioned", false);
        assertEquals(1, GitManager.getCommits().size());
    }


    @Test
    public void checkUntrackedTest() {
        GitManager.registerPath(file.getPath());
        GitManager.addAndCommit("A message to manager", false);
        Set<String> untracked = GitManager.getUntracked();
        assertEquals(true, untracked.contains("Untracked.txt"));
        assertEquals(false, untracked.contains("File1.txt"));
    }


    @Test
    public void checkoutTest() {
        GitManager.registerPath(file.getPath());
        GitManager.addAndCommit("First commit",false);
        RevCommit firstCommit = GitManager.getCommits().get(0);

        GitManager.addAndCommit("Second commit",false);
        GitManager.addAndCommit("Third commit",false);
        RevCommit lastCommit = GitManager.getCommits().get(0);


        GitManager.checkout(firstCommit);

        assertEquals(firstCommit.getName(), GitManager.getHead().getName());

        GitManager.checkout(firstCommit);

        GitManager.restore();

        assertEquals(lastCommit.getName(), GitManager.getHead().getName());

    }


}
