package icesi.edu.co.icesicare.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import icesi.edu.co.icesicare.R
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val IS_UPDATING_KEY = "isUpdating"
private const val EVENT_ID_KEY = "eventId"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminEventsAddUpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminEventsAddUpdateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var isUpdating: Boolean = false
    private var eventId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isUpdating = it.getBoolean(IS_UPDATING_KEY)
            eventId = it.getString(EVENT_ID_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_events_add_update, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param isUpdating If an event is being updated
         * @param eventId id of the event being updated,should be null
         * if this fragment is used for creating an event.
         * @return A new instance of fragment AdminEventsAddUpdateFragment.
         */
        @JvmStatic fun newInstance(isUpdating: Boolean, eventId: String) =
                AdminEventsAddUpdateFragment().apply {
                    arguments = Bundle().apply {
                        putBoolean(IS_UPDATING_KEY, isUpdating)
                        putString(EVENT_ID_KEY, eventId)
                    }
                }
    }
}