package com.example.talit.projetotcc.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.adapters.MessageListAdapter;
import com.example.talit.projetotcc.connectionAPI.ChatBoxRequisicao;
import com.example.talit.projetotcc.logicalView.Message;
import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatBot extends AppCompatActivity {

    public static RecyclerView mMessageRecycler;
    public static MessageListAdapter mMessageAdapter;
    public static ArrayList<Message>msg = new ArrayList<Message>();
    private EditText edtMsg;
    public static Context context;
    private ImageView btnSend;
    private ImageView btnVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat_bot);
        context = this;
        mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
        btnSend = (ImageButton)findViewById(R.id.button_chatbox_send);
        btnVoice = (ImageButton)findViewById(R.id.button_voice);
        edtMsg = (EditText)findViewById(R.id.edittext_chatbox);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.chat_bot_title));

        mMessageRecycler.setHasFixedSize(true);
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMessageRecycler.setLayoutManager(llm);

        mMessageAdapter = new MessageListAdapter(ChatBot.this, msg);
        mMessageRecycler.setAdapter(mMessageAdapter);



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg.add(new Message(edtMsg.getText().toString(),"USUARIO"));
                ChatBot.mMessageAdapter.notifyDataSetChanged();
                ChatBoxRequisicao conn = new ChatBoxRequisicao(null);
                conn.execute(edtMsg.getText().toString().replace("^","").replace("~","").replace("´","").replace(".","").replace(",","").replace(" ","%20").replace("ç","c"));
                edtMsg.setText(null);
            }
        });
        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
    }

    public void promptSpeechInput() {//

        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.txt_diga_algo));

        try {
            startActivityForResult(i, 100);

        } catch (ActivityNotFoundException e) {
            //Toast.makeText(ChatBot.this, getResources().getString(R.string.txt_sem_permissao), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> voiceText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String voice = voiceText.get(0);
                    edtMsg.setText(voice);

                    //Toast.makeText(SearchViewPaginaInicial.this, voice, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChatBot.this, CentralAtendmento.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:break;
        }
        return true;
    }
}
