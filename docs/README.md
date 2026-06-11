
# Források
[EASY XPath Tutorial](https://www.youtube.com/watch?v=XyBxEnyBb0A)              
[XPath Tutorial For Beginners](https://www.youtube.com/watch?v=128luZRWOpw&list=PLmRg3gEG2XIackdOpGvb_jEX1ywaplUmh)             
[Selenium XPath Tutorial](https://www.youtube.com/watch?v=5LV4z_-91WY&list=PLL34mf651faO1vJWlSoYYBJejN9U_rwy-)              
[XPath Tutorial](https://www.youtube.com/watch?v=ppnDfNuSjro&list=PL699Xf-_ilW4sqC76skEN5vHT0M1YNXoU)           

[Xpath cheatsheet](https://devhints.io/xpath)           

Az első selenium webdriver-es projekt létrehozásához való tészletes útmutatót [itt](https://github.com/Nagraggini/selenium-playground/blob/main/README.md) találsz (projekt létrehozása, verziókezelés,  feltöltése github-ra, CI/CD beállítása.)       

[Programozásról szóló jegyzeteim](https://nagraggini.github.io/my-awesome-book/)            

# Eclipse

Ha Eclipse-ben hozod létre a projektet.
A pom.xml-nél az első sorban ennél a linknél https helyett http legyen.
http://maven.apache.org/xsd/maven-4.0.0.xsd


Projekten jobb klikk -> Build Path -> Configure Build Path -> Libraries -> ModulePath -> Jobb szélén Edit -> Java 21 -> Majd bal szélén katt a Java Compiler-re és Java 21.


https://mvnrepository.com/-ról a pom.xml-be másold be a függőségeket.
Selenium Java
WebDriverManager (bonigarcia)
JUnit Jupiter (Aggregator)


# Xpath

Abszolút útvonal: 

Az abszolút XPath a közvetlen utat jelöli ki az elemhez a struktúra legtetejétől kezdve.
Mindig egyetlen pernyjellel (/) kezdődik.

Relatív útvonal: 

Mindig dupla perjellel (//) kezdődik, ami azt jelenti, hogy az aktuális csomóponttól (node) indulva keres a dokumentumban bárhol, nem kell a teljes útvonalat megadni.

A .// -el lehet relatív keresést csinálni,a vagyis az adott elemhez képest keresni például egy gombot. 

Text és Contains függvények:

//*[contains(@placeholder,'User')]

Value-t, vagyis értéket keres és nem valamelyik attribútumban lévő értéket (pl.: class).
//*[text()='Password for all users:']

Vagy használata:
Egy szögletes zárójelben lehet használni.
//*[@data-test='username' or @placeholder='Username']

És használata:
Egy szögletes zárójelben lehet használni.
//*[@data-test='username' and @placeholder='Username']
Amúgy and helyett használhatsz láncolást is.
//*[@data-test='username'][@placeholder='Username']

Függvények:
//input[starts-with(@class,'input')]

<!-- TODO paraméterezett tesztek json-el. https://mockaroo.com/-->


# Logolás beállítása

pom.xml-be a dependencies részre ezt másold be:
```xml
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>2.24.1</version>
    </dependency>

    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.24.1</version>
    </dependency>
```

Keresd meg a projektedben a src/main/resources (vagy ha csak tesztekhez használod, a src/test/resources) mappát.

Hozz létre benne egy új fájlt pontosan ezzel a névvel: log4j2.xml

Ezt másold bele: 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>

        <RollingFile name="FileAppender"                
                     filePattern="logs/test-%d{yyyy-MM-dd-HH-mm-ss}.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
            <Policies>
                <OnStartupTriggeringPolicy />
            </Policies>
        </RollingFile>
    </Appenders>

    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="FileAppender"/>
        </Root>
    </Loggers>
</Configuration>
```
Terminálba: 
mvn verify
mvn clean test

# Konfig fájl és használata

Amikor GitHubon fut:
ConfigReader.get("PASSWORD") → Secretből olvassa ki a jelszót.

Amikor a gépen fut:
ConfigReader.get("PASSWORD") → config.properties-ból olvassa ki a jelszót.

A config.properties fájlt ide hozd létre: src/test/resources/

config.properties fájl tartalma (a jobb oldali részt töltsd ki.):
BASE_URL=
USERNAME=
PASSWORD=
WRONG_PASSWORD=


Az osztály tartalma:
```java
//package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input != null) {
                properties.load(input);
            }

        } catch (IOException e) {
            throw new RuntimeException("Nem sikerült betölteni a config.properties fájlt.", e);
        }
    }

    public static String get(String key) {

        // Először környezeti változó (GitHub Secrets)
        String envValue = System.getenv(key);

        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        // Ha nincs, akkor config.properties
        return properties.getProperty(key);
    }
}
```

.gitignore fájlba ezt írd be: config.properties

Használat a tesztben:
```java
String password = ConfigReader.get("PASSWORD");
String username = ConfigReader.get("USERNAME");
String baseUrl = ConfigReader.get("BASE_URL");
```

A .github/workflows/ci.yml-be írd be a Run Tests alá az env és a többi sorokat:
```yml  
            # =========================
            # 3. Run Tests & Generate JaCoCo Report
            # =========================
            - name: Run Tests and Coverage
              env:
                  USERNAME: ${{ secrets.USERNAME }}
                  PASSWORD: ${{ secrets.PASSWORD }}
                  WRONG_PASSWORD: ${{ secrets.WRONG_PASSWORD }}
                  BASE_URL: ${{ secrets.BASE_URL }}
              run: mvn -B clean verify jacoco:report
```

Github-on is hozzá kell adni az fenti változókat, így:
Nyisd meg a repod:
Settings -> Secrets and variables → New repository secret.

USERNAME = 
PASSWORD = 
BASE_URL = 

# Jacoco report beállítása külön weboldalként

ci.yml fájl végére:
```yml
            # =========================
            # 7. Deploy JaCoCo Report to GitHub Pages
            # =========================
            - name: Deploy JaCoCo Report to GitHub Pages
              uses: JamesIves/github-pages-deploy-action@v4
              with:
                  folder: target/site/jacoco # A mappa, amit publikálni szeretnél
                  branch: gh-pages
```

A ci.yml fájl elejét módosítsd erre:
```yml
permissions:
    contents: write
    pages: write      # Ez is kell a Pages-hez
    id-token: write   # Ez is szükséges a biztonságos deploy-hoz
```

Commitold a fenti új workflow-t.

Külön weboldal beállítása:
A GitHub Pages működéséhez egyetlen dolgot kell még beállítanod a GitHubon (ezt csak egyszer kell):

Menj a repository-d Settings fülére.
Bal oldali menü: Pages.
A Build and deployment rész alatt a Source legördülőnél válaszd ki a Deploy from Branchlehetőséget.
Branch: gh-pages és /root.
Majd menj a Save gombra.
Aztán meg fog jelenni felül a linket.

# Futtatás

Terminálba: mvn clean test

Egy konkrét teszt futtatása: mvn -Dtest=CheckoutStepOnePageTest#shouldDisplayErrorMessageForPostalCode test

Itt találod a fájlt: target/site/jacoco/index.html -> Jobb klikk Open with Live Server

Házi feladat:
mvn clean test -Dgroups="homework"

<!--TODO Allure Report -->
