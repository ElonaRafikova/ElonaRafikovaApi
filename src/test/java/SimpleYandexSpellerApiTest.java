import beans.YandexSpellerAnswer;
import core.YandexSpellerApi;
import org.junit.Test;

import java.util.List;

import static enums.Language.*;
import static enums.Options.*;
import static enums.Words.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;


public class SimpleYandexSpellerApiTest {

    private static List<List<YandexSpellerAnswer>> answers;

    @Test
    public void checkRussianWords() {
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(RU_BICYCLE.wrongVer(), RU_HELLO.wrongVer())
                        .language(RU)
                        .callApiTexts());

        assertThat(answers,hasSize(2));

        assertThat(answers.get(0),not(empty()));
        assertThat(answers.get(0).get(0).s, hasItem(RU_BICYCLE.corrVer()));

        assertThat(answers.get(1),not(empty()));
        assertThat(answers.get(1).get(0).s, hasItem(RU_HELLO.corrVer()));
    }

    @Test
    public void checkEnglishWords() {
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(BROTHER.wrongVer(), MOTHER.wrongVer())
                        .language(EN)
                        .callApiTexts());

        assertThat(answers,hasSize(2));

        assertThat(answers.get(0),not(empty()));
        assertThat(answers.get(0).get(0).s, hasItem(BROTHER.corrVer()));

        assertThat(answers.get(1),not(empty()));
        assertThat(answers.get(1).get(0).s, hasItem(MOTHER.corrVer()));
    }

    @Test
    public void checkUkrainianWords() {
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi.with().
                        texts(UK_BOY.wrongVer(), UK_GIRL.wrongVer())
                        .language(UK)
                        .callApiTexts());

        assertThat(answers,hasSize(2));

        assertThat(answers.get(0),not(empty()));
        assertThat(answers.get(0).get(0).s, hasItem(UK_BOY.corrVer()));

        assertThat(answers.get(1),not(empty()));
        assertThat(answers.get(1).get(0).s, hasItem(UK_GIRL.corrVer()));

        //should fail as default "ru,en" but passed
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(UK_BOY.wrongVer(), UK_GIRL.wrongVer())
                        .callApiTexts());
        assertThat(answers,hasSize(2));

        assertThat(answers.get(0),not(empty()));
        assertThat(answers.get(0).get(0).s, not(hasItem(UK_BOY.corrVer())));

        assertThat(answers.get(1),not(empty()));
        assertThat(answers.get(1).get(0).s, not(hasItem(UK_GIRL.corrVer())));
    }

    @Test
    public void checkDigitsOptions() {

        //no option
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(WORD_WITH_DIGITS_END.wrongVer(), WORD_WITH_DIGITS_MIDDLE.wrongVer())
                        .language(EN)
                        .callApiTexts());
        assertThat(answers,hasSize(2));

        assertThat(answers.get(0),not(empty()));
        assertThat(answers.get(0).get(0).s, hasItem(WORD_WITH_DIGITS_END.corrVer()));

        assertThat(answers.get(1),not(empty()));
        assertThat(answers.get(1).get(0).s, hasItem(WORD_WITH_DIGITS_MIDDLE.corrVer()));

        //with option
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(WORD_WITH_DIGITS_END.wrongVer(), WORD_WITH_DIGITS_MIDDLE.wrongVer())
                        .language(EN)
                        .options(IGNORE_DIGITS.code)
                        .callApiTexts());
        assertThat(answers,hasSize(2));
        assertThat(answers.get(0), empty());
        assertThat(answers.get(1), empty());
    }

    @Test
    public void checkUrlsOptions() {

        //no option
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(WORD_WITH_URL.wrongVer(), WORD_WITH_EMAIL.wrongVer(), WORD_WITH_FILE.wrongVer())
                        .language(EN)
                        .callApiTexts());

        //failed because response is empty
        assertThat(answers,hasSize(3));

        assertThat(answers.get(0), not(empty()));
        assertThat(answers.get(0).get(0).s, hasItem(WORD_WITH_URL.corrVer()));

        assertThat(answers.get(1), not(empty()));
        assertThat(answers.get(1).get(0).s, hasItem(WORD_WITH_EMAIL.corrVer()));

        assertThat(answers.get(2), not(empty()));
        assertThat(answers.get(2).get(0).s, hasItem(WORD_WITH_FILE.corrVer()));

        //with option
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(WORD_WITH_URL.wrongVer(), WORD_WITH_EMAIL.wrongVer(), WORD_WITH_FILE.wrongVer())
                        .language(EN)
                        .options(IGNORE_URLS.code)
                        .callApiTexts());
        //passed but not because option is on
        assertThat(answers,hasSize(3));
        assertThat(answers.get(0), empty());
        assertThat(answers.get(1), empty());
        assertThat(answers.get(2), empty());
    }

    @Test
    public void checkRepeatOptions() {

        //no option
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(TEXT_WITH_REPEATS.wrongVer(), TEXT_WITH_NO_REPEATS.wrongVer())
                        .language(EN)
                        .callApiTexts());
        assertThat(answers,hasSize(2));
        assertThat(answers.get(0), empty());
        assertThat(answers.get(1), empty());

        //with option
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(TEXT_WITH_REPEATS.wrongVer(), TEXT_WITH_NO_REPEATS.wrongVer())
                        .language(EN)
                        .options(FIND_REPEAT_WORDS.code)
                        .callApiTexts());

        //failed , response empty
        assertThat(answers,hasSize(2));

        assertThat(answers.get(0), not(empty()));
        assertTrue(answers.get(0).get(0).s.contains(TEXT_WITH_REPEATS.corrVer()));

        assertThat(answers.get(1),empty());
    }

    @Test
    public void checkCapitalizationOptions() {

        //no option
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(LOWER_LONDON.wrongVer(), UPPER_BROTHER.wrongVer())
                        .language(EN)
                        .callApiTexts());

        //failed empty response
        assertThat(answers,hasSize(2));

        assertThat(answers.get(0), not(empty()));
        assertThat(answers.get(0).get(0).s, hasItem(LOWER_LONDON.corrVer()));

        assertThat(answers.get(1), not(empty()));
        assertThat(answers.get(1).get(0).s, hasItem(UPPER_BROTHER.corrVer()));

        //with options
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(LOWER_LONDON.wrongVer(), UPPER_BROTHER.wrongVer())
                        .options(IGNORE_CAPITALIZATION.code)
                        .language(EN)
                        .callApiTexts());

        //passed but not because option is on
        assertThat(answers,hasSize(2));
        assertThat(answers.get(0), empty());
        assertThat(answers.get(1), empty());
    }

    @Test
    public void checkMultiOptions() {

        //no options
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(UPPER_BROTHER.wrongVer(), WORD_WITH_DIGITS_MIDDLE.wrongVer())
                        .language(EN)
                        .callApiTexts());

        //failed, only WORD_WITH_DIGITS_MIDDLE.corrVer() in response
        assertThat(answers,hasSize(2));

        assertThat(answers.get(0), not(empty()));
        assertThat(answers.get(0).get(0).s, hasItem(UPPER_BROTHER.corrVer()));

        assertThat(answers.get(1), not(empty()));
        assertThat(answers.get(1).get(0).s, hasItem(WORD_WITH_DIGITS_MIDDLE.corrVer()));


        //with options
        String options = sum(IGNORE_DIGITS, IGNORE_CAPITALIZATION);
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(UPPER_BROTHER.wrongVer() ,WORD_WITH_DIGITS_MIDDLE.wrongVer())
                        .language(EN)
                        .options(options)
                        .callApiTexts());
        assertThat(answers,hasSize(2));
        assertThat(answers.get(0), empty());
        assertThat(answers.get(1), empty());
    }

    /* notes
     * 1) strange behaviour with uk language , work as it default but in documentation : "ru,en"
     * 2) do not work with urls,files, emails, option can be tested properly, because response both with option
     *    and without it is empty
     * 3) same situation with capitalization
     * 4) and repeats
     */
}
