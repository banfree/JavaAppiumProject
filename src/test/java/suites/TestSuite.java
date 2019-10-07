package suites;

import Tests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ArticleTests.class,
        ChangeAppCondition.class,
        GetStartedTest.class,
        MyListsTests.class,
        SearchTests.class
})

public class TestSuite {
}
