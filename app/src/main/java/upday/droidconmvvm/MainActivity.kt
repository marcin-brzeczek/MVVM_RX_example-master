package upday.droidconmvvm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import upday.droidconmvvm.model.Language

class MainActivity : AppCompatActivity() {

    private var mGreetingView: TextView? = null

    private var mLanguagesSpinner: Spinner? = null

    private var mLanguageSpinnerAdapter: LanguageSpinnerAdapter? = null

    private val viewModel: MainViewModel by lazy {(application as DroidconApplication).viewModel}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    private fun setupViews() {
        mGreetingView = findViewById(R.id.greeting) as TextView?

        mLanguagesSpinner = findViewById(R.id.languages) as Spinner?
        assert(mLanguagesSpinner != null)
        mLanguagesSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {
                itemSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //nothing to do here
            }
        }
    }

    override fun onResume() {
        super.onResume()
        bind()
    }

    override fun onPause() {
        super.onPause()
        unBind()
    }

    private fun bind() {

        mCompositeDisposable.add(mViewModel.supportedLanguages
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer<List<Language>> { this.setLanguages(it) }))
    }

    private fun setGreeting(greeting: String) {
        assert(mGreetingView != null)

        mGreetingView!!.text = greeting
    }

    private fun setLanguages(languages: List<Language>) {
        assert(mLanguagesSpinner != null)

        mLanguageSpinnerAdapter = LanguageSpinnerAdapter(this,
            R.layout.language_item,
            languages)
        mLanguagesSpinner!!.adapter = mLanguageSpinnerAdapter
    }

    private fun itemSelected(position: Int) {
        assert(mLanguageSpinnerAdapter != null)

        val languageSelected = mLanguageSpinnerAdapter!!.getItem(position)
        mViewModel.languageSelected(languageSelected!!)
    }
}
