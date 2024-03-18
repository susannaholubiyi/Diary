package data.repository;

import data.model.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntryRepositoryImplementTest {
    private EntryRepository repository;
    @BeforeEach
    public void EntryRepositoryInitializer(){
        repository = new EntryRepositoryImplement();
    }
    @Test
    public void saveOneEntryTest(){
        Entry entry = new Entry("Love life", "Love na scam");
        repository.save(entry);
        assertEquals(1l, repository.count());
    }
    @Test
    public void saveTwoEntry_countIsTwoTest(){
        Entry entry = new Entry("Love life", "Love na scam");
        repository.save(entry);
        Entry entry2 = new Entry("Love life", "Love na scam");
        repository.save(entry2);
        assertEquals(2l, repository.count());
    }
    @Test
    public void saveOneEntry_idIsOneTest(){
        Entry entry = new Entry("Love life", "Love na scam");
        repository.save(entry);
        Entry foundEntry = repository.findById(1);
        assertEquals(1, foundEntry.getId());
    }
    @Test
    public void entryCanBeUpdatedTest(){
        Entry entry = new Entry("Love life", "Love na scam");
        repository.save(entry);
        entry.setTitle("Updated love life");
        entry.setBody("Love na scam, big time");
        repository.updateEntry(entry);
        System.out.println(entry.getTitle());
        System.out.println(entry.getBody());
        assertEquals("Updated love life", entry.getTitle());
        assertEquals("Love na scam, big time",entry.getBody());
    }
    @Test
    public void saveTwoEntries_updateTheSecondTest(){
        Entry entry = new Entry("Love life", "Love na scam");
        repository.save(entry);
        assertEquals("Love life", entry.getTitle());
        assertEquals("Love na scam",entry.getBody());

        Entry entry2 = new Entry("Money", "is life");
        repository.save(entry2);
        entry2.setTitle("Updated Money");
        entry2.setBody("I need to make money so I go soft like today's bread");

        System.out.println(entry2.getTitle());
        System.out.println(entry2.getBody());
        repository.updateEntry(entry2);
        assertEquals("Updated Money", entry2.getTitle());
        assertEquals("I need to make money so I go soft like today's bread",entry2.getBody());
    }
    @Test
    public void saveOneEntry_deleteEntryTest(){
        Entry entry = new Entry("Love life", "Love na scam");
        repository.save(entry);
        repository.delete(1);
        assertNull(repository.findById(1));
    }
    @Test
    public void saveTwoEntry_deleteFirstEntry_findSecondTest(){
        Entry entry = new Entry("Love life", "Love na scam");
        repository.save(entry);

        Entry entry2 = new Entry("Money wahala", "I go make am this yeah aje!");
        repository.save(entry2);
        repository.delete(1);
        assertNull(repository.findById(entry.getId()));
        assertEquals(entry2, repository.findById(entry2.getId()));
    }
    @Test
    public void saveOneEntry_deleteByObjectTest(){
        Entry entry = new Entry("Love life", "Love na scam");
        repository.save(entry);
        repository.delete(entry);
        assertNull(repository.findById(entry.getId()));
    }
    @Test
    public void saveTwoEntries_deleteFirstEntry_findSecondByTest(){
        Entry entry = new Entry("Love life", "Love na scam");
        repository.save(entry);
        repository.delete(entry);
        assertNull(repository.findById(entry.getId()));
    }

}