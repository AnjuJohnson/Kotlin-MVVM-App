package com.example.mvvmsampleapp.ui.quotes

import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.data.db.entities.Quote
import com.example.mvvmsampleapp.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

//binding class should be given as generic type which is  generated automaticaly
//use this class for groupie
class QuoteItem(
    private val quote: Quote
) : BindableItem<ItemQuoteBinding>() {
    override fun getLayout()= R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }
}