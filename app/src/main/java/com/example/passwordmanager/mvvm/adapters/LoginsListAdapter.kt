package com.example.passwordmanager.mvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordmanager.R
import com.example.passwordmanager.database.LoginEntity

class LoginListAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<LoginListAdapter.LoginViewHolder>() {

    private val logins = mutableListOf<LoginEntity>()

    inner class LoginViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.name_text_view)
        private val tvEmail: TextView = itemView.findViewById(R.id.email_text_view)
        private val tvPassword: TextView = itemView.findViewById(R.id.password_text_view)
        private val tvUrl: TextView = itemView.findViewById(R.id.url_text_view)
        private val tvNote: TextView = itemView.findViewById(R.id.note_text_view)

        fun bind(login: LoginEntity) {
            tvName.text = login.name
            tvEmail.text = login.email
            tvPassword.text = login.password
            tvUrl.text = login.url
            tvNote.text = login.note

            itemView.setOnClickListener {
                onItemClickListener.onItemClick(login)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_login, parent, false)
        return LoginViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoginViewHolder, position: Int) {
        holder.bind(logins[position])
    }

    override fun getItemCount(): Int {
        return logins.size
    }

    fun setLogins(logins: List<LoginEntity>) {
        this.logins.clear()
        this.logins.addAll(logins)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(login: LoginEntity)
    }
}