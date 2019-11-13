import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GitManagerTest {

    private static String absTestPath = "/tmp/versioner/";
    private static File file;

    @BeforeAll
    public static void create() {
        String file1 = "File1.txt";

        //Test if exists dir and delete it
        File directory = new File(absTestPath);
        if (directory.exists()) {
            try {
                FileUtils.deleteDirectory(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Create a new dir and file
        directory.mkdirs();
        file = new File(absTestPath + file1);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void registerPathTest() {
        GitManager.registerPath(file.getPath());
        assertEquals(file.getPath(), GitManager.getPaths().get(0));
    }


    @Test
    public void getCommitsTest() {
        GitManager.registerPath(file.getPath());
        GitManager.manage();
        assertEquals(1, GitManager.getCommits().size());
    }
}
