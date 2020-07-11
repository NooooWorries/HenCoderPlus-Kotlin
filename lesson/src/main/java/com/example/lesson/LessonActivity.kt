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
    override val presenter by lazy {
        LessonPresenter(this)
    }

    private val lessonAdapter = LessonAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        toolbar.run {
            inflateMenu(R.menu.menu_lesson)
            setOnMenuItemClickListener(this@LessonActivity)
        }

        list.run {
            layoutManager = LinearLayoutManager(this@LessonActivity)
            adapter = lessonAdapter
            addItemDecoration(DividerItemDecoration(this@LessonActivity, LinearLayout.VERTICAL))
        }

        swipe_refresh_layout.run {
            setOnRefreshListener { presenter.fetchData() }
            isRefreshing = true
        }

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