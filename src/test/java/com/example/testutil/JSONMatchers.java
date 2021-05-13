package com.example.testutil;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareResult;

import static com.example.testutil.TestUtil.readFileContent;
import static org.junit.Assert.assertEquals;
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT;

@Slf4j
public class JSONMatchers {
    private static class JSONMatcher extends BaseMatcher<String> {
        private static final int INDENT_SPACES = 1;
        private final String expectedJSON;

        JSONMatcher(String expectedJSON) {
            this.expectedJSON = expectedJSON;
        }

        @Override
        @SneakyThrows
        public boolean matches(Object actualResult) {
            String actualJSON = (String) actualResult;
            JSONCompareResult result = JSONCompare.compareJSON(expectedJSON, actualJSON, STRICT);
            if (result.failed()) {
                assertEquals(result.getMessage(), expectedJSON, pretty(actualJSON));
            }
            return true; // never return false as it won't reach the return statement if exception happens
        }

        private String pretty(String actualJSON) throws JSONException {
            if (isObject(actualJSON)) {
                return new JSONObject(actualJSON).toString(INDENT_SPACES);
            } else if (isArray(actualJSON)) {
                return new JSONArray(actualJSON).toString(INDENT_SPACES);
            } else {
                throw new RuntimeException("Unable to check if JSON object or array: " + actualJSON);
            }
        }

        private boolean isArray(String actualJSON) {
            return actualJSON.charAt(0) == '[';
        }

        private boolean isObject(String actualJSON) {
            return actualJSON.charAt(0) == '{';
        }

        @Override
        public void describeTo(Description description) {
        }
    }

    /**
     * @param classpathFile to use its content as expected json.
     * @return matcher to assert json regardless formatting or order of fields.
     */
    public static Matcher<String> matchesJSONInFile(String classpathFile) {
        String fileContent = readFileContent(classpathFile);
        return new JSONMatcher(fileContent);
    }
}
