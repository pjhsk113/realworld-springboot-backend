package study.backend.realworld.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import study.backend.realworld.application.article.domain.Article;
import study.backend.realworld.application.article.domain.ArticleContents;
import study.backend.realworld.application.article.domain.ArticleTitle;
import study.backend.realworld.application.article.domain.Tag;
import study.backend.realworld.application.article.repository.ArticleRepository;
import study.backend.realworld.application.user.domain.Email;
import study.backend.realworld.application.user.domain.Password;
import study.backend.realworld.application.user.domain.User;
import study.backend.realworld.application.user.domain.UserName;
import study.backend.realworld.application.user.repository.UserRepository;
import study.backend.realworld.infra.security.jwt.TokenGenerator;

import javax.transaction.Transactional;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public abstract class IntegrationTest {
    protected static final String AUTHORIZATION = "Authorization";

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ArticleRepository articleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenGenerator tokenGenerator;

    protected String setUpToken;

    protected User setUpUser;

    protected Article setUpArticle;

    @BeforeEach
    protected void setUp() {
        setUpUser = userRepository.save(
                User.of(
                        new Email("user@email.com"),
                        new UserName("user"),
                        Password.of("password", passwordEncoder)
                )
        );

        setUpToken = "Token " + tokenGenerator.generateToken(setUpUser.getEmail());

        ArticleContents contents = new ArticleContents(
                ArticleTitle.of("How to train your dragon"),
                "Ever wonder how?",
                "Very carefully.",
                Set.of(new Tag("dragons"), new Tag("training"))
        );

        Article article = new Article(setUpUser, contents);
        setUpArticle = articleRepository.save(article);
    }
}
