package data.repository;

import data.model.Diary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiaryRepositoryImplementTest {
    private DiaryRepository repository;
    @BeforeEach
    public void repositoryInitializer(){
        repository = new DiaryRepositoryImplement();
    }
    @AfterEach
    public void repositoryTearDown(){
        repository.clearDiary();
    }
    @Test
    public void diariesCanSaveOneDiaryTest(){
        Diary diary = new Diary();
        diary.setUserName("username");
        diary.setPassword("password");
        repository.save(diary);
        assertEquals(1l, repository.count());

    }
    @Test
    public void diariesCanAddTwoTest(){
        Diary diary = new Diary();
        diary.setUserName("username");
        diary.setPassword("password");
        repository.save(diary);
        Diary diary2 = new Diary();
        diary2.setUserName("second username");
        diary2.setPassword("password");
        repository.save(diary2);
        assertEquals(2l, repository.count());
    }
    @Test
    public void diariesCanFindUserByUserNameTest(){
        Diary diary = new Diary();
        diary.setUserName("username");
        diary.setPassword("password");
        repository.save(diary);
        Diary foundDiary = repository.findById("username");
        assertEquals("username", foundDiary.getUserName());
    }
    @Test
    public void addTwoDiaries_find_bothTest(){
        Diary diary1 = new Diary();
        diary1.setUserName("joy");
        diary1.setPassword("password");
        Diary diary2 = new Diary();
        diary2.setUserName("Macotee");
        diary2.setPassword("password");
        repository.save(diary1);
        repository.save(diary2);
        Diary foundDiary1 = repository.findById("joy");
        Diary foundDiary2 = repository.findById("Macotee");
        assertEquals("joy", foundDiary1.getUserName());
        assertEquals("Macotee", foundDiary2.getUserName());
    }
    @Test
    public void addOneDiary_deleteDiaryAndFind_diariesIsEmptyTest(){
        Diary diary1 = new Diary();
        diary1.setUserName("username");
        diary1.setPassword("password");
        repository.save(diary1);
        Diary foundDiary1 = repository.findById("username");
        assertEquals("username", foundDiary1.getUserName());
        repository.delete("username");
        assertEquals(0l, repository.count());
    }
    @Test
    public void addTwoDiaries_removeTheFirst_diayTwoIsFoundTest(){
        Diary diary1 = new Diary();
        diary1.setUserName("joy");
        diary1.setPassword("password");
        Diary diary2 = new Diary();
        diary2.setUserName("Macotee");
        diary2.setPassword("password");
        repository.save(diary1);
        repository.save(diary2);
        repository.delete("joy");
        Diary foundDiary2 = repository.findById("Macotee");
        assertEquals("Macotee", foundDiary2.getUserName());
        assertEquals(1l, repository.count());
    }
    @Test
    public void addTwoDiaries_findAllTest(){
        Diary diary1 = new Diary();
        diary1.setUserName("joy");
        diary1.setPassword("password");
        Diary diary2 = new Diary();
        diary2.setUserName("Macotee");
        diary2.setPassword("password");
        repository.save(diary1);
        repository.save(diary2);
        repository.findAll();
        assertEquals(2l, repository.count());
    }
    @Test
    public void addOneDiary_deleteDiaryByObject_diaryIsDeletedTest(){
        Diary diary1 = new Diary();
        diary1.setUserName("joy");
        diary1.setPassword("password");
        repository.save(diary1);
        repository.delete(diary1);
        assertEquals(null, repository.findById("joy"));
    }
    @Test
    public void addThreeDiary_deleteDiaryTwoByObject_findDiaryOneAndThreeTest(){
        Diary diary1 = new Diary();
        diary1.setUserName("joy");
        diary1.setPassword("password");
        Diary diary2 = new Diary();
        diary2.setUserName("Macotee");
        diary2.setPassword("password");
        Diary diary3 = new Diary();
        diary3.setUserName("seun");
        diary3.setPassword("1111");
        repository.save(diary1);
        repository.save(diary2);
        repository.save(diary3);
        repository.delete(diary2);
        assertEquals(diary1, repository.findById("joy"));
        assertEquals(diary3, repository.findById("seun"));
    }


}