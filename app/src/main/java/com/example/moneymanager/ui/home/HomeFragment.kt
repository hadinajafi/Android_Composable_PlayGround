package com.example.moneymanager.ui.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TableRow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moneymanager.databinding.FragmentHomeBinding
import com.example.moneymanager.models.Transaction


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val fab = binding.createTransactionFab

        val listView = binding.homeListView
        val adapter = ArrayAdapter<Transaction>(listView.context, R.layout.simple_list_item_1)
        adapter.addAll(homeViewModel.transactions)
        listView.adapter = adapter

        fab.setOnClickListener {
            adapter.add(
                Transaction(
                    title = "New me",
                    description = null,
                    amount = 2.3F
                )
            )
        }

        //todo: didn't work
//        row.updateLayoutParams<ConstraintLayout.LayoutParams> {
//            startToEnd = binding.root.id
//            topToTop = binding.root.id
//            rightToRight = binding.root.id
//        }
//        val tableView = binding.homeTableView
//        val row = TableRow(tableView.context)
//        val textView = TextView(row.context)

//        textView.text = "Hadi"
//        row.addView(textView)
//        tableView.addView(row)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}