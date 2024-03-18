package data.repository;

import data.model.Diary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiaryRepositoryImplementTest {
    private DiaryRepository repository;
    @BeforeEach
    public void repositoryInitializer(){
        repository = new DiaryRepositoryImplement();
    }
    @Test
    public void diariesCanSaveOneDiaryTest(){
        Diary diary = new Diary("Joy", "1111");
        repository.save(diary);
        assertEquals(1l, repository.count());

    }
    @Test
    public void diariesCanAddTwoTest(){
        Diary diary = new Diary("Macotee","1234");
        repository.save(diary);
        repository.save(diary);
        assertEquals(2l, repository.count());
    }
    @Test
    public void diariesCanFindUserByUserNameTest(){
        Diary diary = new Diary("joy", "1111");
        repository.save(diary);
        Diary foundDiary = repository.findById("joy");
        assertEquals("joy", foundDiary.getUserName());
    }
    @Test
    public void addTwoDiaries_find_bothTest(){
        Diary diary1 = new Diary("joy", "1111");
        Diary diary2 = new Diary("Macotee", "1234");
        repository.save(diary1);
        repository.save(diary2);
        Diary foundDiary1 = repository.findById("joy");
        Diary foundDiary2 = repository.findById("Macotee");
        assertEquals("joy", foundDiary1.getUserName());
        assertEquals("Macotee", foundDiary2.getUserName());
    }
    @Test
    public void addOneDiary_deleteDiaryAndFind_diariesIsEmptyTest(){
        Diary diary1 = new Diary("joy", "1111");
        repository.save(diary1);
        Diary foundDiary1 = repository.findById("joy");
        assertEquals("joy", foundDiary1.getUserName());
        repository.delete("joy");
        assertEquals(0l, repository.count());
    }
    @Test
    public void addTwoDiaries_removeTheFirst_diayTwoIsFoundTest(){
        Diary diary1 = new Diary("joy", "1111");
        Diary diary2 = new Diary("Macotee", "1234");
        repository.save(diary1);
        repository.save(diary2);
        repository.delete("joy");
        Diary foundDiary2 = repository.findById("Macotee");
        assertEquals("Macotee", foundDiary2.getUserName());
        assertEquals(1l, repository.count());
    }
    @Test
    public void addTwoDiaries_findAllTest(){
        Diary diary1 = new Diary("joy", "1111");
        Diary diary2 = new Diary("Macotee", "1234");
        repository.save(diary1);
        repository.save(diary2);
        repository.findAll();
        assertEquals(2l, repository.count());
    }
    @Test
    public void addOneDiary_deleteDiaryByObject_diaryIsDeletedTest(){
        Diary diary1 = new Diary("joy", "1111");
        repository.save(diary1);
        repository.delete(diary1);
        assertEquals(null, repository.findById("joy"));
    }
    @Test
    public void addThreeDiary_deleteDiaryTwoByObject_findDiaryOneAndThreeTest(){
        Diary diary1 = new Diary("joy", "1111");
        Diary diary2 = new Diary("seyi", "1111");
        Diary diary3 = new Diary("seun", "1111");
        repository.save(diary1);
        repository.save(diary2);
        repository.save(diary3);
        repository.delete(diary2);
        assertEquals(diary1, repository.findById("joy"));
        assertEquals(diary3, repository.findById("seun"));
    }


}