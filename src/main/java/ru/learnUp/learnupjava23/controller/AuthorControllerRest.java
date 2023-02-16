package ru.learnUp.learnupjava23.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.learnUp.learnupjava23.dao.entity.Author;
import ru.learnUp.learnupjava23.dao.filters.AuthorFilter;
import ru.learnUp.learnupjava23.dao.service.AuthorService;
import ru.learnUp.learnupjava23.view.AuthorView;

import java.util.List;

@RestController
@RequestMapping("rest/authors")
public class AuthorControllerRest {

    private final AuthorService authorService;
    private final AuthorView mapper;

    public AuthorControllerRest(AuthorService authorService, AuthorView mapper) {
        this.authorService = authorService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<AuthorView> getAuthors(
            @RequestParam(value = "fullName", required = false) String fullName
    ) {
        return mapper.mapToViewList(authorService.getAuthorBy(new AuthorFilter(fullName)));
    }

    @GetMapping("/{authorId}")
    public AuthorView getAuthor(@PathVariable("authorId") Long authorId) {
        return mapper.mapToView(authorService.getAuthorById(authorId));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public AuthorView createAuthor(@RequestBody AuthorView body) {
        Author author = mapper.mapFromView(body);
        Author createdAuthor = authorService.createAuthor(author);
        return mapper.mapToView(createdAuthor);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{authorId}")
    public AuthorView updateAuthor(
            @PathVariable("authorId") Long authorId,
            @RequestBody AuthorView body
    ) {

        Author author = authorService.getAuthorById(authorId);

        if (!author.getFullName().equals(body.getFullName())) {
            author.setFullName(body.getFullName());
        }

        Author updated = authorService.update(author);

        return mapper.mapToView(updated);

    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{authorId}")
    public Boolean deleteAuthor(@PathVariable("authorId") Long id) {
        return authorService.deleteAuthor(id);
    }
}