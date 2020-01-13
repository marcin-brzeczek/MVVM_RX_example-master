package upday.droidconmvvm

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import java.util.Arrays

import rx.Observable
import rx.observers.TestSubscriber
import upday.droidconmvvm.datamodel.IDataModel
import upday.droidconmvvm.model.Language
import upday.droidconmvvm.schedulers.ImmediateSchedulerProvider

import upday.droidconmvvm.model.Language.LanguageCode

class MainViewModelTest {

    @Mock
    private val mDataModel: IDataModel? = null

    private var mMainViewModel: MainViewModel? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mMainViewModel = MainViewModel(mDataModel!!, ImmediateSchedulerProvider())
    }

    @Test
    fun testGetSupportedLanguages_emitsCorrectLanguages() {
        val de = Language("German", LanguageCode.DE)
        val en = Language("English", LanguageCode.EN)
        val languages = Arrays.asList(de, en)
        Mockito.`when`<Observable<List<Language>>>(mDataModel!!.supportedLanguages).thenReturn(Observable.just(languages))
        val testSubscriber = TestSubscriber()

        mMainViewModel!!.supportedLanguages.subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
        testSubscriber.assertValue(languages)
    }

    @Test
    fun testGetGreeting_doesNotEmit_whenNoLanguageSet() {
        val testSubscriber = TestSubscriber()
        mMainViewModel!!.greeting.subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
        testSubscriber.assertNoValues()
    }

    @Test
    fun testGetGreeting_emitsCorrectGreeting_whenLanguageSet() {
        val enGreeting = "Hello"
        val en = Language("English", LanguageCode.EN)
        Mockito.`when`<Observable<String>>(mDataModel!!.getGreetingByLanguageCode(LanguageCode.EN))
            .thenReturn(Observable.just(enGreeting))
        val testSubscriber = TestSubscriber()
        mMainViewModel!!.greeting.subscribe(testSubscriber)

        mMainViewModel!!.languageSelected(en)

        testSubscriber.assertNoErrors()
        testSubscriber.assertValue(enGreeting)
    }
}

