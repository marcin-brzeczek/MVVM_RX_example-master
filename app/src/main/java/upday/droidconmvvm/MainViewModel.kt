package upday.droidconmvvm

import androidx.lifecycle.MutableLiveData
import io.reactivex.subjects.BehaviorSubject
import upday.droidconmvvm.datamodel.IDataModel
import upday.droidconmvvm.model.Language
import upday.droidconmvvm.schedulers.ISchedulerProvider

class MainViewModel(
    mDataModel: IDataModel,
    mSchedulerProvider: ISchedulerProvider
) : BaseViewModel() {

    val greetingText = MutableLiveData<String>()
    val greetingsList = MutableLiveData<List<Language>>()

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

    fun languageSelected(language: Language) {
        mSelectedLanguage.onNext(language)
    }
}