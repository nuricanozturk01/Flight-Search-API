package nuricanozturk.dev.service.flight.provider.util;

import java.util.List;
import java.util.Random;

public final class CityProvider
{
    private CityProvider()
    {
    }

    private static final List<String> TURKISH_CITIES = List.of(
            "ADANA", "ADIYAMAN", "AFYONKARAHISAR", "AGRI", "AMASYA",
            "ANKARA", "ANTALYA", "ARTVIN", "AYDIN", "BALIKESIR",
            "BILECIK", "BINGOL", "BITLIS", "BOLU", "BURDUR",
            "BURSA", "CANAKKALE", "CANKIRI", "CORUM", "DENIZLI",
            "DIYARBAKIR", "EDIRNE", "ELAZIG", "ERZINCAN", "ERZURUM",
            "ESKISEHIR", "GAZIANTEP", "GIRESUN", "GUMUSHANE", "HAKKARI",
            "HATAY", "ISPARTA", "MERSIN", "ISTANBUL", "IZMIR",
            "KARS", "KASTAMONU", "KAYSERI", "KIRKLARELI", "KIRSEHIR",
            "KOCAELI", "KONYA", "KUTAHYA", "MALATYA", "MANISA",
            "KAHRAMANMARAS", "MARDIN", "MUGLA", "MUS", "NEVSEHIR",
            "NIGDE", "ORDU", "RIZE", "SAKARYA", "SAMSUN",
            "SIIRT", "SINOP", "SIVAS", "TEKIRDAG", "TOKAT",
            "TRABZON", "TUNCELI", "SANLIURFA", "USAK", "VAN",
            "YOZGAT", "ZONGULDAK", "AKSARAY", "BAYBURT", "KARAMAN",
            "KIRIKKALE", "BATMAN", "SIRNAK", "BARTIN", "ARDAHAN",
            "IGDIR", "YALOVA", "KARABUK", "KILIS", "OSMANIYE",
            "DUZCE"
    );

    public static String getRandomTurkishCity(Random random)
    {
        return TURKISH_CITIES.get(random.nextInt(TURKISH_CITIES.size() - 1));
    }
}
