package upday.droidconmvvm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer

import upday.droidconmvvm.model.Language

class MainActivity : AppCompatActivity() {

    private var mGreetingView: TextView? = null

    private var mLanguagesSpinner: Spinner? = null

    private var mLanguageSpinnerAdapter: LanguageSpinnerAdapter? = null

    private val viewModel: MainViewModel by lazy { (application as DroidconApplication).viewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        getSupportedLanguages()
        observe()
    }

    private fun observe() {
        viewModel.greetingsList.observe(this, Observer { languages ->
            languages?.let {
                setLanguages(it)
            }
        })
        viewModel.greetingText.observe(this, Observer { text ->
            text?.let {
                setGreeting(it)
            }
        })
    }

    private fun setupViews() {
        mGreetingView = findViewById(R.id.greeting) as TextView?
        mLanguagesSpinner = findViewById(R.id.languages) as Spinner?
        assert(mLanguagesSpinner != null)
        mLanguagesSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                itemSelected(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun getSupportedLanguages() = viewModel.supportedLanguages

    private fun setGreeting(greeting: String) {
        assert(mGreetingView != null)
        mGreetingView!!.text = greeting
    }

    private fun setLanguages(languages: List<Language>) {
        assert(mLanguagesSpinner != null)

        mLanguageSpinnerAdapter = LanguageSpinnerAdapter(this, R.layout.language_item, languages)
        mLanguagesSpinner!!.adapter = mLanguageSpinnerAdapter
    }

    private fun itemSelected(position: Int) {
        assert(mLanguageSpinnerAdapter != null)

        val languageSelected = mLanguageSpinnerAdapter!!.getItem(position)
        viewModel.languageSelected(languageSelected!!)
    }
}
