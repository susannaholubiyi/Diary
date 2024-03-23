package data.repository;

import data.model.Diary;

import java.util.List;

public interface DiaryRepository  {
    Diary save (Diary diary);
    List<Diary> findAll();
    Diary findById(String userName);
    long count();
    void delete(String userName);
    void delete(Diary diary);

    void clear();
}
