package upday.droidconmvvm.datamodel

import io.reactivex.Observable
import upday.droidconmvvm.model.Language
import upday.droidconmvvm.model.LanguageCode
import upday.droidconmvvm.model.LanguageCode.*

class DataModel : IDataModel {

    override val supportedLanguages: Observable<List<Language>>
        get() = Observable.fromCallable(this::languages)

    private val languages = listOf(
        Language("English", EN),
        Language("German", DE),
        Language("Slovakian", HR))

    override fun getGreetingByLanguageCode(code: LanguageCode): Observable<String> = when (code) {
        DE -> Observable.just("Guten Tag!")
        EN -> Observable.just("Hello!")
        HR -> Observable.just("Zdravo!")
    }
}