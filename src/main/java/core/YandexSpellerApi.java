package core;

import beans.YandexSpellerAnswer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.Language;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static core.YandexSpellerConstants.*;

public class YandexSpellerApi {

    //builder pattern
    private YandexSpellerApi() {
    }

    private HashMap<String, String> params = new HashMap<>();
    //for checkTexts
    private HashMap<String, List<String>> texts = new HashMap<>();

    public static class ApiBuilder {
        YandexSpellerApi spellerApi;

        private ApiBuilder(YandexSpellerApi gcApi) {
            spellerApi = gcApi;
        }

        public ApiBuilder text(String text) {
            spellerApi.params.put(PARAM_TEXT, text);
            return this;
        }

        //for checkTexts
        public ApiBuilder texts(String... texts) {
            spellerApi.texts.put(PARAM_TEXT, Arrays.asList(texts));
            return this;
        }


        public ApiBuilder options(String options) {
            spellerApi.params.put(PARAM_OPTIONS, options);
            return this;
        }

        public ApiBuilder language(Language language) {
            spellerApi.params.put(PARAM_LANG, language.langCode());
            return this;
        }

        public Response callApi() {
            return RestAssured.with()
                    .queryParams(spellerApi.params)
                    .log().all()
                    .get(YANDEX_SPELLER_API_URI).prettyPeek();
        }

        public Response callApiTexts() {
            return RestAssured.with()
                    .queryParams(spellerApi.params)
                    .queryParams(spellerApi.texts)
                    .log().all()
                    .get(YANDEX_SPELLER_API_URI_TEXTS).prettyPeek();
        }
    }

    public static ApiBuilder with() {
        YandexSpellerApi api = new YandexSpellerApi();
        return new ApiBuilder(api);
    }


    public static List<List<YandexSpellerAnswer>> getYandexSpellerAnswers(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<Collection<List<YandexSpellerAnswer>>>() {
        }.getType());
    }

}
