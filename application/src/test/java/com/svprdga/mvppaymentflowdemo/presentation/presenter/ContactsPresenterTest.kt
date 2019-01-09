package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.svprdga.mvppaymentflowdemo.data.datasource.ContactDataSource
import com.svprdga.mvppaymentflowdemo.data.network.client.ApiClient
import com.svprdga.mvppaymentflowdemo.domain.model.Contact
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.ContactsBus
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.MainBus
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.MainData
import com.svprdga.mvppaymentflowdemo.presentation.eventbus.MainEvent
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IContactsView
import com.svprdga.mvppaymentflowdemo.util.Logger
import com.svprdga.mvppaymentflowdemo.util.SchedulersProvider
import de.bechte.junit.runners.context.HierarchicalContextRunner
import io.reactivex.observers.DisposableObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.*
import org.mockito.ArgumentMatchers.anyListOf
import java.lang.Exception

@RunWith(HierarchicalContextRunner::class)
class ContactsPresenterTest {

    // ****************************************** VARS ***************************************** //

    lateinit var presenter: ContactsPresenter
    private val testScheduler = TestScheduler()

    // ************************************* CALLBACK VARS ************************************* //

    val mainDisposable: DisposableObserver<MainData>
        get() = object : DisposableObserver<MainData>() {
            override fun onNext(result: MainData) {}
            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        }

    val contactsDisposable: DisposableObserver<List<Contact>> =
        object : DisposableObserver<List<Contact>>() {
            override fun onNext(list: List<Contact>) {}
            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        }

    val contact0 = Contact(name = "contact0")

    // *************************************** MOCK VARS *************************************** //

    @Mock
    lateinit var view: IContactsView
    @Mock
    lateinit var log: Logger
    @Mock
    lateinit var mainBus: MainBus
    @Mock
    lateinit var contactDataSource: ContactDataSource
    @Mock
    lateinit var contactBus: ContactsBus
    @Mock
    lateinit var apiClient: ApiClient
    @Mock
    lateinit var observableMainData: Observable<MainData>
    @Mock
    lateinit var schedulers: SchedulersProvider

    // ***************************************** SET UP **************************************** //

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        whenever(schedulers.io()).thenReturn(testScheduler)
        whenever(schedulers.ui()).thenReturn(testScheduler)

        presenter = ContactsPresenter(log, mainBus, contactDataSource, contactBus, apiClient, schedulers)
    }

    // ***************************************** TESTS ***************************************** //

    inner class WhenCallingBind {

        @Before
        fun setUp() {
            whenever(mainBus.getData()).thenReturn(observableMainData)
            presenter.mainDisposable = mainDisposable
            presenter.contactsDisposable = contactsDisposable
            presenter.bind(view)
        }

        @Test
        fun shouldAttachView() {
            assertEquals(view, presenter.getView())
        }

    }

    inner class WhenCallingUnbind {

        @Before
        fun setUp() {
            presenter.setView(view)
            presenter.unBind()
        }

        @Test
        fun shouldReleaseView() {
            assertNull(presenter.getView())
        }
    }

    inner class WhenCallingContactSelected {

        @Before
        fun setUp() {
            presenter.contactSelected(contact0)
        }

        @Test
        fun shouldStoreContact() {
            assertNotSame(-1, presenter.getContacts().indexOf(contact0))
        }

        @Test
        fun shouldBroadcastContacts() {
            verify(contactBus).setData(presenter.getContacts())
        }

    }

    inner class WhenCallingContactUnselected {

        @Before
        fun setUp() {
            presenter.getContacts().add(contact0)
            presenter.contactUnselected(contact0)
        }

        @Test
        fun shouldRemoveStoredContact() {
            assertEquals(-1, presenter.getContacts().indexOf(contact0))
        }

        @Test
        fun shouldBroadcastContacts() {
            verify(contactBus).setData(presenter.getContacts())
        }
    }

    inner class WhenEventInMainDisposable {

        lateinit var disposable: DisposableObserver<MainData>

        @Before
        fun setUp() {
            presenter.setView(view)
            disposable = presenter.mainDisposable
        }

        inner class GivenOnNext() {

            inner class GivenLoadContactsEvent() {

                val data = MainData(event = MainEvent.LOAD_CONTACTS)

                @Before
                fun setUp() {
                    whenever(contactDataSource.getAllContacts()).thenReturn(
                        Observable.just(getDeviceContacts()))
                    whenever(apiClient.getCharacters(any())).thenReturn(
                        Observable.just(getApiContacts()))
                    disposable.onNext(data)
                }

                @Test
                fun shouldShowLoading() {
                    verify(view).showLoading()
                }

                @Test
                fun shouldIncludeDeviceContacts() {
                    verify(contactDataSource).getAllContacts()
                }

                @Test
                fun shouldIncludeFirst50ApiCharacters() {
                    verify(apiClient).getCharacters(50)
                }

            }

            inner class GivenUnselectAllEvent() {

                val data = MainData(event = MainEvent.UNSELECT_ALL)

                @Before
                fun setUp() {
                    disposable.onNext(data)
                }

                @Test
                fun shouldCallUnselectAllViewsInView() {
                    verify(view).unselectAllViews()
                }

                @Test
                fun shouldEraseStoredContacts() {
                    assertTrue(presenter.getContacts().isEmpty())
                }

                @Test
                fun shouldBroadcastContacts() {
                    verify(contactBus).setData(presenter.getContacts())
                }

            }

        }

    }

    inner class WhenEventInContactsDisposable {

        lateinit var disposable: DisposableObserver<List<Contact>>
        val deviceContacts: ArrayList<Contact> = getDeviceContacts()
        val apiContacts: ArrayList<Contact> = getApiContacts()

        @Before
        fun setUp() {
            presenter.setView(view)
            disposable = presenter.contactsDisposable
        }

        inner class GivenCorrectFlow {

            @Before
            fun setUp() {
                disposable.onNext(deviceContacts)
                disposable.onNext(apiContacts)
                disposable.onComplete()
            }

            @Test
            fun shouldHideLoadingInView() {
                verify(view).hideLoading()
            }

            @Test
            fun shouldShowFullListOfContacts() {
                verify(view).showContacts(anyListOf(Contact::class.java))
            }

        }

        inner class GivenErrorFlow {

            @Before
            fun setUp() {
                disposable.onError(Exception())
            }

            @Test
            fun shouldHideLoadingInView() {
                verify(view).hideLoading()
            }

            @Test
            fun shouldShowErrorLayoutInView() {
                verify(view).showErrorLayout()
            }

        }
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun getDeviceContacts(): ArrayList<Contact> {
        val amount = 20
        val list = ArrayList<Contact>()

        for (i in 1..amount) {
            val contact = Contact(name = "device_contact_$i")
            list.add(contact)
        }

        return list
    }

    private fun getApiContacts(): ArrayList<Contact> {
        val amount = 50
        val list = ArrayList<Contact>()

        for (i in 1..amount) {
            val contact = Contact(name = "api_contact_$i")
            list.add(contact)
        }

        return list
    }
}


