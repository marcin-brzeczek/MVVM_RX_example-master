package upday.droidconmvvm.datamodel

import io.reactivex.Observable
import upday.droidconmvvm.model.Language
import upday.droidconmvvm.model.LanguageCode

interface IDataModel {

    val supportedLanguages: Observable<List<Language>>

    fun getGreetingByLanguageCode(code: LanguageCode): Observable<String>
}
