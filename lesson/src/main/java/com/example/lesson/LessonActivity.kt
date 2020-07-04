package com.example.lesson

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.core.BaseView
import com.example.lesson.entity.Lesson
import kotlinx.android.synthetic.main.activity_lesson.*

class LessonActivity : AppCompatActivity(), BaseView<LessonPresenter?>, Toolbar.OnMenuItemClickListener {
    override val presenter = LessonPresenter(this)

    private val lessonAdapter = LessonAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        toolbar.inflateMenu(R.menu.menu_lesson)
        toolbar.setOnMenuItemClickListener(this)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = lessonAdapter
        list.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

        swipe_refresh_layout.setOnRefreshListener { presenter.fetchData() }
        swipe_refresh_layout.isRefreshing = true
        presenter.fetchData()
    }

    fun showResult(lessons: List<Lesson>) {
        lessonAdapter.updateAndNotify(lessons)
        swipe_refresh_layout!!.isRefreshing = false
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        presenter.showPlayback()
        return false
    }
}