package upday.droidconmvvm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import upday.droidconmvvm.model.Language

class LanguageSpinnerAdapter(context: Context, resource: Int,
                             objects: List<Language>) : ArrayAdapter<Language>(context, resource, objects) {

    override fun getDropDownView(position: Int, convertView: View?,
                                 parent: ViewGroup): View {
        return getCustomView(position, convertView)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView)
    }

    private fun getCustomView(position: Int, convertView: View?): View {
        val holder: ViewHolder
        var view = convertView

        if (view == null) {
            view = inflateView()
            val textView = view.findViewById(android.R.id.text1) as TextView
            holder = ViewHolder(textView)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val language = getItem(position)
        holder.bind(language!!.name)

        return view
    }

    private fun inflateView(): View {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(R.layout.language_item, null)
    }

    private inner class ViewHolder(private val mText: TextView) {

        fun bind(text: String) {
            mText.text = text
        }
    }
}
