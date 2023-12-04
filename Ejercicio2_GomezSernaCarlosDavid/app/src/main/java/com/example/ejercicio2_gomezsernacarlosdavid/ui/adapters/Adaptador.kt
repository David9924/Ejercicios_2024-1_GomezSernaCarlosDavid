package com.example.ejercicio2_gomezsernacarlosdavid.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ejercicio2_gomezsernacarlosdavid.Modelos.Datos
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejercicio2_gomezsernacarlosdavid.Network.ApiGame
import com.example.ejercicio2_gomezsernacarlosdavid.databinding.ActivityMain2Binding
import com.example.ejercicio2_gomezsernacarlosdavid.databinding.GameElementBinding


class Adaptador(private var games: ArrayList<Datos>, private var onGameClicked: (Datos) -> Unit): RecyclerView.Adapter<Adaptador.ViewHolder>() {

    class ViewHolder(private  var binding: GameElementBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(datos: Datos)
        {
            binding.Title.text = datos.fullName
            Glide.with(itemView.context)
                .load(datos.imageUrl)
                .into(binding.ivThumbnail)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GameElementBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])

        holder.itemView.setOnClickListener { onGameClicked(games[position]) }

    }


}