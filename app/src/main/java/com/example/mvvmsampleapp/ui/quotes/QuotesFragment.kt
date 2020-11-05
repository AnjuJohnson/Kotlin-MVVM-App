package com.example.mvvmsampleapp.ui.quotes

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.data.db.entities.Quote
import com.example.mvvmsampleapp.ui.profile.ProfileViewModelFactory
import com.example.mvvmsampleapp.util.Coroutines
import com.example.mvvmsampleapp.util.hide
import com.example.mvvmsampleapp.util.show
import com.example.mvvmsampleapp.util.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory by instance<QuotesViewModelFactory>()
  //  private val factory: QuotesViewModelFactory by instance()

    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(QuotesViewModel::class.java)
       bindUi()

////////
  //before adding groupie
//await should be called from a suspend n or from coroutine,so call main fn
      /*  Coroutines.main{
            //use await as quotes is of type deferred
          val quotes=  viewModel.quotes.await()
            quotes.observe(this, Observer {
                context?.toast(it.size.toString())
            })
        }*/
        //////
    }

    private fun bindUi() = Coroutines.main {

        progress_bar.show()

        //here we have List of Quotes but we need  List of QuoteItem to display using groupie,so we have to convert list of quote to
        //list of quoteitem for that use an extension fn called "toQuoteItem" of list quote
        viewModel.quotes.await().observe(this, Observer {
            progress_bar.hide()

            //@todo if it==0,then no internet

            initRecyclerview(it.toQuoteItem())

        })
    }
    private fun initRecyclerview(quoteItemList: List<QuoteItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(quoteItemList)
        }
        recyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter=mAdapter
        }
    }


    //here we are taking each qoute from List<Quote> and map this to QuoteItem class and return the list od QuoteItem
    private fun List<Quote>.toQuoteItem(): List<QuoteItem> {
        return this.map {
            QuoteItem(it)
        }
    }
}
