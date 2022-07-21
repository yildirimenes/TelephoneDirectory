package com.project.telephonedirectory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.CardViewDesignObjectHandler> {

    private Context context;
    private List<Persons> personsList;
    private Database db;

    public PersonsAdapter(Context context, List<Persons> personsList, Database db) {
        this.context = context;
        this.personsList = personsList;
        this.db = db;
    }

    public class CardViewDesignObjectHandler extends RecyclerView.ViewHolder {

        private ImageView imgIcon;
        private TextView twPersonName;
        private ImageButton imgBtnCall;
        private ImageButton imgBtnMessage;

        public CardViewDesignObjectHandler(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            twPersonName = itemView.findViewById(R.id.twPersonName);
            imgBtnCall = itemView.findViewById(R.id.imgBtnCall);
            imgBtnMessage = itemView.findViewById(R.id.imgBtnMessage);
        }
    }

    @NonNull
    @Override
    public PersonsAdapter.CardViewDesignObjectHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_card_design,parent,false);
        return new CardViewDesignObjectHandler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonsAdapter.CardViewDesignObjectHandler holder, int position) {

        final Persons person = personsList.get(position);

        holder.twPersonName.setText(person.getPerson_name());

        holder.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.imgIcon);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                Snackbar.make(holder.imgIcon,"Delete The Contact ?",Snackbar.LENGTH_SHORT)
                                        .setAction("Yes", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                new PersonsDao().personDelete(db,person.getPerson_id());
                                                personsList = new PersonsDao().allPersons(db);
                                                notifyDataSetChanged();
                                            }
                                        })
                                        .show();
                                return true;

                            case R.id.action_update:
                                alertshow(person);
                                return true;

                            default:
                                return false;
                        }

                    }
                });

                popupMenu.show();

            }
        });

        holder.imgBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneCallNumber = person.getPhone_number();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel: " + phoneCallNumber));
                context.startActivity(intent);
            }
        });

        holder.imgBtnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.imgBtnMessage);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_message_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_message:
                                Intent intent = new Intent(context,SendSMS.class);
                                intent.putExtra("person_object", person);
                                context.startActivity(intent);
                                return true;

                            case R.id.action_wp_message:
                                intent = new Intent(context, SendWhatsAppMessage.class);
                                intent.putExtra("person_object",person);
                                context.startActivity(intent);
                                return true;

                            case R.id.action_email_message:
                                intent = new Intent(context, SendEmail.class);
                                intent.putExtra("person_object", person);
                                context.startActivity(intent);

                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();

            }
        });

    }


    @Override
    public int getItemCount() {
        return personsList.size();
    }

    private void alertshow(Persons person) {

        LayoutInflater layout = LayoutInflater.from(context);
        View design = layout.inflate(R.layout.alert_design,null);

        final EditText editTextName = design.findViewById(R.id.editTextName);
        final EditText editTextPhoneNumber = design.findViewById(R.id.editTextPhoneNumber);
        final EditText editTextEmail = design.findViewById(R.id.editTextEmail);

        editTextName.setText(person.getPerson_name());
        editTextPhoneNumber.setText(person.getPhone_number());
        editTextEmail.setText(person.getPerson_email());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Update Person");
        builder.setView(design);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String person_name = editTextName.getText().toString().trim();
                String person_phoneNumber = editTextPhoneNumber.getText().toString().trim();
                String person_email = editTextEmail.getText().toString().trim();

                new PersonsDao().personUpdate(db,person.getPerson_id(),person_name,person_phoneNumber,person_email);
                personsList = new PersonsDao().allPersons(db);
                notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();

    }

}
