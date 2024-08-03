package Diary2.diary2.controller;

import Diary2.diary2.Entry.DiaryEntry;
import Diary2.diary2.Entry.DiaryEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    @Autowired
    private DiaryEntryRepository diaryEntryRepository;

    @GetMapping
    public ResponseEntity<List<DiaryEntry>> getAllEntries() {
        List<DiaryEntry> entries = diaryEntryRepository.findAll();
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<DiaryEntry>> getEntriesByDate(@PathVariable("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<DiaryEntry> entries = diaryEntryRepository.findByDate(localDate);
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaryEntry> getEntryById(@PathVariable("id") Long id) {
        DiaryEntry entry = diaryEntryRepository.findById(id).orElseThrow(() -> new RuntimeException("Entry not found"));
        return ResponseEntity.ok(entry);
    }

    @PostMapping
    public ResponseEntity<DiaryEntry> createEntry(@RequestBody DiaryEntry entry) {
        DiaryEntry savedEntry = diaryEntryRepository.save(entry);
        return ResponseEntity.ok(savedEntry);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiaryEntry> updateEntry(@PathVariable("id") Long id, @RequestBody DiaryEntry entry) {
        DiaryEntry existingEntry = diaryEntryRepository.findById(id).orElseThrow(() -> new RuntimeException("Entry not found"));
        existingEntry.setDate(entry.getDate());
        existingEntry.setTitle(entry.getTitle());
        existingEntry.setContent(entry.getContent());
        DiaryEntry updatedEntry = diaryEntryRepository.save(existingEntry);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable("id") Long id) {
        diaryEntryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
