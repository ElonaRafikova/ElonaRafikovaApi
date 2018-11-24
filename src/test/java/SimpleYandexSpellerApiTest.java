import beans.YandexSpellerAnswer;
import core.YandexSpellerApi;
import org.junit.Test;

import java.util.List;

import static enums.Language.*;
import static enums.Options.*;
import static enums.Words.*;
import static junit.framework.TestCase.assertTrue;

public class SimpleYandexSpellerApiTest {

    private static List<List<YandexSpellerAnswer>> answers;

    @Test
    public void checkRussianWords() {
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(RU_BYCYCLE.wrongVer(), RU_HELLO.wrongVer())
                        .language(RU)
                        .callApiTexts());
        assertTrue(answers.get(0).get(0).s.contains(RU_BYCYCLE.corrVer()));
        assertTrue(answers.get(1).get(0).s.contains(RU_HELLO.corrVer()));
    }

    @Test
    public void checkEnglishWords() {
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(BROTHER.wrongVer(), MOTHER.wrongVer())
                        .language(EN)
                        .callApiTexts());
        assertTrue(answers.get(0).get(0).s.contains(BROTHER.corrVer()));
        assertTrue(answers.get(1).get(0).s.contains(MOTHER.corrVer()));
    }

    @Test
    public void checkUkrainianWords() {
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi.with().
                        texts(UK_BOY.wrongVer(), UK_GIRL.wrongVer())
                        .language(UK)
                        .callApiTexts());
        assertTrue(answers.get(0).get(0).s.contains(UK_BOY.corrVer()));
        assertTrue(answers.get(1).get(0).s.contains(UK_GIRL.corrVer()));

        //should fail as default "ru,en" but passed
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(UK_BOY.wrongVer(), UK_GIRL.wrongVer())
                        .callApiTexts());
        assertTrue(answers.get(0).get(0).s.contains(UK_BOY.corrVer()));
        assertTrue(answers.get(1).get(0).s.contains(UK_GIRL.corrVer()));
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
        assertTrue(answers.get(0).get(0).s.contains(WORD_WITH_DIGITS_END.corrVer()));
        assertTrue(answers.get(1).get(0).s.contains(WORD_WITH_DIGITS_MIDDLE.corrVer()));

        //with option
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(WORD_WITH_DIGITS_END.wrongVer(), WORD_WITH_DIGITS_MIDDLE.wrongVer())
                        .language(EN)
                        .options(IGNORE_DIGITS.code)
                        .callApiTexts());
        assertTrue(answers.get(0).isEmpty());
        assertTrue(answers.get(1).isEmpty());
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
        assertTrue(answers.get(0).get(0).s.contains(WORD_WITH_URL.corrVer()));
        assertTrue(answers.get(1).get(0).s.contains(WORD_WITH_EMAIL.corrVer()));
        assertTrue(answers.get(2).get(0).s.contains(WORD_WITH_FILE.corrVer()));

        //with option
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(WORD_WITH_URL.wrongVer(), WORD_WITH_EMAIL.wrongVer(), WORD_WITH_FILE.wrongVer())
                        .language(EN)
                        .options(IGNORE_URLS.code)
                        .callApiTexts());
        //passed but not because option is on
        assertTrue(answers.get(0).isEmpty());
        assertTrue(answers.get(1).isEmpty());
        assertTrue(answers.get(2).isEmpty());
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
        assertTrue(answers.get(0).isEmpty());
        assertTrue(answers.get(1).isEmpty());

        //with option
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(TEXT_WITH_REPEATS.wrongVer(), TEXT_WITH_NO_REPEATS.wrongVer())
                        .language(EN)
                        .options(FIND_REPEAT_WORDS.code)
                        .callApiTexts());

        //failed , response empty
        assertTrue(answers.get(0).get(0).s.contains(TEXT_WITH_REPEATS.corrVer()));
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
        assertTrue(answers.get(0).get(0).s.contains(LOWER_LONDON.corrVer()));
        assertTrue(answers.get(1).get(0).s.contains(UPPER_BROTHER.corrVer()));

        //with options
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(LOWER_LONDON.wrongVer(), UPPER_BROTHER.wrongVer())
                        .options(IGNORE_CAPITALIZATION.code)
                        .language(EN)
                        .callApiTexts());

        //passed but not because option is on
        assertTrue(answers.get(0).isEmpty());
        assertTrue(answers.get(1).isEmpty());
    }

    @Test
    public void checkMultiOptions() {

        //no options
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(UPPER_BROTHER.wrongVer() + " " + WORD_WITH_DIGITS_MIDDLE.wrongVer())
                        .language(EN)
                        .callApiTexts());

        //failed, only WORD_WITH_DIGITS_MIDDLE.corrVer() in response
        assertTrue(answers.get(0).get(0).s.contains(UPPER_BROTHER.corrVer()));
        assertTrue(answers.get(0).get(1).s.contains(WORD_WITH_DIGITS_MIDDLE.corrVer()));

        //with options
        String options = sum(IGNORE_DIGITS, IGNORE_CAPITALIZATION);
        answers = YandexSpellerApi.getYandexSpellerAnswers(
                YandexSpellerApi
                        .with()
                        .texts(UPPER_BROTHER.wrongVer() + " " + WORD_WITH_DIGITS_MIDDLE.wrongVer())
                        .language(EN)
                        .options(options)
                        .callApiTexts());
        assertTrue(answers.get(0).isEmpty());
    }

    /* notes
    * 1) strange behaviour with uk language , work as it default but in documentation : "ru,en"
    * 2) do not work with urls,files, emails, option can be tested properly, because response both with option
    *    and without it is empty
    * 3) same situation with capitalization
    * 4) and repeats
    */
}
