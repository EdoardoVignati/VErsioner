import it.edoardovignati.versioner.GitManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GitManagerTest {

    private static String absTestPath = "/tmp/versioner/";
    private static File file, untracked;

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
        GitManager.addAndCommit("A message to manager1");
        assertEquals(1, GitManager.getCommits().size());
    }


    @Test
    public void checkUntrackedTest() {
        GitManager.registerPath(file.getPath());
        GitManager.addAndCommit("A message to manager2");
        Set<String> untracked = GitManager.getUntracked();
        assertEquals(true, untracked.contains("Untracked.txt"));
    }
}
