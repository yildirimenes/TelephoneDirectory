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
    //public Persons persons;

    public PersonsAdapter(Context context, List<Persons> personsList, Database db) {
        this.context = context;
        this.personsList = personsList;
        this.db = db;
    }

    public class CardViewDesignObjectHandler extends RecyclerView.ViewHolder {

        private TextView twPersonName;
        private TextView twPhoneNumber;
        private TextView twEmail;
        private ImageButton imgBtnCall;
        private ImageButton imgBtnMessage;
        private ImageButton imgBtnEmail;
        private ImageButton imgBtnWhatsApp;
        private ImageView imgIcon;

        public CardViewDesignObjectHandler(@NonNull View itemView) {
            super(itemView);
            twPersonName = itemView.findViewById(R.id.twPersonName);
            twPhoneNumber = itemView.findViewById(R.id.twPhoneNumber);
            twEmail = itemView.findViewById(R.id.twEmail);
            imgBtnCall = itemView.findViewById(R.id.imgBtnCall);
            imgBtnMessage = itemView.findViewById(R.id.imgBtnMessage);
            imgBtnEmail = itemView.findViewById(R.id.imgBtnEmail);
            imgBtnWhatsApp = itemView.findViewById(R.id.imgBtnWhatsApp);
            imgIcon = itemView.findViewById(R.id.imgIcon);
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

        holder.twPhoneNumber.setText(person.getPhone_number());

        holder.twEmail.setText(person.getPerson_email());

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
                                Snackbar.make(holder.imgIcon,"Kişi Silinsin Mi ? ",Snackbar.LENGTH_SHORT)
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
                String phoneCallNumber = holder.twPhoneNumber.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel: " + phoneCallNumber));
                context.startActivity(intent );
            }
        });

        holder.imgBtnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SendSMS.class);
                intent.putExtra("person_object", person);
                context.startActivity(intent);

            }
        });

        holder.imgBtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SendEmail.class);
                intent.putExtra("person_object", person);
                context.startActivity(intent);
            }
        });

        holder.imgBtnWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SendWhatsAppMessage.class);
                intent.putExtra("person_object",person);
                context.startActivity(intent);
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
        builder.setTitle("Kişi Güncelle");
        builder.setView(design);

        builder.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
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

        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();

    }

}
