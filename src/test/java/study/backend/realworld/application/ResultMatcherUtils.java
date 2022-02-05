package study.backend.realworld.application;

import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ResultMatcherUtils {
    private static final Pattern LOCAL_DATE_TIME_PATTERN = Pattern.compile("^\\d{4,}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d.\\d+$");

    private ResultMatcherUtils() {}

    public static ResultMatcher validSingleArticleResponse() {
        return matchAll(
                jsonPath("$.slug").value("how-to-train-your-dragon"),
                jsonPath("$.title").value("How to train your dragon"),
                jsonPath("$.description").value("Ever wonder how?"),
                jsonPath("$.body").value("Very carefully."),
                jsonPath("$.tagList").isArray(),
                jsonPath("$.createdAt", Matchers.matchesPattern(LOCAL_DATE_TIME_PATTERN)),
                jsonPath("$.updatedAt", Matchers.matchesPattern(LOCAL_DATE_TIME_PATTERN)),
                jsonPath("$.favorited").isBoolean(),
                jsonPath("$.favoritesCount").isNumber(),
                validProfileWithPath("author")
        );
    }

    public static ResultMatcher validMultipleArticleResponse(String path) {
        return matchAll(
                jsonPath("$." + path + ".slug").value("how-to-train-your-dragon"),
                jsonPath("$." + path + ".title").value("How to train your dragon"),
                jsonPath("$." + path + ".description").value("Ever wonder how?"),
                jsonPath("$." + path + ".body").value("Very carefully."),
                jsonPath("$." + path + ".tagList").isArray(),
                jsonPath("$." + path + ".createdAt", Matchers.matchesPattern(LOCAL_DATE_TIME_PATTERN)),
                jsonPath("$." + path + ".updatedAt", Matchers.matchesPattern(LOCAL_DATE_TIME_PATTERN)),
                jsonPath("$." + path + ".favorited").isBoolean(),
                jsonPath("$." + path + ".favoritesCount").isNumber(),
                validProfileWithPath(path +".author")

        );
    }

    public static ResultMatcher validSingleComment() {
        return matchAll(

                jsonPath("$.id").isNumber(),
                jsonPath("$.body").value("It takes a Jacobian"),
                jsonPath("$.createdAt", Matchers.matchesPattern(LOCAL_DATE_TIME_PATTERN)),
                jsonPath("$.updatedAt", Matchers.matchesPattern(LOCAL_DATE_TIME_PATTERN)),
                validProfileWithPath("author")
        );
    }

    public static ResultMatcher validMultipleComment(String path) {
        return matchAll(
                jsonPath("$." + path + ".id").isNumber(),
                jsonPath("$." + path + ".body").value("It takes a Jacobian"),
                jsonPath("$." + path + ".createdAt", Matchers.matchesPattern(LOCAL_DATE_TIME_PATTERN)),
                jsonPath("$." + path + ".updatedAt", Matchers.matchesPattern(LOCAL_DATE_TIME_PATTERN)),
                validProfileWithPath(path + ".author")
        );
    }

    public static ResultMatcher validProfileWithPath(String path) {
        return matchAll(
                jsonPath("$." + path).isMap(),
                jsonPath("$." + path + ".username").isString(),
                jsonPath("$." + path + ".bio").hasJsonPath(),
                jsonPath("$." + path + ".image").hasJsonPath(),
                jsonPath("$." + path + ".following").isBoolean()
        );
    }
}
