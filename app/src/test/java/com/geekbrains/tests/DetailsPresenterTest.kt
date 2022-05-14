package com.geekbrains.tests

import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter
    @Mock
    private lateinit var viewContract: ViewDetailsContract

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsPresenter()
    }

    @Test
    fun onAttach_Test() {
        presenter.onAttach(viewContract)
        Assert.assertNotNull(presenter.view)
    }

    @Test
    fun onAttach_SetUi_Test() {
        presenter.onAttach(viewContract)
        Mockito.verify(viewContract, Mockito.times(1)).setUI()
    }

    @Test
    fun onDetach_Test() {
        presenter.onDetach()
        Assert.assertNull(presenter.view)
    }

    @Test
    fun onIncrement_Test() {
        presenter.onAttach(viewContract)
        presenter.onIncrement()
        verify(viewContract, times(1)).setCount(1)
    }

    @Test
    fun onDecrement_Test() {
        presenter.onAttach(viewContract)
        presenter.onDecrement()
        verify(viewContract, times(1)).setCount(-1)
    }

    @Test
    fun setCounter_Test() {
        presenter.onAttach(viewContract)
        presenter.setCounter(9)
        presenter.onIncrement()
        verify(viewContract).setCount(10)
    }
}