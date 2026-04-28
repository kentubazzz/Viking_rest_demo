package ru.mephi.vikingdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/vikings")
public class VikingController {
    private final VikingService vikingService;
    private final VikingListener vikingListener;

    public VikingController(VikingService vikingService, VikingListener vikingListener) {
        this.vikingService = vikingService;
        this.vikingListener = vikingListener;
    }

    @GetMapping
    public List<Viking> getAllVikings() {
        return vikingService.findAll();
    }

    @GetMapping("/{id}")
    public Viking getVikingById(@PathVariable UUID id) {
        return vikingService.findById(id);
    }

    @GetMapping("/test")
    public List<String> test() {
        return List.of("Ragnar", "Bjorn");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viking addViking(@RequestBody Viking viking) {
        return vikingService.addViking(viking);
    }

    @PostMapping("/post")
    public void addRandomVikingViaGuiListener() {
        vikingListener.testAdd();
    }

    @PutMapping("/{id}")
    public Viking updateViking(@PathVariable UUID id, @RequestBody Viking viking) {
        return vikingService.updateViking(id, viking);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteViking(@PathVariable UUID id) {
        vikingService.deleteViking(id);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(NoSuchElementException exception) {
        return Map.of("error", exception.getMessage());
    }
}
