package Tests;

import lib.CoreTestCase;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;


public class MyListsTests extends CoreTestCase {
    private static final String name_of_folder = "programming_language";
    private static final String
    login = "Banfree",
    password = "zavagudav";

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isMW())
        {
            AutorizationPageObject Auth = new AutorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            assertEquals(
                    "We are not on the same page after login.",
                    article_title,
                    ArticlePageObject.getArticleTitle()
            );

            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(name_of_folder);
        } else if (Platform.getInstance().isIOS()) {
            ArticlePageObject.closeSyncWindow();
        }

        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveAndDeleteOneOfTwoArticlesInMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        if (Platform.getInstance().isMW())
        {
            AutorizationPageObject Auth = new AutorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        if (Platform.getInstance().isAndroid() || (Platform.getInstance().isMW()))
        {
            SearchPageObject.typeSearchLine("Java");
        }
        SearchPageObject.clickByArticleWithSubstring("sland of Indonesia");
        ArticlePageObject.waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addNextArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
            ArticlePageObject.closeArticle();

            NavigationUI NavigationUI = NavigationUIFactory.get(driver);
            NavigationUI.openNavigation();
            NavigationUI.clickMyLists();

            MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

            if (Platform.getInstance().isAndroid()) {
                MyListsPageObject.openFolderByName(name_of_folder);
            } else if (Platform.getInstance().isIOS())
            {
                ArticlePageObject.closeSyncWindow();
            }
            if(Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
                MyListsPageObject.swipeByArticleToDelete("Java");

                SearchPageObject.clickByArticleWithSubstring("Java (programming language)");
                String saved_article_subtitle = ArticlePageObject.getArticleSubtitle();

                assertEquals(
                        "We see unexpected subtitle",
                        "bject-oriented programming language",
                        saved_article_subtitle
                );
            }
            else {
                MyListsPageObject.swipeByArticleToDelete("Java (programming language)");
                SearchPageObject.clickByArticleInListWithSubstring("Java");


                String saved_article_note = ArticlePageObject.getArticleNote();

                assertEquals(
                        "We see unexpected note",
                        "This article is about the Indonesian island. For the programming language, see Java (programming language). For other uses, see Java (disambiguation).",
                        saved_article_note
                );
            }
        }
    }

