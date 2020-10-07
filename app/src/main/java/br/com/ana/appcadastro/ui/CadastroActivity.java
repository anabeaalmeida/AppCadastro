package br.com.ana.appcadastro.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import br.com.ana.appcadastro.R;
import br.com.ana.appcadastro.model.Usuario;
import br.com.ana.appcadastro.model.UsuarioViewModel;

public class CadastroActivity extends AppCompatActivity {

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;
    private EditText editTextNome;
    private EditText editTextCPF;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private JSONArray Hawk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        editTextNome = findViewById(R.id.editTextNome);
        editTextCPF = findViewById(R.id.editTextCpf);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);


        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario usuario) {
                updateView(usuario);
            }
        });
    }

    private void updateView(Usuario usuario){
        if(usuario != null && usuario.getId() > 0){
            usuarioCorrente = usuario;
            editTextNome.setText(usuario.getNome());
            editTextCPF.setText(usuario.getCpf());
            editTextEmail.setText(usuario.getEmail());
            editTextSenha.setText(usuario.getSenha());
        }
    }

    public void salvar(View view) throws JSONException {

        if(usuarioCorrente == null){
            usuarioCorrente = new Usuario();
        }
        usuarioCorrente.setNome(editTextNome.getText().toString());
        usuarioCorrente.setCpf(editTextCPF.getText().toString());
        usuarioCorrente.setEmail(editTextEmail.getText().toString());
        usuarioCorrente.setSenha(editTextSenha.getText().toString());
        usuarioViewModel.insert(usuarioCorrente);
        Toast.makeText(this, "Usuário salvo com sucesso", Toast.LENGTH_SHORT).show();
        Hawk.put(Integer.parseInt("tem cadastro"),true);
        finish();
    }
}
