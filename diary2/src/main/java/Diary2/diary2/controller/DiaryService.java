package Diary2.diary2.controller;

import Diary2.diary2.Entry.DiaryEntry;
import Diary2.diary2.Entry.DiaryEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiaryService {
    private final DiaryEntryRepository repository;

    public DiaryService(DiaryEntryRepository repository) {
        this.repository = repository;
    }

    public List<DiaryEntry> getEntriesByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public DiaryEntry addEntry(DiaryEntry entry) {
        return repository.save(entry);
    }


    public DiaryEntry updateEntry(Long id, DiaryEntry newEntry) {
        return repository.findById(id)
                .map(entry -> {
                    entry.setTitle(newEntry.getTitle());
                    entry.setContent(newEntry.getContent());
                    entry.setDate(newEntry.getDate());
                    return repository.save(entry);
                })
                .orElseGet(() -> {
                    newEntry.setId(id);
                    return repository.save(newEntry);
                });
    }

    public void deleteEntry(Long id) {
        repository.deleteById(id);
    }
}

