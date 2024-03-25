package africa.semicolon.DiaryThatRemembers.service;

import africa.semicolon.DiaryThatRemembers.data.model.Entry;
import africa.semicolon.DiaryThatRemembers.exceptions.NoEntryException;
import africa.semicolon.DiaryThatRemembers.repository.EntryRepository;
import africa.semicolon.DiaryThatRemembers.dtos.request.DeleteEntryRequest;
import africa.semicolon.DiaryThatRemembers.dtos.request.UpdateEntryRequest;
import africa.semicolon.DiaryThatRemembers.exceptions.InvalidId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class EntryServicesImpl implements EntryServices {
@Autowired
    private   EntryRepository entryRepository;

    @Override
    public void save(Entry entry) {
        entryRepository.save(entry);
    }

    @Override
    public List<Entry> findAllEntriesBy(String author) {
        return entryRepository.findByAuthor(author);
    }
    @Override
    public void updateEntry(UpdateEntryRequest updateEntryRequest) {
        validateId(updateEntryRequest.getId());
        Entry entry = new Entry();

        entry.setTitle(updateEntryRequest.getTitle());
        entry.setBody(updateEntryRequest.getBody());
        entry.setAuthor(updateEntryRequest.getAuthor());
        entry.setId(updateEntryRequest.getId());

        entryRepository.save(entry);
    }

    @Override
    public void deleteEntry(DeleteEntryRequest deleteEntryRequest) {
        validateId(deleteEntryRequest.getId());
        entryRepository.deleteById(deleteEntryRequest.getId());
    }

    @Override
    public Entry findBy(String id) {
        Optional<Entry> entry = entryRepository.findById(id);
        if (entry.isEmpty()) throw new NoEntryException(String.format("Entry with ID %s not found", id));
        return entry.get();
    }

    private void validateId(String id) {
        Optional<Entry> entry = entryRepository.findById(id);
        if (entry.isEmpty()) {
            throw new InvalidId(String.format("Entry with ID %s not found", id));
        }

    }
}
