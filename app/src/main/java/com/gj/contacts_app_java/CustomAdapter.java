package com.gj.contacts_app_java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter
{
    ArrayList<Contact> contactList;
    Context mContext;

    public CustomAdapter(Context context, ArrayList<Contact> contactArrayList) {
        this.mContext = context;
        this.contactList = contactArrayList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Contact contact = contactList.get(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view_item,null);

        TextView textViewName = convertView.findViewById(R.id.nameItemTextView);
        TextView textViewPhone = convertView.findViewById(R.id.phoneItemTextView);
        TextView textViewAddress = convertView.findViewById(R.id.addressItemTextView);
        TextView textViewType = convertView.findViewById(R.id.typeItemTextView);

        textViewName.setText(contact.getName());
        textViewPhone.setText(contact.getPhone());
        textViewAddress.setText(contact.getAddress());
        textViewType.setText(contact.getType());

        return convertView;
    }
}