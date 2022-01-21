package study.backend.realworld.application.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.article.domain.Tag;
import study.backend.realworld.application.article.repository.TagRepository;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    public Set<Tag> findAllTags() {
        return new HashSet<>(tagRepository.findAll());
    }
}
