package br.com.ana.appcadastro.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import br.com.ana.appcadastro.R;
import br.com.ana.appcadastro.model.Usuario;
import br.com.ana.appcadastro.model.UsuarioViewModel;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewNovoCadastro;
    private Button buttonLogin;
    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;
    private EditText editTextEmail;
    private EditText editTextSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Hawk.init(this).build();

        editTextEmail = findViewById(R.id.editTextUsuario);
        editTextSenha = findViewById(R.id.editTextSenha);

        textViewNovoCadastro = findViewById(R.id.textViewNovoCadastro);
        buttonLogin = findViewById(R.id.buttonLogin);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario usuario) {
                updateUsuario(usuario);
            }
        });

    }

    private void updateUsuario(Usuario usuario){
        usuarioCorrente = usuario;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (Hawk.contains("tem_cadastro")){
            if (Hawk.get("tem_cadastro")){
                habilitarLogin();
            }else{
                desabilitarLogin();
            }
        }else{
            desabilitarLogin();
        }
    }

    private void habilitarLogin(){
        textViewNovoCadastro.setVisibility(View.GONE);
        buttonLogin.setEnabled(true);
        buttonLogin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    private void desabilitarLogin(){
        textViewNovoCadastro.setVisibility(View.VISIBLE);
        buttonLogin.setEnabled(false);
        buttonLogin.setBackgroundColor(getResources().getColor(R.color.Gray));
    }

    public void novoCadastro(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void login(View view) {

        if(usuarioCorrente != null){
            if(usuarioCorrente.getEmail().equalsIgnoreCase(editTextEmail.getText().toString())
                    && usuarioCorrente.getSenha().equals(editTextSenha.getText().toString())){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Usuário ou senha inválidos!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
