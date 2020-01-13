package upday.droidconmvvm

import android.location.Location
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import upday.droidconmvvm.datamodel.IDataModel
import upday.droidconmvvm.model.Language
import upday.droidconmvvm.model.LanguageCode
import upday.droidconmvvm.schedulers.ISchedulerProvider

/**
 * View model for the main activity.
 */
class MainViewModel(
    mDataModel: IDataModel,
    mSchedulerProvider: ISchedulerProvider
) : BaseViewModel() {

    val greetingText = MutableLiveData<String>()
    val greetingsList = MutableLiveData<List<String>>()

    private val mSelectedLanguage = BehaviorSubject.create<Language>()

    val greeting = mSelectedLanguage
        .subscribeOn(mSchedulerProvider.computation())
        .map(Language::code)
        .flatMap(mDataModel::getGreetingByLanguageCode)
        .observeOn(mSchedulerProvider.ui())
        .subscribe { greetingText.value = it }
        .collect()


    val supportedLanguages = mDataModel.supportedLanguages
        .subscribeOn(mSchedulerProvider.computation())
        .observeOn(mSchedulerProvider.ui())
        .subscribe { greetingsList.value = it }
        .collect()
e
    fun languageSelected(language: Language) {
        mSelectedLanguage.onNext(language)
    }
}