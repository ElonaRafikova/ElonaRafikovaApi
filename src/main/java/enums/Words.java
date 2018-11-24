package enums;

public enum Words{
    MOTHER("mother", "mottherr"),
    BROTHER("brother", "bbrother"),
    RU_HELLO("здравствуйте","здраствуйте"),
    RU_BYCYCLE("велосипед","велосепед"),
    UK_BOY("хлопець","хлопец"),
    UK_GIRL("дівчина","дівчена"),
    WORD_WITH_DIGITS_END("5 days","next 5days"),
    WORD_WITH_DIGITS_MIDDLE("hello","hel1o"),
    WORD_WITH_URL("https://www.yandex.ru/","https://www.yandex.ru/"),
    WORD_WITH_EMAIL("myemail@yandex.com","myemail@yandex.com"),
    WORD_WITH_FILE("brother.txt","bbrother.txt"),
    TEXT_WITH_REPEATS("food","i like food food"),
    TEXT_WITH_NO_REPEATS("","i very like"),
    LOWER_LONDON("London","london"),
    UPPER_BROTHER("brother","broTher");

    private String corrVer;
    private String wrongVer;

    public String corrVer(){return corrVer;}
    public String wrongVer(){return wrongVer;}

    Words(String corrVer, String wrongVer){
        this.corrVer = corrVer;
        this.wrongVer = wrongVer;

    }
}
