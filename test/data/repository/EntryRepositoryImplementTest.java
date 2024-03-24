package data.repository;

import data.model.Entry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntryRepositoryImplementTest {
    private EntryRepository repository;
    @BeforeEach
    public void entryRepositoryInitializer(){
        repository = new EntryRepositoryImplement();
    }
    @AfterEach
    public void entryRepositoryTearDown(){
        repository.clear();
    }
    @Test
    public void saveOneEntryTest(){
        Entry entry = new Entry();
        entry.setTitle("title");
        entry.setBody("body");
        repository.save(entry);
        assertEquals(1l, repository.count());
    }
    @Test
    public void saveTwoEntry_countIsTwoTest(){
        Entry entry = new Entry();
        entry.setTitle("title");
        entry.setBody("body");
        repository.save(entry);
        Entry entry2 = new Entry();
        entry2.setTitle("second title");
        entry2.setBody("second body");
        repository.save(entry2);
        assertEquals(2l, repository.count());
    }
    @Test
    public void saveOneEntry_idIsOneTest(){
        Entry entry = new Entry();
        entry.setTitle("title");
        entry.setBody("body");
        repository.save(entry);
        Entry foundEntry = repository.findById(1);
        assertEquals(1, foundEntry.getId());
    }
    @Test
    public void entryCanBeUpdatedTest(){
        Entry entry = new Entry();
        entry.setTitle("title");
        entry.setBody("body");
        repository.save(entry);
        entry.setTitle("Updated title");
        entry.setBody("Updated body");
        repository.updateEntry(entry);
        assertEquals("Updated title", entry.getTitle());
        assertEquals("Updated body",entry.getBody());
    }
    @Test
    public void saveTwoEntries_updateTheSecondTest(){
        Entry entry = new Entry();
        entry.setTitle("title");
        entry.setBody("body");
        repository.save(entry);
        assertEquals("title", entry.getTitle());
        assertEquals("body",entry.getBody());

        Entry entry2 = new Entry();
        entry2.setTitle("second title");
        entry2.setBody("second body");

        repository.save(entry2);
        entry2.setTitle("Updated second title");
        entry2.setBody("Updated second body");

        System.out.println(entry2.getTitle());
        System.out.println(entry2.getBody());
        repository.updateEntry(entry2);
        assertEquals("Updated second title", entry2.getTitle());
        assertEquals("Updated second body",entry2.getBody());
    }
    @Test
    public void saveOneEntry_deleteEntryTest(){
        Entry entry = new Entry();
        entry.setTitle("title");
        entry.setBody("body");
        repository.save(entry);
        repository.delete(1);
        assertNull(repository.findById(1));
    }
    @Test
    public void saveTwoEntry_deleteFirstEntry_findSecondTest(){
        Entry entry = new Entry();
        entry.setTitle("title");
        entry.setBody("body");
        repository.save(entry);

        Entry entry2 = new Entry();
        entry2.setTitle("second title");
        entry2.setBody("second body");
        repository.save(entry2);
        repository.delete(1);
        assertNull(repository.findById(entry.getId()));
        assertEquals(entry2, repository.findById(entry2.getId()));
    }
    @Test
    public void saveOneEntry_deleteByObjectTest(){
        Entry entry = new Entry();
        entry.setTitle("title");
        entry.setBody("body");
        repository.save(entry);
        repository.delete(entry);
        assertNull(repository.findById(entry.getId()));
    }
    @Test
    public void saveTwoEntries_deleteFirstEntry_findSecondByTest(){
        Entry entry = new Entry();
        entry.setTitle("title");
        entry.setBody("body");
        repository.save(entry);
        repository.delete(entry);
        assertNull(repository.findById(entry.getId()));
    }

}