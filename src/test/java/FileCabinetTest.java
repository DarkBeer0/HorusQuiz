import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FileCabinetTest {

    @Test
    public void testFindFolderByName() {
        Folder folder1 = new SimpleFolder("Documents", "MEDIUM");
        Folder folder2 = new SimpleFolder("Pictures", "LARGE");
        Folder folder3 = new SimpleFolder("Music", "SMALL");
        Folder folder4 = new SimpleFolder("Videos", "LARGE");

        List<Folder> subFolders = Arrays.asList(folder3, folder4);
        MultiFolder multiFolder = new CompositeFolder("Media", "LARGE", subFolders);

        List<Folder> rootFolders = Arrays.asList(folder1, folder2, multiFolder);
        FileCabinet fileCabinet = new FileCabinet(rootFolders);

        Optional<Folder> foundFolder = fileCabinet.findFolderByName("Music");
        assertTrue(foundFolder.isPresent());
        assertEquals("Music", foundFolder.get().getName());

        Optional<Folder> notFoundFolder = fileCabinet.findFolderByName("Games");
        assertFalse(notFoundFolder.isPresent());
    }

    @Test
    public void testFindFoldersBySize() {
        Folder folder1 = new SimpleFolder("Documents", "MEDIUM");
        Folder folder2 = new SimpleFolder("Pictures", "LARGE");
        Folder folder3 = new SimpleFolder("Music", "SMALL");
        Folder folder4 = new SimpleFolder("Videos", "LARGE");

        List<Folder> subFolders = Arrays.asList(folder3, folder4);
        MultiFolder multiFolder = new CompositeFolder("Media", "LARGE", subFolders);

        List<Folder> rootFolders = Arrays.asList(folder1, folder2, multiFolder);
        FileCabinet fileCabinet = new FileCabinet(rootFolders);

        List<Folder> largeFolders = fileCabinet.findFoldersBySize("LARGE");
        assertEquals(3, largeFolders.size());
        List<String> expectedNames = Arrays.asList("Pictures", "Videos", "Media");
        for (Folder folder : largeFolders) {
            assertTrue(expectedNames.contains(folder.getName()));
        }
    }

    @Test
    public void testCount() {
        Folder folder1 = new SimpleFolder("Documents", "MEDIUM");
        Folder folder2 = new SimpleFolder("Pictures", "LARGE");
        Folder folder3 = new SimpleFolder("Music", "SMALL");
        Folder folder4 = new SimpleFolder("Videos", "LARGE");

        List<Folder> subSubFolders = Arrays.asList(
                new SimpleFolder("SubMusic1", "SMALL"),
                new SimpleFolder("SubMusic2", "SMALL")
        );
        MultiFolder subMultiFolder = new CompositeFolder("SubMedia", "MEDIUM", subSubFolders);

        List<Folder> subFolders = Arrays.asList(folder3, folder4, subMultiFolder);
        MultiFolder multiFolder = new CompositeFolder("Media", "LARGE", subFolders);

        List<Folder> rootFolders = Arrays.asList(folder1, folder2, multiFolder);
        FileCabinet fileCabinet = new FileCabinet(rootFolders);

        int totalCount = fileCabinet.count();
        assertEquals(8, totalCount);
    }
}