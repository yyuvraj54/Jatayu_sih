package com.example.jatayu_sih.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jatayu_sih.R
import com.example.jatayu_sih.dataClass.RequirementsData
import com.example.jatayu_sih.databinding.ListItemBinding
import com.example.jatayu_sih.databinding.RequirementsListItemBinding

class RequirementsAdapter(val c:Context, val userList:ArrayList<RequirementsData>):RecyclerView.Adapter<RequirementsAdapter.UserViewHolder>()
{
    inner class UserViewHolder(private val binding: RequirementsListItemBinding):   RecyclerView.ViewHolder(binding.root)
    {
        var name: TextView = binding.mTitle
        var mbNum: TextView = binding.mSubTitle
        var mMenus: ImageView = binding.mMenus


        init{
            mMenus.setOnClickListener {
                popupMenus(it)
            }
        }
        private fun popupMenus(v:View)
        {
           val position= userList[bindingAdapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)
                        val name = v.findViewById<EditText>(R.id.itemName)
                        val number = v.findViewById<EditText>(R.id.quantityNo)
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                position.itemName = name.text.toString()
                                position.quantity = number.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(c,"User Information is Edited",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete->{
                        /**set delete*/
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                userList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted this Information",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding  = RequirementsListItemBinding.inflate(inflater,parent,false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.name.text = newList.itemName
        holder.mbNum.text = newList.quantity
    }

    override fun getItemCount(): Int {
        return  userList.size
    }
}