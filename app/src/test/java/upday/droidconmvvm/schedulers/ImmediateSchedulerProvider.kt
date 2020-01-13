package upday.droidconmvvm.schedulers

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import rx.Scheduler
import rx.schedulers.Schedulers

/**
 * Implementation of the [ISchedulerProvider] making all [Scheduler]s immediate.
 */
class ImmediateSchedulerProvider : ISchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.immediate()
    }

    override fun ui(): Scheduler {
        return Schedulers.immediate()
    }
}
