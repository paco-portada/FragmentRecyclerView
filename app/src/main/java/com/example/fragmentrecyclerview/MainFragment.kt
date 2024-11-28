package com.example.fragmentrecyclerview

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainFragment : Fragment(), ItemClickListener {

    private lateinit var adapter : RecyclerViewAdapter
    private val list = ArrayList<DataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        buildListData()
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = RecyclerViewAdapter(list, this)
        recyclerView.adapter = adapter

        //right swipe on recyclerview
        val itemSwipe =  object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //TODO("Not yet implemented")
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDialog(viewHolder)
            }
        }

        val swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(recyclerView)
    }

    private fun showDialog(viewHolder: RecyclerView.ViewHolder) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Delete Item")
        builder.setMessage("Are you sure to delete the item?")
        builder.setPositiveButton("Confirm") {dialog, which ->
            val position = viewHolder.adapterPosition
            list.removeAt(position)
            // adapter.notifyItemRemoved(position)
            adapter.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancel") {dialog, which ->
            val position = viewHolder.adapterPosition
            adapter.notifyItemChanged(position)
        }
        builder.show()

    }

    private fun buildListData() {
        list.add(DataModel("BMW"))
        list.add(DataModel("Audi"))
        list.add(DataModel("Chevrolet"))
        list.add(DataModel("Ford"))
        list.add(DataModel("Honda"))
        list.add(DataModel("Ferrari"))
    }

    override fun onItemClick(dataModel: DataModel) {
        val fragment: Fragment = DetailFragment.newInstance(dataModel.title)
        val transaction = activity?.supportFragmentManager!!.beginTransaction()

        // transaction.replace(R.id.frame_container, fragment, "detail_fragment");
        transaction.hide(activity?.supportFragmentManager?.findFragmentByTag("main_fragment")!!)
        transaction.add(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}