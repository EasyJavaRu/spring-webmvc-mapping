package ru.easyjava.spring.webmvc.helloboot;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Typical CRUD controller.
 */
@RestController
@RequestMapping("/notes")
public final class NoteController {

    private Map<String, String> notes = new TreeMap<>();

    @GetMapping
    public List<String> list() {
        return notes
                .entrySet()
                .stream()
                .map(entry -> String.format("%s - %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public String read(@PathVariable String id) {
        if (!notes.containsKey(id)) {
            throw new NoteNotFoundException();
        }
        return notes.get(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestParam(required = false) String id, @RequestBody String note) {
        if (StringUtils.isEmpty(id)) {
            id = note.split(" ")[0];
        }
        notes.put(id, note);
        return id;
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody String note) {
        if (!notes.containsKey(id)) {
            throw new NoteNotFoundException();
        }
        notes.put(id, note);
        return this.read(id);
    }

    @RequestMapping(value = "/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        notes.remove(id);
    }
}
