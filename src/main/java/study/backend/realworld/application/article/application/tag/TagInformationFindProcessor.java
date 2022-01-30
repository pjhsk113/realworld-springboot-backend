package study.backend.realworld.application.article.application.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.domain.Tag;
import study.backend.realworld.application.article.repository.TagRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TagInformationFindProcessor {

    private final TagRepository tagRepository;

    public Set<Tag> findAllTags() {
        return new HashSet<>(tagRepository.findAll());
    }

    public Optional<Tag> findTagValue(String tagName) {
        return tagRepository.findFirstByValue(tagName);
    }

    public Set<Tag> findMappedTags(ArticleContents contents) {
        return contents.getTags().stream()
                .map(tag -> tagRepository.findFirstByValue(String.valueOf(tag))
                        .orElse(tag))
                .collect(Collectors.toSet());
    }
}
