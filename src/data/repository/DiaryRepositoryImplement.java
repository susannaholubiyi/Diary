package data.repository;

import data.model.Diary;

import java.util.ArrayList;
import java.util.List;

public class DiaryRepositoryImplement implements DiaryRepository{
    List<Diary> diaries = new ArrayList<>();
    @Override
    public Diary save(Diary diary) {
        removeExistingDiary(diary);
        diaries.add(diary);
        return diary;
    }

    private void removeExistingDiary(Diary diary) {
        diaries.removeIf(foundDiary -> foundDiary == diary);
    }

    @Override
    public List<Diary> findAll() {
        return diaries;
    }

    @Override
    public Diary findById(String userName) {
        for (Diary diary: diaries){
            if (diary.getUserName().equals(userName)) return diary;
        }
        return null;
    }

    @Override
    public long count() {
        return diaries.size();
    }

    @Override
    public void delete(String userName) {
        diaries.removeIf(diary -> diary.getUserName().equals(userName));
    }

    @Override
    public void delete(Diary diary) {
        for (Diary diaryToBeDeleted : diaries){
            if (diary.equals(diaryToBeDeleted)) diaries.remove(diary);
            break;
        }


    }
}
