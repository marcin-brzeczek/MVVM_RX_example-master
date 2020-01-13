package upday.droidconmvvm

import android.app.Application

import upday.droidconmvvm.datamodel.DataModel
import upday.droidconmvvm.datamodel.IDataModel
import upday.droidconmvvm.schedulers.ISchedulerProvider
import upday.droidconmvvm.schedulers.SchedulerProvider

class DroidconApplication : Application() {

    val dataModel: IDataModel

    val schedulerProvider: ISchedulerProvider
        get() = SchedulerProvider.instance

    val viewModel: MainViewModel
        get() = MainViewModel(dataModel, schedulerProvider)

    init {
        dataModel = DataModel()
    }

}
