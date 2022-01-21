package study.backend.realworld.application.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.domain.Tag;
import study.backend.realworld.application.article.repository.ArticleRepository;
import study.backend.realworld.application.article.repository.TagRepository;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public Article createArticle(User user, ArticleContents contents) {
        // 태그 정보 갱신
        Set<Tag> refreshTags = contents.getTags().stream()
                .map(tag -> tagRepository.findFirstByValue(String.valueOf(tag))
                        .orElse(tag))
                .collect(Collectors.toSet());

        contents.refreshTags(refreshTags);

        // user 정보 찾기
        Optional<User> findUser = userRepository.findById(user.getId());

        // 찾은 user 정보를 article author에 매핑하기

        // 저장
    }

    public Page<Article> findArticleByTag(String tagName, Pageable pageable) {
        return tagRepository.findFirstByValue(tagName)
                .map(tag -> articleRepository.findAllByContentsTags(tag, pageable))
                .orElse(Page.empty());
    }
}
